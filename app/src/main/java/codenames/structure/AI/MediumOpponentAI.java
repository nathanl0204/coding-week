package codenames.structure.AI;

import codenames.controller.*;
import codenames.structure.*;

import java.util.*;

public class MediumOpponentAI extends OpponentAI {

    public MediumOpponentAI(GameController gameController) {
        super(gameController);
    }

    @Override
    public void play() {
        Game game = gameController.getGame();
        Deck deck = game.getDeck();

        List<PlayableCard> remainingRedCards = deck.getRemainingRedCards();
        if (remainingRedCards.isEmpty()) {
            return;
        }

        List<PlayableCard> remainingNonRedCards = new ArrayList<>(deck.getRemainingCards());
        remainingNonRedCards.removeAll(remainingRedCards);

        int cardsToGuess = random.nextInt(remainingRedCards.size()) + 1;
        game.setRemainingCardGuess(cardsToGuess);

        List<PlayableCard> selectedCards = new ArrayList<>();
        for (int i = 0; i < cardsToGuess && (!remainingRedCards.isEmpty() || !remainingNonRedCards.isEmpty()); i++) {
            boolean pickRedCard = random.nextBoolean();

            if (pickRedCard && !remainingRedCards.isEmpty()) {
                System.out.println("The AI is going to pick a red card");
                selectedCards.add(remainingRedCards.remove(random.nextInt(remainingRedCards.size())));
            } else if (!remainingNonRedCards.isEmpty()) {
                System.out.println("The AI is going to pick a non-red card");
                selectedCards.add(remainingNonRedCards.remove(random.nextInt(remainingNonRedCards.size())));
            }
        }

        for (PlayableCard card : selectedCards) {
            gameController.processCardSelection(card);
        }
    }
}
