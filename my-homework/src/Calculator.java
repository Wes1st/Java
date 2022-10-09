import java.util.Objects;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) throws ScannerException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Калькулятор:\nвведите пример (a+b,a-b,a*b,a/b)\nвведите число от 1 до 10 или от I до X");
        String inputScan = scanner.nextLine();
        String userInput = inputScan.toUpperCase();
        String [] numUserInput = userInput.split("[+\\-*/]");
        if (numUserInput.length < 3){
            try{
                if (isNumeric(numUserInput[0]) && isNumeric(numUserInput[1])){
                    float a = Float.parseFloat(numUserInput[0]);
                    float b = Float.parseFloat(numUserInput[1]);
                    getCalc(userInput, a, b);
                } else {
                    try{
                        NumRom inputNumRome1 = NumRom.valueOf(numUserInput[0]);
                        NumRom inputNumRome2 = NumRom.valueOf(numUserInput[1]);
                        int a = inputNumRome1.getTranslation();
                        int b = inputNumRome2.getTranslation();
                        getCalcRom(userInput, a, b);
                    } catch (ArrayIndexOutOfBoundsException err){
                        System.out.println("строка не является математической операцией");
                    }
                }
            } catch (ArrayIndexOutOfBoundsException err) {
                System.out.println("строка не является математической операцией");
            } catch (IllegalArgumentException err){
                System.out.println("используются одновременно разные системы счисления");
            }
        } else {
            throw new ScannerException("формат математической операции не удовлетворяет заданию - " +
                    "два операнда и один оператор (+, -, /, *)");
        }


        scanner.close();
    }

    private static boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException err) {
            return  false;
        }
    }

    public static void getCalc(String userInput, float a, float b){
        String [] arrSymbolCalc = new String[4];
        char [] symbolCalc = "+-*/".toCharArray();
        for(int i = 0; i < symbolCalc.length; i++){
            arrSymbolCalc[i] = String.valueOf(symbolCalc[i]);
            if (userInput.contains(arrSymbolCalc[i])) {
                if (a > -1 && b > -1 && a < 11 && b < 11) {
                    int d = opChrResult(arrSymbolCalc[i], (int) a, (int) b);
                    System.out.println("Ответ: " + d);
                } else {
                    System.out.println("Введите число от 0 до 10 включительно");
                }
            }
        }
    }
    public static void getCalcRom(String userInput, float a, float b) throws ScannerException {
        NumRom [] arrNum = NumRom.values();
        String [] arrSymbolCalc = new String[4];
        char [] symbolCalc = "+-*/".toCharArray();
        for(int i = 0; i < 4; i++){
            arrSymbolCalc[i] = String.valueOf(symbolCalc[i]);
            if (userInput.contains(arrSymbolCalc[i])) {
                for (int j = 0; j < symbolCalc.length; j++){
                    if(Objects.equals(arrSymbolCalc[i], arrSymbolCalc[j])){
                        int result = opChrResult(arrSymbolCalc[i], (int) a, (int) b);
                        if (result > 0){
                            String [] arrResult = Integer.toString(result).split("");
                            for (NumRom numRom : arrNum) {
                                try {
                                    switch (arrResult.length) {
                                        case (1):
                                            if (numRom.getTranslation() == result) {
                                                System.out.println("Ответ: " + numRom);
                                            }
                                            break;
                                        case (2):
                                            int arrA = Integer.parseInt(arrResult[0]) * 10;
                                            int arrB = Integer.parseInt(arrResult[1]);
                                            if (numRom.getTranslation() == arrA) {
                                                try {
                                                    System.out.println("Ответ: " + numRom + arrNum[arrB - 1]);
                                                } catch (ArrayIndexOutOfBoundsException err) {
                                                    System.out.println("Ответ: " + numRom);
                                                }
                                            }
                                            break;
                                        case (3):
                                            if (numRom.getTranslation() == result) {
                                                System.out.println("Ответ: " + arrNum[18]);
                                            }
                                            break;
                                    }
                                } catch (Exception err) {
                                    System.out.println("Введите число от I до X включительно");
                                }
                            }
                        }
                        else throw new ScannerException("в римской системе нет отрицательных чисел");
                    }
                }
            }
        }
    }

    public static int opChrResult (String opChr, int a, int b){
        try {
            switch (opChr){
                case ("+"):
                    return a + b;
                case ("-"):
                    return a - b;
                case ("*"):
                    return a * b;
                case ("/"):
                    return a / b;
            }
        } catch (ArithmeticException err) {
            System.out.println("Деление на ноль невозможно");
        }
        return a;
    }
}
