package codenames.observers;

import codenames.structure.Game;
import codenames.structure.GameSinglePlayer;
import codenames.structure.GameTwoTeams;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import codenames.observers.*;

public class MenuBarView implements Observer {


    @FXML MenuBar tmp;
    @FXML
    private RadioMenuItem classicMode;
    @FXML
    private RadioMenuItem duoMode;
    @FXML
    private RadioMenuItem soloMode;
    @FXML
    private CheckMenuItem blitzMode;

    private Game game;

    public MenuBarView(Game game) {
        this.game = game;
        this.game.addObserver(this);
    }

    @FXML 
    public void initialize(){
        
    }

    @FXML
    private void handleNewGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoadingGame.fxml"));
        loader.setControllerFactory(iC->new LoadingGameView());
        GridPane root = loader.load();

        Scene scene = new Scene(root);

        ((Stage) tmp.getScene().getWindow()).setScene(scene);
    }

    @FXML
    private void handleLoadGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Charger une partie");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers de sauvegarde", "*.save"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                game = Game.loadGame(file);

                BorderPane root = new BorderPane();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Game.fxml"));

                GameView gameView;
                if (game instanceof GameTwoTeams) {
                    gameView = new GameTwoTeamsView((GameTwoTeams) game);
                } else {
                    gameView = new GameSinglePlayerView((GameSinglePlayer) game);
                }

                loader.setControllerFactory(iC-> gameView);
                root.setCenter(loader.load());

                FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/view/MenuBar.fxml"));
                MenuBarView menuBarView = new MenuBarView(game);
                menuLoader.setControllerFactory(iC-> menuBarView);
                root.setTop(menuLoader.load());

                LoadingBarView loadingBarView = new LoadingBarView(game, 200, 20);
                FXMLLoader barLoader = new FXMLLoader(getClass().getResource("/view/LoadingBar.fxml"));
                barLoader.setControllerFactory(iC-> loadingBarView);
                barLoader.load();
                gameView.setLoadingBarView(loadingBarView);

                FXMLLoader blueLoader = new FXMLLoader();
                blueLoader.setLocation(getClass().getResource("/view/BlueTeam.fxml"));
                TeamView blueTeamView = new TeamView(game, true);
                blueLoader.setControllerFactory(iC->blueTeamView);
                root.setLeft(blueLoader.load());

                FXMLLoader redLoader = new FXMLLoader();
                redLoader.setLocation(getClass().getResource("/view/RedTeam.fxml"));
                TeamView redTeamView = new TeamView(game, false);
                redLoader.setControllerFactory(iC->redTeamView);
                root.setRight(redLoader.load());

                Scene scene = new Scene(root);
                Stage stage = (Stage) tmp.getScene().getWindow();
                stage.setScene(scene);

                game.notifyObservers();

            } catch (Exception e) {
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

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sauvegarde réussie");
                alert.setHeaderText(null);
                alert.setContentText("La partie a été sauvegardée avec succès.");
                alert.showAndWait();

            } catch (IOException e) {
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
    private void handleEditTextCards() throws IOException {
        ManageWordView rc = new ManageWordView();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/manageword.fxml"));
        loader.setControllerFactory(iC -> iC.equals(codenames.observers.ManageWordView.class) ? rc : null);

        Scene scene = new Scene(loader.load());

        Stage stage = new Stage();
        stage.setScene(scene); 
        stage.show();
    }

    @FXML
    private void handleEditImageCards() throws IOException {
        ManageImageView rc = new ManageImageView();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/manageword.fxml"));
        loader.setControllerFactory(iC -> iC.equals(codenames.observers.ManageWordView.class) ? rc : null);

        Scene scene = new Scene(loader.load());

        Stage stage = new Stage();
        stage.setScene(scene); 
        stage.show();
    }

    @FXML
    private void handleRules() {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
                Desktop.getDesktop().browse(URI.create("https://iello.fr/wp-content/uploads/2016/10/Codenames_rulebook_FR-web_2018.pdf"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void react() {}
}
