package aoc.days;

import aoc.util.FileUtil;

import java.util.*;

public class Day09 extends Day {
    private List<String> input;
    private Set<Coordinate> coordinates = new HashSet<>();
    private final Coordinate[] knots = new Coordinate[10];
    private Coordinate head;
    private Coordinate tail;

    protected void initialize() throws Exception {
        input = FileUtil.readFileToStrings(getDay());
        head = new Coordinate(0, 0);
        tail = new Coordinate(0, 0);

        for (int j = 0; j < 10; j++) {
            knots[j] = new Coordinate(0, 0);
        }
    }

    protected String getPart1Solution() {
        coordinates = new HashSet<>();

        for (String s : input) {
            String direction = s.split(" ")[0];
            int steps = Integer.parseInt(s.split(" ")[1]);

            switch (direction) {
                case "R":
                    moveRight(steps);
                    break;
                case "U":
                    moveUp(steps);
                    break;
                case "L":
                    moveLeft(steps);
                    break;
                case "D":
                    moveDown(steps);
                    break;
                default:
                    throw new RuntimeException("Error!");
            }
        }
        return "" + coordinates.size();
    }

    protected String getPart2Solution() {
        coordinates = new HashSet<>();

        for (String s : input) {
            String direction = s.split(" ")[0];
            int steps = Integer.parseInt(s.split(" ")[1]);
            switch (direction) {
                case "R":
                    moveKnotsRight(steps);
                    break;
                case "U":
                    moveKnotsUp(steps);
                    break;
                case "L":
                    moveKnotsLeft(steps);
                    break;
                case "D":
                    moveKnotsDown(steps);
                    break;
                default:
                    throw new RuntimeException("Error");
            }
        }
        return "" + coordinates.size();
    }

    private void moveKnotsRight(int steps) {
        for (int i = 0; i < steps; i++) {
            moveAllKnots(1, 0);
            coordinates.add(knots[9]);
        }
    }

    private void moveKnotsLeft(int steps) {
        for (int i = 0; i < steps; i++) {
            moveAllKnots(-1, 0);
            coordinates.add(knots[9]);
        }
    }

    private void moveKnotsUp(int steps) {
        for (int i = 0; i < steps; i++) {
            moveAllKnots(0, 1);
            coordinates.add(knots[9]);
        }
    }

    private void moveKnotsDown(int steps) {
        for (int i = 0; i < steps; i++) {
            moveAllKnots(0, -1);
            coordinates.add(knots[9]);
        }
    }

    private void moveAllKnots(int dx, int dy) {
        knots[0].setX(knots[0].getX() + dx);
        knots[0].setY(knots[0].getY() + dy);

        for (int i = 1; i < knots.length; i++) {
            if (!knots[i].isAdjacentTo(knots[i - 1])) {
                if (knots[i].shareOneCoordinate(knots[i - 1])) {
                    knots[i] = knots[i].getCoordinateAdjacentTo(knots[i - 1]);
                } else {
                    knots[i] = knots[i].getDiagonalCoordinateAdjacentTo(knots[i - 1]);
                }
            }
        }
    }

    private void moveRight(int steps) {
        for (int i = 0; i < steps; i++) {
            move(1, 0);
        }
    }

    private void moveLeft(int steps) {
        for (int i = 0; i < steps; i++) {
            move(-1, 0);
        }
    }


    private void moveUp(int steps) {
        for (int i = 0; i < steps; i++) {
            move(0, 1);
        }
    }

    private void moveDown(int steps) {
        for (int i = 0; i < steps; i++) {
            move(0, -1);
        }
    }

    private void move(int dx, int dy) {
        final Coordinate temp = head.copyOf();
        head.setX(head.getX() + dx);
        head.setY(head.getY() + dy);
        if (!tail.isAdjacentTo(head)) {
            tail = temp;
        }
        coordinates.add(tail);
    }
}