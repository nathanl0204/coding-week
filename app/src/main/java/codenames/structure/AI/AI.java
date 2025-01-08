package codenames.structure;
import codenames.game.GameController;

public abstract class AI {
    GameController gameController;
    
    public AI(GameController gameControllerà) {
        this.game = game;
    }
    
    
    protected abstract void makeMove();
}
