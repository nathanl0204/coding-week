package codenames.controller;

public class Chronometer {
    
    private double elapsedSeconds; 

    public Chronometer(){}

    public void start(){
        elapsedSeconds = System.currentTimeMillis();
    }

    public void stop(){
        elapsedSeconds = (System.currentTimeMillis() - elapsedSeconds)/1000.0;
    }

    public double getElapsedSeconds() {
        return elapsedSeconds;
    }

}
