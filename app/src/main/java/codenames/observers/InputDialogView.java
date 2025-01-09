package codenames.observers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class InputDialogController {

    public TextField inputField;
    private String word;

    public InputDialogController() {

    }

    @FXML
    public void initialize() {}

    public String getValue() {
        String value;
        value = inputField.getText();
        return value;
    }
}
