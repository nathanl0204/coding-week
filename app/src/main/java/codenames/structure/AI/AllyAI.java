package codenames.structure;

public interface AllyAI {
    void makeMove();
    String giveHint(List<PlayableCard> cards);
    int giveHintCount(List<PlayableCard> cards);
    PlayableCard selectCard(Game game);
}
