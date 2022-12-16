package aoc.days;

import aoc.data.CharArray;
import aoc.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day14 extends Day {
    private CharArray cave;
    private List<Coordinate> sand;
    private int floorLevel;

    protected void initialize() throws Exception {
        List<String> input = FileUtil.readFileToStrings(14);
        List<List<Coordinate>> coordinates = input.stream().map(c -> Coordinate.parseMultipleSeparatedBy(c, "->")).collect(Collectors.toList());

        sand = new ArrayList<>();

        int maxX = 2*coordinates.stream().flatMap(List::stream).mapToInt(Coordinate::getX).max().getAsInt();
        floorLevel = 2+coordinates.stream().flatMap(List::stream).mapToInt(Coordinate::getY).max().getAsInt();

        cave = new CharArray(2*maxX, floorLevel+10);

        for (List<Coordinate> coordinate : coordinates) {
            for (int i = 0; i < coordinate.size() - 1; i++) {
                cave.fillBetween(coordinate.get(i), coordinate.get(i + 1), '#');
            }
        }
    }

    protected String getPart1Solution() {
        int counter = 0;
        while (addSand(Coordinate.of(500, 0)) && counter < 10_000) {
            counter++;
        }
        return "" + sand.size();
    }

    protected String getPart2Solution() {
        cave.fillBetween(Coordinate.of(0, floorLevel), Coordinate.of(cave.getHorizontalSize()-1, floorLevel), '#');

        int counter = 0;
        while (addSand(Coordinate.of(500, 0)) && counter < 10_0000) {
            counter++;
        }
        return "" + sand.size();
    }

    private boolean addSand(final Coordinate coordinate) {
        Coordinate sandCoordinate = coordinate.copyOf();
        boolean isFirstIteration = true;
        do {
            Coordinate nextSandCoordinate = getNextSandCoordinate(sandCoordinate);

            if (nextSandCoordinate == null) {
                sand.add(sandCoordinate);
                cave.setValue(sandCoordinate, 'o');
                return !isFirstIteration;
            }
            if (nextSandCoordinate.equals(coordinate)) {
                return false;
            }

            if (nextSandCoordinate.getY() > floorLevel) {
                return false;
            }
            isFirstIteration = false;
            sandCoordinate = nextSandCoordinate;
        } while (true);
    }

    public Coordinate getNextSandCoordinate(Coordinate sandCoordinate) {
        //Below
        Coordinate temp = Coordinate.of(sandCoordinate.getX(), sandCoordinate.getY() + 1);
        if (isCoordinateFree(temp)) {
            return temp;
        }
        // Below-left
        temp = Coordinate.of(sandCoordinate.getX() - 1, sandCoordinate.getY() + 1);
        if (isCoordinateFree(temp)) {
            return temp;
        }
        // Below-right
        temp = Coordinate.of(sandCoordinate.getX() + 1, sandCoordinate.getY() + 1);
        if (isCoordinateFree(temp)) {
            return temp;
        }
        return null;
    }

    private boolean isCoordinateFree(final Coordinate c) {
        return cave.getValue(c) == '.';
    }
}