package codenames;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        Scene scene = new Scene(root, 1000, 1000);
        stage.setTitle("Codenames");
        stage.setScene(scene);
        stage.show();
    }
}

