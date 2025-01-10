package codenames.structure;

import codenames.observers.*;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public abstract class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int id;

    protected Boolean onGoing;
    protected Statistics blueStat, redStat;
    protected Boolean blueTurn;
    protected int remainingCardGuess;
    protected int cols;
    private boolean blitzMode;
    protected transient ArrayList<Observer> observers;

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

    public void majStatTemps(double time) {
        if (blueTurn)
            blueStat.addTimePerTurn(time);
        else
            redStat.addTimePerTurn(time);
    }

    public void wrongGuess(CardType cardType, double temps) {
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

    public static class GameState implements Serializable {
        private final int id;
        private final Boolean onGoing;
        private final Statistics blueStat;
        private final Statistics redStat;
        private final Boolean blueTurn;
        private final int remainingCardGuess;
        private final int cols;
        private final boolean blitzMode;
        private final List<PlayableCard> cards;
        private final String gameType; // "TwoTeams" ou "SinglePlayer"

        public GameState(Game game) {
            this.id = game.id;
            this.onGoing = game.onGoing;
            this.blueStat = game.blueStat;
            this.redStat = game.redStat;
            this.blueTurn = game.blueTurn;
            this.remainingCardGuess = game.remainingCardGuess;
            this.cols = game.cols;
            this.blitzMode = game.blitzMode;
            this.cards = game.getDeck().getCards();
            this.gameType = (game instanceof GameTwoTeams) ? "TwoTeams" : "SinglePlayer";
        }

        public int getId() { return id; }
        public Boolean getOnGoing() { return onGoing; }
        public Statistics getBlueStat() { return blueStat; }
        public Statistics getRedStat() { return redStat; }
        public Boolean getBlueTurn() { return blueTurn; }
        public int getRemainingCardGuess() { return remainingCardGuess; }
        public int getCols() { return cols; }
        public boolean isBlitzMode() { return blitzMode; }
        public List<PlayableCard> getCards() { return cards; }
        public String getGameType() { return gameType; }
    }

    public void saveGame(File file) throws IOException {
        GameState state = new GameState(this);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT)
                .setPrettyPrinting()
                .registerTypeAdapter(Image.class, new TypeAdapter<Image>() {
                    @Override
                    public void write(JsonWriter out, Image image) throws IOException {
                        out.nullValue();
                    }

                    @Override
                    public Image read(JsonReader in) throws IOException {
                        in.nextNull();
                        return null;
                    }
                })
                .create();

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(state, writer);
        }
    }

    public static Game loadGame(File file) throws IOException {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT | java.lang.reflect.Modifier.STATIC)
                .registerTypeAdapter(Image.class, new TypeAdapter<Image>() {
                    @Override
                    public void write(JsonWriter out, Image image) throws IOException {
                        out.nullValue();
                    }

                    @Override
                    public Image read(JsonReader in) throws IOException {
                        in.nextNull();
                        return null;
                    }
                })
                .registerTypeAdapter(Card.class, new CardTypeAdapter())
                .registerTypeAdapter(PlayableCard.class, new PlayableCardAdapter())
                .setPrettyPrinting()
                .create();

        try (FileReader reader = new FileReader(file)) {
            GameState state = gson.fromJson(reader, GameState.class);

            Game game;
            if (state.getGameType().equals("TwoTeams")) {
                DeckTwoTeams deck = new DeckTwoTeams(state.getCards());
                game = new GameTwoTeams(deck, state.getCols(),
                        state.getBlueStat().getNumberOfRemainingCardsToFind(),
                        state.getRedStat().getNumberOfRemainingCardsToFind());
            } else {
                List<PlayableCardWithHints> cardsWithHints = state.getCards().stream()
                        .map(card -> new PlayableCardWithHints(card.getCard(), card.getCardType(),
                                Arrays.asList("hint1", "hint2")))
                        .collect(Collectors.toList());
                DeckSinglePlayer deck = new DeckSinglePlayer(cardsWithHints);
                game = new GameSinglePlayer(deck, state.getCols(),
                        state.getBlueStat().getNumberOfRemainingCardsToFind(),
                        state.getRedStat().getNumberOfRemainingCardsToFind());
            }

            game.id = state.getId();
            game.onGoing = state.getOnGoing();
            game.blueTurn = state.getBlueTurn();
            game.remainingCardGuess = state.getRemainingCardGuess();
            game.setBlitzMode(state.isBlitzMode());
            copyStatistics(state.getBlueStat(), game.blueStat);
            copyStatistics(state.getRedStat(), game.redStat);

            game.getDeck().getCards().forEach(PlayableCard::recreateStackPane);

            return game;
        }
    }

    private static void copyStatistics(Statistics source, Statistics target) {
        target.addTimePerTurn(source.getAverageTimePerTurn() * source.getNumberOfTurns());
        for (int i = 0; i < source.getNumberOfErrors(); i++) {
            target.incrNumberOfErrors();
        }
        for (int i = 0; i < source.getNumberOfTurns(); i++) {
            target.incrNumberOfTurns();
        }
        for (int i = 0; i < source.getNumberOfCorrectGuess(); i++) {
            target.incrNumberOfCorrectGuess();
        }
    }
}
