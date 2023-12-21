package aoc.days;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import aoc.data.CharArray;
import aoc.data.Coordinate;
import aoc.data.Direction;
import aoc.data.Pair;
import aoc.util.FileUtil;

public class Day16 extends Day {
    private CharArray layout;
    protected void initialize() throws Exception {
        layout = CharArray.fromStrings(FileUtil.readFileToStrings(getDay()));
    }

    protected Object getPart1Solution() {
        Coordinate positionCoordinate = Coordinate.of(-1, 0);
        Direction positionDirection = new Direction(Direction.Directions.RIGHT);

        Set<Pair<Coordinate, Direction>> positionsVisited = getPositionsVisited(positionCoordinate, positionDirection);
        return getCoordinatesVisited(positionsVisited);
    }

    protected Object getPart2Solution() {
        long result = 0;

        for (int y = 0; y < layout.getHeight(); y++) {
            Coordinate positionCoordinate = Coordinate.of(-1, y);
            Direction positionDirection = new Direction(Direction.Directions.RIGHT);
            Set<Pair<Coordinate, Direction>> positionsVisited = getPositionsVisited(positionCoordinate, positionDirection);
            result = Math.max(result, getCoordinatesVisited(positionsVisited));
        }

        for (int y = 0; y < layout.getHeight(); y++) {
            Coordinate positionCoordinate = Coordinate.of(layout.getWidth(), y);
            Direction positionDirection = new Direction(Direction.Directions.LEFT);
            Set<Pair<Coordinate, Direction>> positionsVisited = getPositionsVisited(positionCoordinate, positionDirection);
            result = Math.max(result, getCoordinatesVisited(positionsVisited));
        }

        for (int x = 0; x < layout.getWidth(); x++) {
            Coordinate positionCoordinate = Coordinate.of(x, -1);
            Direction positionDirection = new Direction(Direction.Directions.DOWN);
            Set<Pair<Coordinate, Direction>> positionsVisited = getPositionsVisited(positionCoordinate, positionDirection);
            result = Math.max(result, getCoordinatesVisited(positionsVisited));
        }

        for (int x = 0; x < layout.getWidth(); x++) {
            Coordinate positionCoordinate = Coordinate.of(x, layout.getWidth());
            Direction positionDirection = new Direction(Direction.Directions.UP);
            Set<Pair<Coordinate, Direction>> positionsVisited = getPositionsVisited(positionCoordinate, positionDirection);
            result = Math.max(result, getCoordinatesVisited(positionsVisited));
        }

        return result;
    }

    private Set<Pair<Coordinate, Direction>> getPositionsVisited(Coordinate positionNewCoordinate, Direction positionNewDirection) {
        final Set<Pair<Coordinate, Direction>> positionsVisited = new HashSet<>();

        final Deque<Pair<Coordinate, Direction>> light = new ArrayDeque<>();
        light.add(Pair.of(positionNewCoordinate, positionNewDirection));

        boolean firstIteration = true;

        while (!light.isEmpty()) {
            Pair<Coordinate, Direction> position = light.pop();
            positionNewCoordinate = position.getFirst();
            positionNewDirection = position.getSecond();

            if (positionsVisited.contains(Pair.of(positionNewCoordinate, positionNewDirection))) {
                continue;
            }

            if (firstIteration) {
                firstIteration = false;
            } else {
                positionsVisited.add(Pair.of(positionNewCoordinate, positionNewDirection));
            }

            Coordinate nextCoordinate = positionNewDirection.getNext(positionNewCoordinate);
            if (nextCoordinate.getX() < 0 || nextCoordinate.getX() > layout.getWidth() - 1 || nextCoordinate.getY() < 0 || nextCoordinate.getY() > layout.getHeight() - 1) {
                continue;
            }

            char value = layout.getValue(nextCoordinate);

            switch (value) {
                case '.':
                    light.add(Pair.of(nextCoordinate, positionNewDirection));
                    break;
                case '/':
                    if (positionNewDirection.getDirection() == Direction.Directions.UP) {
                        light.add(Pair.of(nextCoordinate, new Direction(Direction.Directions.RIGHT)));
                    } else if (positionNewDirection.getDirection() == Direction.Directions.DOWN) {
                        light.add(Pair.of(nextCoordinate, new Direction(Direction.Directions.LEFT)));
                    } else if (positionNewDirection.getDirection() == Direction.Directions.RIGHT) {
                        light.add(Pair.of(nextCoordinate, new Direction(Direction.Directions.UP)));
                    } else if (positionNewDirection.getDirection() == Direction.Directions.LEFT) {
                        light.add(Pair.of(nextCoordinate, new Direction(Direction.Directions.DOWN)));
                    }
                    break;
                case '\\':
                    if (positionNewDirection.getDirection() == Direction.Directions.UP) {
                        light.add(Pair.of(nextCoordinate, new Direction(Direction.Directions.LEFT)));
                    } else if (positionNewDirection.getDirection() == Direction.Directions.DOWN) {
                        light.add(Pair.of(nextCoordinate, new Direction(Direction.Directions.RIGHT)));
                    } else if (positionNewDirection.getDirection() == Direction.Directions.RIGHT) {
                        light.add(Pair.of(nextCoordinate, new Direction(Direction.Directions.DOWN)));
                    } else if (positionNewDirection.getDirection() == Direction.Directions.LEFT) {
                        light.add(Pair.of(nextCoordinate, new Direction(Direction.Directions.UP)));
                    }
                    break;
                case '|':
                    if (positionNewDirection.getDirection() == Direction.Directions.UP || positionNewDirection.getDirection() == Direction.Directions.DOWN) {
                        light.add(Pair.of(nextCoordinate, positionNewDirection));
                    } else if (positionNewDirection.getDirection() == Direction.Directions.RIGHT || positionNewDirection.getDirection() == Direction.Directions.LEFT) {
                        light.add(Pair.of(nextCoordinate, new Direction(Direction.Directions.DOWN)));
                        light.add(Pair.of(nextCoordinate, new Direction(Direction.Directions.UP)));
                    }
                    break;
                case '-':
                    if (positionNewDirection.getDirection() == Direction.Directions.RIGHT || positionNewDirection.getDirection() == Direction.Directions.LEFT) {
                        light.add(Pair.of(nextCoordinate, positionNewDirection));
                    } else if (positionNewDirection.getDirection() == Direction.Directions.UP || positionNewDirection.getDirection() == Direction.Directions.DOWN) {
                        light.add(Pair.of(nextCoordinate, new Direction(Direction.Directions.RIGHT)));
                        light.add(Pair.of(nextCoordinate, new Direction(Direction.Directions.LEFT)));
                    }
                    break;
                default:
                    throw new RuntimeException();

            }
        }
        return positionsVisited;
    }

    private static int getCoordinatesVisited(Set<Pair<Coordinate, Direction>> positionsVisited) {
        return positionsVisited.stream().map(Pair::getFirst).collect(Collectors.toSet()).size();
    }
}
