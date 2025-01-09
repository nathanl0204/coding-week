package codenames.controller;

import java.util.Optional;
import java.util.Random;

import codenames.structure.CardType;
import codenames.structure.GameTwoTeams;
import codenames.structure.PlayableCard;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.shape.Rectangle;

import java.io.File;
import javafx.application.Platform;

public class GameTwoTeamsController extends GameController {

    public GameTwoTeamsController() {
        super();
    }

    public GameTwoTeamsController(GameTwoTeams game) {
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
            
            String path = null;
            Random rand = new Random();

            switch (card.getCardType()) {
                case Black:
                    path = String.valueOf(getClass().getResource("/assassin.jpg"));
                    loadingBarController.stop();
                    game.wrongGuess(CardType.Black, loadingBarController.getElapsedSeconds());
                    game.ends();
                    if (game.isBlueTurn())
                        info.setText("Red Team win");
                    else
                        info.setText("Blue Team win");
                    button.setVisible(false);
                    displayStatistics();
                    break;
                case White:
                    
                    
                    if (rand.nextBoolean()) {
                        path = String.valueOf(getClass().getResource("/White1.jpg"));
                    } else {
                        path = String.valueOf(getClass().getResource("/White2.jpg"));
                    }
                    loadingBarController.stop();
                    game.wrongGuess(CardType.White, loadingBarController.getElapsedSeconds());
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
                        loadingBarController.stop();
                        game.wrongGuess(CardType.Blue, loadingBarController.getElapsedSeconds());
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
                        loadingBarController.stop();
                        game.wrongGuess(CardType.Red, loadingBarController.getElapsedSeconds());
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
                loadingBarController.start();
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
}
