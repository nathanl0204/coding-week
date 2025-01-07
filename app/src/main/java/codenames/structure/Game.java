package codenames.structure;

import codenames.controller.*;
import java.util.ArrayList;

public class Game {
    ArrayList<Observer> observers;

    public void notifyObservers() {
        for (int i = 0; i<this.observers.size(); i++) {
            this.observers.get(i).react();
        }
    }
}
