package codenames.structure;

import java.util.List;
import java.util.stream.Collectors;

public class DeckSinglePlayer implements Deck {
    private List<PlayableCardWithHints> cards;

    public List<PlayableCard> getCard(){
        return cards.stream()
            .map(PlayableCardWithHints::getPlayableCard)
            .collect(Collectors.toList());
    }

    public int size(){
        return cards.size();
    }
}
