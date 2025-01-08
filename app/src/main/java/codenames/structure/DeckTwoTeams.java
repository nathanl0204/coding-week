package codenames.structure;

import java.util.List;

public class DeckTwoTeams implements Deck {
    private List<PlayableCard> cards;

    public DeckTwoTeams(List<PlayableCard> listCard) {
        cards = listCard;
    }

    public List<PlayableCard> getCard(){
        return cards;
    }

    public int size(){
        return cards.size();
    }

}
