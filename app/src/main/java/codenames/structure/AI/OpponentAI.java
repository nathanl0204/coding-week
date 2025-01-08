package codenames.structure;

public interface OpponentAI {
    void makeMove();
    PlayableCard selectCard(Game game);
}
