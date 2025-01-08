package codenames.structure;

import java.util.List;

public class PlayableCardWithHints {
    private PlayableCard playableCard;
    private List<String> hints;

    public PlayableCardWithHints(PlayableCard card, List<String> hints) {
        playableCard = card;
        this.hints = hints;
    }

    public PlayableCard getPlayableCard() {
        return playableCard;
    }

    public List<String> getHints() {
        return hints;
    }
}
