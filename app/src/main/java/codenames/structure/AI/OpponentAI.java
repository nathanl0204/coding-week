package codenames.structure.AI;

import codenames.observers.*;
import codenames.structure.PlayableCard;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.*;

public abstract class OpponentAI extends AI {
    protected Random random;

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
    
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> {
            gameView.processCardSelection(card);
            if (gameView.getGame().getRemainingCardGuess() > 0) playNextCard(selectedCards, index + 1);
        });
    
        pause.play();
    }
}
