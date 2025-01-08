package codenames.structure;

import javafx.scene.paint.Color;

public class PlayableCard {

    private Card card;
    private CardType cardType;
    private Boolean guessed;

    public PlayableCard(Card card, CardType cardType){
        this.card = card;
        this.cardType = cardType;
        this.guessed = false;
    }

    public Card getCard(){
        return card;
    }

    public void guessed(){
        guessed = true;
    }

    public Boolean isGuessed(){
        return guessed;
    }

    public CardType getCardType(){
        return cardType;
    }

    public Color getColor(){
        switch (cardType) {
            case Black:
                return Color.BLACK;
            case White:
                return Color.GREY;
            case Red:
                return Color.RED;
            case Blue:
                return Color.BLUE;
            default:
                return null;
        }
    }

    public char getColorCode() {
        switch (cardType) {
            case Black:
                return 'a';
            case White:
                return 'w';
            case Red:
                return 'r';
            case Blue:
                return 'b';
            default:
                return 'w';
        }
    }
}
