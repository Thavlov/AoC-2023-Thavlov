package aoc.days;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.function.Predicate;

import aoc.data.CharArray;
import aoc.data.Coordinate;
import aoc.util.FileUtil;
import aoc.util.StringUtil;

public class Day18 extends Day {
    private List<String> input;

    protected void initialize() throws Exception {
        input = FileUtil.readFileToStrings(getDay());
    }

    protected Object getPart1Solution() {
        CharArray map = new CharArray(1000, 1000);

        Coordinate location = Coordinate.of(500, 500);
        Coordinate nextLocation;

        for (String s : input) {
            String[] split = s.split(StringUtil.SPACE);
            String dir = split[0];
            int length = Integer.parseInt(split[1]);

            switch (dir) {
                case "U":
                    nextLocation = location.add(Coordinate.of(0, -length));
                    break;
                case "D":
                    nextLocation = location.add(Coordinate.of(0, length));
                    break;
                case "L":
                    nextLocation = location.add(Coordinate.of(-length, 0));
                    break;
                case "R":
                    nextLocation = location.add(Coordinate.of(length, 0));
                    break;
                default:
                    throw new RuntimeException();
            }
            Coordinate[] allCoordinatesBetween = Coordinate.getAllCoordinatesBetween(location, nextLocation);

            for (Coordinate coordinate : allCoordinatesBetween) {
                map.setValue(coordinate, '#');
            }
            location = nextLocation;
        }

        createPockets(Coordinate.of(0, 0), map);

        return map.findAllCoordinatesWith('#').size() + map.findAllCoordinatesWith('.').size();
    }

    protected Object getPart2Solution() {
        long result = 0;

        Coordinate location = Coordinate.of(0, 0);
        Coordinate nextLocation;

        long perimeter = 0;

        for (int i = input.size() - 1; i >= 0; i--) {
            String s = input.get(i);
            String[] split = s.split(StringUtil.SPACE);
            char dir = split[2].charAt(7);
            int length = Integer.parseInt(split[2].substring(2, 7), 16);

            switch (dir) {
                case '0': // Right
                    nextLocation = location.add(Coordinate.of(length, 0));
                    break;
                case '1': // Down
                    nextLocation = location.add(Coordinate.of(0, -length));
                    break;
                case '2': // Left
                    nextLocation = location.add(Coordinate.of(-length, 0));
                    break;
                case '3': // Up - 952404941483
                    nextLocation = location.add(Coordinate.of(0, length));
                    break;
                default:
                    throw new RuntimeException();
            }

            perimeter += location.manhattanDistanceTo(nextLocation);

            result += (long) (location.getY() + nextLocation.getY()) * (location.getX() - nextLocation.getX());

            location = nextLocation;
        }

        return (perimeter + result) / 2 + 1;
    }

    private void createPockets(Coordinate coordinates, CharArray map) {
        final Deque<Coordinate> pocketCoordinates = new ArrayDeque<>();
        pocketCoordinates.push(coordinates);

        Coordinate pocketCoordinate;

        while (!pocketCoordinates.isEmpty()) {
            pocketCoordinate = pocketCoordinates.pop();
            map.setValue(pocketCoordinate, 'X');
            Arrays.stream(pocketCoordinate.getCoordinateAdjacentInsideBounds(map.getWidth(), map.getHeight())).filter(notVisited(map)).forEach(pocketCoordinates::push);
        }
    }

    private Predicate<Coordinate> notVisited(CharArray map) {
        return c -> map.getValue(c) == '.';
    }
}
