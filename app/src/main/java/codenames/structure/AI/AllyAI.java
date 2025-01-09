package codenames.structure.AI;

import codenames.controller.GameSinglePlayerController;
import codenames.structure.*;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AllyAI extends AI {
    public GameSinglePlayerController gameController;

    public AllyAI(GameSinglePlayerController gameControllerSinglePlayer) {
        super(gameControllerSinglePlayer);
        this.gameController = gameControllerSinglePlayer;
    }

    protected Map<String, Integer> getHintScores(CardType teamColor) {
        Game game = gameController.getGame();
        DeckSinglePlayer deck = (DeckSinglePlayer) game.getDeck();
        CardType opponentColor = (teamColor == CardType.Blue) ? CardType.Red : CardType.Blue;

        List<PlayableCardWithHints> cardsWithHints = deck.getCardsWithHints();

        Map<String, Integer> hintScores = new HashMap<>();

        // for each card, we define the score as the amount of the related cards of that
        // team's color (i.e the amound of cards that have this hint) minus the amount
        // of the related cards of the opponent's color
        for (PlayableCardWithHints card : cardsWithHints) {
            if (card.isGuessed()) {
                continue;
            }

            for (String hint : card.getHints()) {
                hintScores.putIfAbsent(hint, 0);

                if (card.getCardType() == teamColor) {
                    hintScores.put(hint, hintScores.get(hint) + 1);
                } else if (card.getCardType() == opponentColor) {
                    hintScores.put(hint, hintScores.get(hint) - 1);
                }
            }
        }

        return hintScores;
    }

    protected List<Map.Entry<String, Integer>> getSortedHintScores(CardType teamColor) {
        Map<String, Integer> scores = getHintScores(teamColor);
        return scores.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());
    }
}
