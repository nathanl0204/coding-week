package codenames.structure;

public class HardAllyAI implements AllyAI {
    protected Game game;

    public HardAllyAI(Game game) {
        this.game = game;
    }

    @Override
    public void makeMove() {
        // Implementation will go here
    }

    @Override
    public String giveHint(List<PlayableCard> cards) {
        // Implementation will go here
        return null;
    }

    @Override
    public int giveHintCount(List<PlayableCard> cards) {
        // Implementation will go here
        return 0;
    }

    @Override
    public PlayableCard selectCard(Game game) {
        // Implementation will go here
        return null;
    }
}
