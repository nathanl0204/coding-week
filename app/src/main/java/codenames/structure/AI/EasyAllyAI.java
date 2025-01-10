package codenames.structure.AI;

import codenames.observers.GameSinglePlayerView;
import codenames.structure.CardType;

import java.util.Map;

public class EasyAllyAI extends AllyAI {

    public EasyAllyAI(GameSinglePlayerView gameView) {
        super(gameView);
    }

    protected Map.Entry<String, Integer> getBestHint(CardType teamColor) {
        return getSortedHintScores(teamColor).get(0);
    }

    @Override
    public void play() {
        Map.Entry<String, Integer> bestHint = getBestHint(CardType.Blue);

        gameView.alertAllyAIHint(bestHint.getKey(), countMatchingCardsForHint(bestHint.getKey(), CardType.Blue));
    }
}
