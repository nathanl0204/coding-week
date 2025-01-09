package codenames.structure;

import java.util.ArrayList;
import java.util.List;

public class ThemedDeck {
    private List<String> words;
    private List<String> hints;

    public ThemedDeck() {
        words = new ArrayList<>();
        hints = new ArrayList<>();
    }

    public void clear() {
        words.clear();
        hints.clear();
    }

    public void addWord(String word) {
        words.add(word);
    }

    public void addHint(String hint) {
        hints.add(hint);
    }

    public List<String> getWords() {
        return words;
    }

    public List<String> getHints() {
        return hints;
    }

    public void removeWord(String word) {
        words.remove(word);
    }

    public void removeHint(String hint) {
        hints.remove(hint);
    }
}
