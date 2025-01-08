package codenames.controller;

import codenames.structure.CardType;
import codenames.structure.GameSinglePlayer;
import codenames.structure.PlayableCard;
import javafx.fxml.FXML;

public class GameSinglePlayerController extends GameController{

    public GameSinglePlayerController(){
        super();
    }
    
    public GameSinglePlayerController(GameSinglePlayer game){
        super(game);
    }

    public void processCardSelection(PlayableCard card) {
         switch (card.getCardType()) {
            case Black:
                
                loadingBarController.stop();
                game.wrongGuess(CardType.Black,loadingBarController.getElapsedSeconds());
                alertWrongGuest("Black Card selected, you lose");
                game.ends();
                if (game.isBlueTurn()) info.setText("Red Team win");
                else info.setText("Blue Team win");
                button.setVisible(false);
                displayStatistics();
                break;
            case White:
                loadingBarController.stop();
                game.wrongGuess(CardType.White,loadingBarController.getElapsedSeconds());
                break;
            case Blue:
                if (game.isBlueTurn()) {
                    game.correctGuess();
                } else {
                    loadingBarController.stop();
                    game.wrongGuess(CardType.Blue,loadingBarController.getElapsedSeconds());
                }
                break;
            case Red:
                if (!game.isBlueTurn()) {
                    game.correctGuess();
                } else {
                    loadingBarController.stop();
                    game.wrongGuess(CardType.Red,loadingBarController.getElapsedSeconds());
                }
                break;
            default:
                break;
        }
        

        if (game.getNumberOfRemainingCardsToFind() == 0 && game.isOnGoing()){
            game.ends();
            displayStatistics();
            if (game.isBlueTurn()) info.setText("Blue Team win");
            else info.setText("Red Team win");
            button.setVisible(false);
        }
        else if (game.getRemainingCardGuess() == 0 && game.isBlueTurn() && game.isOnGoing()){
            // l'IA opponent joue
            
        }
    }

    @FXML 
    public void handleButton(){
        
        if (game.isBlueTurn()){
            // l'IA equipier joue

        }
        
    }

}
