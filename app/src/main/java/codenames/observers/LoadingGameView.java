package codenames.observers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;
import codenames.structure.DeckFactory;
import codenames.structure.DeckSinglePlayer;
import codenames.structure.DeckTwoTeams;
import codenames.structure.Game;
import codenames.structure.GameSinglePlayer;
import codenames.structure.GameTwoTeams;
import codenames.structure.AI.*;
import codenames.structure.AI.OpponentAI;
import javafx.collections.FXCollections;

public class LoadingGameView {

    @FXML private CheckBox blitzMode;
    @FXML private ComboBox<String> gameMode;
    @FXML private ComboBox<String> aiLevel;
    @FXML private ComboBox<String> cardMode;
    @FXML private TextField gridWidth;
    @FXML private TextField gridHeight;
    @FXML private TextField blitzTime;
    @FXML private Label blitzLabel;
    @FXML private Label aiLabel;

    public LoadingGameView(){}

    @FXML
    public void initialize() {
        gameMode.setItems(FXCollections.observableArrayList("Joueur Seul", "Une Equipe" ,"Deux Equipes")); 
        gameMode.setValue("Deux Equipes");  
        
        cardMode.setItems(FXCollections.observableArrayList("Texte", "Image"));
        cardMode.setValue("Texte");  

        aiLevel.setItems(FXCollections.observableArrayList("Facile", "Moyenne", "Difficile"));
        aiLevel.setValue("Moyenne");
        aiLevel.setVisible(false);
        aiLabel.setVisible(false);
    }

    @FXML
    public void updateGameMode() {
        
        if (!gameMode.getSelectionModel().getSelectedItem().equals("Deux Equipes")) {
            aiLevel.setVisible(true);
            aiLabel.setVisible(true);
        }
        else {
            aiLevel.setVisible(false);
            aiLabel.setVisible(false);
        }
    }


    @FXML private void toggleBlitzField() {
        boolean isBlitzSelected = blitzMode.isSelected();
        blitzLabel.setVisible(isBlitzSelected);
        blitzTime.setVisible(isBlitzSelected);
    }

