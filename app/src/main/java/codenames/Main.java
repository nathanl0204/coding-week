package codenames;

import codenames.structure.*;
import codenames.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        Game game = new Game();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Board.fxml"));
        loader.setControllerFactory(iC->new BoardView(game));
        root.setCenter(loader.load());

        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("/view/Menu.fxml"));
        loader2.setControllerFactory(iC->new MenuView(game));
        root.setTop(loader2.load());

        FXMLLoader loader3 = new FXMLLoader();
        loader3.setLocation(getClass().getResource("/view/BlueTeam.fxml"));
        TeamView blueTeamView = new TeamView(game);
        loader3.setControllerFactory(iC->blueTeamView);
        root.setLeft(loader3.load());

        FXMLLoader loader4 = new FXMLLoader();
        loader4.setLocation(getClass().getResource("/view/RedTeam.fxml"));
        TeamView redTeamView = new TeamView(game);
        loader4.setControllerFactory(iC->redTeamView);
        root.setRight(loader4.load());

        game.notifyObservers();

        Scene scene = new Scene(root, 1000, 1000);
        stage.setTitle("Codenames");
        stage.setScene(scene);
        stage.show();
    }
}

