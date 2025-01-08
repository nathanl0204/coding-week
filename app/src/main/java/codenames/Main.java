package codenames;

import codenames.controller.GameDuoController;
import codenames.controller.MenuBarController;
import codenames.structure.CardType;
import codenames.structure.Game;
import codenames.structure.ListCard;
import codenames.structure.TextCard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Game game = testGame();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Game.fxml"));
        loader.setControllerFactory(iC->new GameDuoController(game));
        BorderPane root = loader.load();

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("CodeName");
        primaryStage.show();
    }

    private Game testGame(){
        ListCard listCard = new ListCard();

        String[] texts = {
                "Apple", "Banana", "Cherry", "Dog", "Elephant", "Football",
                "Guitar", "Helicopter", "Igloo", "Jacket", "Kangaroo", "Lemon",
                "Monkey", "Notebook", "Octopus", "Penguin", "Quilt", "Rocket",
                "Sunflower", "Tiger", "Umbrella", "Violin", "Whale", "Xylophone",
                "Yacht"
        };

        CardType[] cardTypes = {
                CardType.Red, CardType.Red, CardType.Blue, CardType.Black, CardType.White, CardType.Blue,
                CardType.Blue, CardType.Red, CardType.White, CardType.Red, CardType.Blue, CardType.Black,
                CardType.White, CardType.Red, CardType.Blue, CardType.Blue, CardType.Red, CardType.White,
                CardType.Blue, CardType.Red, CardType.Red, CardType.White, CardType.Blue, CardType.Red,
                CardType.Blue
        };

        int i = 0;

        for (String text : texts) {
            listCard.addCard(new TextCard(cardTypes[i],text));
            i++;
        }

        return new Game( 5, listCard,9,9);

    }
}

