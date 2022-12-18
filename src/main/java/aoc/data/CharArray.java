package aoc.data;

import aoc.days.Coordinate;

import java.util.Arrays;
import java.util.List;

public class CharArray {
    private static final char FILL_CHAR = '.';
    final private char[][] array;

    public CharArray(int size) {
        this(size, size);
    }

    public CharArray(int sizeX, int sizeY) {
        this.array = new char[sizeY][sizeX];
        fillArray(FILL_CHAR);
    }

    public CharArray(char[][] array) {
        this.array = array;
    }

    public static CharArray fromStrings(final List<String> input) {
        final char[][] array = new char[input.size()][];
        int yIndex = 0;

        for (String line : input) {
            array[yIndex++] = line.toCharArray();
        }
        return new CharArray(array);
    }

    public IntArray toIntArray(char base) {
        int[][] intArray = new int[array.length][array[0].length];

        for (int x = 0; x < array[0].length; x++) {
            for (int y = 0; y < array.length; y++) {
                intArray[y][x] = getValue(x, y) - base;
            }
        }
        return new IntArray(intArray);
    }

    public int getHorizontalSize() {
        return array[0].length;
    }

    public int getVerticalSize() {
        return array.length;
    }

    public char getValue(Coordinate coordinate) {
        return array[coordinate.getY()][coordinate.getX()];
    }

    public boolean isOutOfBounds(Coordinate coordinate) {
        try {
            char c = array[coordinate.getY()][coordinate.getX()]; // TODO MORE GENTLE
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }
    }

    public char getValue(int x, int y) {
        return array[y][x];
    }

    public void setValue(Coordinate coordinate, char c) {
        setValue(coordinate.getX(), coordinate.getY(), c);
    }

    public void setValue(int x, int y, char c) {
        array[y][x] = c;
    }

    public char[] getHorizontalSlice(int y) {
        return array[y];
    }

    public String getHorizontalSliceAsString(int y) {
        return new String(getHorizontalSlice(y));
    }

    public char[] getVerticalSlice(int x) {
        char[] slice = new char[array.length];
        for (int y = 0; y < array.length; y++) {
            slice[y] = getValue(x, y);
        }
        return slice;
    }

    public String getVerticalSliceAsString(int x) {
        return new String(getVerticalSlice(x));
    }

    public Coordinate findCoordinateWith(char c) {
        for (int x = 0; x < getHorizontalSize(); x++) {
            for (int y = 0; y < getVerticalSize(); y++) {
                if (getValue(x, y) == c) {
                    return new Coordinate(x, y);
                }
            }
        }
        throw new RuntimeException("Coordinate not found");
    }

    private void fillArray(char c) {
        for (char[] chars : array) {
            Arrays.fill(chars, c);
        }
    }

    public void fillBetween(Coordinate c1, Coordinate c2, char c) {
        Coordinate[] allCoordinatesBetween = Coordinate.getAllCoordinatesBetween(c1, c2);
        for (Coordinate coordinate : allCoordinatesBetween) {
            setValue(coordinate, c);
        }
    }

    public int getWidth() {
        return array[0].length;
    }

    public int getHeight() {
        return array.length;
    }

    public void printArray() {
        for (int y = 0; y < array.length; y++) {
            System.out.println(getHorizontalSliceAsString(y));
        }
    }

    public void printSection(int x, int dx, int y, int dy) {
        for (int i = y; i <= y + dy; i++) {
            System.out.println(getHorizontalSliceAsString(i).substring(x, x + dx ));
        }
    }
}
