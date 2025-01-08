package codenames.structure.AI;

import codenames.controller.*;

public abstract class AI {
    GameController gameController;

    public AI(GameController gameController) {
        this.gameController = gameController;
    }

    public abstract void play();
}
