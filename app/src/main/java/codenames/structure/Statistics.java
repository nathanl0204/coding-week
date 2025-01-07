package codenames.structure;

public class Statistics {
    private double sumTimePerTurn;
    private int numberOfErrors;
    private int numberOfTurns;
    private int numberOfCorrectGuess;

    private int numberOfRemainingCardsToFind;

    public Statistics(int numberOfCard) {
        this.sumTimePerTurn = 0;
        this.numberOfErrors = 0;
        this.numberOfTurns = 0;
        this.numberOfCorrectGuess = 0;
        numberOfRemainingCardsToFind = numberOfCard;
    }

    public int getNumberOfRemainingCardsToFind(){
        return numberOfRemainingCardsToFind;
    }

    public void decrNumberOfRemainingCardsToFind(){
        numberOfRemainingCardsToFind--;
    }

    public double addTimePerTurn(double time) {
        return sumTimePerTurn + time;
    }

    public double getAverageTimePerTurn() {
        return numberOfTurns != 0 ? sumTimePerTurn/numberOfTurns : 0;
    }

    public double getAverageCardsPerTurn() {
        return numberOfTurns != 0 ? numberOfCorrectGuess/numberOfTurns : 0;
        
    }

    public int getNumberOfErrors() {
        return numberOfErrors;
    }

    public void incrNumberOfErrors() {
        this.numberOfErrors += 1;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void incrNumberOfTurns() {
        this.numberOfTurns += 1;
    }

    public int getNumberOfCorrectGuess() {
        return numberOfCorrectGuess;
    }

    public void incrNumberOfCorrectGuess() {
        this.numberOfCorrectGuess += 1;
    }

    public String toString() {
        return "Average time per turn: " + getAverageTimePerTurn() + " seconds\nAverage words per turn: " + getAverageCardsPerTurn() + "\n Number of errors: " + numberOfErrors + "\n Number of guess: " + numberOfCorrectGuess;
    }

}