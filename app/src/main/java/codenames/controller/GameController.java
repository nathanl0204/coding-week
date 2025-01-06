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
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController {

    @FXML GridPane gridPane;

    private Game game;

    public GameController(){}
    
    public GameController(Game game){
        this.game = game;
    }

    @FXML 
public void initialize() {
    
    int cols = game.getCols();
    final int[] currentPos = {0, 0};

    game.getListCard().getCards().forEach(card -> {

        if (card instanceof TextCard) {
            Label label = new Label(((TextCard) card).getText());

            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(label);


            label.setOnMouseClicked(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if (event instanceof MouseEvent) {
                        MouseEvent mouseEvent = (MouseEvent) event;
                        if (mouseEvent.getButton() == MouseButton.PRIMARY && game.getRemainingCardGuess() > 0 && !card.isGuessed()) {
                            Rectangle transparency = new Rectangle(label.getWidth(), label.getHeight());
                            transparency.setFill(Color.RED.deriveColor(0, 1, 1, 0.5));
                            stackPane.getChildren().add(transparency);
                            card.guessed();
                            processCardSelection(card);
                        }
                    }
                }
            });
           
            gridPane.add(label, currentPos[1], currentPos[0]);

        } else {
            ImageView imgView = new ImageView(new Image(((ImageCard) card).getUrl()));
            imgView.setOnMouseClicked(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if (event instanceof MouseEvent) {
                        MouseEvent mouseEvent = (MouseEvent) event;
                        if (mouseEvent.getButton() == MouseButton.PRIMARY && game.getRemainingCardGuess() > 0 && !card.isGuessed()) {
                            
                            card.guessed();
                            processCardSelection(card);
                        }
                    }
                }
                
            });

            // Ajouter l'image dans le GridPane à la position (currentPos[1], currentPos[0])
            gridPane.add(imgView, currentPos[1], currentPos[0]);
        }

        // Mettre à jour la position pour la prochaine carte
        currentPos[1]++; // Incrementer la colonne
        if (currentPos[1] >= cols) {
            currentPos[1] = 0; // Réinitialiser la colonne
            currentPos[0]++;  // Passer à la ligne suivante
        }
    });
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
                if (N > 0) game.changeTurn(N);
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
