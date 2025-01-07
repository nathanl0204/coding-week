package codenames.structure;

import java.io.Serializable;

public class Game implements Serializable {

    private int id;
    
    private Boolean onGoing;
    private Statistics blueStat,redStat;
    private ListCard cards;
    private Boolean blueTurn;
    private int remainingCardGuess;
    private int cols;

    public Game(int cols, ListCard cards,int numberOfBlueCard, int numberOfRedCard){
        this.cols = cols;
        blueStat = new Statistics(numberOfBlueCard);
        redStat = new Statistics(numberOfRedCard);
        this.cards = cards;
        blueTurn = true;
        onGoing = true;
    }

    public int getCols(){
        return cols;
    }

    public Statistics getBlueStatistics(){
        return blueStat;
    }

    public Statistics getRedStatistics(){
        return redStat;
    }

    public int getRemainingCardGuess(){
        return remainingCardGuess;
    }

    public void setRemainingCardGuess(int remainingCardGuess){
        this.remainingCardGuess = remainingCardGuess;
    }

    public int getId(){
        return id;
    }

    public ListCard getListCard(){
        return cards;
    }

    public Boolean isBlueTurn(){
        return blueTurn;
    }

    public CardType getColorTurn(){
        if (blueTurn) return CardType.Blue;
        else return CardType.Red;
    }

    public void changeTurn(int remainingCardGuess){
        blueTurn = !blueTurn;
        if (blueTurn) blueStat.incrNumberOfTurns();
        else redStat.incrNumberOfTurns();
        this.remainingCardGuess = remainingCardGuess;
    }

    public void ends(){
        onGoing = false;
    } 

    public Boolean isOnGoing(){
        return onGoing && remainingCardGuess > 0;
    }

    public void correctGuess(){
        if (blueTurn) {
            blueStat.incrNumberOfCorrectGuess();
            blueStat.decrNumberOfRemainingCardsToFind();
        }
        else {
            redStat.incrNumberOfCorrectGuess();
            redStat.decrNumberOfRemainingCardsToFind();
        } 
        remainingCardGuess--;
        
    }

    public void wrongGuess(){
        remainingCardGuess = 0;
        if (blueTurn) {
            blueStat.incrNumberOfErrors();
            redStat.decrNumberOfRemainingCardsToFind();
        }
        else {
            redStat.incrNumberOfErrors();
            blueStat.decrNumberOfRemainingCardsToFind();
        }
        // temps
    }

    public int getNumberOfOpponentRemainingCardsToFind() {
        if (blueTurn) return redStat.getNumberOfRemainingCardsToFind();
        else return blueStat.getNumberOfRemainingCardsToFind();
    }

    public int getNumberOfRemainingCardsToFind() {
        if (blueTurn) return blueStat.getNumberOfRemainingCardsToFind();
        else return redStat.getNumberOfRemainingCardsToFind();
    }

}
