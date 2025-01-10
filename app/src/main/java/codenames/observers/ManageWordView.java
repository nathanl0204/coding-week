package codenames.observers;

import codenames.observers.view.InputDialogView;
import codenames.structure.Card;
import codenames.structure.TextCard;
import codenames.structure.ThemedDeck;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.io.*;
import java.util.List;

public class ManageWordView {

    public ListView<String> wordsContainer;
    public ListView<String> listContainer;
    public ListView<String> hintsContainer;

    private ThemedDeck currentDeck = new ThemedDeck();
    private Integer selectedIndexListContainer = null;

    public ManageWordView() {
    }

    public void initialize() {
        listContainer.setOnMouseClicked(e -> {
            update();
        });

        loadLists();
        if (!listContainer.getItems().isEmpty()) {
            listContainer.getSelectionModel().select(0);
            loadCurrentDeck();
            displaycurrentDeck();
        }
        update();
    }

    public void loadLists() {
        listContainer.getItems().clear();
        String dirPath = "resources/card/text/wordslist/";
        // filePath = dirPath + newListName + ".txt";

        File dir = new File(dirPath);
        File[] fileList = dir.listFiles();

        if (fileList == null)
            return;
        for (File f : fileList) {
            listContainer.getItems().add(f.getName());
        }
    }

    public void update() {
        if (!listContainer.getItems().isEmpty()) {
            selectedIndexListContainer = listContainer.getSelectionModel().getSelectedIndex();
            loadLists();
            listContainer.getSelectionModel().select(selectedIndexListContainer);
            loadCurrentDeck();
        } else {
            wordsContainer.getItems().clear();
            hintsContainer.getItems().clear();
        }

    }

    public void loadCurrentDeck() {
        currentDeck.clear();
        if (listContainer.getSelectionModel().getSelectedItem() == null)
            return;

        String fileName = listContainer.getSelectionModel().getSelectedItem();

        String dirPathWord = "resources/card/text/wordslist/";
        String filePathWord = dirPathWord + fileName;
        File fileWord = new File(filePathWord);

        if (fileWord.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileWord));
                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        currentDeck.addCard(new TextCard(line));
                    }
                    br.close();

                } catch (IOException e) {

                    throw new RuntimeException(e);
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        String dirPathHint = "resources/card/text/hintslist/";
        String filePathHint = dirPathHint + fileName;
        File fileHint = new File(filePathHint);

        if (fileHint.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileHint));
                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line != null && !line.trim().isEmpty() && !line.equals("null")) {
                            currentDeck.addHint(line);
                        }
                    }
                    br.close();

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
        for (Card s : currentDeck.getCards()) {
            if (s.getString() != null && !s.getString().equals("null")) {
                wordsContainer.getItems().add(s.getString());
            }
        }
        hintsContainer.getItems().clear();
        for (String s : currentDeck.getHints()) {
            if (s != null && !s.equals("null")) {
                hintsContainer.getItems().add(s);
            }
        }
    }

    public void addNewList() {
        InputDialogView dialog = null;
        try {
            dialog = new InputDialogView();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String newListName = dialog.getValue();

        String dirPath = "resources/card/text/wordslist/";
        String filePath = dirPath + newListName + ".txt";
        File file = new File(filePath);
        File dir = new File(dirPath);
        boolean fileIsOk = false;
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!file.exists()) {
            try {
                fileIsOk = file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (!fileIsOk)
                new Alert(Alert.AlertType.ERROR, "file error", ButtonType.OK).showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR, "new list created", ButtonType.OK).showAndWait();
        }

        loadLists();
        int index = listContainer.getItems().indexOf(file.getName());
        listContainer.getSelectionModel().select(index);
        update();
    }

    public void removeList() {
        if (!listContainer.getSelectionModel().isEmpty()) {
            String fileName = listContainer.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette liste ?");
            alert.setContentText("Cette action est irréversible.");

            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeYes) {
                    String dirPathWords = "resources/card/text/wordslist/";
                    String filePathWords = dirPathWords + fileName;
                    File fileWords = new File(filePathWords);
                    if (fileWords.exists()) {
                        fileWords.delete();
                    }

                    String dirPathHints = "resources/card/text/hintlist/";
                    String filePathHints = dirPathHints + fileName;
                    File fileHints = new File(filePathHints);
                    if (fileHints.exists()) {
                        fileHints.delete();
                    }

                    loadLists();

                    if (!listContainer.getSelectionModel().isEmpty()) {
                        listContainer.getSelectionModel().select(0);
                    }

                    update();
                }
            });
        }
    }

    public void savecurrentDeck() {
        String fileName = listContainer.getSelectionModel().getSelectedItem();

        // Chemins des fichiers
        String dirPathWord = "resources/card/text/wordslist/";
        String filePathWord = dirPathWord + fileName;

        String dirPathHint = "resources/card/text/hintslist/";
        String filePathHint = dirPathHint + fileName;

        // Sauvegarde des mots
        saveCardsToFile(currentDeck.getCards(), filePathWord);

        // Sauvegarde des indices
        saveHintsToFile(currentDeck.getHints(), filePathHint);

        update();
        displaycurrentDeck();
    }

    // Sauvegarde des mots dans un fichier spécifique
    private void saveCardsToFile(List<Card> cards, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Card card : cards) {
                if (card.getString() != null && !card.getString().trim().isEmpty()) {
                    bw.write(card.getString());
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
        if (listContainer.getSelectionModel() == null) {
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
        currentDeck.addCard(new TextCard(word));
        savecurrentDeck();
        listContainer.getSelectionModel().select(index);
    }

    public void removeWordInList() {
        if (listContainer.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Select list before", ButtonType.OK).showAndWait();
            return;
        }

        if (wordsContainer.getSelectionModel() != null) {
            String word = wordsContainer.getSelectionModel().getSelectedItem();
            currentDeck.removeCard(word);
            savecurrentDeck();
        }
    }

    public void addHintInList() {
        if (listContainer.getSelectionModel().isEmpty()) {
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

    public void removeHintInList() {
        if (listContainer.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Select list before", ButtonType.OK).showAndWait();
            return;
        }

        if (hintsContainer.getSelectionModel() != null) {
            String hint = hintsContainer.getSelectionModel().getSelectedItem();

            currentDeck.removeHint(hint);
            savecurrentDeck();
        }
    }
}
