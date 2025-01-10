package codenames.structure.AI;

import codenames.observers.GameSinglePlayerView;
import codenames.structure.CardType;

import java.util.Map;
import java.util.List;

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

        List<Map.Entry<String, Integer>> hintScores = getSortedHintScores(CardType.Blue);
        // prints the list of score
        for (Map.Entry<String, Integer> hintScore : hintScores) {
            System.out.println(hintScore.getKey() + " " + hintScore.getValue());
        }
        gameView.alertAllyAIHint(bestHint.getKey(), bestHint.getValue());
    }
}
