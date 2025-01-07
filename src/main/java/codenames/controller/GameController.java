package codenames.controller;

import java.util.Optional;

import codenames.structure.Card;
import codenames.structure.Game;
import codenames.structure.ImageCard;
import codenames.structure.TextCard;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import java.io.File;
import javafx.application.Platform;

public class GameController {

    @FXML private GridPane gridPane;
    @FXML private Button button;
    @FXML private Label info;
    @FXML private Label timerLabel;
    @FXML private VBox timerContainer;

    private Game game;
    private LoadingBar loadingBar;
    private boolean blitzMode = false;
    private static final int DEFAULT_TURN_TIME = 60; // 60 seconds per turn

    public GameController(){}
    
    public GameController(Game game){
        this.game = game;
    }

    @FXML 
    public void initialize() {
        // Initialize loading bar
        loadingBar = new LoadingBar(200, 20);
        timerContainer.getChildren().add(loadingBar);

        // Initialize timer label
        timerLabel = new Label("Temps restant : 60s");
        timerContainer.getChildren().add(timerLabel);

        info.setText("Blue turn = "+game.isBlueTurn());
        int cols = game.getCols();
        final int[] currentPos = {0, 0};

        game.getListCard().getCards().forEach(card -> {

            StackPane stackPane = new StackPane();

            if (card instanceof TextCard) {
                Label label = new Label(((TextCard) card).getText());
                label.setTextFill(card.getColor());
                
                gridPane.add(stackPane, currentPos[1], currentPos[0]);
                stackPane.getChildren().add(label);

                label.setOnMouseClicked(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        if (event instanceof MouseEvent) {
                            MouseEvent mouseEvent = (MouseEvent) event;
                            if (mouseEvent.getButton() == MouseButton.PRIMARY && game.getRemainingCardGuess() > 0 && !card.isGuessed()) {
                                Rectangle transparency = new Rectangle(label.getWidth(),label.getHeight());
                                transparency.setFill(card.getColor().deriveColor(0,1,1,0.5));
                                stackPane.getChildren().add(transparency);
                                card.guessed();
                                processCardSelection(card);
                            }
                        }
                    }
                });
                
            } else {
                ImageView imgView = new ImageView(new Image(((ImageCard) card).getUrl()));

                gridPane.add(stackPane, currentPos[1], currentPos[0]);
                stackPane.getChildren().add(imgView);

                imgView.setOnMouseClicked(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        if (event instanceof MouseEvent) {
                            MouseEvent mouseEvent = (MouseEvent) event;
                            if (mouseEvent.getButton() == MouseButton.PRIMARY && game.getRemainingCardGuess() > 0 && !card.isGuessed()) {
                                Rectangle transparency = new Rectangle(imgView.getFitWidth(),imgView.getFitHeight());
                                transparency.setFill(card.getColor().deriveColor(0,1,1,0.5));
                                stackPane.getChildren().add(transparency);
                                card.guessed();
                                processCardSelection(card);
                            }
                        }
                    }
                });
                
                
            }   
            
            

            currentPos[1]++; 
            if (currentPos[1] >= cols) {
                currentPos[1] = 0;
                currentPos[0]++;  
            }
        });

        if (blitzMode) {
            startTimer();
        }
    }

    private void startTimer() {
        loadingBar.reset();
        loadingBar.start(DEFAULT_TURN_TIME);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    timerLabel.setText("Temps restant : " + loadingBar.getRemainingSeconds() + "s");

                    if (loadingBar.isComplete()) {
                        handleTimerComplete();
                    }
                })
        );
        timeline.setCycleCount(DEFAULT_TURN_TIME);
        timeline.play();
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

    public void setBlitzMode(boolean enabled) {
        this.blitzMode = enabled;
        if (enabled && game != null) {
            startTimer();
        } else {
            loadingBar.stop();
            timerLabel.setText("");
        }
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

    private void processCardSelection(Card card) {
        switch (card.getCardType()) {
            case Black:
                alertWrongGuest("Black Card selected, you lose");
                game.wrongGuess();
                game.calculStat();
                break;
            case White:
                game.wrongGuess();
                alertWrongGuest("White Card selected, your turn ends");
                break;
            case Blue:
                if (game.isBlueTurn()) {
                    game.correctGuess();
                } else {
                    game.wrongGuess();
                    alertWrongGuest("Red Card selected, your turn ends");
                }
                break;
            case Red:
                if (!game.isBlueTurn()) {
                    game.correctGuess();
                } else {
                    game.wrongGuess();
                    alertWrongGuest("Blue Card selected, your turn ends");
                }
                break;
            default:
                break;
        }
        if (game.getNumberOfRemainingCardsToFind() == 0){
            if (game.isBlueTurn()) info.setText("Blue Team win");
            else info.setText("Red Team win");
            button.setVisible(false);
        }
    }

    private void alertWrongGuest(String message){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Wrong Card");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML 
    public void handleChangeTurn(){
        
        if (game.getRemainingCardGuess() == 0){
            askForNumberGuess().ifPresent( n -> {
                int N = Integer.parseInt(n);
                if (N > 0 && N <= game.getNumberOfOpponentRemainingCardsToFind()) {
                    game.changeTurn(N);
                    if (blitzMode) {
                        startTimer();
                    }
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Wrong Number Of Cards");
                    alert.setContentText("Please enter a number less than the number of cards you have left to guess");
                    alert.showAndWait();
                }

                if (game.isBlueTurn()) info.setText("Blue turn");
                else info.setText("Red turn");
            });
            
        }
    }

    private Optional<String> askForNumberGuess(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Number of guess");
        dialog.setHeaderText("Enter the number of guess");
        dialog.setContentText("Number :");

        return dialog.showAndWait();
    }



}
