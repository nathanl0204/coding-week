package codenames.structure;
import java.io.*;

public class TextThemedDeckDB extends ThemedDeckDB {

    protected static final TextThemedDeckDB instance = new TextThemedDeckDB();

    public static TextThemedDeckDB getInstance(){
        return instance;
    }

    private TextThemedDeckDB() {
        super();
    }

    public void loadAll(){
        data.clear();
        String dirPath = "resources/card/text/hintslist/";

        File dir = new File(dirPath);
        File[] fileList = dir.listFiles();
        System.out.println(fileList);

        if (fileList == null) {
            return;
        }

        for (File f : fileList) {
            data.add(loadFromFile(f.getName()));
            System.out.println(f.getName());
        }
    }

    public ThemedDeck loadFromFile(String fileName) {
        ThemedDeck currentList = new ThemedDeck();

        String dirPathWord = "resources/card/text/wordslist/";
        String filePathWord = dirPathWord + fileName;
        File fileWord = new File(filePathWord);

        if(fileWord.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileWord));
                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line != null && !line.trim().isEmpty() && !line.equals("null")) {
                            currentList.addCard(new TextCard(line));
                        }
                    }
                    br.close();
                } catch (IOException e) {
                    
                    throw new RuntimeException(e);
                }
                
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        String dirPathHint = "resources/card/text/hintslist/";
        String filePathHint = dirPathHint + fileName;
        File fileHint = new File(filePathHint);

        if(fileHint.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileHint));
                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line != null && !line.trim().isEmpty() && !line.equals("null")) {
                            currentList.addHint(line);
                        }
                    }
                    br.close();
                } catch (IOException e) {
                    
                    throw new RuntimeException(e);
                }
                
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return currentList;
    }
}
