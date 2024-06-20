import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {

    private static final Map<Character, Integer> romanToIntMap = new HashMap<>();
    private static final Map<Integer, String> intToRomanMap = new HashMap<>();

    static {
        romanToIntMap.put('I', 1);
        romanToIntMap.put('V', 5);
        romanToIntMap.put('X', 10);
        romanToIntMap.put('L', 50);
        romanToIntMap.put('C', 100);
        romanToIntMap.put('D', 500);
        romanToIntMap.put('M', 1000);

        intToRomanMap.put(1, "I");
        intToRomanMap.put(4, "IV");
        intToRomanMap.put(5, "V");
        intToRomanMap.put(9, "IX");
        intToRomanMap.put(10, "X");
        intToRomanMap.put(40, "XL");
        intToRomanMap.put(50, "L");
        intToRomanMap.put(90, "XC");
        intToRomanMap.put(100, "C");
        intToRomanMap.put(400, "CD");
        intToRomanMap.put(500, "D");
        intToRomanMap.put(900, "CM");
        intToRomanMap.put(1000, "M");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            System.out.println(calc(input));
        } catch (Exception e) {
            System.out.println("throws Exception");
        }
        scanner.close();
    }

    public static String calc(String input) throws Exception {
        String[] parts = input.trim().split(" ");
        if (parts.length != 3) {
            throw new Exception("Invalid input format");
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        boolean isRoman1 = isRoman(operand1);
        boolean isRoman2 = isRoman(operand2);

        if (isRoman1 != isRoman2) {
            throw new Exception("Different number systems used");
        }

        int num1 = isRoman1 ? romanToInt(operand1) : Integer.parseInt(operand1);
        int num2 = isRoman2 ? romanToInt(operand2) : Integer.parseInt(operand2);

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new Exception("Numbers out of range");
        }

        int result;

        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) throw new Exception("Division by zero");
                result = num1 / num2;
                break;
            default:
                throw new Exception("Invalid operator");
        }

        if (isRoman1) {
            if (result < 1) {
                throw new Exception("Result is less than I in Roman numerals");
            }
            return intToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    private static boolean isRoman(String s) {
        return s.matches("[IVXLCDM]+");
    }

    private static int romanToInt(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            int current = romanToIntMap.get(s.charAt(i));
            int next = (i + 1 < s.length()) ? romanToIntMap.get(s.charAt(i + 1)) : 0;
            if (current < next) {
                result -= current;
            } else {
                result += current;
            }
        }
        return result;
    }

    private static String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        for (int key : intToRomanMap.keySet()) {
            while (num >= key) {
                sb.append(intToRomanMap.get(key));
                num -= key;
            }
        }
        return sb.toString();
    }
}
