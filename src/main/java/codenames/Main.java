package codenames;

import codenames.controller.*;
import codenames.controller.view.ManageWordView;
import codenames.structure.CardType;
import codenames.structure.Game;
import codenames.structure.ListCard;
import codenames.structure.TextCard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        ManageWordView manageWordView = new ManageWordView();

        BorderPane test = new ManageWordView().getGraphic();
        assert test != null : "Main window is null";
        primaryStage.setScene(new Scene(test, 1180.0D, 795.0D));
        primaryStage.show();
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/manageword.fxml"));
        
        ManageWordController controller = new ManageWordController();
        loader.setController(controller);

        Scene scene = new Scene(loader.load());

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("CodeName");
        primaryStage.show();*/
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Game testGame(){
        ListCard listCard = new ListCard();

        String[] texts = {
            "Apple", "Banana", "Cherry",
            "Dog", "Elephant", "Football", 
            "Guitar", "Helicopter", "Igloo"
        };

        CardType[] cardTypes = { 
            CardType.Red,CardType.Red,CardType.Blue,
            CardType.Black,CardType.White,CardType.Blue,
            CardType.Blue,CardType.Red,CardType.White
        };

        int i = 0;

        for (String text : texts) {
            listCard.addCard(new TextCard(cardTypes[i],text));
            i++;
        }

        return new Game( 3, listCard,3,3);



    }
}

