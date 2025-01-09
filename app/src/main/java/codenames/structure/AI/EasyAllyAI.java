package codenames.structure.AI;

import codenames.controller.GameSinglePlayerController;
import codenames.structure.CardType;

import java.util.Map;

public class EasyAllyAI extends AllyAI {

    public EasyAllyAI(GameSinglePlayerController gameController) {
        super(gameController);
    }

    protected Map.Entry<String, Integer> getBestHint(CardType teamColor) {
        return getSortedHintScores(teamColor).get(0);
    }

    @Override
    public void play() {
        Map.Entry<String, Integer> bestHint = getBestHint(CardType.Red);

        gameController.alertAllyAIHint(bestHint.getKey(), bestHint.getValue());
    }
}
