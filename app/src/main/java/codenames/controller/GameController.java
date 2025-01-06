package codenames.controller;

import codenames.structure.Game;
import codenames.structure.ImageCard;
import codenames.structure.TextCard;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
                            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                                label.setEffect( card.getColorAdjust());
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

    @FXML 
    public void handleChangeTurn(){
        game.changeTurn();
    }




}
