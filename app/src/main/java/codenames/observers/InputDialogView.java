package codenames.observers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class InputDialogView {

    public TextField inputField;
    private String word;

    public InputDialogView() {

    }

    @FXML
    public void initialize() {}

    public String getValue() {
        String value;
        value = inputField.getText();
        return value;
    }
}
