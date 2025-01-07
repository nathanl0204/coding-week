package codenames.structure;

public class Statistics {
    private double averageTimePerTurn;
    private double averageCardsPerTurn;
    private int numberOfErrors;
    private int numberOfTurns;
    private int numberOfGuess;

    private int numberOfRemainingCardsToFind;

    public Statistics(int numberOfCard) {
        this.averageTimePerTurn = 0;
        this.averageCardsPerTurn = 0;
        this.numberOfErrors = 0;
        this.numberOfTurns = 0;
        this.numberOfGuess = 0;
        numberOfRemainingCardsToFind = numberOfCard;

    }

    public int getNumberOfRemainingCardsToFind(){
        return numberOfRemainingCardsToFind;
    }

    public void decrNumberOfRemainingCardsToFind(){
        numberOfRemainingCardsToFind--;
    }

    public double getAverageTimePerTurn() {
        return averageTimePerTurn;
    }

    public double getAverageCardsPerTurn() {
        return averageCardsPerTurn;
    }

    public int getNumberOfErrors() {
        return numberOfErrors;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void incrNumberOfTurns() {
        this.numberOfTurns += 1;
    }

    public void incrNumberOfErrors() {
        this.numberOfErrors += 1;
    }

    public void incrNumberOfGuess() {
        this.numberOfGuess += 1;
    }

    public String toString() {
        return "Average time per turn: " + averageTimePerTurn + " seconds\nAverage words per turn: " + averageCardsPerTurn + "\n Number of errors: " + numberOfErrors + "\n Number of guess: " + numberOfGuess;
    }

    public void calcul(){
        averageTimePerTurn /= numberOfTurns;
        averageCardsPerTurn /= numberOfTurns;
    }
}