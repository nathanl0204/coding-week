package codenames.structure;

public class Statistics {
    private double averageTimePerTurn;
    private double averageWordsPerTurn;
    private int numberOfErrors;
    private int numberOfTurns;

    public Statistics() {
        this.averageTimePerTurn = 0;
        this.averageWordsPerTurn = 0;
        this.numberOfErrors = 0;
        this.numberOfTurns = 0;
    }

    public double getAverageTimePerTurn() {
        return averageTimePerTurn;
    }

    public void setAverageTimePerTurn(int averageTimePerTurn) {
        this.averageTimePerTurn = averageTimePerTurn;
    }

    public double getAverageWordsPerTurn() {
        return averageWordsPerTurn;
    }

    public void setAverageWordsPerTurn(int averageWordsPerTurn) {
        this.averageWordsPerTurn = averageWordsPerTurn;
    }

    public int getNumberOfErrors() {
        return numberOfErrors;
    }

    public void setNumberOfErrors(int numberOfErrors) {
        this.numberOfErrors = numberOfErrors;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void incrNumberOfTurns() {
        this.numberOfTurns += 1;
    }

    public String toString() {
        return "Average time per turn: " + averageTimePerTurn + " seconds\nAverage words per turn: " + averageWordsPerTurn + "\n Number of errors: " + numberOfErrors;
    }
}