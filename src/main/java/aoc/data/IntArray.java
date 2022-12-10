package aoc.data;

import java.util.Arrays;
import java.util.List;

public class IntArray {
    final private int[][] array;

    public IntArray(int[][] array) {
        this.array = array;
    }

    public static IntArray fromStrings(final List<String> input) {
        final int[][] array = new int[input.size()][];
        int yIndex = 0;

        for (String line : input) {
            array[yIndex++] = line.chars().map(Character::getNumericValue).toArray();
        }
        return new IntArray(array);
    }

    public int getHorizontalSize() {
        return array[0].length;
    }

    public int getVerticalSize() {
        return array.length;
    }

    public int getValue(int x, int y) {
        return array[y][x];
    }

    public int[] getHorizontalSlice(int y) {
        return array[y];
    }

    public String getHorizontalSliceAsString(int y) {
        StringBuilder slice = new StringBuilder();
        Arrays.stream(getHorizontalSlice(y)).forEach(slice::append);
        return slice.toString();
    }

    public int[] getVerticalSlice(int x) {
        int[] slice = new int[array.length];
        for (int y = 0; y < array.length; y++) {
            slice[y] = getValue(x, y);
        }
        return slice;
    }

    public String getVerticalSliceAsString(int x) {
        StringBuilder slice = new StringBuilder();
        Arrays.stream(getVerticalSlice(x)).forEach(slice::append);
        return slice.toString();
    }

    public void printArray() {
        for (int y = 0; y < array.length; y++) {
            System.out.println(getHorizontalSliceAsString(y));
        }
    }
}
