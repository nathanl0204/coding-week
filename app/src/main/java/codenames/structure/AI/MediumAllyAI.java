package codenames.structure.AI;

import codenames.controller.GameSinglePlayerController;
import codenames.structure.CardType;

import java.util.Map;
import java.util.List;

public class MediumAllyAI extends AllyAI {

    public MediumAllyAI(GameSinglePlayerController gameController) {
        super(gameController);
    }

    protected Map.Entry<String, Integer> getMedianHint(CardType teamColor) {
        List<Map.Entry<String, Integer>> sorted = getSortedHintScores(teamColor);
        return sorted.get(sorted.size() / 2);
    }

    @Override
    public void play() {
        Map.Entry<String, Integer> bestHint = getMedianHint(CardType.Blue);

        gameController.alertAllyAIHint(bestHint.getKey(), bestHint.getValue());

    }
}
