package codenames;

import codenames.observers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/view/MenuBar.fxml"));
        MenuBarView menuBarView = new MenuBarView(null);
        menuLoader.setControllerFactory(iC-> menuBarView);
        

        Scene scene = new Scene(menuLoader.load());

        primaryStage.setScene(scene);
        primaryStage.setTitle("CodeName");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

                
}
