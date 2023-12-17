package task3;

import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;


public class task3 {
    static private String currentWorkingDirectory = System.getProperty("user.dir");
    static private Map<String, String> valuesMap = new HashMap<>();

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.print("Необходимо ввести 2 аргумента!");
            System.exit(-1);
        }
        File file1 = new File(args[0]);
        File file2 = new File(args[1]);

        if (!file1.exists() || !file2.exists()) {
            System.err.print("Нет такого файла по данному пути!");
            System.exit(-1);
        }
        String directoryPath = getFileDirectory(args[0]);
        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject root1 = jsonParser.parse(new FileReader(file1)).getAsJsonObject();
            JsonObject root2 = jsonParser.parse(new FileReader(file2)).getAsJsonObject();
            Object tests = root1.get("tests");
            Object values = root2.get("values");
            ExtractFromTests(values, valuesMap);
            UpdateTestsResult(tests, valuesMap);

            File file = createFile(directoryPath);
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            String jsonString = gson.toJson(root1);
            fileWriter.write(jsonString);
            fileWriter.close();
            System.out.print("Программа успешно отработала!");
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }
    static private void UpdateTestsResult(Object obj, Map<String, String> map) {
        if (obj instanceof JsonArray) {
            JsonArray jsonArray = (JsonArray) obj;
            for (Object tmp : jsonArray)
                UpdateTestsResult(tmp, map);
        } else {
            JsonObject jsonObject = (JsonObject) obj;
            if (map.containsKey(jsonObject.get("id").toString()))
                jsonObject.addProperty("value", map.get(jsonObject.get("id").toString()));
            Object values = jsonObject.get("values");
            if (values != null)
                UpdateTestsResult(values, map);
        }
    }
    static private void ExtractFromTests(Object obj, Map<String, String> map) {
        if (obj instanceof JsonArray) {
            JsonArray jsonArray = (JsonArray) obj;
            for (Object tmp : jsonArray)
                ExtractFromTests(tmp, map);
        } else {
            JsonObject jsonObject = (JsonObject) obj;
            Object id = jsonObject.get("id");
            Object value = jsonObject.get("value");
            Object values = jsonObject.get("values");
            map.put(id.toString(), value.toString().replaceAll("\"", ""));
            if (values != null)
                ExtractFromTests(values, map);
        }
    }

    static private File createFile(String str) throws IOException {
        File file = new File(str +"/report.json");
        if (!file.exists()) file.createNewFile();
        return file;
    }
    static private String getFileDirectory(String filePath) {
        int lastIndexOfSlash = filePath.lastIndexOf('/');
        return (lastIndexOfSlash != -1) ? filePath.substring(0, lastIndexOfSlash) : null;
    }
}
