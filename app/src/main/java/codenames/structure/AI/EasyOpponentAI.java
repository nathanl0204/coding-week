package codenames.structure;

public class EasyOpponentAI implements OpponentAI {
    protected Game game;

    public EasyOpponentAI(Game game) {
        this.game = game;
    }

    @Override
    public void makeMove() {
        // Implementation will go here
    }

    @Override
    public PlayableCard selectCard(Game game) {
        // Implementation will go here
        return null;
    }
}
