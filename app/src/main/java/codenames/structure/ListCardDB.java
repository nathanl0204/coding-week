package codenames.structure;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ListCardDB {

    private static  final ListCardDB instance = new ListCardDB();
    private HashMap<String, ArrayList<String>> data;

    private ListCardDB() {
        data = new HashMap<>();
    }

    public static ListCardDB getInstance() {
        return instance;
    }

    public HashMap<String, ArrayList<String>> getData() {
        loadAll();
        return data;
    }

    public void loadAll(){
        data.clear();
        String dirPath = "resources/wordslist/";

        File dir = new File(dirPath);
        File[] fileList = dir.listFiles();

        if (fileList == null) return;

        for (File f : fileList) {
            data.put(f.getName(), loadFromFile(f.getName()));
        }
    }

    public ArrayList<String> loadFromFile(String fileName) {
        ArrayList<String> currentList = new ArrayList<>();
        String dirPath = "resources/wordslist/";
        String filePath = dirPath + fileName;
        File file = new File(filePath);

        if(file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                try {
                    String line = "ee";
                    while (line != null) {
                        line = br.readLine();
                        if(line != null) {
                            currentList.add(line);
                        }
                    }

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
