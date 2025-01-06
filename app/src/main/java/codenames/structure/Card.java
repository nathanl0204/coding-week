package codenames.structure;

import javafx.scene.effect.ColorAdjust;

public abstract class Card {
    
    private CardType cardType;

    public Card(CardType cardType){
        this.cardType = cardType;
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
}
