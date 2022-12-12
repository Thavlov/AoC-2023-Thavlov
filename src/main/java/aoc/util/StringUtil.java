package aoc.util;

import aoc.data.Monkey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static int parseInteger(String s) {
        return Integer.parseInt(s);
    }


}
