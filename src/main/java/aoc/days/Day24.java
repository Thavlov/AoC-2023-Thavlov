package aoc.days;

import aoc.data.Blizzard;
import aoc.data.CharArray;
import aoc.util.FileUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Day24 extends Day {
    private List<Blizzard> blizzards;
    private int mapWidth;
    private int mapHeight;

    protected void initialize() throws Exception {
        final List<Character> blizzardChars = Arrays.asList('>', '<', 'v', '^');
        final List<String> input = FileUtil.readFileToStrings(getDay());

        blizzards = new ArrayList<>();
        mapHeight = input.size();
        mapWidth = input.get(0).length();

        Blizzard.MAX_HEIGHT = mapHeight - 2;
        Blizzard.MAX_WIDTH = mapWidth - 2;

        for (int y = 0; y < input.size(); y++) {
            String s = input.get(y);
            char[] charArray = s.toCharArray();
            for (int x = 0; x < charArray.length; x++) {
                char c = charArray[x];
                if (blizzardChars.contains(c)) {
                    blizzards.add(new Blizzard(Coordinate.of(x, y), c));
                }
            }
        }
    }

    protected String getPart1Solution() {
        Set<Coordinate> frontier = new HashSet<>();
        frontier.add(Coordinate.of(1, 0));

        Coordinate endCoordinate = Coordinate.of(mapWidth - 2, mapHeight - 1);

        List<Coordinate> availableLocations;
        Set<Coordinate> temp = new HashSet<>();
        int steps = 1;
        outer:
        while (true) {
            moveBlizzards();
            availableLocations = getAvailableLocations();
            for (Coordinate frontierCoordinate : frontier) {
                Coordinate[] coordinateAdjacent = frontierCoordinate.getCoordinateAdjacentIncludingSelf();
                for (Coordinate coordinate : coordinateAdjacent) {
                    if (endCoordinate.equals(coordinate)) {
                        break outer;
                    }
                    if (availableLocations.contains(coordinate)) {
                        temp.add(coordinate);
                    }
                }

            }
            frontier = temp;
            temp = new HashSet<>();
            steps++;
        }

        return "" + steps;
    }

    protected String getPart2Solution() {
        Coordinate startCoordinate = Coordinate.of(1, 0);
        Coordinate endCoordinate = Coordinate.of(mapWidth - 2, mapHeight - 1);

        Set<Coordinate> frontier = new HashSet<>();
        frontier.add(startCoordinate);

        List<Coordinate> availableLocations;
        Set<Coordinate> temp = new HashSet<>();
        int steps = 1;
        outer:
        while (true) {
            moveBlizzards();
            availableLocations = getAvailableLocations();
            for (Coordinate frontierCoordinate : frontier) {
                Coordinate[] coordinateAdjacent = frontierCoordinate.getCoordinateAdjacentIncludingSelf();
                for (Coordinate coordinate : coordinateAdjacent) {
                    if (endCoordinate.equals(coordinate)) {
                        break outer;
                    }
                    if (availableLocations.contains(coordinate)) {
                        temp.add(coordinate);
                    }
                }
            }
            frontier = temp;
            temp = new HashSet<>();
            steps++;
        }

        steps++;
        frontier = new HashSet<>();
        temp = new HashSet<>();
        frontier.add(endCoordinate);

        outer:
        while (true) {
            moveBlizzards();
            availableLocations = getAvailableLocations();
            for (Coordinate frontierCoordinate : frontier) {
                Coordinate[] coordinateAdjacent = frontierCoordinate.getCoordinateAdjacentIncludingSelf();
                for (Coordinate coordinate : coordinateAdjacent) {
                    if (startCoordinate.equals(coordinate)) {
                        break outer;
                    }
                    if (availableLocations.contains(coordinate)) {
                        temp.add(coordinate);
                    }
                }
            }
            frontier = temp;
            temp = new HashSet<>();
            steps++;
        }

        steps++;
        frontier = new HashSet<>();
        temp = new HashSet<>();
        frontier.add(startCoordinate);

        outer:
        while (true) {
            moveBlizzards();
            availableLocations = getAvailableLocations();
            for (Coordinate frontierCoordinate : frontier) {
                Coordinate[] coordinateAdjacent = frontierCoordinate.getCoordinateAdjacentIncludingSelf();
                for (Coordinate coordinate : coordinateAdjacent) {
                    if (endCoordinate.equals(coordinate)) {
                        break outer;
                    }
                    if (availableLocations.contains(coordinate)) {
                        temp.add(coordinate);
                    }
                }
            }
            frontier = temp;
            temp = new HashSet<>();
            steps++;
        }

        return "" + steps;
    }

    private List<Coordinate> getAvailableLocations() {
        return createMapOfBlizzards().findAllCoordinatesWith('.');
    }

    private void moveBlizzards() {
        blizzards.forEach(Blizzard::move);
    }

    private void printBlizzards(Coordinate start, Coordinate end) {
        CharArray mapOfBlizzards = createMapOfBlizzards();
        mapOfBlizzards.setValue(start, 'S');
        mapOfBlizzards.setValue(end, 'E');
        mapOfBlizzards.printArray();
        System.out.println("--------------------");
    }

    private void printBlizzards() {
        createMapOfBlizzards().printArray();
        System.out.println("--------------------");
    }

    private CharArray createMapOfBlizzards() {
        CharArray charArray = CharArray.fromCoordinates(blizzards.stream().map(Blizzard::getPosition).collect(Collectors.toList()), mapWidth, mapHeight, '*');

        for (int x = 0; x < mapWidth; x++) {
            if (x != 1) {
                charArray.setValue(x, 0, '#');
            }
            if (x != mapWidth - 2) {
                charArray.setValue(x, mapHeight - 1, '#');
            }
        }
        for (int y = 0; y < mapHeight; y++) {
            charArray.setValue(0, y, '#');
            charArray.setValue(mapWidth - 1, y, '#');
        }
        return charArray;
    }
/*
    private CharArray getCombinedMap() {
        CharArray result = blizzards[0].copyOf();

        for (int i = 1; i < blizzards.length; i++) {
            CharArray blizzard = blizzards[i];
            for (int x = 1; x < blizzard.getWidth() - 1; x++) {
                blizzard.get

                for (int x = 1; x < blizzards.length - 1; x++) {

                    CharArray charArray = blizzards[j];

                }


            }
        }*/
}