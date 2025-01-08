package codenames.structure;

public class HardOpponentAI implements OpponentAI {
    protected Game game;

    public HardOpponentAI(Game game) {
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
