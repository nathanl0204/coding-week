package codenames.observers;

import codenames.structure.Game;
import codenames.structure.Statistics;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StatisticsView implements Observer {
    @FXML
    private Label blueAverageTimeLabel;
    @FXML
    private Label blueAverageCardsLabel;
    @FXML
    private Label blueNumberOfErrorsLabel;
    @FXML
    private Label blueNumberOfGuessLabel;
    @FXML
    private Label blueNumberOfTurnsLabel;
    @FXML
    private Label blueRemainingCardsLabel;

    @FXML
    private Label redAverageTimeLabel;
    @FXML
    private Label redAverageCardsLabel;
    @FXML
    private Label redNumberOfErrorsLabel;
    @FXML
    private Label redNumberOfGuessLabel;
    @FXML
    private Label redNumberOfTurnsLabel;
    @FXML
    private Label redRemainingCardsLabel;

    private Game game;

    public StatisticsView(Game game) {
        this.game = game;
        this.game.addObserver(this);
    }

    @Override
    public void react() {
        Statistics blueTeamStatistics = this.game.getBlueStatistics();
        Statistics redTeamStatistics = this.game.getRedStatistics();

        blueAverageTimeLabel.setText("Average Time Per Round: " + String.format("%.2f", blueTeamStatistics.getAverageTimePerTurn()) + " seconds");
        blueAverageCardsLabel.setText("Average Cards Per Round: " + String.format("%.2f", blueTeamStatistics.getAverageCardsPerTurn()));
        blueNumberOfErrorsLabel.setText("Errors: " + blueTeamStatistics.getNumberOfErrors());
        blueNumberOfGuessLabel.setText("Correct Guesses: " + blueTeamStatistics.getNumberOfCorrectGuess());
        blueNumberOfTurnsLabel.setText("Turns: " + blueTeamStatistics.getNumberOfTurns());
        blueRemainingCardsLabel.setText("Remaining Cards: " + blueTeamStatistics.getNumberOfRemainingCardsToFind());

        redAverageTimeLabel.setText("Average Time Per Round: " + String.format("%.2f", redTeamStatistics.getAverageTimePerTurn()) + " seconds");
        redAverageCardsLabel.setText("Average Cards Per Round: " + String.format("%.2f", redTeamStatistics.getAverageCardsPerTurn()));

        redNumberOfErrorsLabel.setText("Errors: " + redTeamStatistics.getNumberOfErrors());
        redNumberOfGuessLabel.setText("Correct Guesses: " + redTeamStatistics.getNumberOfCorrectGuess());
        redNumberOfTurnsLabel.setText("Turns: " + redTeamStatistics.getNumberOfTurns());
        redRemainingCardsLabel.setText("Remaining Cards: " + redTeamStatistics.getNumberOfRemainingCardsToFind());
    }
}
