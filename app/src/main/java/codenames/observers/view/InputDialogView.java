package codenames.observers.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.io.IOException;

public class InputDialogView extends Dialog<DialogPane> {
    private final codenames.observers.InputDialogView idc;

    private String value;

    public InputDialogView() throws IOException {
        this.idc = new codenames.observers.InputDialogView();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/InputDialog.fxml"));
        loader.setControllerFactory(iC -> this.idc);
        setDialogPane((DialogPane)loader.load());
        Button apply = (Button)getDialogPane().lookupButton(ButtonType.APPLY);
        apply.setOnAction(e1 -> this.value = idc.getValue());
        Button cancel = (Button)getDialogPane().lookupButton(ButtonType.CLOSE);
        cancel.setOnAction(e1 -> this.value = "null");
        setOnCloseRequest(e -> this.value = "null");
    }

    public String getValue() {
        showAndWait();
        return this.value;
    }
}
