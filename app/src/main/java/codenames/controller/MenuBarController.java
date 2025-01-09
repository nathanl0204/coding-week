package codenames.controller;

import codenames.structure.Game;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
import javafx.application.Platform;
import java.util.Optional;

public class MenuBarController {

    @FXML
    private RadioMenuItem classicMode;
    @FXML
    private RadioMenuItem duoMode;
    @FXML
    private RadioMenuItem soloMode;
    @FXML
    private CheckMenuItem blitzMode;

    private GameController gameController;

    public MenuBarController() {
    }

    public MenuBarController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setGameController(GameTwoTeamsController gameController) {
        this.gameController = gameController;
    }

    @FXML
    private void handleNewGame() {
        // Implement new game logic
        // gameController.startNewGame();
    }

    @FXML
    private void handleLoadGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Charger une partie");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers de sauvegarde (*.json)", "*.json")
        );

        // Obtient la fenêtre actuelle à partir d'un élément FXML (par exemple, blitzMode)
        Window window = blitzMode.getParentPopup().getOwnerWindow();
        File file = fileChooser.showOpenDialog(window);

        if (file != null) {
            // Vérifier si la partie en cours doit être sauvegardée
            if (confirmSaveCurrentGame()) {
                File saveFile = fileChooser.showSaveDialog(window);
                if (saveFile != null) {
                    gameController.saveGame(saveFile);
                }
            }

            // Charger la nouvelle partie
            gameController.loadGame(file);

            // Réinitialiser les options de jeu
            blitzMode.setSelected(false);
            classicMode.setSelected(true);
        }
    }

    @FXML
    private void handleSaveGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvegarder la partie");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers de sauvegarde (*.json)", "*.json")
        );

        // Obtient la fenêtre actuelle à partir d'un élément FXML
        Window window = blitzMode.getParentPopup().getOwnerWindow();
        File file = fileChooser.showSaveDialog(window);

        if (file != null) {
            // Ajouter l'extension .json si elle n'est pas présente
            if (!file.getName().toLowerCase().endsWith(".json")) {
                file = new File(file.getAbsolutePath() + ".json");
            }
            gameController.saveGame(file);
        }
    }

    private boolean confirmSaveCurrentGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sauvegarder la partie en cours");
        alert.setHeaderText("Voulez-vous sauvegarder la partie en cours avant d'en charger une nouvelle ?");
        alert.setContentText("Les progrès non sauvegardés seront perdus.");

        ButtonType buttonTypeSave = new ButtonType("Sauvegarder");
        ButtonType buttonTypeNoSave = new ButtonType("Ne pas sauvegarder");
        ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeNoSave, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonTypeSave) {
                return true;
            } else if (result.get() == buttonTypeNoSave) {
                return false;
            }
        }

        // Si l'utilisateur annule ou ferme la boîte de dialogue
        throw new RuntimeException("Load game cancelled");
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

    @FXML
    private void handleEditCards() {
        // À implémenter
    }

    @FXML
    private void handleEditThemes() {
        // À implémenter
    }

    @FXML
    private void handlePreferences() {
        // À implémenter
    }

    @FXML
    private void handleBlitzMode() {
        // gameController.setBlitzMode(blitzMode.isSelected());
    }

    @FXML
    private void handleStatistics() {
        // Show statistics window
    }

    @FXML
    private void handleRules() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Règles du jeu");
        alert.setHeaderText("Règles de Codenames");
        alert.setContentText("Les règles du jeu...");
        alert.showAndWait();
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("À propos");
        alert.setHeaderText("Codenames");
        alert.setContentText("Version 1.0\nCréé par...");
        alert.showAndWait();
    }
}
