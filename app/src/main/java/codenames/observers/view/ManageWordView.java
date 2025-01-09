package codenames.observers.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ManageWordView extends ViewLoader<BorderPane> {
    private codenames.observers.ManageWordView rc;

    public ManageWordView() throws IOException {
        this.rc = new codenames.observers.ManageWordView();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/manageword.fxml"));
        loader.setControllerFactory(iC -> iC.equals(codenames.observers.ManageWordView.class) ? this.rc : null);
        loadGraphic(loader);
    }
}
