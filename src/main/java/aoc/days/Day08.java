package aoc.days;

import aoc.util.FileUtil;

import java.util.List;

public class Day08 extends Day {
    private List<String> input;

    protected void initialize() throws Exception {
        input = FileUtil.readFileToStrings(getDay());
    }

    protected String getPart1Solution() {
        long result = 0;

        for (int x = 0; x < input.get(0).length(); x++) {
            for (int y = 0; y < input.size(); y++) {
                if (isVisible(x, y)) {
                    result++;
                }
            }
        }
        return "" + result;
    }

    boolean isVisible(int x, int y) {
        return isVisibleFromLeft(x, y) || isVisibleFromRight(x, y) || isVisibleFromTop(x, y) || isVisibleFromBelow(x, y);
    }

    private boolean isVisibleFromLeft(int x, int y) {
        final String threes = input.get(y).substring(0, x + 1);
        return isVisible(threes);
    }

    private boolean isVisibleFromRight(int x, int y) {
        final String threes = new StringBuilder(input.get(y).substring(x)).reverse().toString();
        return isVisible(threes);
    }

    private boolean isVisibleFromTop(int x, int y) {
        final String s = getSlice(x).substring(0, y + 1);
        return isVisible(s);
    }


    private boolean isVisibleFromBelow(int x, int y) {
        final String s = getSlice(x).substring(y);
        return isVisible(new StringBuilder(s).reverse().toString());
    }

    private boolean isVisible(String s) {
        int value = Character.getNumericValue(s.charAt(s.length() - 1));
        for (char c : s.substring(0, s.length() - 1).toCharArray()) {
            if (Character.getNumericValue(c) >= value) {
                return false;
            }
        }
        return true;
    }

    protected String getPart2Solution() {
        int maxScenicScore = 0;
        for (int x = 1; x < input.get(0).length(); x++) {
            for (int y = 0; y < input.size(); y++) {
                int scenicScore = getScenicScore(x, y);
                if (scenicScore > maxScenicScore) {
                    maxScenicScore = scenicScore;
                }
            }
        }
        return "" + maxScenicScore;
    }

    private int getScenicScore(int x, int y) {
        int result = 1;
        // Left
        result *= countVisible(input.get(y).substring(0, x + 1));
        // Right
        result *= countVisible(new StringBuilder(input.get(y).substring(x)).reverse().toString());
        // Up
        result *= countVisible(getSlice(x).substring(0, y + 1));
        // Down
        result *= countVisible(new StringBuilder(getSlice(x).substring(y)).reverse().toString());
        return result;
    }

    private int countVisible(String ss) {
        final String threes = new StringBuilder(ss).reverse().toString();
        int thisTreeHeight = Character.getNumericValue(threes.charAt(0));
        String otherThrees = threes.substring(1);
        int count = 0;
        for (char c : otherThrees.toCharArray()) {
            count++;
            if (thisTreeHeight <= Character.getNumericValue(c)) {
                return count;
            }
        }
        return count;
    }

    private String getSlice(int column) {
        final StringBuilder sb = new StringBuilder();
        for (String s : input) {
            sb.append(s.charAt(column));
        }
        return sb.toString();
    }
}