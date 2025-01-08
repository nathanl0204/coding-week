package codenames.structure;

import java.util.List;

public class PlayableCardWithHints extends PlayableCard {
    private List<String> hints;

    public PlayableCardWithHints(Card card,CardType cardType,List<String> hints){
        super(card,cardType);
        this.hints = hints;

    }
}
