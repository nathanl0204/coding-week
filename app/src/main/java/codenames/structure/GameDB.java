package codenames.structure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

public class GameDB {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void save(Game game, File file) throws IOException {
        GameSave gameSave = new GameSave(game);
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(gameSave, writer);
        }
    }

    public static Game load(File file) throws IOException {
        try (Reader reader = new FileReader(file)) {
            GameSave gameSave = gson.fromJson(reader, GameSave.class);
            return gameSave.toGame();
        }
    }
}
