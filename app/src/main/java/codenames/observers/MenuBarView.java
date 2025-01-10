package codenames.observers;

import codenames.structure.Game;
import codenames.structure.GameSinglePlayer;
import codenames.structure.GameTwoTeams;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;

public class MenuBarView implements Observer {

    @FXML
    private RadioMenuItem classicMode;
    @FXML
    private RadioMenuItem duoMode;
    @FXML
    private RadioMenuItem soloMode;
    @FXML
    private CheckMenuItem blitzMode;

    private Game game;
    private Stage primaryStage;

    public MenuBarView(Game game) {
        this.game = game;
        this.game.addObserver(this);
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void handleNewGame() {
        // gameController.startNewGame();
    }

    @FXML
    private void handleLoadGame() {
        if (primaryStage == null) {
            // Attempt to get stage through the menu items if not set directly
            if (classicMode != null && classicMode.getParentPopup() != null) {
                primaryStage = (Stage) classicMode.getParentPopup().getOwnerWindow();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Cannot access main window");
                alert.setContentText("Application window reference not found");
                alert.showAndWait();
                return;
            }
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Charger une partie");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers de sauvegarde", "*.save"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                Game loadedGame = Game.loadGame(file);

                // Utiliser classicMode (ou n'importe quel autre élément FXML) pour obtenir la référence à la fenêtre
                //Stage stage = (Stage) classicMode.getParentPopup().getOwnerWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Game.fxml"));

                // Reste du code inchangé...
                GameView gameView;
                if (loadedGame instanceof GameTwoTeams) {
                    gameView = new GameTwoTeamsView((GameTwoTeams) loadedGame);
                } else {
                    gameView = new GameSinglePlayerView((GameSinglePlayer) loadedGame);
                }

                loader.setController(gameView);
                Scene scene = new Scene(loader.load());
                primaryStage.setScene(scene);

                // Notifier les observers
                loadedGame.notifyObservers();

            } catch (Exception e) {
                System.err.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur lors du chargement");
                alert.setContentText("Impossible de charger la partie : " + e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleSaveGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvegarder la partie");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers de sauvegarde", "*.save"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                game.saveGame(file);

                // Afficher un message de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sauvegarde réussie");
                alert.setHeaderText(null);
                alert.setContentText("La partie a été sauvegardée avec succès.");
                alert.showAndWait();

            } catch (IOException e) {
                // Afficher une erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur lors de la sauvegarde");
                alert.setContentText("Impossible de sauvegarder la partie : " + e.getMessage());
                alert.showAndWait();
            }
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

    @Override
    public void react() {}
}
