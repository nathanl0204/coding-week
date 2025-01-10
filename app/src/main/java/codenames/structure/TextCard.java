package codenames.structure;

import java.io.Serializable;

public class TextCard implements Card, Serializable {

    private static final long serialVersionUID = 1L;
    private String text;

    public TextCard(String text){
        this.text = text;
    }

    public String getString(){
        return text;
    }
}   