    @FXML private void startGame() throws IOException {
        boolean isBlitz = blitzMode.isSelected();
        String selectedGameMode = gameMode.getValue();
        int width, height;

        try {
            width = Integer.parseInt(gridWidth.getText());
            height = Integer.parseInt(gridHeight.getText());
            if (width <= 0 || height <= 0) {
                throw new NumberFormatException("Les dimensions doivent être supérieures à zéro.");
            }
            

            GameView gameView;
            DeckFactory factory = new DeckFactory();
            Game game;

            if (selectedGameMode.endsWith("Deux Equipes")){
                DeckTwoTeams deck = null;
                switch (cardMode.getSelectionModel().getSelectedItem()) {
                    case "Texte":
                        deck  = factory.createTextDeckTwoTeams(height*width);
                        break;
                    case "Image":
                        deck = factory.createImageDeckTwoTeams(height*width);
                        break;
                    default:
                        break;
                }
                game = new GameTwoTeams(deck, width, (int) (width*height*0.3), (int) (width*height*0.3));
                gameView = new GameTwoTeamsView( (GameTwoTeams) game);
            } 
            else if (selectedGameMode.endsWith("Une Equipe")){
                DeckTwoTeams deck = null;
                switch (cardMode.getSelectionModel().getSelectedItem()) {
                    case "Texte":
                        deck  = factory.createTextDeckTwoTeams(height*width);
                        break;
                    case "Image":
                        deck = factory.createImageDeckTwoTeams(height*width);
                        break;
                    default:
                        break;
                }
                game = new GameTwoTeams(deck, width, (int) (width*height*0.3), (int) (width*height*0.3));
                gameView = new GameSingleTeamView( (GameTwoTeams) game);

                OpponentAI oppAI = null;

                switch (aiLevel.getSelectionModel().getSelectedItem()) {
                    case "Facile":
                        oppAI = new EasyOpponentAI((GameSingleTeamView) gameView);
                        break;
                    case "Moyenne":
                        oppAI = new MediumOpponentAI((GameSingleTeamView) gameView);
                        break;
                    case "Difficile":
                        oppAI = new HardOpponentAI((GameSingleTeamView) gameView);
                        break;
                    default:
                        break;
                }

                ((GameSingleTeamView) gameView).setOpponentAI(oppAI);

            }
            else {
                DeckSinglePlayer deck = null;
                switch (cardMode.getSelectionModel().getSelectedItem()) {
                    case "Texte":
                        deck = factory.createTextDeckSinglePlayer(height*width);
                        break;
                    case "Image":
                        deck = factory.createImageDeckSinglePlayer(height*width);
                        break;
                    default:
                        break;
                }

                game = new GameSinglePlayer( deck, width, (int) (width*height*0.3), (int) (width*height*0.3));
                gameView = new GameSinglePlayerView((GameSinglePlayer) game);
                
                AllyAI allyAI = null;
                OpponentAI oppAI = null;

                switch (aiLevel.getSelectionModel().getSelectedItem()) {
                    case "Facile":
                        allyAI = new EasyAllyAI((GameSinglePlayerView) gameView);
                        oppAI = new EasyOpponentAI((GameSinglePlayerView) gameView);
                        break;
                    case "Moyenne":
                        allyAI = new MediumAllyAI((GameSinglePlayerView) gameView);
                        oppAI = new MediumOpponentAI((GameSinglePlayerView) gameView);
                        break;
                    case "Difficile":
                        allyAI = new HardAllyAI((GameSinglePlayerView) gameView);
                        oppAI = new HardOpponentAI((GameSinglePlayerView) gameView);
                        break;
                    default:
                        break;
                }

                ((GameSinglePlayerView) gameView).setAllyAI(allyAI);
                ((GameSinglePlayerView) gameView).setOpponentAI(oppAI);
                
            }

            Chronometer loadingBarController;
        
            if (isBlitz) {
                int blitzDuration = Integer.parseInt(blitzTime.getText());
                if (blitzDuration <= 0) {
                    throw new NumberFormatException("Le temps pour Blitz doit être supérieur à zéro.");
                }

                loadingBarController = new LoadingBarView(game, blitzDuration, 20);
                ((LoadingBarView) loadingBarController).setGameController(game);
                
            }
            else {
                loadingBarController = new Chronometer();
            }

            gameView.setLoadingBarView(loadingBarController);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Game.fxml"));
            loader.setControllerFactory(controllerClass -> {
                if (controllerClass.equals(GameView.class)) return gameView;
                else if (controllerClass.equals(LoadingBarView.class)) return loadingBarController;
                else return null;
            });

            BorderPane root = new BorderPane();
            root.setCenter(loader.load());

            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/view/MenuBar.fxml"));
            MenuBarView menuBarView = new MenuBarView(game);
            menuLoader.setControllerFactory(iC-> menuBarView);
            root.setTop(menuLoader.load());

            FXMLLoader loader3 = new FXMLLoader();
            loader3.setLocation(getClass().getResource("/view/BlueTeam.fxml"));
            TeamView blueTeamView = new TeamView(game, true);
            loader3.setControllerFactory(iC->blueTeamView);
            root.setLeft(loader3.load());

            FXMLLoader loader4 = new FXMLLoader();
            loader4.setLocation(getClass().getResource("/view/RedTeam.fxml"));
            TeamView redTeamView = new TeamView(game, false);
            loader4.setControllerFactory(iC->redTeamView);
            root.setRight(loader4.load());
            
            game.notifyObservers();
            
            Scene scene = new Scene(root);

            Stage currentStage = (Stage) blitzLabel.getScene().getWindow();

            currentStage.setScene(scene);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Entrée invalide");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

    }
}
