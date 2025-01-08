package codenames.controller;

import codenames.controller.view.InputDialogView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.io.*;
import java.util.ArrayList;


public class ManageWordController {

    public ListView<String> wordsContainer;
    public ListView<String> listContainer;
    public Button removeList;
    public Button addWordInList;
    public Button RemoveWordInList;
    @FXML
    private Button addNewList;
    private ArrayList<String> currentList = new ArrayList<>();
    private Integer selectedIndexListContainer = null;

    public ManageWordController() {
    }

    public void initialize() {
        System.out.println(addNewList.isVisible());
        this.addNewList.setOnAction(e -> test());
        removeList.setOnAction(e -> {
            deleteOneList();
        });
        listContainer.setOnMouseClicked(e -> { loadCurrentList();
        });
        addWordInList.setOnAction(e -> {
            addWordInList();
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
        selectedIndexListContainer = listContainer.getSelectionModel().getSelectedIndex();
        loadList();
        loadCurrentList();
        if(selectedIndexListContainer != null) {
            listContainer.getSelectionModel().select(selectedIndexListContainer);
        }

    }

    public void loadCurrentList() {
        currentList.clear();
        if(listContainer.getSelectionModel().getSelectedItem() == null)
            return;

        String fileName = listContainer.getSelectionModel().getSelectedItem();
        String dirPath = "resources/wordslist/";
        String filePath = dirPath + fileName;
        File file = new File(filePath);

        if(file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                try {
                    String line = "ee";
                    while (line != null) {
                        line = br.readLine();
                        currentList.add(line);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        displayCurrentList();
    }

    public void displayCurrentList() {
        wordsContainer.getItems().clear();
        for(String s : currentList) {
            if(s != null && !s.equals("null")) {
                wordsContainer.getItems().add(s);
            }
        }
    }

    public void deleteOneList() {
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


    public void addWordInList() {
        if(listContainer.getSelectionModel() == null) {
            new Alert(Alert.AlertType.ERROR, "Select list before", ButtonType.OK);
            return;
        }
        InputDialogView dialog = null;
        try {
            dialog = new InputDialogView();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String word = dialog.getValue();

        currentList.add(word);
        saveCurrentList(currentList);
        update();
    }

    public void saveCurrentList(ArrayList<String> currentList) {

        String fileName = listContainer.getSelectionModel().getSelectedItem();
        String dirPath = "resources/wordslist/";
        String filePath = dirPath + fileName;

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            for(String s : currentList) {
                if(s != null && !s.equals("null")) {
                    bw.write(s);
                    System.out.println(s +"-" + filePath);
                    bw.newLine();
                }
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        displayCurrentList();

    }
}