package codenames.structure;

import java.io.Serializable;

public class Game implements Serializable {

    private int id;
    
    private Statistics blueStat,redStat;
    private ListCard cards;
    private Boolean blueTurn;
    private int remainingCardGuess;
    private int rows,cols;

    public Game(int rows, int cols, ListCard cards){
        this.rows = rows;
        this.cols = cols;
        blueStat = new Statistics();
        redStat = new Statistics();
        this.cards = cards;
        blueTurn = true;
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

    public void changeTurn(int n){
        blueTurn = !blueTurn;
        if (blueTurn) blueStat.incrNumberOfTurns();
        else redStat.incrNumberOfTurns();
        remainingCardGuess = n;
    }

    public void calculStat(){
        blueStat.calcul();
        redStat.calcul();
    }

    public void correctGuess(){
        if (blueTurn) blueStat.incrNumberOfGuess();
        else redStat.incrNumberOfGuess();
        remainingCardGuess -= 1;
    }

    public void wrongGuess(){
        if (blueTurn) blueStat.incrNumberOfErrors();
        else redStat.incrNumberOfErrors();
        // temps
    }

}
