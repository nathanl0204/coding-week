package codenames.structure.AI;

import codenames.controller.*;
import java.util.*;

public abstract class OpponentAI extends AI {
    protected Random random;

    public OpponentAI(GameController gameController) {
        super(gameController);
        this.random = new Random();
    }

    public abstract void play();
}
