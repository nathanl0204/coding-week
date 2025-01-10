package codenames.structure;

import codenames.observers.*;

import java.util.ArrayList;

public abstract class Game {

    protected int id;

    protected Boolean onGoing;
    protected Statistics blueStat, redStat;
    protected Boolean blueTurn;
    protected int remainingCardGuess;
    protected int cols;
    private boolean blitzMode;
    protected ArrayList<Observer> observers;

    public Game(int cols, int numberOfBlueCard, int numberOfRedCard) {
        this.cols = cols;
        blueStat = new Statistics(numberOfBlueCard);
        redStat = new Statistics(numberOfRedCard);
        blueTurn = false;
        onGoing = true;
        this.observers = new ArrayList<>();
    }

    public void addObserver(Observer obs) {
        this.observers.add(obs);
    }

    public void notifyObservers() {
        for (int i = 0; i < this.observers.size(); i++) {
            this.observers.get(i).react();
        }
    }

    public void setBlitzMode(boolean blitzMode) {
        this.blitzMode = blitzMode;
    }

    public boolean getBlitzMode() {
        return this.blitzMode;
    }

    public int getCols() {
        return cols;
    }

    public Statistics getBlueStatistics() {
        return blueStat;
    }

    public Statistics getRedStatistics() {
        return redStat;
    }

    public int getRemainingCardGuess() {
        return remainingCardGuess;
    }

    public void setRemainingCardGuess(int remainingCardGuess) {
        this.remainingCardGuess = remainingCardGuess;
    }

    public int getId() {
        return id;
    }

    public abstract Deck getDeck();

    public Boolean isBlueTurn() {
        return blueTurn;
    }

    public CardType getColorTurn() {
        if (blueTurn)
            return CardType.Blue;
        else
            return CardType.Red;
    }

    public void changeTurn(int remainingCardGuess) {
        blueTurn = !blueTurn;
        if (blueTurn)
            blueStat.incrNumberOfTurns();
        else
            redStat.incrNumberOfTurns();
        this.remainingCardGuess = remainingCardGuess;
    }

    public void ends() {
        onGoing = false;
    }

    public Boolean isOnGoing() {
        return onGoing;
    }

    public void correctGuess() {
        if (blueTurn) {
            blueStat.incrNumberOfCorrectGuess();
            blueStat.decrNumberOfRemainingCardsToFind();
        } else {
            redStat.incrNumberOfCorrectGuess();
            redStat.decrNumberOfRemainingCardsToFind();
        }
        remainingCardGuess--;

    }

    public void majStatTemps(double time) {
        if (blueTurn)
            blueStat.addTimePerTurn(time);
        else
            redStat.addTimePerTurn(time);
    }

    public void wrongGuess(CardType cardType, double temps) {
        System.out.println("" + temps);
        remainingCardGuess = 0;
        if (blueTurn) {
            blueStat.incrNumberOfErrors();
            blueStat.addTimePerTurn(temps);
            if (cardType == CardType.Red)
                redStat.decrNumberOfRemainingCardsToFind();
        } else {
            redStat.incrNumberOfErrors();
            redStat.addTimePerTurn(temps);
            if (cardType == CardType.Blue)
                blueStat.decrNumberOfRemainingCardsToFind();
        }
    }

    public int getNumberOfOpponentRemainingCardsToFind() {
        if (blueTurn)
            return redStat.getNumberOfRemainingCardsToFind();
        else
            return blueStat.getNumberOfRemainingCardsToFind();
    }

    public int getNumberOfRemainingCardsToFind() {
        if (blueTurn)
            return blueStat.getNumberOfRemainingCardsToFind();
        else
            return redStat.getNumberOfRemainingCardsToFind();
    }

    /*
     * game.simuleOpponent(){
     * // blueTurn = false;
     * // temps artificiel
     * // pick nb random de case a retourner
     * // pick des cartes aleatoirement
     * // blueTurn = true;
     * }
     * 
     */

    /*
     * game.simuleCoequipier(){
     * // changer la structure des mots
     * // et renvoie un int (nb de carte) et un string (indice)
     * parmi les differents liste de mots du jeu
     * }
     * 
     */

    /*
     * utilisation du pattern strategy pour different type d'ia
     * 
     * 
     * creer nouvelle classe pour gameSoloController
     * 
     */
}
