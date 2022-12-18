package aoc.days;

import aoc.data.CharArray;
import aoc.data.Rock;
import aoc.util.FileUtil;

import java.util.*;

public class Day17 extends Day {
    private static final int CAVE_HEIGHT = 100_000;

    private List<String> input;
    private CharArray cave;
    private int directionCounter = 0;

    protected void initialize() throws Exception {
        input = FileUtil.readFileToStrings(getDay());
        cave = new CharArray(7, CAVE_HEIGHT);
        directionCounter = 0;
    }

    protected String getPart1Solution() {

        createCaveFloor();

        Coordinate rockLocation;
        Coordinate[] nextRockLocations = null;
        Rock currentRock;

        for (int i = 0; i < 2022; i++) {
            int rockHeight = getRockHeight();
            rockLocation = Coordinate.of(2, getYFromHeight(rockHeight) - 2);
            currentRock = Rock.getType(i % 5, rockLocation);

            while (true) {
                char direction = nextDirection();

                if (direction == '>') {
                    nextRockLocations = currentRock.getRockCoordinatesRight();
                } else if (direction == '<') {
                    nextRockLocations = currentRock.getRockCoordinatesLeft();
                }

                if (!isOverlappingOrOutOfBounds(nextRockLocations)) {
                    if (direction == '>') {
                        currentRock.moveRockRight();
                    } else if (direction == '<') {
                        currentRock.moveRockLeft();
                    }
                }

                nextRockLocations = currentRock.getRockCoordinatesBelow();
                if (isOverlappingOrOutOfBounds(nextRockLocations)) {
                    freezeRock(currentRock);
                    break;
                }
                currentRock.moveRockDown();
            }
        }
        return "" + (getRockHeight() - 2);
    }

    protected String getPart2Solution() throws Exception {
        long rocksToSimulate = 1000000000000L;
        createCaveFloor();

        Coordinate rockLocation;
        Coordinate[] nextRockLocations = null;
        Rock currentRock;

        Map<String, Set<Integer>> rocksHeightsMap = new HashMap<>();
        String lastAddedUniqueHeight = "";

        for (int i = 0; i < 10_000; i++) {
            int rockHeight = getRockHeight();
            rockLocation = Coordinate.of(2, getYFromHeight(rockHeight) - 2);
            currentRock = Rock.getType(i % 5, rockLocation);

            while (true) {
                char direction = nextDirection();

                if (direction == '>') {
                    nextRockLocations = currentRock.getRockCoordinatesRight();
                } else if (direction == '<') {
                    nextRockLocations = currentRock.getRockCoordinatesLeft();
                }

                if (!isOverlappingOrOutOfBounds(nextRockLocations)) {
                    if (direction == '>') {
                        currentRock.moveRockRight();
                    } else if (direction == '<') {
                        currentRock.moveRockLeft();
                    }
                }

                nextRockLocations = currentRock.getRockCoordinatesBelow();
                if (isOverlappingOrOutOfBounds(nextRockLocations)) {
                    freezeRock(currentRock);


                    for (Coordinate rockCoordinate : currentRock.getRockCoordinates()) {
                        String key = cave.getHorizontalSliceAsString(rockCoordinate.getY());
                        if (rocksHeightsMap.containsKey(key)) {
                            rocksHeightsMap.get(key).add(i);
                        } else {
                            Set<Integer> list = new HashSet<>();
                            list.add(i);
                            rocksHeightsMap.put(key, list);
                            lastAddedUniqueHeight = key;
                        }
                    }
                    break;
                }
                currentRock.moveRockDown();
            }
        }

        int[] rocks = rocksHeightsMap.get(lastAddedUniqueHeight).stream().mapToInt(Integer::intValue).sorted().toArray();

        long rocksToFirstRepeatment = rocks[0];
        long rocksDifferenceBetweenRepeatment = rocks[1] - rocks[0];

        long missingStones = (rocksToSimulate - rocksToFirstRepeatment) % rocksDifferenceBetweenRepeatment;
        long numberOfRepeatments = (rocksToSimulate - rocksToFirstRepeatment) / rocksDifferenceBetweenRepeatment;
        long heightToFirstRepeatment = 0;
        long heightToSecondRepeatment = 0;
        int found = 0;
        for (int i = CAVE_HEIGHT - 2; i > 0; i--) {
            String key = cave.getHorizontalSliceAsString(i);

            if (found == 0 && key.equals(lastAddedUniqueHeight)) {
                heightToFirstRepeatment = CAVE_HEIGHT - i + 1;
                found++;
            } else if (found == 1 && (CAVE_HEIGHT - i + 1 - heightToFirstRepeatment) > 10 && key.equals(lastAddedUniqueHeight)) {
                heightToSecondRepeatment = CAVE_HEIGHT - i + 1;
                break;
            }
        }


        initialize();
        createCaveFloor();

        for (int i = 0; i < (rocksToFirstRepeatment + missingStones); i++) {
            int rockHeight = getRockHeight();
            rockLocation = Coordinate.of(2, getYFromHeight(rockHeight) - 2);
            currentRock = Rock.getType(i % 5, rockLocation);

            while (true) {
                char direction = nextDirection();

                if (direction == '>') {
                    nextRockLocations = currentRock.getRockCoordinatesRight();
                } else if (direction == '<') {
                    nextRockLocations = currentRock.getRockCoordinatesLeft();
                }

                if (!isOverlappingOrOutOfBounds(nextRockLocations)) {
                    if (direction == '>') {
                        currentRock.moveRockRight();
                    } else if (direction == '<') {
                        currentRock.moveRockLeft();
                    }
                }

                nextRockLocations = currentRock.getRockCoordinatesBelow();
                if (isOverlappingOrOutOfBounds(nextRockLocations)) {
                    freezeRock(currentRock);
                    break;
                }
                currentRock.moveRockDown();
            }
        }
        return "" + ((getRockHeight() - 2) + (numberOfRepeatments * (heightToSecondRepeatment - heightToFirstRepeatment)));
    }

    private char nextDirection() {
        if (directionCounter >= input.get(0).length()) {
            directionCounter = 0;
        }
        return input.get(0).charAt(directionCounter++);
    }

    private void freezeRock(Rock rock) {
        for (Coordinate rockCoordinate : rock.getRockCoordinates()) {
            cave.setValue(rockCoordinate, '#');
        }
    }

    private void printCave(int height) {
        cave.printSection(0, 7, CAVE_HEIGHT - height, height - 1);
    }

    private int getRockHeight() {
        int height = cave.getHeight();
        for (int i = 1; i < height; i++) {
            if (new String(cave.getHorizontalSlice(height - i)).equals(".......")) {
                return i;
            }
        }
        throw new RuntimeException("Cave is full.");
    }

    private void createCaveFloor() {
        for (int i = 0; i < cave.getWidth(); i++) {
            cave.setValue(Coordinate.of(i, getYFromHeight(0)), '#');
        }
    }

    private boolean isOverlappingOrOutOfBounds(Coordinate[] coordinates) {
        if (coordinates == null) {
            return false;
        }
        for (Coordinate coordinate : coordinates) {
            if (cave.isOutOfBounds(coordinate) || cave.getValue(coordinate) == '#') {
                return true;
            }
        }
        return false;
    }

    private int getYFromHeight(int height) {
        return CAVE_HEIGHT - height - 1;
    }
}