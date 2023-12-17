public class task1 {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.print("Необходимо ввести 2 аргумента!");
            System.exit(-1);
        }
        int arrayLength = getNum(args[0]);
        int arrayInterval = getNum(args[1]);
        if (arrayLength < 1 || arrayInterval < 1 || arrayLength < arrayInterval) {
            System.err.print("Должны соблюдаться следующие условия: arg1 >= 1, arg2 >= 1, arg1 >= arg2");
            System.exit(-1);
        }
        int[] arr = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) arr[i] = i+1;
        int i = 0;
        System.out.print(arr[0]);
        while(true) {
            i = (i + arrayInterval - 1) % arrayLength;
            if (i == 0) break;
            System.out.print(arr[i]);
        }
    }
    static private int getNum(String str) {
        int result = -1;
        try {
            result = Integer.parseInt(str);
            return result;
        } catch (NumberFormatException e) {
            System.err.print("Необходимо ввести целочисленные аргументы!");
            System.exit(-1);
            return result;
        }
    }
}
