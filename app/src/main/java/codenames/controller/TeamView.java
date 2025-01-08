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
    private boolean isRedTeam;
    private Game game;

    public TeamView(Game game, boolean isRedTeam) {
        this.game = game;
        game.addObserver(this);
        this.isRedTeam = isRedTeam;
    }

    public void react() {
        if (this.isRedTeam) {
            this.points.setText(String.valueOf(game.getBlueStatistics().getNumberOfCorrectGuess()));
        }
        else {
            this.points.setText(String.valueOf(game.getRedStatistics().getNumberOfCorrectGuess()));
        }

    }
}
