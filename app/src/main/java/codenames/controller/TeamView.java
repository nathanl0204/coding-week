package codenames.controller;

import codenames.structure.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;


public class TeamView implements Observer {
    @FXML
    private Label points;
    @FXML
    private ImageView teamPicture;
    @FXML
    private ListView spymastersList;
    @FXML
    private ListView spyList;
    private boolean hasWon;
    private Game game;

    public TeamView(Game game) {
        this.game = game;
        game.addObserver(this);
    }

    public void react() {

    }
}
