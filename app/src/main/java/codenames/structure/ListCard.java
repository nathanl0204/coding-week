package codenames.structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sun.javafx.css.StyleCacheEntry.Key.hashCode;

public class ListCard implements Serializable {
    private int id;
    private List<Card> cards;
    //private List<String> indice;

    public ListCard(){
        this.id = hashCode();
        this.cards = new ArrayList<Card>();
    }

    public int getId(){
        return id;
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public List<Card> getCards(){
        return cards;
    }

    private ListCard(List<Card> cards){
        this.cards = cards;
    }

    public ListCard extract(int n){
        List<Card> copy = new ArrayList<Card>(cards);
        Collections.shuffle(copy);
        return new ListCard(copy.subList(0, n));
    }

}
