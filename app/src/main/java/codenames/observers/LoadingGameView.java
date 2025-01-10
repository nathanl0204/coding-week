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
import java.util.ArrayList;
import java.util.List;

import codenames.structure.CardType;
import codenames.structure.DeckFactory;
import codenames.structure.DeckSinglePlayer;
import codenames.structure.DeckTwoTeams;
import codenames.structure.Game;
import codenames.structure.GameSinglePlayer;
import codenames.structure.GameTwoTeams;
import codenames.structure.PlayableCard;
import codenames.structure.TextCard;
import codenames.structure.AI.*;
import codenames.structure.AI.OpponentAI;
import javafx.collections.FXCollections;
import codenames.observers.*;

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
        gameMode.setItems(FXCollections.observableArrayList("Joueur Seul", "Deux Equipes")); 
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
        
        if (gameMode.getSelectionModel().getSelectedItem().equals("Joueur Seul")) {
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
                game = new GameTwoTeams(deck, width, 7, 7);
                gameView = new GameTwoTeamsView( (GameTwoTeams) game);
                
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

                game = new GameSinglePlayer( deck, width, 7, 7);
                gameView = new GameSinglePlayerView((GameSinglePlayer) game);
                
                AllyAI allyAI = null;
                OpponentAI oppAI = null;

                switch (aiLevel.getSelectionModel().getSelectedItem()) {
                    case "Facile":
                        allyAI = new EasyAllyAI(gameView);
                        oppAI = new EasyOpponentAI(gameView);
                        break;
                    case "Moyenne":
                        allyAI = new MediumAllyAI(gameView);
                        oppAI = new MediumOpponentAI(gameView);
                        break;
                    case "Difficile":
                        allyAI = new HardAllyAI(gameView);
                        oppAI = new HardOpponentAI(gameView);
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
            menuLoader.setControllerFactory(iC-> new MenuBarView(game));
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

            Scene scene = new Scene(root);

            Stage currentStage = (Stage) blitzLabel.getScene().getWindow();

            currentStage.setScene(scene);
            System.out.println("Mode Blitz: " + isBlitz);
            System.out.println("Mode de jeu: " + selectedGameMode);
            System.out.println("Largeur de la grille: " + width);
            System.out.println("Hauteur de la grille: " + height);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Entrée invalide");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

    }

    private GameTwoTeams testGame() {
        List<PlayableCard> listCard = new ArrayList<PlayableCard>();

        String[] texts = {
                "Apple", "Banana", "Cherry", "Dog", "Elephant", "Football",
                "Guitar", "Helicopter", "Igloo", "Jacket", "Kangaroo", "Lemon",
                "Monkey", "Notebook", "Octopus", "Penguin", "Quilt", "Rocket",
                "Sunflower", "Tiger", "Umbrella", "Violin", "Whale", "Xylophone",
                "Yacht"
        };

        CardType[] cardTypes = {
                CardType.Red, CardType.Red, CardType.Blue, CardType.Black, CardType.White, CardType.Blue,
                CardType.Blue, CardType.Red, CardType.White, CardType.Red, CardType.Blue, CardType.Black,
                CardType.White, CardType.Red, CardType.Blue, CardType.Blue, CardType.Red, CardType.White,
                CardType.Blue, CardType.Red, CardType.Red, CardType.White, CardType.Blue, CardType.Red,
                CardType.Blue
        };

        int i = 0;

        for (String text : texts) {
            listCard.add(new PlayableCard(new TextCard(text), cardTypes[i]));
            i++;
        }

        return new GameTwoTeams(new DeckTwoTeams(listCard), 5, 9, 9);

    }
    
}
