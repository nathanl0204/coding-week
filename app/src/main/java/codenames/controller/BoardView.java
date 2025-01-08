package codenames.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import codenames.structure.*;

public class BoardView implements Observer {
    @FXML
    private GridPane board;
    @FXML
    private Label turns;

    private Game game;

    public BoardView(Game game) {
        this.game = game;
        game.addObserver(this);
    }

    public void react() {

    }

    @FXML
    public void initialize() {
        
    }
}
