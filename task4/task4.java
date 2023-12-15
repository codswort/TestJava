package task4;
import java.util.*;
import java.io.BufferedReader;
import static java.lang.Math.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class task4 {
    static private ArrayList<Integer> arrayList = new ArrayList<>();
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.print("Необходимо ввести 1 аргумент!");
            System.exit(-1);
        }
        File file = new File(args[0]);
        if (!file.exists()) {
            System.err.print("Нет такого файла по данному пути!");
            System.exit(-1);
        }
        readInputFile(file);
        Collections.sort(arrayList);
        System.out.print(findMinStepAmount());
    }

    static private int findMinStepAmount() {
        int min = 0;
        for (int i = 0; i < arrayList.size(); i++)
            min += abs(arrayList.get(i) - arrayList.get(0));
        int tmp = 0;
        for (int k = 1; k < arrayList.size(); k++) {
            for (int i = 0; i < arrayList.size(); i++) {
                tmp += abs(arrayList.get(i) - arrayList.get(k));
            }
            if (tmp < min) min = tmp;
        }
        return min;
    }

    static private void readInputFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String str;
            while ((str = reader.readLine()) != null) {
                String[] parts = str.split(" ");
                for (int i = 0; i < parts.length; i++)
                    arrayList.add(getNum(parts[i]));
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
    static private int getNum(String str) {
        int result = -1;
        try {
            result = Integer.parseInt(str);
            return result;
        } catch (NumberFormatException e) {
            System.err.print("В файле имеются нечисловые данные!");
            System.exit(-1);
            return result;
        }
    }
}
