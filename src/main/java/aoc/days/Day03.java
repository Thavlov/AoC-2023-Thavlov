package aoc.days;

import aoc.data.Coordinate;
import aoc.util.FileUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 extends Day {
    private char[][] schematic;
    private final Map<Coordinate, List<Integer>> gears = new HashMap<>();

    protected void initialize() throws Exception {
        schematic = FileUtil.readFileToCharMap(getDay());
    }

    protected Object getPart1Solution() {
        int sum = 0;
        for (int line = 0; line < schematic.length; line++) {
            sum += calculatePartsInLine(line);
        }
        return sum;
    }

    protected Object getPart2Solution() {
        for (int line = 0; line < schematic.length; line++) {
            calculatePartsInLineAndSetGears(line);
        }

        int sumGearRatios = 0;
        for (List<Integer> gearValues : gears.values()) {
            if (gearValues.size() == 2) {
                sumGearRatios += gearValues.get(0) * gearValues.get(1);
            }
        }
        return sumGearRatios;
    }

    private int calculatePartsInLine(int line) {
        final String s = new String(schematic[line]);
        int sum = 0;

        final String regex = "(\\d+)";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(s);

        int index = 0;
        while (matcher.find(index)) {
            if (isValueNextToSymbol(matcher, line)) {
                int value = Integer.parseInt(matcher.group(1));
                sum += value;
            }
            index = matcher.end();
        }
        return sum;
    }

    private boolean isValueNextToSymbol(Matcher matcher, int line) {
        int start = matcher.start();
        int length = matcher.group().length();
        for (int x = start; x < start + length; x++) {
            if (isCoordinateNextToSymbol(x, line)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCoordinateNextToSymbol(int x, int y) {
        final Coordinate coordinate = Coordinate.of(x, y);
        final Coordinate[] coordinateAdjacent = coordinate.getCoordinateAdjacentIncludingDiagonalInsideBounds(schematic[0].length, schematic.length);

        for (Coordinate c : coordinateAdjacent) {
            if (isCoordinateSymbol(c.getX(), c.getY())) {
                return true;
            }
        }
        return false;
    }

    private boolean isCoordinateSymbol(int x, int y) {
        return !(Character.isDigit(schematic[y][x]) || schematic[y][x] == '.');
    }

    private void calculatePartsInLineAndSetGears(int line) {
        final String s = new String(schematic[line]);

        final String regex = "(\\d+)";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(s);

        int index = 0;
        while (matcher.find(index)) {
            checkIfValueNextToGear(matcher, line);
            index = matcher.end();
        }
    }

    private void checkIfValueNextToGear(Matcher matcher, int line) {
        int start = matcher.start();
        int length = matcher.group().length();
        for (int x = start; x < start + length; x++) {
            if (isCoordinateNextToGear(Coordinate.of(x, line), Integer.parseInt(matcher.group(1)))) {
                return;
            }
        }
    }

    private boolean isCoordinateNextToGear(final Coordinate coordinate, int value) {
        final Coordinate[] coordinateAdjacent = coordinate.getCoordinateAdjacentIncludingDiagonalInsideBounds(schematic[0].length, schematic.length);

        for (Coordinate c : coordinateAdjacent) {
            if (isCoordinateGearSymbol(c.getX(), c.getY())) {
                if (gears.containsKey(c)) {
                    gears.get(c).add(value);
                } else {
                    List<Integer> objects = new ArrayList<>();
                    objects.add(value);
                    gears.put(c, objects);
                }
                return true;
            }
        }
        return false;
    }

    private boolean isCoordinateGearSymbol(int x, int y) {
        return schematic[y][x] == '*';
    }
}