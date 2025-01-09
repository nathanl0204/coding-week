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
    @FXML
    private Label info_zone;
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

            if (game.isBlueTurn()) {
                this.info_zone.setText("Blue turn");
            }
            else {
                this.info_zone.setText("Red turn");
            }
        }
        else {
            this.points.setText(String.valueOf(game.getRedStatistics().getNumberOfCorrectGuess()));

            if (game.isBlueTurn()) {
                if (game.isOnGoing()) {
                    this.info_zone.setText("Game running...");
                }
                else {
                    this.info_zone.setText("Game over");
                }
            }
        }
    }
}
