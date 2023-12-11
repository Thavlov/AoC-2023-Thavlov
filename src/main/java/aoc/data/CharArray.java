package aoc.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CharArray {
    private static final char FILL_CHAR = '.';
    private final char[][] array;
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

        CharArray result = new CharArray(xdim + 1, ydim + 1);
        result.xOffset = minX > 0 ? 0 : -minX;
        result.yOffset = minY > 0 ? 0 : -minY;

        for (Coordinate coordinate : coordinates) {
            result.setValue(Coordinate.of(coordinate.getX(), coordinate.getY()), '#');
        }
        return result;
    }

    public static CharArray fromCoordinates(List<Coordinate> coordinates, int width, int height, char c) {
        if (coordinates.isEmpty()) {
            return new CharArray(1);
        }
        int minX = coordinates.stream().mapToInt(Coordinate::getX).min().orElseThrow(() -> new RuntimeException(""));
        int maxX = coordinates.stream().mapToInt(Coordinate::getX).max().orElseThrow(() -> new RuntimeException(""));
        int minY = coordinates.stream().mapToInt(Coordinate::getY).min().orElseThrow(() -> new RuntimeException(""));
        int maxY = coordinates.stream().mapToInt(Coordinate::getY).max().orElseThrow(() -> new RuntimeException(""));

        int xdim = minX > 0 ? maxX : maxX - minX;
        int ydim = minY > 0 ? maxY : maxY - minY;

        CharArray result = new CharArray(width, height);
        result.xOffset = minX > 0 ? 0 : -minX;
        result.yOffset = minY > 0 ? 0 : -minY;

        for (Coordinate coordinate : coordinates) {
            result.setValue(Coordinate.of(coordinate.getX(), coordinate.getY()), c);
        }
        return result;
    }

    public List<Coordinate> getFrameCoordinates() {
        List<Coordinate> result = new ArrayList<>();
        result.addAll(List.of(getHorizontalSliceAsCoordinates(0)));
        result.addAll(Arrays.stream(getVerticalSliceAsCoordinates(0)).skip(1).limit(getHeight() - 2L).collect(Collectors.toList()));
        result.addAll(Arrays.stream(getVerticalSliceAsCoordinates(getWidth() - 1)).skip(1).limit(getWidth() - 2L).collect(Collectors.toList()));
        result.addAll(List.of(getHorizontalSliceAsCoordinates(getHeight() - 1)));
        return result;
    }

    /*    public IntArray toIntArray(char base) {
            int[][] intArray = new int[array.length][array[0].length];

            for (int x = 0; x < array[0].length; x++) {
                for (int y = 0; y < array.length; y++) {
                    intArray[y][x] = getValue(x, y) - base;
                }
            }
            return new IntArray(intArray);
        }
    */
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

    public Coordinate[] getHorizontalSliceAsCoordinates(int y) {
        Coordinate[] slice = new Coordinate[array[y].length];
        for (int x = 0; x < array[y].length; x++) {
            slice[x] = Coordinate.of(x, y);
        }
        return slice;
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

    public Coordinate[] getVerticalSliceAsCoordinates(int x) {
        Coordinate[] slice = new Coordinate[array.length];
        for (int y = 0; y < array.length; y++) {
            slice[y] = Coordinate.of(x, y);
        }
        return slice;
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

    public List<Coordinate> findAllCoordinatesWith(char c) {
        List<Coordinate> result = new ArrayList<>();
        for (int x = 0; x < getHorizontalSize(); x++) {
            for (int y = 0; y < getVerticalSize(); y++) {
                if (getValue(x, y) == c) {
                    result.add(Coordinate.of(x, y));
                }
            }
        }
        return result;
    }

    public List<Coordinate> findAllCoordinateLongsWith(char c) {
        List<Coordinate> result = new ArrayList<>();
        for (int x = 0; x < getHorizontalSize(); x++) {
            for (int y = 0; y < getVerticalSize(); y++) {
                if (getValue(x, y) == c) {
                    result.add(Coordinate.of(x, y));
                }
            }
        }
        return result;
    }

    public List<Coordinate> getAllCoordinates() {
        List<Coordinate> result = new ArrayList<>();
        for (int x = 0; x < getHorizontalSize(); x++) {
            for (int y = 0; y < getVerticalSize(); y++) {
                result.add(Coordinate.of(x - xOffset, y - yOffset));
            }
        }
        return result;
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

    public CharArray copyOf() {
//        char[][] newArray = new char[array.length][array[0].length];
//
//        for (int i = 0; i < array.length; i++) {
//            newArray[i] = Arrays.copyOf(array[i], array[i].length);
//        }

        CharArray result = new CharArray(this.array);
        result.xOffset = this.xOffset;
        result.yOffset = this.yOffset;
        return result;
    }
}
