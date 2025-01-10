package codenames.observers;

import codenames.observers.view.InputDialogView;
import codenames.structure.Card;
import codenames.structure.ImageCard;
import codenames.structure.ThemedDeck;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;


public class ManageImageView {

    public ListView<ImageView> wordsContainer;
    public ListView<String> listContainer;
    public ListView<String> hintsContainer;

    private ThemedDeck currentDeck = new ThemedDeck();

    public ManageImageView() {
    }

    public void initialize() {
        listContainer.setOnMouseClicked(e -> {
            update();
        });

        loadLists();
        if (!listContainer.getItems().isEmpty()){
            listContainer.getSelectionModel().select(0);
            loadCurrentDeck();
            displaycurrentDeck();
        }
        update();
    }



    public void loadLists() {
        listContainer.getItems().clear();
        String dirPath = "resources/card/image/hintslist/";
        //filePath = dirPath + newListName + ".txt";

        File dir = new File(dirPath);
        File[] fileList = dir.listFiles();

        if (fileList == null) return;
        for (File f : fileList) {
            listContainer.getItems().add(f.getName());
        }
    }

    public void update() {
        if(!listContainer.getItems().isEmpty()) {
            int selectedIndexListContainer = listContainer.getSelectionModel().getSelectedIndex();
            loadLists();
            listContainer.getSelectionModel().select(selectedIndexListContainer);
            loadCurrentDeck();
        }
        else {
            wordsContainer.getItems().clear();
            hintsContainer.getItems().clear();
        }

    }

    public void loadCurrentDeck() {
        currentDeck.clear();
        if(listContainer.getSelectionModel().getSelectedItem() == null)
            return;

        String fileName = listContainer.getSelectionModel().getSelectedItem();

        String dirPathHint = "resources/card/image/hintslist/";
        String filePathHint = dirPathHint + fileName;
        File fileHint = new File(filePathHint);

        if(fileHint.exists()) {
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


        String dirPathWord = "resources/card/image/imageslist/";

        String filePathWord;
        int dotIndex = fileName.lastIndexOf('.');
        
        if (dotIndex > 0) {
            filePathWord = dirPathWord + fileName.substring(0, dotIndex);
        } else {
            filePathWord = dirPathWord + fileName;
        }
        File directory = new File(filePathWord);

        String[] imageExtensions = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            hintsContainer.getItems().clear();
            for (File file : files) {
                if (file.isFile()) {
                    String fileName_ = file.getName().toLowerCase();
                    for (String ext : imageExtensions) {
                        if (fileName_.endsWith(ext)) {
                            currentDeck.addCard(new ImageCard("file:"+file.getAbsolutePath()));
                            break;
                        }
                    }
                }
            }
        }

        displaycurrentDeck();
    }

    public void displaycurrentDeck() {
        wordsContainer.getItems().clear();
        for(Card s : currentDeck.getCards()) {
            if(s.getString() != null && !s.getString().equals("null")) {
                ImageView imgView = new ImageView(new Image(s.getString()));
                imgView.setFitWidth(200);
                imgView.setPreserveRatio(true);
                wordsContainer.getItems().add(imgView);
            }
        }
        hintsContainer.getItems().clear();
        for(String s : currentDeck.getHints()) {
            if(s != null && !s.equals("null")) {
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
    
        String dirPath = "resources/card/image/hintslist/";
        String filePath = dirPath + newListName + ".txt";
        File file = new File(filePath);
        File dir = new File(dirPath);
        boolean fileIsOk = false;
        
        if (!dir.exists()) {
            dir.mkdirs();
        }


    
        String imagesListDirPath = "resources/card/image/imageslist/";

        String dirPathImage;
        int dotIndex = newListName.lastIndexOf('.');
        
        if (dotIndex > 0) 
            dirPathImage = imagesListDirPath + newListName.substring(0, dotIndex);
        else 
            dirPathImage = imagesListDirPath + newListName;


        File imagesListDir = new File(dirPathImage);
        if (!imagesListDir.exists()) {
            boolean created = imagesListDir.mkdirs();
            if (!created) {
                new Alert(Alert.AlertType.ERROR, "Erreur lors de la création du dossier imageslist.", ButtonType.OK).showAndWait();
            }
        }
    
        if (!file.exists()) {
            try {
                fileIsOk = file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    
            if (!fileIsOk) {
                new Alert(Alert.AlertType.ERROR, "Erreur de création de fichier.", ButtonType.OK).showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "La nouvelle liste existe déjà.", ButtonType.OK).showAndWait();
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
                    String dirPathWords = "resources/card/image/imageslist/";
                    
                    String filePathWord;
                    int dotIndex = fileName.lastIndexOf('.');
                    
                    if (dotIndex > 0) 
                        filePathWord = dirPathWords + fileName.substring(0, dotIndex);
                    else 
                        filePathWord = dirPathWords + fileName;
                    
                    File directory = new File(filePathWord);

                    if (directory.exists() && directory.isDirectory()) {
                        File[] files = directory.listFiles();
            
                        if (files != null) 
                            for (File file : files) 
                                file.delete();
                        
                        directory.delete();
                    }
                    
                    String dirPathHints = "resources/card/image/hintlist/";
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


        String dirPathHint = "resources/card/image/hintslist/";
        String filePathHint = dirPathHint + fileName;

        // Sauvegarde des indices
        saveHintsToFile(currentDeck.getHints(), filePathHint);

        update();
        displaycurrentDeck();
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

        FileChooser fileChooser = new FileChooser();
        
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp")
        );

        File selectedFile = fileChooser.showOpenDialog((Stage) listContainer.getScene().getWindow() );


        String fileName = listContainer.getSelectionModel().getSelectedItem();
        String dirPathWords = "resources/card/image/imageslist/";
        String filePathWord = listContainer.getSelectionModel().getSelectedItem();
        int dotIndex = fileName.lastIndexOf('.');
        
        if (dotIndex > 0) 
            filePathWord = dirPathWords + fileName.substring(0, dotIndex);
        else 
            filePathWord = dirPathWords + fileName;

        Path targetPath = Path.of(filePathWord +"/"+ selectedFile.getName());
        
        try {
            Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        update();
    }

    public void removeWordInList() {
        if(listContainer.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Select list before", ButtonType.OK).showAndWait();
            return;
        }

        if(!wordsContainer.getSelectionModel().isEmpty()) {
            
            ImageView word = wordsContainer.getSelectionModel().getSelectedItem();
            String url = word.getImage().getUrl();
            
            File file = new File(url.substring(5));
            file.delete();
            update();
        }
    }


    public void addHintInList(){
        if(listContainer.getSelectionModel().isEmpty()) {
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
        if(listContainer.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Select list before", ButtonType.OK).showAndWait();
            return;
        }

        if(!hintsContainer.getSelectionModel().isEmpty()) {
            String hint = hintsContainer.getSelectionModel().getSelectedItem();
            
            currentDeck.removeHint(hint);
            savecurrentDeck();
        }
    }
}