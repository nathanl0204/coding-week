package codenames.controller;

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
import java.util.Scanner;

import codenames.structure.CardType;
import codenames.structure.Deck;
import codenames.structure.DeckFactory;
import codenames.structure.DeckSinglePlayer;
import codenames.structure.DeckTwoTeams;
import codenames.structure.Game;
import codenames.structure.GameSinglePlayer;
import codenames.structure.GameTwoTeams;
import codenames.structure.PlayableCard;
import codenames.structure.TextCard;
import javafx.collections.FXCollections;

public class LoadingGameController {

    @FXML private CheckBox blitzMode;
    @FXML private ComboBox<String> gameMode;
    @FXML private TextField gridWidth;
    @FXML private TextField gridHeight;
    @FXML private TextField blitzTime;
    @FXML private Label blitzLabel;

    public LoadingGameController(){}

    @FXML
    public void initialize() {
        gameMode.setItems(FXCollections.observableArrayList("Single Player", "Two Teams")); 
        gameMode.setValue("Two Teams");  
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
            

            GameController gameController;
            Game game;
            Deck deck;

            if (selectedGameMode.endsWith("Two Teams")){
                DeckFactory factory = new DeckFactory();

                
                deck = factory.createDeckTwoTeams(height*width);
                game = new GameTwoTeams((DeckTwoTeams) deck, width, width, height);
                gameController = new GameTwoTeamsController( (GameTwoTeams) game);
                
            }
            else {
                // Creer les IA
                deck = new DeckSinglePlayer(null);
                game = new GameSinglePlayer((DeckSinglePlayer) deck, width, 9, 9);
                gameController = new GameSinglePlayerController((GameSinglePlayer) game);
                
            }

            Chronometer loadingBarController;
        
            if (isBlitz) {
                int blitzDuration = Integer.parseInt(blitzTime.getText());
                if (blitzDuration <= 0) {
                    throw new NumberFormatException("Le temps pour Blitz doit être supérieur à zéro.");
                }

                loadingBarController = new LoadingBarController(blitzDuration, 20);
                ((LoadingBarController) loadingBarController).setGameController(gameController);
                
            }
            else {
                loadingBarController = new Chronometer();
            }

            gameController.setLoadingBarController(loadingBarController);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Game.fxml"));
            loader.setControllerFactory(controllerClass -> {
                if (controllerClass.equals(GameController.class)) return gameController;
                else if (controllerClass.equals(LoadingBarController.class)) return loadingBarController;
                else return null;
            });

            Scene scene = new Scene(loader.load());

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
