package codenames.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class ManageWordController {

    @FXML
    private ListView<String> listViewLeft;

    @FXML
    private ListView<String> listViewRight;

    @FXML
    private Button addButtonLeft;

    @FXML
    private Button removeButtonLeft;

    @FXML
    private Button addButtonRight;

    @FXML
    private Button removeButtonRight;

    @FXML
    public void initialize() {
    }

    public ManageWordController(){}

    @FXML
    private void handleAddLeft() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Item");
        dialog.setHeaderText("Enter item to add to the list:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(item -> listViewLeft.getItems().add(item));
    }

    @FXML
    private void handleRemoveLeft() {
        String selectedItem = listViewLeft.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            listViewLeft.getItems().remove(selectedItem);
        }
    }

    @FXML
    private void handleAddRight() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Word");
        dialog.setHeaderText("Enter word to add to the list:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(word -> listViewRight.getItems().add(word));
    }

    @FXML
    private void handleRemoveRight() {
        String selectedItem = listViewRight.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            listViewRight.getItems().remove(selectedItem);
        }
    }
}