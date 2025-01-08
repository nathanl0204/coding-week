package codenames.controller;

import codenames.structure.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.application.Platform;

public class MenuBarController {

    @FXML private RadioMenuItem classicMode;
    @FXML private RadioMenuItem duoMode;
    @FXML private RadioMenuItem soloMode;
    @FXML private CheckMenuItem blitzMode;
    @FXML private ToggleGroup modeGroup;

    private GameDuoController gameController;

    public void setGameController(GameDuoController gameController) {
        this.gameController = gameController;

        if (modeGroup == null) {
            modeGroup = new ToggleGroup();
            classicMode.setToggleGroup(modeGroup);
            duoMode.setToggleGroup(modeGroup);
            soloMode.setToggleGroup(modeGroup);
        }

        // Désactiver les options de mode de jeu au début d'une partie
        modeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (gameController != null && oldValue != null) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Changement de mode");
                alert.setHeaderText("Impossible de changer de mode");
                alert.setContentText("Vous devez démarrer une nouvelle partie pour changer de mode de jeu.");
                alert.showAndWait();
                modeGroup.selectToggle(oldValue);
            }
        });
    }

    @FXML
    private void handleNewGame() {
        gameController.startNewGame();
        // Réactiver les options de mode de jeu
        classicMode.setDisable(false);
        duoMode.setDisable(false);
        soloMode.setDisable(false);
    }

    @FXML
    private void handleLoadGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Charger une partie");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers de sauvegarde", "*.save")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            gameController.loadGame(file);
        }
    }

    @FXML
    private void handleSaveGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvegarder la partie");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers de sauvegarde", "*.save")
        );
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            gameController.saveGame(file);
        }
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
        gameController.setBlitzMode(blitzMode.isSelected());
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