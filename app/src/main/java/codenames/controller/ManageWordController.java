package codenames.controller;

import codenames.controller.view.InputDialogView;
import codenames.structure.ThemedDeck;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.io.*;
import java.util.List;


public class ManageWordController {

    public ListView<String> wordsContainer;
    public ListView<String> listContainer;
    public ListView<String> hintsContainer;

    private ThemedDeck currentDeck = new ThemedDeck();
    private Integer selectedIndexListContainer = null;

    public ManageWordController() {
    }

    public void initialize() {
        loadList();
        if (!listContainer.getSelectionModel().isEmpty()){
            listContainer.getSelectionModel().select(0);
            displaycurrentDeck();
        }
        update();
    }

    public void addNewList() {
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
        if(selectedIndexListContainer != null) {
            selectedIndexListContainer = listContainer.getSelectionModel().getSelectedIndex();
            loadList();
            loadcurrentDeck();
            listContainer.getSelectionModel().select(selectedIndexListContainer);
        }

    }

    public void loadcurrentDeck() {
        currentDeck.clear();
        if(listContainer.getSelectionModel().getSelectedItem() == null)
            return;

        String fileName = listContainer.getSelectionModel().getSelectedItem();

        String dirPathWord = "resources/wordslist/";
        String filePathWord = dirPathWord + fileName;
        File fileWord = new File(filePathWord);

        if(fileWord.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileWord));
                try {
                    String line = "ee";
                    while (line != null) {
                        line = br.readLine();
                        currentDeck.addWord(line);
                    }br.close();

                } catch (IOException e) {
                    
                    throw new RuntimeException(e);
                }
                
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        String dirPathHint = "resources/hintslist/";
        String filePathHint = dirPathHint + fileName;
        File fileHint = new File(filePathHint);

        if(fileHint.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileHint));
                try {
                    String line = "ee";
                    while (line != null) {
                        line = br.readLine();
                        currentDeck.addHint(line);
                    }br.close();

                } catch (IOException e) {
                    
                    throw new RuntimeException(e);
                }
                
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        displaycurrentDeck();
    }

    public void displaycurrentDeck() {
        wordsContainer.getItems().clear();
        for(String s : currentDeck.getWords()) {
            if(s != null && !s.equals("null")) {
                wordsContainer.getItems().add(s);
            }
        }
        hintsContainer.getItems().clear();
        for(String s : currentDeck.getHints()) {
            if(s != null && !s.equals("null")) {
                hintsContainer.getItems().add(s);
            }
        }
    }

    public void removeList() {
        if(listContainer.getSelectionModel() != null) {
            String fileName = listContainer.getSelectionModel().getSelectedItem();
            
            String dirPathWords = "resources/wordslist/";
            String filePathWords = dirPathWords+ fileName;
            File fileWords = new File(filePathWords);
            System.out.println(fileWords.getAbsolutePath() + " " + fileWords.exists());
            if (fileWords.exists()) {
                fileWords.delete();
            }
            
            
            String dirPathHints = "resources/hintlist/";
            String filePathHints = dirPathHints + fileName;
            File fileHints = new File(filePathHints);
            System.out.println(fileHints.getAbsolutePath() + " " + fileHints.exists());
            if (fileHints.exists()) {
                fileHints.delete();
            }

            if (!listContainer.getSelectionModel().isEmpty()){
                listContainer.getSelectionModel().select(0);
            }
            loadList();
            update();
        }
    }



    public void savecurrentDeck() {
        String fileName = listContainer.getSelectionModel().getSelectedItem();

        // Chemins des fichiers
        String dirPathWord = "resources/wordslist/";
        String filePathWord = dirPathWord + fileName;

        String dirPathHint = "resources/hintslist/";
        String filePathHint = dirPathHint + fileName;

        // Sauvegarde des mots
        saveWordsToFile(currentDeck.getWords(), filePathWord);

        // Sauvegarde des indices
        saveHintsToFile(currentDeck.getHints(), filePathHint);

        update();
        displaycurrentDeck();
    }

    // Sauvegarde des mots dans un fichier spécifique
    private void saveWordsToFile(List<String> words, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String word : words) {
                if (word != null && !word.trim().isEmpty()) {
                    bw.write(word);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error saving words data: " + e.getMessage(), ButtonType.OK).showAndWait();
            e.printStackTrace();
        }
    }

    // Sauvegarde des indices dans un fichier spécifique
    private void saveHintsToFile(List<String> hints, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String hint : hints) {
                if (hint != null && !hint.trim().isEmpty()) {
                    bw.write(hint);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error saving hints data: " + e.getMessage(), ButtonType.OK).showAndWait();
            e.printStackTrace();
        }
    }


    public void addWordInList() {
        if(listContainer.getSelectionModel() == null) {
            new Alert(Alert.AlertType.ERROR, "Select list before", ButtonType.OK).showAndWait();
            return;
        }
        InputDialogView dialog = null;
        try {
            dialog = new InputDialogView();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String word = dialog.getValue();


        int index = listContainer.getSelectionModel().getSelectedIndex();
        currentDeck.addWord(word);
        savecurrentDeck();
        listContainer.getSelectionModel().select(index);
    }

    public void removeWordInList() {
        if(listContainer.getSelectionModel() == null) {
            new Alert(Alert.AlertType.ERROR, "Select list before", ButtonType.OK).showAndWait();
            return;
        }

        if(wordsContainer.getSelectionModel() != null) {
            String word = wordsContainer.getSelectionModel().getSelectedItem();
            currentDeck.removeWord(word);
            savecurrentDeck();
        }
    }


    public void addHintInList(){
        if(listContainer.getSelectionModel() == null) {
            new Alert(Alert.AlertType.ERROR, "Select list before", ButtonType.OK).showAndWait();
            return;
        }
        InputDialogView dialog = null;
        try {
            dialog = new InputDialogView();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String hint = dialog.getValue();

        currentDeck.addHint(hint);
        savecurrentDeck();
    }

    public void removeHintInList(){
        if(listContainer.getSelectionModel() == null) {
            new Alert(Alert.AlertType.ERROR, "Select list before", ButtonType.OK).showAndWait();
            return;
        }

        if(hintsContainer.getSelectionModel() != null) {
            String hint = hintsContainer.getSelectionModel().getSelectedItem();
            
            currentDeck.removeHint(hint);
            savecurrentDeck();
        }
    }
}