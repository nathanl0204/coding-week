package codenames.structure.AI;

import codenames.observers.*;

public abstract class AI {
    GameView gameView;

    public AI(GameView gameView) {
        this.gameView = gameView;
    }

    public abstract void play();
}
