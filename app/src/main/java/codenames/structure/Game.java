package codenames.structure;

import codenames.controller.*;
import java.util.ArrayList;

public class Game {
    ArrayList<Observer> observers;

    public Game() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(Observer obs) {
        this.observers.add(obs);
    }

    public void notifyObservers() {
        for (int i = 0; i<this.observers.size(); i++) {
            this.observers.get(i).react();
        }
    }
}
