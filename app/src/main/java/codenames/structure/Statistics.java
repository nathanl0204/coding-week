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

    public double getAverageWordsPerTurn() {
        return averageWordsPerTurn;
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

    public String toString() {
        return "Average time per turn: " + averageTimePerTurn + " seconds\nAverage words per turn: " + averageWordsPerTurn + "\n Number of errors: " + numberOfErrors;
    }

    public void calcul(){
        averageTimePerTurn /= numberOfTurns;
        averageWordsPerTurn /= numberOfTurns;
    }
}