package codenames;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import codenames.controller.*;
import codenames.controller.view.ManageWordView;
import codenames.structure.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        ManageWordView manageWordView = new ManageWordView();
        BorderPane manageWordPane = manageWordView.getGraphic();

        Scene scene = new Scene(manageWordPane);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("CodeName");
        primaryStage.show();

        /* 
        GameTwoTeams game = testGame();
        
        LoadingBarController loadingBarController = new LoadingBarController(200, 20);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoadingBar.fxml"));
        loader.setControllerFactory(iC-> loadingBarController);
        loader.load();

        GameTwoTeamsController gc = new GameTwoTeamsController(game);

        gc.setLoadingBarController(loadingBarController);
        loadingBarController.setGameController(gc);

        EasyOpponentAI ai = new EasyOpponentAI(gc);


        
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/Game.fxml"));
        loader2.setControllerFactory(iC-> gc);
        BorderPane root = loader2.load();

        FXMLLoader loader3 = new FXMLLoader();
        loader3.setLocation(getClass().getResource("/view/BlueTeam.fxml"));
        TeamView blueTeamView = new TeamView(game, true);
        loader3.setControllerFactory(iC->blueTeamView);
        root.setLeft(loader3.load());

        FXMLLoader loader4 = new FXMLLoader();
        loader4.setLocation(getClass().getResource("/view/RedTeam.fxml"));
        TeamView redTeamView = new TeamView(game, false);
        loader4.setControllerFactory(iC->redTeamView);
        root.setRight(loader4.load());

        Scene scene = new Scene(root);
        */

        primaryStage.setScene(scene);
        primaryStage.setTitle("CodeName");
        primaryStage.show();
        */
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
            listCard.add(new PlayableCardWithHints(new TextCard(text), cardTypes[i],
                    Arrays.asList("hint1", "hint2")));
            i++;
        }

        GameSinglePlayer game = new GameSinglePlayer(new DeckSinglePlayer(listCard), 5, 9, 9);

        return game;
    }
}
