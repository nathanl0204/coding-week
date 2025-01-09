package codenames.structure;

import codenames.observers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int id;

    protected Boolean onGoing;
    protected Statistics blueStat, redStat;
    protected Boolean blueTurn;
    protected int remainingCardGuess;
    protected int cols;
    private boolean blitzMode;
    protected ArrayList<Observer> observers;

    public Game(int cols, int numberOfBlueCard, int numberOfRedCard) {
        this.cols = cols;
        blueStat = new Statistics(numberOfBlueCard);
        redStat = new Statistics(numberOfRedCard);
        blueTurn = false;
        onGoing = true;
        this.observers = new ArrayList<>();
    }

    public void addObserver(Observer obs) {
        this.observers.add(obs);
    }

    public void notifyObservers() {
        for (int i = 0; i < this.observers.size(); i++) {
            this.observers.get(i).react();
        }
    }

    public void setBlitzMode(boolean blitzMode) {
        this.blitzMode = blitzMode;
    }

    public boolean getBlitzMode() {
        return this.blitzMode;
    }

    public int getCols() {
        return cols;
    }

    public Statistics getBlueStatistics() {
        return blueStat;
    }

    public Statistics getRedStatistics() {
        return redStat;
    }

    public int getRemainingCardGuess() {
        return remainingCardGuess;
    }

    public void setRemainingCardGuess(int remainingCardGuess) {
        this.remainingCardGuess = remainingCardGuess;
    }

    public int getId() {
        return id;
    }

    public abstract Deck getDeck();

    public Boolean isBlueTurn() {
        return blueTurn;
    }

    public CardType getColorTurn() {
        if (blueTurn)
            return CardType.Blue;
        else
            return CardType.Red;
    }

    public void changeTurn(int remainingCardGuess) {
        blueTurn = !blueTurn;
        if (blueTurn)
            blueStat.incrNumberOfTurns();
        else
            redStat.incrNumberOfTurns();
        this.remainingCardGuess = remainingCardGuess;
    }

    public void ends() {
        onGoing = false;
    }

    public Boolean isOnGoing() {
        return onGoing;
    }

    public void correctGuess() {
        if (blueTurn) {
            blueStat.incrNumberOfCorrectGuess();
            blueStat.decrNumberOfRemainingCardsToFind();
        } else {
            redStat.incrNumberOfCorrectGuess();
            redStat.decrNumberOfRemainingCardsToFind();
        }
        remainingCardGuess--;

    }

    public void wrongGuess(CardType cardType) {
        remainingCardGuess = 0;

        if (blueTurn) {
            blueStat.incrNumberOfErrors();
            if (cardType == CardType.Red)
                redStat.decrNumberOfRemainingCardsToFind();
        } else {
            redStat.incrNumberOfErrors();
            if (cardType == CardType.Blue)
                blueStat.decrNumberOfRemainingCardsToFind();
        }
    }

    public void majStatTemps(double time) {
        if (blueTurn)
            blueStat.addTimePerTurn(time);
        else
            redStat.addTimePerTurn(time);
    }

    public void wrongGuess(CardType cardType, double temps) {
        System.out.println("" + temps);
        remainingCardGuess = 0;
        if (blueTurn) {
            blueStat.incrNumberOfErrors();
            blueStat.addTimePerTurn(temps);
            if (cardType == CardType.Red)
                redStat.decrNumberOfRemainingCardsToFind();
        } else {
            redStat.incrNumberOfErrors();
            redStat.addTimePerTurn(temps);
            if (cardType == CardType.Blue)
                blueStat.decrNumberOfRemainingCardsToFind();
        }
    }

    public int getNumberOfOpponentRemainingCardsToFind() {
        if (blueTurn)
            return redStat.getNumberOfRemainingCardsToFind();
        else
            return blueStat.getNumberOfRemainingCardsToFind();
    }

    public int getNumberOfRemainingCardsToFind() {
        if (blueTurn)
            return blueStat.getNumberOfRemainingCardsToFind();
        else
            return redStat.getNumberOfRemainingCardsToFind();
    }

    private static class GameState implements Serializable {
        private static final long serialVersionUID = 1L;

        int id;
        Boolean onGoing;
        Statistics blueStat;
        Statistics redStat;
        Boolean blueTurn;
        int remainingCardGuess;
        int cols;
        boolean blitzMode;
        List<PlayableCard> cards;
        String gameType; // "TwoTeams" ou "SinglePlayer"

        GameState(Game game) {
            this.id = game.id;
            this.onGoing = game.onGoing;
            this.blueStat = game.blueStat;
            this.redStat = game.redStat;
            this.blueTurn = game.blueTurn;
            this.remainingCardGuess = game.remainingCardGuess;
            this.cols = game.cols;
            this.blitzMode = game.blitzMode;
            this.cards = game.getDeck().getCard();
            this.gameType = (game instanceof GameTwoTeams) ? "TwoTeams" : "SinglePlayer";
        }
    }

    public void saveGame(File file) throws IOException {
        GameState state = new GameState(this);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(state, writer);
        }
    }

    public static Game loadGame(File file) throws IOException {
        Gson gson = new GsonBuilder().create();

        try (FileReader reader = new FileReader(file)) {
            GameState state = gson.fromJson(reader, GameState.class);

            // Créer le bon type de jeu
            Game game;
            if (state.gameType.equals("TwoTeams")) {
                DeckTwoTeams deck = new DeckTwoTeams(state.cards);
                game = new GameTwoTeams(deck, state.cols,
                        state.blueStat.getNumberOfRemainingCardsToFind(),
                        state.redStat.getNumberOfRemainingCardsToFind());
            } else {
                // Conversion des cartes normales en cartes avec hints pour le mode solo
                List<PlayableCardWithHints> cardsWithHints = state.cards.stream()
                        .map(card -> new PlayableCardWithHints(card.getCard(), card.getCardType(),
                                Arrays.asList("hint1", "hint2"))) // Vous devrez adapter ceci selon vos besoins
                        .collect(Collectors.toList());
                DeckSinglePlayer deck = new DeckSinglePlayer(cardsWithHints);
                game = new GameSinglePlayer(deck, state.cols,
                        state.blueStat.getNumberOfRemainingCardsToFind(),
                        state.redStat.getNumberOfRemainingCardsToFind());
            }

            // Restaurer l'état
            game.id = state.id;
            game.onGoing = state.onGoing;
            game.blueStat = state.blueStat;
            game.redStat = state.redStat;
            game.blueTurn = state.blueTurn;
            game.remainingCardGuess = state.remainingCardGuess;
            game.setBlitzMode(state.blitzMode);

            // Recréer les composants visuels
            game.getDeck().getCard().forEach(PlayableCard::recreateStackPane);

            return game;
        }
    }
}
