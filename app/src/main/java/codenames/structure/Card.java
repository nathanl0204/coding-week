package codenames.structure;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;

public abstract class Card {
    
    private CardType cardType;
    private Boolean guessed;

    public Card(CardType cardType){
        this.cardType = cardType;
        this.guessed = false;
    }

    public void guessed(){
        guessed = true;
    }

    public Boolean isGuessed(){
        return guessed;
    }

    public ColorAdjust getColorAdjust() {
        ColorAdjust colorAdjust = new ColorAdjust();
        
        switch (cardType) {
            case Black:
                colorAdjust.setBrightness(-0.5);
                break;
            case White:
                colorAdjust.setBrightness(0.5); 
                colorAdjust.setSaturation(-1);
                break;
            case Red:
                colorAdjust.setHue(0.8);     
                break;
            case Blue:
                colorAdjust.setHue(-0.8);
                break;
            default:
                break;
        }

        return colorAdjust;
    }

    public CardType getCardType(){
        return cardType;
    }

    public Color getColor(){
        switch (cardType) {
            case Black:
                return Color.BLACK;
            case White:
                return Color.WHITE;
            case Red:
                return Color.RED;
            case Blue:
                return Color.BLUE;
            default:
                return null;
        }
    }
}
