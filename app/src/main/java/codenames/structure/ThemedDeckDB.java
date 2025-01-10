package codenames.structure;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ThemedDeckDB {

    protected List<ThemedDeck> data;

    protected ThemedDeckDB() {
        data = new ArrayList<>();
    }

    public List<ThemedDeck> getData() {
        loadAll();
        return data;
    }

    public abstract void loadAll();

    public abstract ThemedDeck loadFromFile(String fileName);
}