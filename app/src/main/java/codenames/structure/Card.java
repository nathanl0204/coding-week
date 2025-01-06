package codenames.structure;

public abstract class Card {
    
    private CardType cardType;

    public Card(CardType cardType){
        this.cardType = cardType;
    }

    public CardType getCardType(){
        return cardType;
    }
}
