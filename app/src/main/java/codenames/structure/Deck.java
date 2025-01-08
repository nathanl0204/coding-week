package codenames.structure;

import java.util.List;

public interface Deck {
    public List<PlayableCard> getCard();
    public int size();
}
