package codenames.structure;

import java.util.stream.Collectors;

import java.util.*;

public interface Deck {

    public int size();

    public List<PlayableCard> getCard();

    default List<PlayableCard> getRemainingCards() {
        return getCard().stream()
                .filter(card -> !card.isGuessed())
                .collect(Collectors.toList());
    }

    default List<PlayableCard> getRemainingCardsByType(CardType type) {
        return getCard().stream()
                .filter(card -> !card.isGuessed() && card.getCardType() == type)
                .collect(Collectors.toList());
    }

    default List<PlayableCard> getRemainingRedCards() {
        return getRemainingCardsByType(CardType.Red);
    }

    default List<PlayableCard> getRemainingBlueCards() {
        return getRemainingCardsByType(CardType.Blue);
    }
}
