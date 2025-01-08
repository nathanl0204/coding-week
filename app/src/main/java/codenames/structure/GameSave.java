package codenames.structure;

import java.io.Serializable;
import java.util.List;

public class GameSave implements Serializable {
    private int id;
    private Boolean onGoing;
    private Statistics blueStat;
    private Statistics redStat;
    private List<CardSave> cards;
    private Boolean blueTurn;
    private int remainingCardGuess;
    private int cols;

    // Constructeur pour créer une sauvegarde à partir d'un jeu
    public GameSave(Game game) {
        this.id = game.getId();
        this.onGoing = game.isOnGoing();
        this.blueStat = game.getBlueStatistics();
        this.redStat = game.getRedStatistics();
        this.blueTurn = game.isBlueTurn();
        this.remainingCardGuess = game.getRemainingCardGuess();
        this.cols = game.getCols();

        // Conversion des cartes en CardSave
        this.cards = game.getListCard().getCards().stream()
                .map(card -> new CardSave(card))
                .collect(java.util.stream.Collectors.toList());
    }

    // Méthode pour reconstruire un jeu à partir d'une sauvegarde
    public Game toGame() {
        ListCard listCard = new ListCard();
        cards.forEach(cardSave -> listCard.addCard(cardSave.toCard()));

        Game game = new Game(cols, listCard,
                blueStat.getNumberOfRemainingCardsToFind() + blueStat.getNumberOfCorrectGuess(),
                redStat.getNumberOfRemainingCardsToFind() + redStat.getNumberOfCorrectGuess());

        game.setRemainingCardGuess(remainingCardGuess);
        if (!blueTurn) {
            game.changeTurn(remainingCardGuess);
        }
        if (!onGoing) {
            game.ends();
        }

        return game;
    }
}
