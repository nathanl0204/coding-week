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

    public void addTimePerTurn(double time) {
        sumTimePerTurn += time;
    }

    public double getAverageTimePerTurn() {
        return numberOfTurns != 0 ? sumTimePerTurn/numberOfTurns : 0.0;
    }

    public double getAverageCardsPerTurn() {
        return numberOfTurns != 0 ? ((double) numberOfCorrectGuess)/numberOfTurns : 0;

    }

    public int getNumberOfErrors() {
        return numberOfErrors;
    }

    public void incrNumberOfErrors() {
        numberOfErrors++;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void incrNumberOfTurns() {
        numberOfTurns++;
    }

    public int getNumberOfCorrectGuess() {
        return numberOfCorrectGuess;
    }

    public void incrNumberOfCorrectGuess() {
        numberOfCorrectGuess++;
    }
}