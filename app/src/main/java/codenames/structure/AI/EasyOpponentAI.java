package codenames.structure.AI;

import java.util.Collections;
import java.util.List;

import codenames.observers.*;
import codenames.structure.*;

public class EasyOpponentAI extends OpponentAI {

    public EasyOpponentAI(GameView gameView) {
        super(gameView);
    public EasyOpponentAI(GameView gameView) {
        super(gameView);
    }

    @Override
    public void play() {
        Game game = gameView.getGame();
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

        playNextCard(getCardsUntilFirstNonRed(selectedCards), 0);

    }
}
