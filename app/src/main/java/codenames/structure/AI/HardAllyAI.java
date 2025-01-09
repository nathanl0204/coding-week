package codenames.structure.AI;

import codenames.controller.GameSinglePlayerController;
import codenames.structure.CardType;

import java.util.Map;
import java.util.List;

public class HardAllyAI extends AllyAI {

    public HardAllyAI(GameSinglePlayerController gameController) {
        super(gameController);
    }

    protected Map.Entry<String, Integer> getWorstHint(CardType teamColor) {
        List<Map.Entry<String, Integer>> sorted = getSortedHintScores(teamColor);
        return sorted.get(sorted.size() - 1);
    }

    @Override
    public void play() {
        Map.Entry<String, Integer> bestHint = getWorstHint(CardType.Red);

        gameController.alertAllyAIHint(bestHint.getKey(), bestHint.getValue());
    }
}
