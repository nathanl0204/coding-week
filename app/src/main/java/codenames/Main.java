package codenames;

import codenames.structure.*;
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
        loader.setLocation(getClass().getResource("/view/VuePhotos.fxml"));
        loader.setControllerFactory(iC->new VuePhotos(sujetObserve, stage));
        root.setCenter(loader.load());

        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("/view/VuePages.fxml"));
        loader2.setControllerFactory(iC->new VuePages(sujetObserve));
        root.setLeft(loader2.load());

        FXMLLoader loader3 = new FXMLLoader();
        loader3.setLocation(getClass().getResource("/view/VueMenu.fxml"));
        loader3.setControllerFactory(iC->new VueMenu(sujetObserve, stage));
        root.setTop(loader3.load());

        FXMLLoader loader4 = new FXMLLoader();
        loader4.setLocation(getClass().getResource("/view/VueVignettes.fxml"));
        loader4.setControllerFactory(iC->new VueVignettes(sujetObserve));
        root.setRight(loader4.load());

        game.notifyObservers();


        Scene scene = new Scene(root, 1000, 1000);
        stage.setTitle("Codenames");
        stage.setScene(scene);
        stage.show();
    }
}

