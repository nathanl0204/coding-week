package codenames.structure;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ThemedDeckDB {

    private static  final ThemedDeckDB instance = new ThemedDeckDB();
    private List<ThemedDeck> data;

    private ThemedDeckDB() {
        data = new ArrayList<>();
    }

    public static ThemedDeckDB getInstance() {
        return instance;
    }

    public List<ThemedDeck> getData() {
        loadAll();
        return data;
    }

    public void loadAll(){
        data.clear();
        String dirPath = "resources/wordslist/";

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

        String dirPathWord = "resources/wordslist/";
        String filePathWord = dirPathWord + fileName;
        File fileWord = new File(filePathWord);

        if(fileWord.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileWord));
                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line != null && !line.trim().isEmpty() && !line.equals("null")) {
                            currentList.addWord(line);
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

        String dirPathHint = "resources/hintslist/";
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
