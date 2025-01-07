package codenames.structure;

public class TextCard extends Card {
    
    private String text;

    public TextCard(CardType cardType, String text){
        super(cardType);
        this.text = text;
    }

    public String getText(){
        return text;
    }
}   
