package codenames.structure;

import java.util.ArrayList;
import java.util.List;

public class GameSinglePlayer extends Game {
    private List<PlayableCardWithHints> deck;

    public  GameSinglePlayer(List<PlayableCardWithHints> deck, int cols, int numberOfBlueCard, int numberOfRedCard){
        super(cols, numberOfBlueCard, numberOfRedCard);
        this.deck = deck;
    }

    public List<PlayableCard> getDeck(){
        List<PlayableCard> playableCards = new ArrayList<>();
        
        for (PlayableCardWithHints cardWithHints : deck) {
            playableCards.add(cardWithHints);  
        }

        return playableCards;
    }
}
