package codenames;

import codenames.observers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoadingGame.fxml"));
        loader.setControllerFactory(iC->new LoadingGameView());
        GridPane root = loader.load();

        /* 
        ManageImageView rc = new ManageImageView();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/manageword.fxml"));
        loader.setControllerFactory(iC -> iC.equals(codenames.observers.ManageWordView.class) ? rc : null);
        Pane root = loader.load();
        */
        

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("CodeName");
        primaryStage.setMaximized(true);
        primaryStage.show();

        


        /* 
        BorderPane root = new BorderPane();

        GameTwoTeams game = testGame();

        GameTwoTeamsView gc = new GameTwoTeamsView(game);

        EasyOpponentAI ai = new EasyOpponentAI(gc);
        
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/Game.fxml"));
        loader2.setControllerFactory(iC-> gc);
        root.setCenter(loader2.load());

        LoadingBarView loadingBarView = new LoadingBarView(game, 200, 20);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoadingBar.fxml"));
        loader.setControllerFactory(iC-> loadingBarView);
        loader.load();

        gc.setLoadingBarView(loadingBarView);

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/view/MenuBar.fxml"));
        MenuBarView menuBarController = new MenuBarView(game);
        System.out.println(primaryStage == null);
        menuBarController.setPrimaryStage(primaryStage);
        menuLoader.setControllerFactory(iC-> menuBarController);
        root.setTop(menuLoader.load());

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

                                primaryStage.setScene(scene);
                                primaryStage.setTitle("CodeName");
                                primaryStage.show();*/

                }

                
}
