package codenames;

import codenames.controller.GameController;
import codenames.structure.CardType;
import codenames.structure.Game;
import codenames.structure.ListCard;
import codenames.structure.TextCard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Game.fxml"));
        
        GameController controller = new GameController(testGame());
        loader.setController(controller);

        Scene scene = new Scene(loader.load());

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

        return new Game( 3, listCard);



    }
}

