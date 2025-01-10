package codenames.structure;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImageThemedDeckDB extends ThemedDeckDB {

    protected static final ImageThemedDeckDB instance = new ImageThemedDeckDB();

    public static ImageThemedDeckDB getInstance(){
        return instance;
    }

    private ImageThemedDeckDB() {
        super();
    }

    public void loadAll(){
        data.clear();
        String dirPath = "resources/card/image/hintslist/";

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

        String dirPathHint = "resources/card/image/hintslist/";
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
                            System.out.println(line);
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


        String dirPathWord = "resources/card/image/imageslist/";

        String filePathWord;
        int dotIndex = fileName.lastIndexOf('.');
        
        if (dotIndex > 0) {
            filePathWord = dirPathWord + fileName.substring(0, dotIndex);
        } else {
            filePathWord = dirPathWord + fileName;
        }
        File directory = new File(filePathWord);

        String[] imageExtensions = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            
            for (File file : files) {
                if (file.isFile()) {
                    String fileName_ = file.getName().toLowerCase();
                    for (String ext : imageExtensions) {
                        if (fileName_.endsWith(ext)) {
                            System.out.println(file.getAbsolutePath());
                            currentList.addCard(new ImageCard("file://"+file.getAbsolutePath()));
                            break;
                        }
                    }
                }
            }
        }
        return currentList;
    }






}
