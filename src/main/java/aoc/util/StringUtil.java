package aoc.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    private StringUtil() {
        //EMPTY
    }

    public static String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static List<List<String>> splitInGroupsSeparatedByEmptyLine(List<String> strings) {
        return splitInGroupsSeparatedBy(strings, "");
    }

    public static List<List<String>> splitInGroupsSeparatedBy(List<String> strings, String separateBy) {
        List<List<String>> result = new ArrayList<>();
        List<String> tempArray = new ArrayList<>();
        for (String s : strings) {
            if (separateBy.equals(s)) {
                result.add(tempArray);
                tempArray = new ArrayList<>();
                continue;
            }
            tempArray.add(s);
        }
        result.add(tempArray);
        return result;
    }

    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int parseInteger(char c) {
        return parseInteger("" + c);
    }

    public static int parseInteger(String s) {
        return Integer.parseInt(s);
    }

    public static String padRightToNewSize(String string, int newSize) {
        final int paddingLength = newSize - string.length();
        if (paddingLength < 0) {
            throw new RuntimeException(String.format("Error: String '%s' cannot be padded to size %d.", string, newSize));
        }

        return string + " ".repeat(paddingLength);
    }

    public static int parseNumberFromString(String string) {
        switch (string) {
            case "0":
            case "zero":
                return 0;
            case "1":
            case "one":
                return 1;
            case "2":
            case "two":
                return 2;
            case "3":
            case "three":
                return 3;
            case "4":
            case "four":
                return 4;
            case "5":
            case "five":
                return 5;
            case "6":
            case "six":
                return 6;
            case "7":
            case "seven":
                return 7;
            case "8":
            case "eight":
                return 8;
            case "9":
            case "nine":
                return 9;
            default:
                throw new IllegalArgumentException(String.format("Error: String '%s' cannot be parsed to number.", string));
        }
    }
}
