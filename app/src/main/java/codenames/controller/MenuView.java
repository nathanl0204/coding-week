package codenames.controller;

import codenames.structure.*;

public class MenuView implements Observer {
    private Game game;

    public MenuView(Game game) {
        this.game = game;
        game.addObserver(this);
    }

    public void react() {
        
    }
}
