package codenames.structure.AI;

import codenames.observers.GameSinglePlayerView;
import codenames.structure.CardType;

import java.util.Map;
import java.util.List;

public class MediumAllyAI extends AllyAI {

    public MediumAllyAI(GameSinglePlayerView gameView) {
        super(gameView);
    }

    protected Map.Entry<String, Integer> getMedianHint(CardType teamColor) {
        List<Map.Entry<String, Integer>> sorted = getSortedHintScores(teamColor);
        return sorted.get(sorted.size() / 2);
    }

    @Override
    public void play() {
        Map.Entry<String, Integer> bestHint = getMedianHint(CardType.Blue);

        gameView.alertAllyAIHint(bestHint.getKey(), bestHint.getValue());

    }
}
