package codenames;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import codenames.controller.GameController;
import codenames.controller.GameTwoTeamsController;
import codenames.controller.LoadingBarController;
import codenames.controller.MenuBarController;
import codenames.structure.CardType;
import codenames.structure.DeckSinglePlayer;
import codenames.structure.DeckTwoTeams;
import codenames.structure.GameSinglePlayer;
import codenames.structure.GameTwoTeams;
import codenames.structure.PlayableCard;
import codenames.structure.PlayableCardWithHints;
import codenames.structure.TextCard;
import codenames.structure.AI.EasyOpponentAI;
import codenames.controller.GameSinglePlayerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        GameSinglePlayer game = testSPGame();
        // GameTwoTeams game = testGame();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/All.fxml"));

        LoadingBarController lb = new LoadingBarController(200, 20);

        GameSinglePlayerController gc = new GameSinglePlayerController(game);
        // GameTwoTeamsController gc = new GameTwoTeamsController(game, lb);

        EasyOpponentAI ai = new EasyOpponentAI(gc);

        gc.setOpponentAI(ai);

        MenuBarController mb = new MenuBarController(gc);
        loader.setControllerFactory(controllerClass -> {
            if (controllerClass.equals(MenuBarController.class))
                return mb;
            else if (controllerClass.equals(LoadingBarController.class))
                return lb;
            else if (controllerClass.equals(GameController.class))
                return gc;
            return null;
        });

        Scene scene = new Scene(loader.load());
        lb.initialize();

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("CodeName");
        primaryStage.show();
    }

    private GameTwoTeams testGame() {
        List<PlayableCard> listCard = new ArrayList<PlayableCard>();

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

        return new GameTwoTeams(new DeckTwoTeams(listCard), 5, 9, 9);

    }

    private GameSinglePlayer testSPGame() {
        List<PlayableCardWithHints> listCard = new ArrayList<PlayableCardWithHints>();

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
            listCard.add(new PlayableCardWithHints(new PlayableCard(new TextCard(text), cardTypes[i]),
                    Arrays.asList("hint1", "hint2")));
            i++;
        }

        GameSinglePlayer game = new GameSinglePlayer(new DeckSinglePlayer(listCard), 5, 9, 9);

        return game;
    }
}
