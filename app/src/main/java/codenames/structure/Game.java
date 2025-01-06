package codenames.structure;

import java.io.Serializable;

public class Game implements Serializable {

    private int id;
    
    private Statistics blueStat;
    private Statistics redStat;
    private ListCard cards;
    private Boolean blueTurn;
    private int remainingCardGuess;

    public Game(ListCard cards){
        blueStat = new Statistics();
        redStat = new Statistics();
        this.cards = cards;
        blueTurn = true;
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

    public void decrRemainingCardGuess(){
        remainingCardGuess -= 1;
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
        remainingCardGuess = n;
    }

}
