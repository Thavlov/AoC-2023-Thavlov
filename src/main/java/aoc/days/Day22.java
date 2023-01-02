package aoc.days;

import aoc.data.CharArray;
import aoc.data.Pair;
import aoc.util.FileUtil;
import aoc.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day22 extends Day {
    private enum Direction {UP, RIGHT, DOWN, LEFT}
    private Map<Pair<Coordinate, Direction>, Pair<Coordinate, Direction>> cubeEdgeWrapping;

    private CharArray board;
    private List<String> steps;
    private Direction direction;

    protected void initialize() throws Exception {
        List<String> input = FileUtil.readFileToStrings(getDay());
        List<List<String>> lists = StringUtil.splitInGroupsSeparatedByEmptyLine(input);
        final int maxLength = lists.get(0).stream().mapToInt(String::length).max().getAsInt();

        List<String> boardLines = lists.get(0).stream().map(s -> StringUtil.padRightToNewSize(s, maxLength)).collect(Collectors.toList());
        board = CharArray.fromStrings(boardLines);
        steps = parseMoves(lists.get(1).get(0));
    }

    protected String getPart1Solution() {
        int yPosition = 0;
        int xPosition = board.getHorizontalSliceAsString(yPosition).indexOf(".");
        Coordinate position = Coordinate.of(xPosition, yPosition);
        direction = Direction.RIGHT;

        for (String step : steps) {
            if ("R".equals(step) || "L".equals(step)) {
                direction = getNewDirection(step.charAt(0));
                continue;
            }
            position = move(position, Integer.parseInt(step));
        }

        return "" + (4L * (position.getX() + 1) + 1000L * (position.getY() + 1) + getDirectionValue(direction));
    }


    protected String getPart2Solution() {
        createCubeEdgeWrapping();

        int yPosition = 0;
        int xPosition = board.getHorizontalSliceAsString(yPosition).indexOf(".");
        Coordinate position = Coordinate.of(xPosition, yPosition);
        direction = Direction.RIGHT;

        for (String step : steps) {
            if ("R".equals(step) || "L".equals(step)) {
                direction = getNewDirection(step.charAt(0));
                continue;
            }
            position = move(position, Integer.parseInt(step));
        }

        return "" + (4L * (position.getX() + 1) + 1000L * (position.getY() + 1) + getDirectionValue(direction));
    }

    private void createCubeEdgeWrapping() {
        cubeEdgeWrapping = new HashMap<>();
        // 1
        Coordinate[] horizontalSliceACoordinate = board.getHorizontalSliceACoordinate(49);
        Coordinate[] verticalSliceAsCoordinates = board.getVerticalSliceAsCoordinates(99);
        for (int i = 0; i < 50; i++) {
            Coordinate coordinateFirstSide = horizontalSliceACoordinate[i + 100];
            Coordinate coordinateSecondSide = verticalSliceAsCoordinates[i + 50];
            cubeEdgeWrapping.put(Pair.of(coordinateFirstSide, Direction.DOWN), Pair.of(coordinateSecondSide, Direction.LEFT));
            cubeEdgeWrapping.put(Pair.of(coordinateSecondSide, Direction.RIGHT), Pair.of(coordinateFirstSide, Direction.UP));
        }

        // 2
        horizontalSliceACoordinate = board.getHorizontalSliceACoordinate(100);
        verticalSliceAsCoordinates = board.getVerticalSliceAsCoordinates(50);
        for (int i = 0; i < 50; i++) {
            Coordinate coordinateFirstSide = horizontalSliceACoordinate[i];
            Coordinate coordinateSecondSide = verticalSliceAsCoordinates[i + 50];
            cubeEdgeWrapping.put(Pair.of(coordinateFirstSide, Direction.UP), Pair.of(coordinateSecondSide, Direction.RIGHT));
            cubeEdgeWrapping.put(Pair.of(coordinateSecondSide, Direction.LEFT), Pair.of(coordinateFirstSide, Direction.DOWN));
        }

        // 3
        horizontalSliceACoordinate = board.getHorizontalSliceACoordinate(149);
        verticalSliceAsCoordinates = board.getVerticalSliceAsCoordinates(49);
        for (int i = 0; i < 50; i++) {
            Coordinate coordinateFirstSide = horizontalSliceACoordinate[i + 50];
            Coordinate coordinateSecondSide = verticalSliceAsCoordinates[i + 150];
            cubeEdgeWrapping.put(Pair.of(coordinateFirstSide, Direction.DOWN), Pair.of(coordinateSecondSide, Direction.LEFT));
            cubeEdgeWrapping.put(Pair.of(coordinateSecondSide, Direction.RIGHT), Pair.of(coordinateFirstSide, Direction.UP));
        }

        // 4
        horizontalSliceACoordinate = board.getVerticalSliceAsCoordinates(149);
        verticalSliceAsCoordinates = board.getVerticalSliceAsCoordinates(99);
        for (int i = 0; i < 50; i++) {
            Coordinate coordinateFirstSide = horizontalSliceACoordinate[49 - i];
            Coordinate coordinateSecondSide = verticalSliceAsCoordinates[i + 100];
            cubeEdgeWrapping.put(Pair.of(coordinateFirstSide, Direction.RIGHT), Pair.of(coordinateSecondSide, Direction.LEFT));
            cubeEdgeWrapping.put(Pair.of(coordinateSecondSide, Direction.RIGHT), Pair.of(coordinateFirstSide, Direction.LEFT));
        }

        // 5
        horizontalSliceACoordinate = board.getVerticalSliceAsCoordinates(0);
        verticalSliceAsCoordinates = board.getVerticalSliceAsCoordinates(50);
        for (int i = 0; i < 50; i++) {
            Coordinate coordinateFirstSide = horizontalSliceACoordinate[149 - i];
            Coordinate coordinateSecondSide = verticalSliceAsCoordinates[i];
            cubeEdgeWrapping.put(Pair.of(coordinateFirstSide, Direction.LEFT), Pair.of(coordinateSecondSide, Direction.RIGHT));
            cubeEdgeWrapping.put(Pair.of(coordinateSecondSide, Direction.LEFT), Pair.of(coordinateFirstSide, Direction.RIGHT));
        }

        // 6
        horizontalSliceACoordinate = board.getHorizontalSliceACoordinate(0);
        verticalSliceAsCoordinates = board.getVerticalSliceAsCoordinates(0);
        for (int i = 0; i < 50; i++) {
            Coordinate coordinateFirstSide = horizontalSliceACoordinate[50 + i];
            Coordinate coordinateSecondSide = verticalSliceAsCoordinates[150 + i];
            cubeEdgeWrapping.put(Pair.of(coordinateFirstSide, Direction.UP), Pair.of(coordinateSecondSide, Direction.RIGHT));
            cubeEdgeWrapping.put(Pair.of(coordinateSecondSide, Direction.LEFT), Pair.of(coordinateFirstSide, Direction.DOWN));
        }

        // 7
        horizontalSliceACoordinate = board.getHorizontalSliceACoordinate(0);
        verticalSliceAsCoordinates = board.getHorizontalSliceACoordinate(199);
        for (int i = 0; i < 50; i++) {
            Coordinate coordinateFirstSide = horizontalSliceACoordinate[100 + i];
            Coordinate coordinateSecondSide = verticalSliceAsCoordinates[i];
            cubeEdgeWrapping.put(Pair.of(coordinateFirstSide, Direction.UP), Pair.of(coordinateSecondSide, Direction.UP));
            cubeEdgeWrapping.put(Pair.of(coordinateSecondSide, Direction.DOWN), Pair.of(coordinateFirstSide, Direction.DOWN));
        }
    }

    private Coordinate move(Coordinate from, int steps) {
        Coordinate position = from;
        Coordinate newPosition = position;

        for (int i = 0; i < steps; i++) {
            newPosition = moveSingleStep(position);
            if (newPosition.equals(position)) {
                return newPosition;
            }
            position = newPosition;
        }
        return newPosition;
    }

    private Coordinate moveSingleStep(Coordinate from) {
        Coordinate newPosition = getNextCoordinateInDirection(from, direction);

        if (isOutOfBound(newPosition) || isOutOfBoard(newPosition)) {
            Coordinate wrappedAroundCoordinate;
            if (cubeEdgeWrapping == null) {
                wrappedAroundCoordinate = getWrappedAroundCoordinate(newPosition, direction);
                if (isSolidWall(wrappedAroundCoordinate)) {
                    return from;
                }
            } else {
                final Pair<Coordinate, Direction> wrappedAroundCoordinateFromEdgeMapping = getWrappedAroundCoordinateFromEdgeMapping(from);
                wrappedAroundCoordinate = wrappedAroundCoordinateFromEdgeMapping.getFirst();
                if (isSolidWall(wrappedAroundCoordinate)) {
                    return from;
                }
                direction = wrappedAroundCoordinateFromEdgeMapping.getSecond();
            }
            newPosition = wrappedAroundCoordinate;
        }

        if (!isOutOfBound(newPosition) && isSolidWall(newPosition)) {
            return from;
        }
        return newPosition;
    }

    private Pair<Coordinate, Direction> getWrappedAroundCoordinateFromEdgeMapping(Coordinate position) {
        return cubeEdgeWrapping.get(Pair.of(position, direction));
    }

    private Coordinate getNextCoordinateInDirection(Coordinate from, Direction direction) {
        switch (direction) {
            case UP:
                return Coordinate.of(from.getX(), from.getY() - 1);
            case RIGHT:
                return Coordinate.of(from.getX() + 1, from.getY());
            case DOWN:
                return Coordinate.of(from.getX(), from.getY() + 1);
            case LEFT:
                return Coordinate.of(from.getX() - 1, from.getY());
            default:
                throw new RuntimeException("Unknown direction.");
        }
    }

    private Coordinate getWrappedAroundCoordinate(Coordinate coordinate, Direction direction) {
        if (!(isOutOfBound(coordinate) || isOutOfBoard(coordinate))) {
            throw new RuntimeException("Error; can not (should!) wrap a coordinate that is not out of bounds.");
        }
        Coordinate wrappedAroundCoordinate;

        switch (direction) {
            case UP:
                wrappedAroundCoordinate = Coordinate.of(coordinate.getX(), board.getHeight() - 1);
                break;
            case RIGHT:
                wrappedAroundCoordinate = Coordinate.of(0, coordinate.getY());
                break;
            case DOWN:
                wrappedAroundCoordinate = Coordinate.of(coordinate.getX(), 0);
                break;
            case LEFT:
                wrappedAroundCoordinate = Coordinate.of(board.getWidth() - 1, coordinate.getY());
                break;
            default:
                throw new RuntimeException("Unknown direction.");
        }

        while (isOutOfBoard(wrappedAroundCoordinate)) {
            wrappedAroundCoordinate = getNextCoordinateInDirection(wrappedAroundCoordinate, direction);
        }

        return wrappedAroundCoordinate;
    }

    private boolean isOutOfBoard(Coordinate coordinate) {
        return board.getValue(coordinate) == ' ';
    }

    private boolean isOutOfBound(Coordinate coordinate) {
        return coordinate.getX() < 0 || coordinate.getX() > board.getWidth() - 1 || coordinate.getY() < 0 || coordinate.getY() > board.getHeight() - 1;
    }

    private boolean isSolidWall(Coordinate coordinate) {
        return board.getValue(coordinate) == '#';
    }

    private Direction getNewDirection(char change) {
        switch (direction) {
            case UP:
                if (change == 'R') {
                    return Direction.RIGHT;
                }
                if (change == 'L') {
                    return Direction.LEFT;
                }
            case RIGHT:
                if (change == 'R') {
                    return Direction.DOWN;
                }
                if (change == 'L') {
                    return Direction.UP;
                }
            case DOWN:
                if (change == 'R') {
                    return Direction.LEFT;
                }
                if (change == 'L') {
                    return Direction.RIGHT;
                }
            case LEFT:
                if (change == 'R') {
                    return Direction.UP;
                }
                if (change == 'L') {
                    return Direction.DOWN;
                }
            default:
                throw new RuntimeException("Unknown direction.");
        }
    }

    private int getDirectionValue(Direction direction) {
        switch (direction) {
            case UP:
                return 3;
            case RIGHT:
                return 0;
            case DOWN:
                return 1;
            case LEFT:
                return 2;
            default:
                throw new RuntimeException("Unknown direction.");
        }
    }

    private List<String> parseMoves(String s) {
        final List<String> result = new ArrayList<>();
        final String[] steps = s.split("[RL]");
        final String[] turns = s.split("[0-9]+");

        result.add(steps[0]);
        for (int i = 1; i < steps.length; i++) {
            result.add(turns[i]);
            result.add(steps[i]);
        }
        return result;
    }
}