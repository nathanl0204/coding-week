package codenames.controller;

import codenames.structure.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.application.Platform;

public class MenuBarController {

    @FXML private RadioMenuItem classicMode;
    @FXML private RadioMenuItem duoMode;
    @FXML private RadioMenuItem soloMode;
    @FXML private CheckMenuItem blitzMode;

    private GameTwoTeamsController gameController;

    public void setGameController(GameTwoTeamsController gameController) {
        this.gameController = gameController;
    }

    @FXML
    private void handleNewGame() {
        // Implement new game logic
        //gameController.startNewGame();
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
            //gameController.loadGame(file);
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
            //gameController.saveGame(file);
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
        //gameController.setBlitzMode(blitzMode.isSelected());
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