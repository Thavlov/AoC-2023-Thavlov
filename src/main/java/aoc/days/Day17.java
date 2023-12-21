package aoc.days;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import aoc.data.CharArray;
import aoc.data.Coordinate;
import aoc.data.Direction;
import aoc.data.Triplet;
import aoc.util.FileUtil;
import aoc.util.StringUtil;

public class Day17 extends Day {

    private static int STEP_SIZE_MIN;
    private static int STEP_SIZE_MAX;
    private CharArray layout;

    protected void initialize() throws Exception {
        layout = CharArray.fromStrings(FileUtil.readFileToStrings(getDay()));
    }

    protected Object getPart1Solution() {
        STEP_SIZE_MIN = 0;
        STEP_SIZE_MAX = 3;

        final Coordinate startingPoint = Coordinate.of(0, 0);
        final Coordinate endingPoint = Coordinate.of(layout.getWidth() - 1, layout.getHeight() - 1);

        final Map<Triplet<Coordinate, Coordinate, Integer>, Step> border = new ConcurrentHashMap<>();
        border.put(Triplet.of(null, startingPoint, 0), new Step(0, 0, Direction.Directions.RIGHT, startingPoint));

        int counter = 0;

        while (counter < 100) {
            for (Triplet<Coordinate, Coordinate, Integer> coordinates : new HashSet<>(border.keySet())) {
                Coordinate coordinate = coordinates.getSecond();
                Step step = border.get(coordinates);
                int currentHeatLoss = step.totalHeatLoss;

                Step[] nextSteps = step.getNextSteps();

                for (Step nextStep : nextSteps) {
                    if (isOutOfBounds(nextStep)) {
                        continue;
                    }
                    Coordinate coordinate1 = nextStep.coordinate;
                    int nextHeatLoss = currentHeatLoss + StringUtil.parseInteger(layout.getValue(coordinate1));
                    nextStep.totalHeatLoss = nextHeatLoss;
                    if (border.containsKey(Triplet.of(coordinate, coordinate1, nextStep.stepsInSameDirection))) {
                        int savedHeatLoss = border.get(Triplet.of(coordinate, coordinate1, nextStep.stepsInSameDirection)).totalHeatLoss;

                        if (savedHeatLoss > nextHeatLoss) {
                            border.replace(Triplet.of(coordinate, coordinate1, nextStep.stepsInSameDirection), nextStep);
                        }
                    } else {
                        border.put(Triplet.of(coordinate, coordinate1, nextStep.stepsInSameDirection), nextStep);
                    }
                }
            }
            if (hasReachedEnd(border, endingPoint)) {
                counter++;
            }
        }

        return border.entrySet().stream().filter(e -> e.getKey().getSecond().equals(endingPoint)).map(Map.Entry::getValue).map(s -> s.totalHeatLoss).min(Integer::compare).get();
    }

    private boolean hasReachedEnd(Map<Triplet<Coordinate, Coordinate, Integer>, Step> paths, Coordinate endingPoint) {
        return paths.keySet().stream().map(Triplet::getSecond).anyMatch(endingPoint::equals);
    }

    protected Object getPart2Solution() {
        STEP_SIZE_MIN = 3;
        STEP_SIZE_MAX = 10;

        final Coordinate startingPoint = Coordinate.of(0, 0);
        final Coordinate endingPoint = Coordinate.of(layout.getWidth() - 1, layout.getHeight() - 1);

        final Map<Triplet<Coordinate, Coordinate, Integer>, Step> border = new ConcurrentHashMap<>();
        border.put(Triplet.of(null, startingPoint, 0), new Step(0, 0, Direction.Directions.RIGHT, startingPoint));

        int counter = 0;

        while (counter < 10) {
            for (Triplet<Coordinate, Coordinate, Integer> coordinates : new HashSet<>(border.keySet())) {
                Coordinate coordinate = coordinates.getSecond();
                Step step = border.get(coordinates);
                int currentHeatLoss = step.totalHeatLoss;

                Step[] nextSteps = step.getNextSteps();

                for (Step nextStep : nextSteps) {
                    if (isOutOfBounds(nextStep)) {
                        continue;
                    }
                    Coordinate coordinate1 = nextStep.coordinate;
                    int nextHeatLoss = currentHeatLoss + StringUtil.parseInteger(layout.getValue(coordinate1));
                    nextStep.totalHeatLoss = nextHeatLoss;
                    if (border.containsKey(Triplet.of(coordinate, coordinate1, nextStep.stepsInSameDirection))) {
                        int savedHeatLoss = border.get(Triplet.of(coordinate, coordinate1, nextStep.stepsInSameDirection)).totalHeatLoss;
                        if (savedHeatLoss > nextHeatLoss) {
                            border.replace(Triplet.of(coordinate, coordinate1, nextStep.stepsInSameDirection), nextStep);
                        }
                    } else {
                        border.put(Triplet.of(coordinate, coordinate1, nextStep.stepsInSameDirection), nextStep);
                    }
                }
            }
            if (hasReachedEnd(border, endingPoint)) {
                counter++;
            }
        }

        return border.entrySet().stream().filter(e -> e.getKey().getSecond().equals(endingPoint)).map(Map.Entry::getValue).map(s -> s.totalHeatLoss).min(Integer::compare).get();
    }

    private boolean isOutOfBounds(Step step) {
        return step.coordinate.getX() < 0 || step.coordinate.getX() >= layout.getWidth() || step.coordinate.getY() < 0 || step.coordinate.getY() >= layout.getHeight();
    }

    private static class Step {
        int totalHeatLoss;
        final int stepsInSameDirection;
        final aoc.data.Direction direction;
        final Coordinate coordinate;

        public Step(int totalHeatLoss, int stepsInSameDirection, Direction.Directions direction, Coordinate coordinate) {
            this(totalHeatLoss, stepsInSameDirection, new Direction(direction), coordinate);
        }

        public Step(int totalHeatLoss, int stepsInSameDirection, Direction direction, Coordinate coordinate) {
            this.totalHeatLoss = totalHeatLoss;
            this.stepsInSameDirection = stepsInSameDirection;
            this.direction = direction;
            this.coordinate = coordinate;
        }

        public Step[] getNextSteps() {
            Step[] result;
            if (stepsInSameDirection <= STEP_SIZE_MIN) {
                result = new Step[1];
                result[0] = getStepInFront();
            } else if (stepsInSameDirection >= STEP_SIZE_MAX) {
                result = new Step[2];
                result[0] = getStepToTheRight();
                result[1] = getStepToTheLeft();
            } else {
                result = new Step[3];
                result[0] = getStepInFront();
                result[1] = getStepToTheRight();
                result[2] = getStepToTheLeft();
            }
            return result;
        }

        private Step getStepToTheRight() {
            Direction newDirection = direction.getRight();
            final Coordinate nextCoordinate = newDirection.getNext(coordinate);
            return new Step(totalHeatLoss, 1, newDirection, nextCoordinate);
        }

        private Step getStepToTheLeft() {
            Direction newDirection = direction.getLeft();
            final Coordinate nextCoordinate = newDirection.getNext(coordinate);
            return new Step(totalHeatLoss, 1, newDirection, nextCoordinate);
        }

        private Step getStepInFront() {
            final Coordinate nextCoordinate = direction.getNext(coordinate);
            return new Step(totalHeatLoss, stepsInSameDirection + 1, direction.getDirection(), nextCoordinate);
        }
    }
}
