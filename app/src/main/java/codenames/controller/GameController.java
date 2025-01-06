package codenames.controller;

import java.util.Optional;

import codenames.structure.Game;
import codenames.structure.ImageCard;
import codenames.structure.TextCard;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public abstract class GameController {

    @FXML GridPane gridPane;

    private Game game;

    public GameController(){}
    
    public GameController(Game game){
        this.game = game;
    }

    @FXML 
    public void initialize(){

        ObservableList<Node> childrens = gridPane.getChildren();

        game.getListCard().getCards().forEach(card -> {

            if (card instanceof TextCard){
                Label label = new Label( ((TextCard) card).getText() );
                label.setOnMouseClicked( new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        if (event instanceof MouseEvent) {
                            MouseEvent mouseEvent = (MouseEvent) event;
                            if (mouseEvent.getButton() == MouseButton.PRIMARY && game.getRemainingCardGuess() > 0) {

                                label.setEffect( card.getColorAdjust());

                                switch (card.getCardType()) {
                                    case Black:
                                        handleWrongGuest("Black Card selected, you lose");
                                        game.ends();
                                        break;
                                    case White:
                                        handleWrongGuest("White Card selected, your turn ends");
                                        break;
                                    case Blue:
                                        if (game.isBlueTurn()){
                                            //MAJ des stats bleu
                                            game.decrRemainingCardGuess();
                                        }
                                        else {
                                            handleWrongGuest("Red Card selected, your turn ends");
                                        }
                                        break;
                                    case Red:
                                        if (!game.isBlueTurn()){
                                            game.decrRemainingCardGuess();
                                            //MAJ des stats rouge
                                        }
                                        else {
                                            handleWrongGuest("Blue Card selected, your turn ends");
                                        }
                                        break;
                                    default:
                                        break;
                                }

                            }
                        }
                    }
                });
                childrens.add(label);
            }

            else {
                ImageView imgView = new ImageView( new Image( ((ImageCard) card).getUrl() ) );
                imgView.setOnMouseClicked( new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        if (event instanceof MouseEvent) {
                            MouseEvent mouseEvent = (MouseEvent) event;
                            if (mouseEvent.getButton() == MouseButton.PRIMARY) {

                                imgView.setEffect( card.getColorAdjust());















                            }
                        }
                    }
                });
                childrens.add(imgView);
            }

        });
        
    }

    private void handleWrongGuest(String message){
        game.setRemainingCardGuess(0);
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
