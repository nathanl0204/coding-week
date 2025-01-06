package codenames;

import codenames.view.CodenamesMenuBar;
import codenames.view.LoadingBar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        CodenamesMenuBar menuBar = new CodenamesMenuBar();
        LoadingBar loadingBar = new LoadingBar(300, 30);
        loadingBar.start(30); // 30 seconds timer

        VBox root = new VBox(20);
        root.setStyle("-fx-padding: 20px;");
        root.getChildren().addAll(menuBar, loadingBar);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Codenames Timer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}