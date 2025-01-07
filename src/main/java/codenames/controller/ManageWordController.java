package codenames.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;


public class ManageWordController {

    @FXML
    private Button addNewList;

    public ManageWordController(){}

    public void initialize() {
        System.out.println(addNewList.isVisible() );
        System.out.println("azertyuiop");
        this.addNewList.setOnAction(e -> test());
    }

    public void test() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot access the database", ButtonType.OK);
        alert.showAndWait();
    }
}