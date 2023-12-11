package aoc.days;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import aoc.data.CharArray;
import aoc.data.Coordinate;
import aoc.data.Direction;
import aoc.util.FileUtil;

public class Day10 extends Day {
    private CharArray map;

    protected void initialize() throws Exception {
        map = CharArray.fromStrings(FileUtil.readFileToStrings(getDay()));
    }

    protected Object getPart1Solution() throws Exception {
        Coordinate location = map.findCoordinateWith('S');

        Direction direction = new Direction(Direction.Directions.DOWN);
        char value = '|';

        int steps = 0;
        while (value != 'S') {
            location = direction.moveToNextCoordinate(location, value);
            value = map.getValue(location);
            steps++;
        }

        return (steps + 1) / 2;
    }

    protected Object getPart2Solution() throws Exception {
        Coordinate location = map.findCoordinateWith('S');

        Direction direction = new Direction(Direction.Directions.DOWN);
        char value = '|';

        Set<Coordinate> pointsInsidePath = new HashSet<>();
        while (value != 'S') {
            pointsInsidePath.addAll(direction.getCoordinateToTheRight(location, value));

            location = direction.moveToNextCoordinate(location, value);
            value = map.getValue(location);

            map.setValue(location, 'X');
        }

        List<Coordinate> frameCoordinatesNotInPath = map.getFrameCoordinates().stream().filter(notPath()).collect(Collectors.toList());
        Deque<Coordinate> outsidePoints = new ArrayDeque<>(frameCoordinatesNotInPath);

        Coordinate outsidePoint;
        while (!outsidePoints.isEmpty()) {
            outsidePoint = outsidePoints.pop();
            map.setValue(outsidePoint, 'O');

            Arrays.stream(outsidePoint.getCoordinateAdjacentInsideBounds(map.getWidth(), map.getHeight())).filter(notVisited()).forEach(outsidePoints::push);
        }

        pointsInsidePath = pointsInsidePath.stream().filter(notVisited()).collect(Collectors.toSet());

        // Expand pockets
        Set<Coordinate> coordinatesInPockets = createPockets(pointsInsidePath);

        return coordinatesInPockets.size();

    }

    private Set<Coordinate> createPockets(Set<Coordinate> coordinates) {
        Set<Coordinate> result = new HashSet<>();

        final Deque<Coordinate> pocketCoordinates = new ArrayDeque<>(coordinates);

        Coordinate pocketCoordinate;

        while (!pocketCoordinates.isEmpty()) {
            pocketCoordinate = pocketCoordinates.pop();
            result.add(pocketCoordinate);
            Arrays.stream(pocketCoordinate.getCoordinateAdjacent()).filter(notVisited()).filter(c -> !result.contains(c)).forEach(pocketCoordinates::push);
        }
        return result;
    }

    private Predicate<Coordinate> notPath() {
        return c -> map.getValue(c) != 'S' && map.getValue(c) != 'X';
    }

    private Predicate<Coordinate> notVisited() {
        return c -> map.getValue(c) != 'S' && map.getValue(c) != 'X' && map.getValue(c) != 'O' && map.getValue(c) != '?';
    }
}