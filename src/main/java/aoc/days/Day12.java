package aoc.days;

import aoc.data.CharArray;
import aoc.data.IntArray;
import aoc.util.FileUtil;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day12 extends Day {
    private static final int MAX_STEPS = 1_000;
    private IntArray heightMap;
    private Coordinate startCoordinate;
    private Coordinate endCoordinate;

    protected void initialize() throws Exception {
        final List<String> input = FileUtil.readFileToStrings(getDay());
        final CharArray inputMap = CharArray.fromStrings(input);

        startCoordinate = inputMap.findCoordinateWith('S');
        endCoordinate = inputMap.findCoordinateWith('E');

        inputMap.setValue(startCoordinate.getX(), startCoordinate.getY(), 'a');
        inputMap.setValue(endCoordinate.getX(), endCoordinate.getY(), 'z');

        heightMap = inputMap.toIntArray('a');
    }

    protected String getPart1Solution() {
        return "" + findShortestPathFrom(startCoordinate, endCoordinate);
    }

    protected String getPart2Solution() {
        int shortestPath = 500;
        final List<Coordinate> startCoordinates = findAllStartCoordinates();

        int temp;
        for (Coordinate coordinate : startCoordinates) {
            temp = findShortestPathFrom(coordinate, endCoordinate);
            if (temp < shortestPath) {
                shortestPath = temp;
            }
        }
        return "" + shortestPath;
    }

    private int findShortestPathFrom(final Coordinate from, final Coordinate to) {
        final Set<Coordinate> frontierCoordinates = new HashSet<>();
        frontierCoordinates.add(from);

        final Map<Coordinate, Integer> distances = new HashMap<>();

        int steps = 0;
        final List<Coordinate> tempCoordinates = new ArrayList<>();

        outerLoop:
        do {
            for (Coordinate frontierCoordinate : frontierCoordinates) {
                distances.put(frontierCoordinate, steps);
                List<Coordinate> adjacentCoordinatesNotVisited = getAdjacentCoordinatesNotVisited(frontierCoordinate, distances.keySet());
                if (adjacentCoordinatesNotVisited.contains(to)) {
                    steps++;
                    break outerLoop;
                }
                tempCoordinates.addAll(adjacentCoordinatesNotVisited);
            }
            frontierCoordinates.clear();
            frontierCoordinates.addAll(tempCoordinates);
            tempCoordinates.clear();
            steps++;
        } while (steps < MAX_STEPS);
        return steps;
    }

    private List<Coordinate> findAllStartCoordinates() {
        return heightMap.findAllCoordinatesWithValue(0);
    }

    private List<Coordinate> getAdjacentCoordinatesNotVisited(final Coordinate coordinate, Set<Coordinate> coordinatesVisited) {
        final Coordinate[] adjacentCoordinates = coordinate.getCoordinateAdjacent();
        return Arrays.stream(adjacentCoordinates)
                .filter(isInsideBounds())
                .filter(isStepValidFrom(coordinate))
                .filter(notContainedIn(coordinatesVisited))
                .collect(Collectors.toList());
    }

    private Predicate<Coordinate> isInsideBounds() {
        return c -> c.getX() >= 0 && c.getX() < heightMap.getHorizontalSize() && c.getY() >= 0 && c.getY() < heightMap.getVerticalSize();
    }

    private Predicate<Coordinate> isStepValidFrom(Coordinate from) {
        return to -> getHeightDiff(from, to) <= 1;
    }

    private Predicate<Coordinate> notContainedIn(Set<Coordinate> coordinates) {
        return c -> !coordinates.contains(c);
    }

    private int getHeightDiff(Coordinate from, Coordinate to) {
        return getMapHeight(to) - getMapHeight(from);
    }

    private int getMapHeight(Coordinate c) {
        return heightMap.getValue(c.getX(), c.getY());
    }
}