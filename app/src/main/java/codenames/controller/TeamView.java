package codenames.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class TeamView implements Observer {
    @FXML
    private Label points;
    @FXML
    private Label teamPicture;
    @FXML
    private ListView spymastersList;
    @FXML
    private ListView spyList;
    private boolean hasWon;

    public void react() {

    }
}
