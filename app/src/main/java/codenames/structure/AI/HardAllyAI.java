package codenames.structure.AI;

import codenames.observers.GameSinglePlayerView;
import codenames.structure.CardType;

import java.util.Map;
import java.util.List;

public class HardAllyAI extends AllyAI {

    public HardAllyAI(GameSinglePlayerView gameView) {
        super(gameView);
    }

    protected Map.Entry<String, Integer> getWorstHint(CardType teamColor) {
        List<Map.Entry<String, Integer>> sorted = getSortedHintScores(teamColor);
        return sorted.get(sorted.size() - 1);
    }

    @Override
    public void play() {
        Map.Entry<String, Integer> bestHint = getWorstHint(CardType.Blue);

        gameView.alertAllyAIHint(bestHint.getKey(), countMatchingCardsForHint(bestHint.getKey(), CardType.Blue));
    }
}
