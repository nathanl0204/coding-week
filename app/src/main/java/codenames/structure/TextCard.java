package codenames.structure;

public class TextCard implements Card {

    private static final long serialVersionUID = 1L;
    private String text;

    public TextCard(String text){
        this.text = text;
    }

    public String getString(){
        return text;
    }
}   
