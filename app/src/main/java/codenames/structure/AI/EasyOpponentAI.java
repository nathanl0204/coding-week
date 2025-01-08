package codenames.structure.AI;

import codenames.controller.*;
import codenames.structure.*;

import java.util.*;

public class EasyOpponentAI extends OpponentAI {
    private Random random;

    public EasyOpponentAI(GameController gameController) {
        super(gameController);
        this.random = new Random();
    }

    @Override
    public void play() {
        Game game = gameController.getGame();
        Deck deck = game.getDeck();

        List<PlayableCard> remainingRedCards = deck.getRemainingRedCards();
        if (remainingRedCards.isEmpty()) {
            return;
        }

        List<PlayableCard> remainingCards = deck.getRemainingCards();
        int cardsToGuess = random.nextInt(remainingRedCards.size()) + 1;
        game.setRemainingCardGuess(cardsToGuess);

        System.out.println("the ai will be guessing " + cardsToGuess + " times");

        Collections.shuffle(remainingCards);
        List<PlayableCard> selectedCards = remainingCards.subList(0, Math.min(cardsToGuess, remainingCards.size()));

        for (PlayableCard card : selectedCards) {
            gameController.processCardSelection(card);
        }
    }
}
