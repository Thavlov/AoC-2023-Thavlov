package aoc.days;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import aoc.data.CharArray;
import aoc.data.Coordinate;
import aoc.util.FileUtil;

public class Day21 extends Day {
    private CharArray map;

    protected void initialize() throws Exception {
        map = CharArray.fromStrings(FileUtil.readFileToStrings(getDay()));
    }

    protected Object getPart1Solution() {
        Coordinate coordinate = map.findCoordinateWith('S');

        Set<Coordinate> coordinates = new HashSet<>();
        coordinates.add(coordinate);

        Deque<Coordinate> deque;

        int counter = 0;

        while (counter < 64) {
            deque = new ArrayDeque<>(coordinates);
            coordinates = new HashSet<>();

            while (!deque.isEmpty()) {
                coordinate = deque.pop();

                List<Coordinate> coordinateAdjacent = filterCoordinatesInsideMap(coordinate.getCoordinateAdjacentInsideBounds(map.getWidth(), map.getHeight()), map);
                coordinates.addAll(coordinateAdjacent);
            }
            counter++;
        }

        return coordinates.size();
    }

    protected Object getPart2Solution() {
        final long iterationsNeeded = 26501365L;
        final long twiceMapWidth = 2L * map.getWidth();
        final long distanceToEdge = map.getWidth() - map.findCoordinateWith('S').getX() - 1L;

        Coordinate coordinate = map.findCoordinateWith('S');

        Set<Coordinate> coordinates = new HashSet<>();
        coordinates.add(coordinate);

        Deque<Coordinate> deque;

        final Map<Coordinate, Integer> earlierVisited = new HashMap<>();
        earlierVisited.put(coordinate, 0);

        int counter = 1;
        long coordinateCount = 0;
        long lastCoordinateCount = 0;
        long diff1 = 0L;
        long diff2;

        while (true) {
            deque = new ArrayDeque<>(coordinates);
            coordinates = new HashSet<>();

            while (!deque.isEmpty()) {
                coordinate = deque.pop();
                List<Coordinate> coordinateAdjacentNotRocks = filterCoordinatesOutsideMap(coordinate.getCoordinateAdjacent(), map);
                for (Coordinate coordinateAdjacentNotRock : coordinateAdjacentNotRocks) {
                    if (earlierVisited.get(coordinateAdjacentNotRock) == null) {
                        coordinates.add(coordinateAdjacentNotRock);
                    }
                }
            }

            for (Coordinate c : coordinates) {
                earlierVisited.putIfAbsent(c, counter);
            }

            if (counter % 2 == 1) {
                coordinateCount += coordinates.size();
                if (counter % twiceMapWidth == distanceToEdge) {
                    if (lastCoordinateCount != 0 && diff1 == 0) {
                        diff1 = coordinateCount - lastCoordinateCount;
                    } else if (lastCoordinateCount != 0) {
                        diff2 = coordinateCount - lastCoordinateCount;
                        break;
                    }
                    lastCoordinateCount = coordinateCount;
                }
            }
            counter++;
        }

        long totalRepeatsNeeded = iterationsNeeded / twiceMapWidth - 1L;
        long currentRepeats = counter / twiceMapWidth - 1L;
        long ekstraRepeatsNeeded = totalRepeatsNeeded - currentRepeats;
        return coordinateCount + ((totalRepeatsNeeded * (totalRepeatsNeeded + 1)) / 2 - 1) * (diff2 - diff1) + diff1 * ekstraRepeatsNeeded;
    }

    private List<Coordinate> filterCoordinatesInsideMap(Coordinate[] coordinateAdjacent, CharArray map) {
        return Arrays.stream(coordinateAdjacent).filter(c -> map.getValue(c) != '#').collect(Collectors.toList());
    }

    private List<Coordinate> filterCoordinatesOutsideMap(Coordinate[] coordinateAdjacent, CharArray map) {
        return Arrays.stream(coordinateAdjacent).filter(isNotRock(map)).collect(Collectors.toList());
    }

    private Predicate<Coordinate> isNotRock(CharArray map) {
        return c -> map.getValue(getCoordinateInsideBounds(c, map.getWidth(), map.getHeight())) != '#';
    }

    private Coordinate getCoordinateInsideBounds(Coordinate c, int width, int height) {
        if (c.isWithinBounds(width, height)) {
            return c;
        }
        return Coordinate.of(Math.floorMod(c.getX(), width), Math.floorMod(c.getY(), height));
    }
}
