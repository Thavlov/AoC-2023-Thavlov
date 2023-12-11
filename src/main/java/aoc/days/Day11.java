package aoc.days;

import java.util.ArrayList;
import java.util.List;

import aoc.data.CharArray;
import aoc.data.Coordinate;
import aoc.util.FileUtil;

public class Day11 extends Day {

    private CharArray starMap;

    protected void initialize() throws Exception {
        final List<String> input = FileUtil.readFileToStrings(getDay());
        starMap = CharArray.fromStrings(input);
    }

    protected Object getPart1Solution() {
        final List<Coordinate> galaxies = createGalaxiesFromMapWithDistance(starMap, 1);

        return getDistanceBetweenGalaxies(galaxies);
    }

    protected Object getPart2Solution() {
        final List<Coordinate> galaxies = createGalaxiesFromMapWithDistance(starMap, 1000000 - 1);

        return getDistanceBetweenGalaxies(galaxies);
    }

    private List<Coordinate> createGalaxiesFromMapWithDistance(CharArray starMap, int distance) {
        List<Coordinate> galaxiesCoordinates = starMap.findAllCoordinatesWith('#');

        for (int column = starMap.getWidth() - 1; column >= 0; column--) {
            if (isEmpty(starMap.getVerticalSlice(column))) {
                galaxiesCoordinates = expandRight(galaxiesCoordinates, column, distance);
            }
        }

        for (int row = starMap.getHeight() - 1; row >= 0; row--) {
            if (isEmpty(starMap.getHorizontalSlice(row))) {
                galaxiesCoordinates = expandDown(galaxiesCoordinates, row, distance);
            }
        }
        return galaxiesCoordinates;
    }

    private List<Coordinate> expandRight(List<Coordinate> coordinates, int column, int distance) {
        List<Coordinate> result = new ArrayList<>();
        for (Coordinate coordinate : coordinates) {
            if (coordinate.getX() > column) {
                result.add(Coordinate.of(coordinate.getX() + distance, coordinate.getY()));
            } else {
                result.add(coordinate);
            }
        }
        return result;
    }

    private List<Coordinate> expandDown(List<Coordinate> coordinates, int row, int distance) {
        List<Coordinate> result = new ArrayList<>();
        for (Coordinate coordinate : coordinates) {
            if (coordinate.getY() > row) {
                result.add(Coordinate.of(coordinate.getX(), coordinate.getY() + distance));
            } else {
                result.add(coordinate);
            }
        }
        return result;
    }

    private boolean isEmpty(char[] slice) {
        for (char c : slice) {
            if (c != '.') {
                return false;
            }
        }
        return true;
    }

    private long getDistanceBetweenGalaxies(List<Coordinate> galaxies) {
        long result = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                result += galaxies.get(i).manhattanDistanceTo(galaxies.get(j));
            }
        }
        return result;
    }
}
