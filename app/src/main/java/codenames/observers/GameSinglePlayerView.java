package codenames.observers;

import codenames.structure.AI.*;

import codenames.structure.CardType;
import codenames.structure.GameSinglePlayer;
import codenames.structure.PlayableCard;
import javafx.fxml.FXML;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GameSinglePlayerView extends GameView {
    private AI AllyAI;
    private AI OpponentAI;

    public GameSinglePlayerView(GameSinglePlayer game) {
        super(game);
        this.game.addObserver(this);
    }

    public void setAllyAI(AI allyAI) {
        this.AllyAI = allyAI;
    }

    public void setOpponentAI(AI opponentAI) {
        this.OpponentAI = opponentAI;
    }

    public AI getAllyAI() {
        return AllyAI;
    }

    public AI getOpponentAI() {
        return OpponentAI;
    }

    public void alertAllyAIHint(String hint, int number) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Ally");
        alert.setContentText(
                "Your spymaster tells you the following hint: " + hint + " corresponding to " + number + " cards");
        alert.showAndWait();
    }

    public void processCardSelection(PlayableCard card) {
        if (game.isBlueTurn() && game.isOnGoing() && game.getRemainingCardGuess() > 0 && !card.isGuessed()) {

            Rectangle transparency = new Rectangle(card.getStackPane().getWidth(), card.getStackPane().getHeight());
            transparency.setFill(card.getColor().deriveColor(0, 1, 1, 0.5));
            card.getStackPane().getChildren().add(transparency);
            card.guessed();

            switch (card.getCardType()) {
                case Black:
                    alertWrongGuest("Black Card selected, you lose");
                    game.wrongGuess(CardType.Black);
                    game.ends();
                    button.setVisible(false);
                    displayStatistics();
                    break;
                case White:
                    game.wrongGuess(CardType.White);
                    alertWrongGuest("White Card selected, your turn ends");
                    break;
                case Blue:
                    game.correctGuess();
                    break;
                case Red:
                    game.wrongGuess(CardType.Red);
                    alertWrongGuest("Red Card selected, your turn ends");
                    break;
                default:
                    break;
            }
        }

        if (!game.isBlueTurn() && game.isOnGoing() && game.getRemainingCardGuess() > 0 && !card.isGuessed()) {

            Rectangle transparency = new Rectangle(card.getStackPane().getWidth(), card.getStackPane().getHeight());
            transparency.setFill(card.getColor().deriveColor(0, 1, 1, 0.5));
            card.getStackPane().getChildren().add(transparency);
            card.guessed();

            switch (card.getCardType()) {
                case Black:
                    game.wrongGuess(CardType.Black);
                    game.ends();
                    alertOpponentAIMove("Your opponent chose the black card, you win !");
                    alertWrongGuest("The AI chose the black card, blue team wins !");
                    button.setVisible(false);
                    displayStatistics();
                    break;
                case White:
                    game.wrongGuess(CardType.White);
                    alertOpponentAIMove("Your opponent chose a white card");
                    break;
                case Blue:
                    alertOpponentAIMove("Your opponent chose a blue card");
                    game.wrongGuess(CardType.Blue);
                    break;
                case Red:
                    alertOpponentAIMove("Your opponent chose a red card");
                    game.correctGuess();
                    break;
                default:
                    break;
            }
        }
        if (game.getRemainingCardGuess() == 0 && game.isOnGoing()) {
            if (game.isBlueTurn()) {
                game.changeTurn(0);
                OpponentAI.play();
            } else
                info.setText("Blue turn");
        } else if (game.getNumberOfRemainingCardsToFind() == 0 && game.isOnGoing()) {
            game.ends();
            displayStatistics();
            button.setVisible(false);
        }
        game.notifyObservers();
    }

    @FXML
    public void handleButton() {
        if (game.getRemainingCardGuess() == 0) {
            AllyAI.play();
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
            });
        }
        game.notifyObservers();
    }

    private Optional<String> askForNumberGuess() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Number of guess");
        dialog.setHeaderText("Enter the number of guess");
        dialog.setContentText("Number :");

        return dialog.showAndWait();
    }
}
