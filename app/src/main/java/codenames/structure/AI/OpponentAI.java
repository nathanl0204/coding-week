package codenames.structure.AI;

import codenames.observers.*;
import codenames.structure.*;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.*;

public abstract class OpponentAI extends AI {
    protected Random random;

    public OpponentAI(GameView gameView) {
        super(gameView);
    public OpponentAI(GameView gameView) {
        super(gameView);
        this.random = new Random();
    }

    public abstract void play();

    protected void playNextCard(List<PlayableCard> selectedCards, int index) {
        if (index >= selectedCards.size()) {
            return;
        }

        PlayableCard card = selectedCards.get(index);

        PauseTransition pause = new PauseTransition(Duration.millis(500));
        pause.setOnFinished(e -> {
            gameView.processCardSelection(card);
            if (gameView.getGame().getRemainingCardGuess() > 0)
                playNextCard(selectedCards, index + 1);
        });

        pause.play();
    }

    public List<PlayableCard> getCardsUntilFirstNonRed(List<PlayableCard> cards) {
        if (cards == null || cards.isEmpty()) {
            return new ArrayList<>();
        }

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getCardType() != CardType.Red) {
                return new ArrayList<>(cards.subList(0, i + 1));
            }
        }

        return new ArrayList<>(cards);
    }
}
