package codenames.structure;

import java.util.ArrayList;
import java.util.List;

public class ThemedDeck {
    private List<Card> cards;
    private List<String> hints;

    public ThemedDeck(){
        cards = new ArrayList<>();
        hints = new ArrayList<>();
    }

    public void clear(){
        cards.clear();
        hints.clear();
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public void addHint(String hint){
        hints.add(hint);
    }

    public List<Card> getCards(){
        return cards;
    }

    public List<String> getHints(){
        return hints;
    }

    public Boolean removeCard(String string){
        return cards.removeIf(card -> card.getString().equals(string));
    }
    
    public void removeHint(String hint){
        hints.remove(hint);
    }
}
