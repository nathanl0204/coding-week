package codenames;

import java.util.ArrayList;
import java.util.List;

import codenames.controller.GameDuoController;
import codenames.structure.CardType;
import codenames.structure.Game;
import codenames.structure.PlayableCard;
import codenames.structure.TextCard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Game.fxml"));
        
        Game game = testGame();

        GameDuoController controller = new GameDuoController(game);

        loader.setController(controller);

        Scene scene = new Scene(loader.load());

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("CodeName");
        primaryStage.show();
    }

    private Game testGame(){
        List<PlayableCard> listCard = new  ArrayList<PlayableCard>();

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

        return new Game( 5, listCard,9,9);

    }
}

