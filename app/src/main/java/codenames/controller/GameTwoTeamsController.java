package codenames.controller;

import java.util.Optional;

import codenames.structure.CardType;
import codenames.structure.GameTwoTeams;
import codenames.structure.PlayableCard;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

public class GameTwoTeamsController extends GameController {

    private LoadingBarController loadingBarController;

    public GameTwoTeamsController() {
        super();
    }

    public GameTwoTeamsController(GameTwoTeams game, LoadingBarController loadingBarController) {
        super(game);
        this.loadingBarController = loadingBarController;
    }

    @FXML
    public void initialize() {
        imageView.setImage(((GameTwoTeams) game).getQRCode());
        super.initialize();
    }

    public void processCardSelection(PlayableCard card) {
        switch (card.getCardType()) {
            case Black:
                alertWrongGuest("Black Card selected, you lose");
                game.wrongGuess(CardType.Black);
                game.ends();
                if (game.isBlueTurn())
                    info.setText("Red Team win");
                else
                    info.setText("Blue Team win");
                button.setVisible(false);
                displayStatistics();
                break;
            case White:
                game.wrongGuess(CardType.White);
                alertWrongGuest("White Card selected, your turn ends");
                break;
            case Blue:
                if (game.isBlueTurn()) {
                    game.correctGuess();
                } else {
                    game.wrongGuess(CardType.Blue);
                    alertWrongGuest("Blue Card selected, your turn ends");
                }
                break;
            case Red:
                if (!game.isBlueTurn()) {
                    game.correctGuess();
                } else {
                    game.wrongGuess(CardType.Red);
                    alertWrongGuest("Red Card selected, your turn ends");
                }
                break;
            default:
                break;
        }

        if (game.getNumberOfRemainingCardsToFind() == 0 && game.isOnGoing()) {
            game.ends();
            displayStatistics();
            if (game.isBlueTurn())
                info.setText("Blue Team win");
            else
                info.setText("Red Team win");
            button.setVisible(false);
        } else if (game.getRemainingCardGuess() == 0 && game.isOnGoing()) {
            if (game.isBlueTurn())
                info.setText("Red turn");
            else
                info.setText("Blue turn");
        }
    }

    @FXML
    public void handleButton() {
        if (game.getRemainingCardGuess() == 0) {
            askForNumberGuess().ifPresent(n -> {
                int N = Integer.parseInt(n);
                if (N > 0 && N <= game.getNumberOfOpponentRemainingCardsToFind())
                    game.changeTurn(N);
                else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Wrong Number Of Cards");
                    alert.setContentText("Please enter a number less than the number of cards you have left to guess");
                    alert.showAndWait();
                }

                if (game.isBlueTurn())
                    info.setText("Blue turn");
                else
                    info.setText("Red turn");
            });

        }
    }

    private Optional<String> askForNumberGuess() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Number of guess");
        dialog.setHeaderText("Enter the number of guess");
        dialog.setContentText("Number :");

        return dialog.showAndWait();
    }
}
