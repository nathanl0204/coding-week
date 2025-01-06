package codenames;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import codenames.view.Hourglass;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: lightgray;");

        Hourglass timer = new Hourglass(100, 200, 60);
        timer.setStyle("-fx-background-color: white;");

        root.setRight(timer);

        timer.startTimer();

        timer.setOnTimeUp(() -> {
            System.out.println("Temps écoulé !");
        });

        Scene scene = new Scene(root, 1000, 1000);
        stage.setTitle("Codenames");
        stage.setScene(scene);
        stage.show();
    }
}