package codenames;

import codenames.controller.GameController;
import codenames.controller.MenuBarController;
import codenames.structure.CardType;
import codenames.structure.Game;
import codenames.structure.ListCard;
import codenames.structure.TextCard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Créer le jeu
        Game game = testGame();

        // Charger la vue principale
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/view/Game.fxml"));
        GameController gameController = new GameController(game);
        gameLoader.setController(gameController);
        VBox root = gameLoader.load();

        // Charger et configurer la barre de menu
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/view/MenuBar.fxml"));
        MenuBar menuBar = menuLoader.load();
        MenuBarController menuController = menuLoader.getController();
        menuController.setGameController(gameController);

        // Ajouter la barre de menu au début du VBox root
        root.getChildren().add(0, menuBar);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("CodeName");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Game testGame(){
        ListCard listCard = new ListCard();

        String[] texts = {
                "Apple", "Banana", "Cherry", "Dog", "Elephant",
                "Football", "Guitar", "Helicopter", "Igloo"
        };

        int i = 0;

        for (String text : texts) {
            if (i % 2 == 0) listCard.addCard(new TextCard(CardType.Blue,text));
            else listCard.addCard(new TextCard(CardType.Red,text));
            i++;
        }

        return new Game( 3, listCard,5,4);



    }
}

