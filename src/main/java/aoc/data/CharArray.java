package aoc.data;

import aoc.days.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharArray {
    private static final char FILL_CHAR = '.';
    final private char[][] array;
    private int xOffset = 0;
    private int yOffset = 0;

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

    public static CharArray fromCoordinates(List<Coordinate> coordinates) {
        if (coordinates.isEmpty()) {
            return new CharArray(1);
        }
        int minX = coordinates.stream().mapToInt(Coordinate::getX).min().orElseThrow(() -> new RuntimeException(""));
        int maxX = coordinates.stream().mapToInt(Coordinate::getX).max().orElseThrow(() -> new RuntimeException(""));
        int minY = coordinates.stream().mapToInt(Coordinate::getY).min().orElseThrow(() -> new RuntimeException(""));
        int maxY = coordinates.stream().mapToInt(Coordinate::getY).max().orElseThrow(() -> new RuntimeException(""));

        int xdim = minX > 0 ? maxX : maxX - minX;
        int ydim = minY > 0 ? maxY : maxY - minY;

        CharArray result = new CharArray(xdim+1, ydim+1);
        result.xOffset = minX > 0 ? 0 : -minX;
        result.yOffset = minY > 0 ? 0 : -minY;

        for (Coordinate coordinate : coordinates) {
            result.setValue(Coordinate.of(coordinate.getX(), coordinate.getY()), '#');
        }
        return result;
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

    public char getValue(int x, int y) {
        return array[y][x];
    }

    public char getValue(Coordinate coordinate) {
        return getValue(coordinate.getX() + xOffset, coordinate.getY() + yOffset);
    }

    public boolean isOutOfBounds(Coordinate coordinate) {
        try {
            char c = array[coordinate.getY()][coordinate.getX()]; // TODO MORE GENTLE
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }
    }

    public void setValue(Coordinate coordinate, char c) {
        setValue(coordinate.getX() + xOffset, coordinate.getY() + yOffset, c);
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

    public List<Coordinate> toCoordinates() {
        List<Coordinate> result = new ArrayList<>();
        for (int x = 0; x < getHorizontalSize(); x++) {
            for (int y = 0; y < getVerticalSize(); y++) {
                if (getValue(x, y) == '#') {
                    result.add(Coordinate.of(x - xOffset, y - yOffset));
                }
            }
        }
        return result;
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
            System.out.println(getHorizontalSliceAsString(i).substring(x, x + dx));
        }
    }
}
