package aoc.util;

public class StringUtil {
    private StringUtil() {
        //EMPTY
    }

    public static String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static int parseInteger(char c) {
        return parseInteger("" + c);
    }

    public static int parseInteger(String s) {
        return Integer.parseInt(s);
    }


}
