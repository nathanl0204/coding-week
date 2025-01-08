package codenames.structure;

public abstract class Game {

    protected int id;
    
    protected Boolean onGoing;
    protected Statistics blueStat,redStat;
    protected Boolean blueTurn;
    protected int remainingCardGuess;
    protected int cols;

    public Game(int cols, int numberOfBlueCard, int numberOfRedCard){
        this.cols = cols;
        blueStat = new Statistics(numberOfBlueCard);
        redStat = new Statistics(numberOfRedCard);
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

    public abstract Deck getDeck();
    

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

    public void wrongGuess(CardType cardType){
        remainingCardGuess = 0;
        if (blueTurn) {
            blueStat.incrNumberOfErrors();
            if (cardType == CardType.Red) redStat.decrNumberOfRemainingCardsToFind();
        }
        else {
            redStat.incrNumberOfErrors();
            if (cardType == CardType.Blue) blueStat.decrNumberOfRemainingCardsToFind();
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


    /* 
    game.simuleOpponent(){
        // blueTurn = false;
        // temps artificiel
        // pick nb random de case a retourner
        // pick des cartes aleatoirement
        // blueTurn = true;
    }

    */

    /* 
    game.simuleCoequipier(){
        // changer la structure des mots
        // et renvoie un int (nb de carte) et un string (indice)
        parmi les differents liste de mots du jeu
    }
     
    */

    /* utilisation du pattern strategy pour different type d'ia


        creer nouvelle classe pour gameSoloController

     */
}
