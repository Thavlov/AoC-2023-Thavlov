package aoc.days;

import aoc.data.IntArray;
import aoc.util.FileUtil;
import aoc.util.StringUtil;

import java.util.List;

public class Day08 extends Day {
    private IntArray trees;

    protected void initialize() throws Exception {
        final List<String> input = FileUtil.readFileToStrings(getDay());
        trees = IntArray.fromStrings(input);
    }

    protected String getPart1Solution() {
        long result = 0;

        for (int x = 0; x < trees.getHorizontalSize(); x++) {
            for (int y = 0; y < trees.getVerticalSize(); y++) {
                if (isVisible(x, y)) {
                    result++;
                }
            }
        }
        return "" + result;
    }

    protected String getPart2Solution() {
        int maxScenicScore = 0;
        for (int x = 1; x < trees.getHorizontalSize(); x++) {
            for (int y = 0; y < trees.getVerticalSize(); y++) {
                int scenicScore = getScenicScore(x, y);
                if (scenicScore > maxScenicScore) {
                    maxScenicScore = scenicScore;
                }
            }
        }
        return "" + maxScenicScore;
    }

    boolean isVisible(int x, int y) {
        return isVisibleFromLeft(x, y) || isVisibleFromRight(x, y) || isVisibleFromTop(x, y) || isVisibleFromBelow(x, y);
    }

    private boolean isVisibleFromLeft(int x, int y) {
        final String treeLine = trees.getHorizontalSliceAsString(y).substring(0, x + 1);
        return isVisible(treeLine);
    }

    private boolean isVisibleFromRight(int x, int y) {
        final String treeLine = StringUtil.reverseString(trees.getHorizontalSliceAsString(y).substring(x));
        return isVisible(treeLine);
    }

    private boolean isVisibleFromTop(int x, int y) {
        final String treeLine = trees.getVerticalSliceAsString(x).substring(0, y + 1);
        return isVisible(treeLine);
    }

    private boolean isVisibleFromBelow(int x, int y) {
        final String treeLine = StringUtil.reverseString(trees.getVerticalSliceAsString(x).substring(y));
        return isVisible(treeLine);
    }

    private boolean isVisible(String s) {
        int value = StringUtil.parseInteger(s.charAt(s.length() - 1));
        for (char c : s.substring(0, s.length() - 1).toCharArray()) {
            if (StringUtil.parseInteger(c) >= value) {
                return false;
            }
        }
        return true;
    }

    private int getScenicScore(int x, int y) {
        int result = 1;
        // Left
        result *= countVisible(trees.getHorizontalSliceAsString(y).substring(0, x + 1));
        // Right
        result *= countVisible(StringUtil.reverseString(trees.getHorizontalSliceAsString(y).substring(x)));
        // Up
        result *= countVisible(trees.getVerticalSliceAsString(x).substring(0, y + 1));
        // Down
        result *= countVisible(StringUtil.reverseString(trees.getVerticalSliceAsString(x).substring(y)));
        return result;
    }

    private int countVisible(String s) {
        final String treeLine = StringUtil.reverseString(s);
        int thisTreeHeight = StringUtil.parseInteger(treeLine.charAt(0));
        String otherThrees = treeLine.substring(1);
        int count = 0;
        for (char c : otherThrees.toCharArray()) {
            count++;
            if (thisTreeHeight <= StringUtil.parseInteger(c)) {
                return count;
            }
        }
        return count;
    }
}