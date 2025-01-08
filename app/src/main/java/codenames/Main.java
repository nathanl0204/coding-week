package codenames;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

import codenames.controller.GameTwoTeamsController;
=======
import codenames.controller.GameDuoController;
import codenames.controller.MenuBarController;
>>>>>>> main
import codenames.structure.CardType;
import codenames.structure.DeckTwoTeams;
import codenames.structure.Game;
import codenames.structure.GameTwoTeams;
import codenames.structure.PlayableCard;
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
<<<<<<< HEAD

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Game.fxml"));
        
        GameTwoTeams game = testGame();

        GameTwoTeamsController controller = new GameTwoTeamsController(game);

        loader.setController(controller);
=======
        Game game = testGame();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Game.fxml"));
        loader.setControllerFactory(iC->new GameDuoController(game));
        BorderPane root = loader.load();
>>>>>>> main

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("CodeName");
        primaryStage.show();
    }

    private GameTwoTeams testGame(){
        List<PlayableCard> listCard = new  ArrayList<PlayableCard>();

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
            listCard.add(new PlayableCard(new TextCard(text), cardTypes[i]));
            i++;
        }

        return new GameTwoTeams( new DeckTwoTeams(listCard),5, 9,9);

    }
}

