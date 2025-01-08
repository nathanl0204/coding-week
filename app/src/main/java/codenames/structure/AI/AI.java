package codenames.structure.AI;

import codenames.controller.*;

public abstract class AI {
    GameController gameController;

    public AI(GameController gameController) {
        this.gameController = gameController;
    }

    protected abstract void play();
}
