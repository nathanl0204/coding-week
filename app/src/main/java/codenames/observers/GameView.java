package codenames.observers;

import java.io.IOException;
import java.util.Random;

import codenames.structure.Game;
import codenames.structure.PlayableCard;
import codenames.structure.TextCard;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public abstract class GameView implements Observer {

    @FXML
    GridPane gridPane;
    @FXML
    Button button;
    @FXML
    ImageView imageView;

    protected Boolean blitzMode;
    protected Game game;

    @FXML
    protected Chronometer loadingBarView;

    public GameView(Game game) {
        this.game = game;
    }

    public void react() {
        gridPane.requestLayout();
        gridPane.layout();
    }

    public void setLoadingBarView(Chronometer loadingBarView) {
        this.loadingBarView = loadingBarView;
        blitzMode = true;
    }

    public Game getGame() {
        return game;
    }

    protected void handleTimerEnd() {
        if (game.isOnGoing()) {
            game.setRemainingCardGuess(0);
        }
    }

    @FXML
    public void initialize() {
        int cols = game.getCols();
        final int[] currentPos = { 0, 0 };

        game.getDeck().getCards().forEach(playableCard -> {

            StackPane stackPane = new StackPane();
            gridPane.add(stackPane, currentPos[1], currentPos[0]);

            if (playableCard.getCard() instanceof TextCard) {

                Label label = new Label(playableCard.getCard().getString());
                label.setPadding(new Insets(38, 0, 0, 0));

                ImageView background = new ImageView(new Image(String.valueOf(getClass().getResource("/card_back.jpg"))));

                stackPane.getChildren().addAll(background, label);
                playableCard.setStackPane(stackPane);

            } else {
                ImageView imgView = new ImageView(new Image(playableCard.getCard().getString()));

                imgView.setFitWidth(800 / cols);
                imgView.setPreserveRatio(true);

                stackPane.getChildren().add(imgView);
                playableCard.setStackPane(stackPane);
            }
            
            

            stackPane.setOnMouseClicked(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if (event instanceof MouseEvent) {
                        MouseEvent mouseEvent = (MouseEvent) event;
                        if (mouseEvent.getButton() == MouseButton.PRIMARY && !playableCard.isGuessed()
                                && game.isOnGoing()) {
                            processCardSelection(playableCard);
                        }
                    }
                }
            });

            

            currentPos[1]++;
            if (currentPos[1] >= cols) {
                currentPos[1] = 0;
                currentPos[0]++;
            }
        });
        
        gridPane.requestLayout();
        gridPane.layout();

        game.getDeck().getCards().forEach(playableCard -> {
            if (playableCard.isGuessed()){
                String path = null;
                Random rand = new Random();

                switch ( playableCard.getCardType()) {
                    case Black:
                        path = String.valueOf(getClass().getResource("/assassin.jpg"));
                        break;
                    case White:
                        if (rand.nextBoolean()) {
                            path = String.valueOf(getClass().getResource("/White1.jpg"));
                        } else {
                            path = String.valueOf(getClass().getResource("/White2.jpg"));
                        }
                        break;
                    case Blue:
                        if (rand.nextBoolean()) {
                            path = String.valueOf(getClass().getResource("/Blue1.jpg"));
                        } else {
                            path = String.valueOf(getClass().getResource("/Blue2.jpg"));
                        }
                        break;
                    case Red:
                        if (rand.nextBoolean()) {
                            path = String.valueOf(getClass().getResource("/Blue1.jpg"));
                        } else {
                            path = String.valueOf(getClass().getResource("/Blue2.jpg"));
                        }
                        break;
                    default:
                        break;
                }
                ImageView cover = new ImageView(new Image(path));
                cover.setFitWidth(playableCard.getStackPane().getWidth()); 
                cover.setFitHeight(playableCard.getStackPane().getHeight());
                StackPane.setAlignment(cover, Pos.CENTER);
                playableCard.getStackPane().getChildren().add(cover);
            }
        });

        game.notifyObservers();
    }

    public abstract void processCardSelection(PlayableCard card);

    protected void alertWrongGuest(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Wrong Card");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public abstract void handleButton();

    protected void displayStatistics() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Statistics.fxml"));

        StatisticsView controller = new StatisticsView(game);

        loader.setController(controller);

        HBox hbox = null;
        try {
            hbox = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(hbox);

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setWidth(500);
        newStage.setTitle("Statistics");
        newStage.show();
    }
}
