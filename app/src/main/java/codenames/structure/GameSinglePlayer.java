package codenames.structure;

public class GameSinglePlayer extends Game {
    private DeckSinglePlayer deck;

    public GameSinglePlayer(DeckSinglePlayer deck, int cols, int numberOfBlueCard, int numberOfRedCard) {
        super(cols, numberOfBlueCard, numberOfRedCard);
        this.deck = deck;
    }

    public DeckSinglePlayer getDeck() {
        return deck;
    }

}
