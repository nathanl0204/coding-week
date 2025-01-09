package codenames.structure.AI;

import codenames.observers.*;
import codenames.structure.*;

import java.util.*;

public class EasyOpponentAI extends OpponentAI {

    public EasyOpponentAI(GameView gameView) {
        super(gameView);
    }

    @Override
    public void play() {
        Game game = gameView.getGame();
        Deck deck = game.getDeck();

        List<PlayableCard> remainingRedCards = deck.getRemainingRedCards();
        if (remainingRedCards.isEmpty()) {
            return;
        }

        List<PlayableCard> remainingCards = deck.getRemainingCards();
        int cardsToGuess = random.nextInt(remainingRedCards.size()) + 1;
        game.setRemainingCardGuess(cardsToGuess);

        Collections.shuffle(remainingCards);
        List<PlayableCard> selectedCards = remainingCards.subList(0, Math.min(cardsToGuess, remainingCards.size()));

        for (PlayableCard card : selectedCards) {
            gameView.processCardSelection(card);
        }
    }
}
