package task2;
import java.io.*;
import static java.lang.Math.*;


public class task2 {
    static private float centerX = -1;
    static private float centerY = -1;
    static private float radius = -1;
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
        readCircleDatesFile(file1);
        if (centerX < 0 || centerY < 0 || radius < 0) {
            System.err.print("Неправильные данные в файле 1");
            System.exit(-1);
        }
        readPointsFile(file2);
    }
    static private void readPointsFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String str;
            boolean flag = false;
            while ((str = reader.readLine()) != null) {
                flag = true;
                String[] parts = str.split(" ");
                float pointX = getNum(parts[0]);
                float pointY = getNum(parts[1]);
                float distance = calculateDistanceToPoint(pointX, pointY);
                if (radius == distance) System.out.println("0");
                else if (radius > distance) System.out.println("1");
                else System.out.println("2");

            }
            if (!flag) {
                System.err.print("В файле 2 имеются недостоверные данные!");
                System.exit(-1);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    static private float calculateDistanceToPoint(float pointX, float pointY) {
        return (float) sqrt(pow((pointX -  centerX), 2) + pow((pointY - centerY), 2));
    }
    static private void readCircleDatesFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String str = reader.readLine();

            for (int i = 0; i < 2; i++) {
                if (str == null) {
                    System.err.print("В файле 1 имеются недостоверные данные!");
                    System.exit(-1);
                }
                String[] parts = str.split(" ");
                if (parts.length > 1) {
                    centerX = getNum(parts[0]);
                    centerY = getNum(parts[1]);
                } else if (parts.length == 1) {
                    radius = getNum(parts[0]);
                }
                str = reader.readLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    static private float getNum(String str) {
        float result = -1;
        try {
            result = Float.parseFloat(str);
            return result;
        } catch (NumberFormatException e) {
            System.err.print("В файле имеются нечисловые данные!");
            System.exit(-1);
            return result;
        }
    }
}
