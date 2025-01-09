package codenames.controller.view;

import codenames.controller.ManageWordController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ManageWordView extends ViewLoader<BorderPane> {
    private ManageWordController rc;

    public ManageWordView() throws IOException {
        this.rc = new ManageWordController();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/manageword.fxml"));
        loader.setControllerFactory(iC -> iC.equals(ManageWordController.class) ? this.rc : null);
        loadGraphic(loader);
    }
}
