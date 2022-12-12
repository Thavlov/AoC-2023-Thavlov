package aoc.data;

import aoc.days.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntUnaryOperator;

public class IntArray {
    final private int[][] array;

    public IntArray(int[][] array) {
        this.array = array;
    }

    public static IntArray fromStrings(final List<String> input) {
        return fromStrings(input, Character::getNumericValue);
    }

    public static IntArray fromStrings(final List<String> input, IntUnaryOperator mapper) {
        final int[][] array = new int[input.size()][];
        int yIndex = 0;

        for (String line : input) {
            array[yIndex++] = line.chars().map(mapper).toArray();
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

    public List<Coordinate> findAllCoordinatesWithValue(int value) {
        final List<Coordinate> result = new ArrayList<>();
        for (int x = 0; x < getHorizontalSize(); x++) {
            for (int y = 0; y < getVerticalSize(); y++) {
                if (getValue(x, y) == value) {
                    result.add(new Coordinate(x, y));
                }
            }
        }
        return result;
    }

    public void printArray() {
        for (int y = 0; y < array.length; y++) {
            System.out.println(getHorizontalSliceAsString(y));
        }
    }

    public void printArrayDoubleDigit() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < array.length; y++) {
            for (int x = 0; x < array[0].length; x++) {
                stringBuilder.append(String.format(" %02d", getValue(x, y)));
            }
            System.out.println(stringBuilder.toString());
            stringBuilder = new StringBuilder();
        }
    }
}
