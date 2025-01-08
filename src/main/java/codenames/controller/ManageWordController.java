package codenames.controller;

import codenames.controller.view.InputDialogView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ManageWordController {

    public ListView<String> wordsContainer;
    public ListView<String> listContainer;
    public Button removeList;
    @FXML
    private Button addNewList;
    private ArrayList<String> currentList;

    public ManageWordController() {
    }

    public void initialize() {
        System.out.println(addNewList.isVisible());
        this.addNewList.setOnAction(e -> test());
        removeList.setOnAction(e -> {
            deleteOneList();
        });
        update();
    }

    public void test() {
        InputDialogView dialog = null;
        try {
            dialog = new InputDialogView();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String newListName = dialog.getValue();

        String dirPath = "resources/wordslist/";
        String filePath = dirPath + newListName + ".txt";
        File file = new File(filePath);
        File dir = new File(dirPath);
        boolean fileIsOk = false;
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!file.exists()) {
            try {
                System.out.println(file.getAbsolutePath());
                fileIsOk = file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (!fileIsOk)
                new Alert(Alert.AlertType.ERROR, "file error", ButtonType.OK).showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR, "new list created", ButtonType.OK).showAndWait();
        }

        loadList();
    }

    public void loadList() {
        listContainer.getItems().clear();
        String dirPath = "resources/wordslist/";
        //filePath = dirPath + newListName + ".txt";

        File dir = new File(dirPath);
        File[] fileList = dir.listFiles();

        if (fileList == null) return;
        for (File f : fileList) {
            listContainer.getItems().add(f.getName());
        }


    }

    public void update() {
        loadList();
    }

    public void deleteOneList() {
        System.out.println("azerty");
        if(listContainer.getSelectionModel() != null) {
            String fileName = listContainer.getSelectionModel().getSelectedItem();
            String dirPath = "resources/wordslist/";
            String filePath = dirPath + fileName;
            File file = new File(filePath);
            System.out.println(file.getAbsolutePath() + " " + file.exists());
            if (file.exists()) {
                if(file.delete()){
                    update();
                }
        }

        }
    }
}