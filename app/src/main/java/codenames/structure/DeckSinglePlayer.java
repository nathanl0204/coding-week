package codenames.structure;

import java.util.List;
import java.util.stream.Collectors;

public class DeckSinglePlayer implements Deck {
    private List<PlayableCardWithHints> cards;

    public DeckSinglePlayer(List<PlayableCardWithHints> listCard) {
        cards = listCard;
    }

    public List<PlayableCard> getCards() {
        return cards.stream()
                .map(PlayableCardWithHints::getPlayableCard)
                .collect(Collectors.toList());
    }

    public List<PlayableCardWithHints> getCardsWithHints() {
        return cards;
    }

    public int size() {
        return cards.size();
    }
}
