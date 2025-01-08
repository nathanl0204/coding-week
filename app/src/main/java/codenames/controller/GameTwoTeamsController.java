package codenames.controller;

import java.util.Optional;
import codenames.structure.CardType;
import codenames.structure.GameTwoTeams;
import codenames.structure.PlayableCard;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import java.io.File;
import javafx.application.Platform;

public class GameTwoTeamsController extends GameController {

    public GameTwoTeamsController() {
        super();
    }

    public GameTwoTeamsController(GameTwoTeams game) {
        super(game);
    }

    private void handleTimerComplete() {
        if (game.getRemainingCardGuess() > 0) {
            // Time's up, switch turns
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Temps écoulé");
                alert.setHeaderText(null);
                alert.setContentText("Le temps est écoulé ! Au tour de l'équipe suivante.");
                alert.showAndWait();

                game.setRemainingCardGuess(0);
                handleChangeTurn();
            });
        }
    }

    @FXML
    public void initialize() {
        imageView.setImage(((GameTwoTeams) game).getQRCode());
        super.initialize();
    }

    public void processCardSelection(PlayableCard card) {
        switch (card.getCardType()) {
            case Black:
                loadingBarController.stop();
                game.wrongGuess(CardType.Black, loadingBarController.getElapsedSeconds());
                game.ends();
                if (game.isBlueTurn())
                    info.setText("Red Team win");
                else
                    info.setText("Blue Team win");
                button.setVisible(false);
                alertWrongGuest("Black Card selected, you lose");
                displayStatistics();
                break;
            case White:
                loadingBarController.stop();
                game.wrongGuess(CardType.White, loadingBarController.getElapsedSeconds());
                break;
            case Blue:
                if (game.isBlueTurn()) {
                    game.correctGuess();
                } else {
                    loadingBarController.stop();
                    game.wrongGuess(CardType.Blue, loadingBarController.getElapsedSeconds());
                    alertWrongGuest("Blue Card selected, your turn ends");
                }
                break;
            case Red:
                if (!game.isBlueTurn()) {
                    game.correctGuess();
                } else {
                    loadingBarController.stop();
                    game.wrongGuess(CardType.Red, loadingBarController.getElapsedSeconds());
                    alertWrongGuest("Red Card selected, your turn ends");
                }
                break;
            default:
                break;
        }

        if (game.getNumberOfRemainingCardsToFind() == 0 && game.isOnGoing()) {
            game.ends();
            displayStatistics();
            if (game.isBlueTurn())
                info.setText("Blue Team win");
            else
                info.setText("Red Team win");
            button.setVisible(false);
        } else if (game.getRemainingCardGuess() == 0 && game.isOnGoing()) {
            loadingBarController.stop();
            game.majStatTemps(loadingBarController.getElapsedSeconds());
            if (game.isBlueTurn())
                info.setText("Red turn");
            else
                info.setText("Blue turn");
        }
    }

    @FXML
    public void handleButton() {
        if (game.getRemainingCardGuess() == 0) {
            askForNumberGuess().ifPresent(n -> {
                int N = Integer.parseInt(n);
                if (N > 0 && N <= game.getNumberOfOpponentRemainingCardsToFind())
                    game.changeTurn(N);
                if (game.getNumberOfRemainingCardsToFind() == 0 && game.isOnGoing()) {
                    game.ends();
                    displayStatistics();

                    button.setVisible(false);
                }
                game.notifyObservers();
            });
        }
    }

    @FXML
    public void handleChangeTurn() {

        if (game.getRemainingCardGuess() == 0) {
            askForNumberGuess().ifPresent(n -> {
                int N = Integer.parseInt(n);
                if (N > 0 && N <= game.getNumberOfOpponentRemainingCardsToFind())
                    game.changeTurn(N);
                else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Wrong Number Of Cards");
                    alert.setContentText("Please enter a number less than the number of cards you have left to guess");
                    alert.showAndWait();
                }

                if (game.isBlueTurn())
                    info.setText("Blue turn");
                else
                    info.setText("Red turn");
            });

            if (loadingBarController != null) {
                loadingBarController.start(5);
            }
        }
    }

    private Optional<String> askForNumberGuess() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Number of guess");
        dialog.setHeaderText("Enter the number of guess");
        dialog.setContentText("Number :");

        return dialog.showAndWait();
    }

    public void startNewGame() {
        // Implement new game logic
    }

    public void loadGame(File file) {
        // Implement load game logic
    }

    public void saveGame(File file) {
        // Implement save game logic
    }
}
