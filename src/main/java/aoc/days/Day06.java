package aoc.days;

import aoc.util.FileUtil;

public class Day06 extends Day {
    private String input;

    protected void initialize() throws Exception {
        input = FileUtil.readSingleStringFile(getDay());
    }

    protected String getPart1Solution() throws Exception {
        return "" + findMarker(input, 4);
    }

    protected String getPart2Solution() throws Exception {
        return "" + findMarker(input, 14);
    }

    private int findMarker(String s, int numberOfUniqueLetters) throws Exception {
        for (int i = numberOfUniqueLetters; i < s.length(); i++) {
            if (allUnique(s.substring(i - numberOfUniqueLetters, i))) {
                return i;
            }
        }
        throw new Exception("Error!");
    }

    private boolean allUnique(String s) {
        final char[] chars = s.toCharArray();
        for (char aChar : chars) {
            if (countChar(s, aChar) > 1) {
                return false;
            }
        }
        return true;
    }

    protected int countChar(String someString, char someChar) {
        int count = 0;

        for (int i = 0; i < someString.length(); i++) {
            if (someString.charAt(i) == someChar) {
                count++;
            }
        }
        return count;
    }
}