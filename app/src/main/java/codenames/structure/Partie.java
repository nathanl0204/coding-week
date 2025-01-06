package codenames.structure;

import java.io.Serializable;

public class Partie implements Serializable {
    private int id;
    
    private Statistics blueStat;
    private Statistics redStat;
    private ListCard cards;
    private Boolean blueTurn;

    public Partie(ListCard cards){
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

    public int getId(){
        return id;
    }

    public ListCard getListCard(){
        return cards;
    }

    public Boolean isBlueTurn(){
        return blueTurn;
    }

}
