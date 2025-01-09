package codenames.observers;

import java.util.Optional;
import codenames.structure.CardType;
import codenames.structure.GameTwoTeams;
import codenames.structure.PlayableCard;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.shape.Rectangle;

import java.io.File;
import javafx.application.Platform;

public class GameTwoTeamsView extends GameView implements Observer {

    public GameTwoTeamsView(GameTwoTeams game) {
        super(game);
    }

    public void handleTimerComplete() {
        if (game.getRemainingCardGuess() > 0) {
            // Time's up, switch turns
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Temps écoulé");
                alert.setHeaderText(null);
                alert.setContentText("Le temps est écoulé ! Au tour de l'équipe suivante.");
                alert.showAndWait();

                game.setRemainingCardGuess(0);
                
            });
        }
    }

    @FXML
    public void initialize() {
        imageView.setImage(((GameTwoTeams) game).getQRCode());
        super.initialize();
    }

    public void processCardSelection(PlayableCard card) {

        if (game.getRemainingCardGuess() > 0 && !card.isGuessed()){
            
            Rectangle transparency = new Rectangle(card.getStackPane().getWidth(), card.getStackPane().getHeight());
            transparency.setFill(card.getColor().deriveColor(0, 1, 1, 0.5));
            card.getStackPane().getChildren().add(transparency);
            card.guessed();

            switch (card.getCardType()) {
                case Black:
                    loadingBarView.stop();
                    game.wrongGuess(CardType.Black, loadingBarView.getElapsedSeconds());
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
                    loadingBarView.stop();
                    game.wrongGuess(CardType.White, loadingBarView.getElapsedSeconds());
                    break;
                case Blue:
                    if (game.isBlueTurn()) {
                        game.correctGuess();
                    } else {
                        loadingBarView.stop();
                        game.wrongGuess(CardType.Blue, loadingBarView.getElapsedSeconds());
                        alertWrongGuest("Blue Card selected, your turn ends");
                    }
                    break;
                case Red:
                    if (!game.isBlueTurn()) {
                        game.correctGuess();
                    } else {
                        loadingBarView.stop();
                        game.wrongGuess(CardType.Red, loadingBarView.getElapsedSeconds());
                        alertWrongGuest("Red Card selected, your turn ends");
                    }
                    break;
                default:
                    break;
            }   
        }
        

        if (game.getNumberOfRemainingCardsToFind() == 0 && game.isOnGoing()) {
            game.ends();
            displayStatistics();
            button.setVisible(false);
        } else if (game.getRemainingCardGuess() == 0 && game.isOnGoing()) {
            loadingBarView.stop();
            game.majStatTemps(loadingBarView.getElapsedSeconds());
        }
    }

    @FXML
    public void handleButton() {
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
                if (game.getNumberOfRemainingCardsToFind() == 0 && game.isOnGoing()) {
                    game.ends();
                    displayStatistics();

                    button.setVisible(false);
                }
                loadingBarView.start(5);
                game.notifyObservers();
            });
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

    @Override
    public void react() {

    }
}
