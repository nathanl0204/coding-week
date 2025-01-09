package codenames.observers;

import java.util.Optional;
import java.util.Random;

import codenames.structure.CardType;
import codenames.structure.GameTwoTeams;
import codenames.structure.PlayableCard;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.io.File;
import javafx.application.Platform;

public class GameTwoTeamsView extends GameView {

    public GameTwoTeamsView(GameTwoTeams game) {
        super(game);
        this.game.addObserver(this);
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
            
            String path = null;
            Random rand = new Random();

            switch (card.getCardType()) {
                case Black:
                    path = String.valueOf(getClass().getResource("/assassin.jpg"));
                    loadingBarView.stop();
                    game.wrongGuess(CardType.Black, loadingBarView.getElapsedSeconds());
                    game.ends();
                    button.setVisible(false);
                    displayStatistics();
                    break;
                case White:
                    if (rand.nextBoolean()) {
                        path = String.valueOf(getClass().getResource("/White1.jpg"));
                    } else {
                        path = String.valueOf(getClass().getResource("/White2.jpg"));
                    }
                    loadingBarView.stop();
                    game.wrongGuess(CardType.White, loadingBarView.getElapsedSeconds());
                    break;
                case Blue:
                    if (rand.nextBoolean()) {
                        path = String.valueOf(getClass().getResource("/Blue1.jpg"));
                    } else {
                        path = String.valueOf(getClass().getResource("/Blue2.jpg"));
                    }
                    if (game.isBlueTurn()) {
                        game.correctGuess();
                    } else {
                        loadingBarView.stop();
                        game.wrongGuess(CardType.Blue, loadingBarView.getElapsedSeconds());
                    }
                    break;
                case Red:
                    if (rand.nextBoolean()) {
                        path = String.valueOf(getClass().getResource("/Red1.jpg"));
                    } else {
                        path = String.valueOf(getClass().getResource("/Red2.jpg"));
                    }
                    if (!game.isBlueTurn()) {
                        game.correctGuess();
                    } else {
                        loadingBarView.stop();
                        game.wrongGuess(CardType.Red, loadingBarView.getElapsedSeconds());
                    }
                    break;
                default:
                    break;
            }   

            ImageView cover = new ImageView(new Image(path));
            cover.fitHeightProperty().bind(card.getStackPane().heightProperty());
            cover.fitWidthProperty().bind(card.getStackPane().widthProperty());
            StackPane.setAlignment(cover, Pos.CENTER);
            card.getStackPane().getChildren().add(cover);
            card.guessed();
        }

        

        if (game.getNumberOfRemainingCardsToFind() == 0 && game.isOnGoing()) {
            game.ends();
            displayStatistics();
            button.setVisible(false);
        } else if (game.getRemainingCardGuess() == 0 && game.isOnGoing()) {
            loadingBarView.stop();
            game.majStatTemps(loadingBarView.getElapsedSeconds());
        }
        game.notifyObservers();
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
                loadingBarView.start();
            });
        }
        game.notifyObservers();
    }

    private Optional<String> askForNumberGuess() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Number of guess");
        dialog.setHeaderText("Enter the number of guess");
        dialog.setContentText("Number :");

        return dialog.showAndWait();
    }

    @Override
    public void react() {}
}
