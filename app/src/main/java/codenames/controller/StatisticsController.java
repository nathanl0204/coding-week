package codenames.controller;

import codenames.structure.Statistics;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StatisticsController {
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

    private Statistics blueTeamStatistics;
    private Statistics redTeamStatistics;

    public StatisticsController() {
    }

    public StatisticsController(Statistics blueTeamStatistics, Statistics redTeamStatistics) {
        this.blueTeamStatistics = blueTeamStatistics;
        this.redTeamStatistics = redTeamStatistics;
    }

    @FXML
    public void initialize() {
        blueAverageTimeLabel
                .setText("Average Time Per Round: " + blueTeamStatistics.getAverageTimePerTurn() + " seconds");
        blueAverageCardsLabel.setText("Average Cards Per Round: " + blueTeamStatistics.getAverageCardsPerTurn());
        blueNumberOfErrorsLabel.setText("Errors: " + blueTeamStatistics.getNumberOfErrors());
        blueNumberOfGuessLabel.setText("Correct Guesses: " + blueTeamStatistics.getNumberOfCorrectGuess());
        blueNumberOfTurnsLabel.setText("Turns: " + blueTeamStatistics.getNumberOfTurns());
        blueRemainingCardsLabel.setText("Remaining Cards: " + blueTeamStatistics.getNumberOfRemainingCardsToFind());

        redAverageTimeLabel
                .setText("Average Time Per Round: " + redTeamStatistics.getAverageTimePerTurn() + " seconds");
        redAverageCardsLabel.setText("Average Cards Per Round: " + redTeamStatistics.getAverageCardsPerTurn());
        redNumberOfErrorsLabel.setText("Errors: " + redTeamStatistics.getNumberOfErrors());
        redNumberOfGuessLabel.setText("Correct Guesses: " + redTeamStatistics.getNumberOfCorrectGuess());
        redNumberOfTurnsLabel.setText("Turns: " + redTeamStatistics.getNumberOfTurns());
        redRemainingCardsLabel.setText("Remaining Cards: " + redTeamStatistics.getNumberOfRemainingCardsToFind());
    }
}
