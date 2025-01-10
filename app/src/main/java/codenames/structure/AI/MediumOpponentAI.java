package codenames.structure.AI;

import codenames.observers.*;
import codenames.structure.*;

import java.util.*;

public class MediumOpponentAI extends OpponentAI {

    public MediumOpponentAI(GameView gameView) {
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

        List<PlayableCard> remainingNonRedCards = new ArrayList<>(deck.getRemainingCards());
        remainingNonRedCards.removeAll(remainingRedCards);

        int cardsToGuess = random.nextInt(remainingRedCards.size()) + 1;
        game.setRemainingCardGuess(cardsToGuess);

        List<PlayableCard> selectedCards = new ArrayList<>();
        for (int i = 0; i < cardsToGuess && (!remainingRedCards.isEmpty() || !remainingNonRedCards.isEmpty()); i++) {
            boolean pickRedCard = random.nextBoolean();

            if (pickRedCard && !remainingRedCards.isEmpty()) {
                selectedCards.add(remainingRedCards.remove(random.nextInt(remainingRedCards.size())));
            } else if (!remainingNonRedCards.isEmpty()) {
                selectedCards.add(remainingNonRedCards.remove(random.nextInt(remainingNonRedCards.size())));
            }
        }

        for (PlayableCard card : selectedCards) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Sleep interrupted");
            }
            gameView.processCardSelection(card);
        }
    }
}
