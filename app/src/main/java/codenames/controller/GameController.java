package codenames.controller;

import java.util.Optional;

import codenames.structure.Card;
import codenames.structure.Game;
import codenames.structure.ImageCard;
import codenames.structure.TextCard;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class GameController {

    @FXML GridPane gridPane;
    @FXML Button button;
    @FXML Label info;

    private Game game;

    public GameController(){}
    
    public GameController(Game game){
        this.game = game;
    }

    @FXML 
    public void initialize() {
        info.setText("Click above to start");
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
                            if (mouseEvent.getButton() == MouseButton.PRIMARY && !card.isGuessed() && game.isOnGoing()) {
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
                            if (mouseEvent.getButton() == MouseButton.PRIMARY && !card.isGuessed() && game.isOnGoing()) {
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
    }

    private void processCardSelection(Card card) {
        switch (card.getCardType()) {
            case Black:
                alertWrongGuest("Black Card selected, you lose");
                game.wrongGuess();
                game.ends();
                if (game.isBlueTurn()) info.setText("Red Team win");
                else info.setText("Blue Team win");
                button.setVisible(false);
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
        if (game.getRemainingCardGuess() == 0){
            if (game.isBlueTurn()) info.setText("Red turn");
            else info.setText("Blue turn");
        }




        if (game.getNumberOfRemainingCardsToFind() == 0){
            game.ends();
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
                if (N > 0 && N <= game.getNumberOfOpponentRemainingCardsToFind()) game.changeTurn(N);
                else {
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
