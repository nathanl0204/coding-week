package codenames.structure.AI;

import codenames.observers.*;
import java.util.*;

public abstract class OpponentAI extends AI {
    protected Random random;

    public OpponentAI(GameView gameView) {
        super(gameView);
        this.random = new Random();
    }

    public abstract void play();
}
