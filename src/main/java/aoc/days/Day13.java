package aoc.days;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import aoc.data.CharArray;
import aoc.data.Coordinate;
import aoc.util.FileUtil;

public class Day13 extends Day {
    private List<CharArray> maps;

    protected void initialize() throws Exception {
        final List<List<String>> input = FileUtil.readFileGroupByEmptyLine(getDay());

        maps = input.stream().map(CharArray::fromStrings).collect(Collectors.toList());
    }

    protected Object getPart1Solution() throws Exception {
        return maps.stream()
                .mapToInt(this::getMirrorScore)
                .sum();
    }

    protected Object getPart2Solution() throws Exception {
        return maps.stream()
                .mapToInt(this::flipSmudge)
                .sum();
    }

    private int flipSmudge(CharArray map) {
        return getSmudgeCoordinate(map);
    }

    private int getMirrorScore(CharArray map) {
        int horizontalScore = getHorizontalScore(map);
        if (horizontalScore > 0) {
            return 100 * horizontalScore;
        }

        int verticalScore = getVerticalScore(map);
        if (verticalScore <= 0) {
            throw new RuntimeException();
        }
        return verticalScore;
    }

    private int getHorizontalScore(CharArray map) {
        for (int i = 0; i < map.getHeight() - 1; i++) {
            if (compareHorizontal(i, map)) {
                return i + 1;
            }

        }
        return 0;
    }

    private int getVerticalScore(CharArray map) {
        for (int i = 0; i < map.getWidth() - 1; i++) {
            if (compareVertical(i, map)) {
                return i + 1;
            }

        }
        return 0;
    }

    private boolean compareHorizontal(int line, CharArray map) {
        int i = line;
        int j = line + 1;
        while (i >= 0 && j < map.getHeight()) {
            if (!compareHorizontal(i, j, map)) {
                return false;
            }
            i--;
            j++;
        }
        return true;
    }

    private boolean compareVertical(int line, CharArray map) {
        int i = line;
        int j = line + 1;
        while (i >= 0 && j < map.getWidth()) {
            if (!compareVertical(i, j, map)) {
                return false;
            }
            i--;
            j++;
        }
        return true;
    }

    private boolean compareHorizontal(int i, int j, CharArray map) {
        char[] horizontalSlice1 = map.getHorizontalSlice(i);
        char[] horizontalSlice2 = map.getHorizontalSlice(j);
        return equalSlice(horizontalSlice1, horizontalSlice2);
    }

    private boolean compareVertical(int i, int j, CharArray map) {
        char[] horizontalSlice1 = map.getVerticalSlice(i);
        char[] horizontalSlice2 = map.getVerticalSlice(j);
        return equalSlice(horizontalSlice1, horizontalSlice2);
    }

    private boolean equalSlice(char[] slice1, char[] slice2) {
        return new String(slice1).equals(new String(slice2));
    }

    private int getSmudgeCoordinate(CharArray map) {
        Optional<Integer> horizontalCoordinate = getHorizontalCoordinate(map);
        if (horizontalCoordinate.isPresent()) {
            return 100 * horizontalCoordinate.get();
        }
        return getVerticalCoordinate(map).orElseThrow();
    }

    private Optional<Integer> getHorizontalCoordinate(CharArray map) {
        Optional<Coordinate> result = Optional.empty();
        int found = -1;
        for (int i = 0; i < map.getHeight() - 1; i++) {
            Optional<Coordinate> horizontalCoordinate = getHorizontalCoordinate(i, map);
            if (result.isPresent() && horizontalCoordinate.isPresent()) {
                return Optional.empty();
            }
            if (horizontalCoordinate.isPresent()) {
                result = horizontalCoordinate;
                found = i + 1;
            }
        }
        return found >= 0 ? Optional.of(found) : Optional.empty();
    }

    private Optional<Integer> getVerticalCoordinate(CharArray map) {
        Optional<Coordinate> result = Optional.empty();
        int found = -1;
        for (int i = 0; i < map.getWidth() - 1; i++) {
            Optional<Coordinate> verticalCoordinate = getVerticalCoordinate(i, map);

            if (result.isPresent() && verticalCoordinate.isPresent()) {
                return Optional.empty();
            }
            if (verticalCoordinate.isPresent()) {
                result = verticalCoordinate;
                found = i + 1;
            }
        }
        return found >= 0 ? Optional.of(found) : Optional.empty();
    }

    private Optional<Coordinate> getHorizontalCoordinate(int line, CharArray map) {
        int i = line;
        int j = line + 1;
        int numberOfCharsThatDiffer = 0;
        Coordinate coordinateThatDiffer = null;
        while (i >= 0 && j < map.getHeight()) {
            List<Integer> integers = compareHorizontalIndices(i, j, map);
            numberOfCharsThatDiffer += integers.size();
            if (numberOfCharsThatDiffer > 1) {
                return Optional.empty();
            }
            if (numberOfCharsThatDiffer == 1 && integers.size() == 1) {
                coordinateThatDiffer = Coordinate.of(i, integers.get(0));
            }
            i--;
            j++;
        }
        return Optional.ofNullable(coordinateThatDiffer);
    }

    private Optional<Coordinate> getVerticalCoordinate(int line, CharArray map) {
        int i = line;
        int j = line + 1;
        int numberOfCharsThatDiffer = 0;
        Coordinate coordinateThatDiffer = null;
        while (i >= 0 && j < map.getWidth()) {
            List<Integer> integers = compareVerticalIndices(i, j, map);
            numberOfCharsThatDiffer += integers.size();
            if (numberOfCharsThatDiffer > 1) {
                return Optional.empty();
            }
            if (numberOfCharsThatDiffer == 1 && integers.size() == 1) {
                coordinateThatDiffer = Coordinate.of(i, integers.get(0));
            }
            i--;
            j++;
        }
        return Optional.ofNullable(coordinateThatDiffer);
    }

    private List<Integer> compareHorizontalIndices(int i, int j, CharArray map) {
        char[] horizontalSlice1 = map.getHorizontalSlice(i);
        char[] horizontalSlice2 = map.getHorizontalSlice(j);
        return getCharDiffIndices(horizontalSlice1, horizontalSlice2);
    }

    private List<Integer> compareVerticalIndices(int i, int j, CharArray map) {
        char[] horizontalSlice1 = map.getVerticalSlice(i);
        char[] horizontalSlice2 = map.getVerticalSlice(j);
        return getCharDiffIndices(horizontalSlice1, horizontalSlice2);
    }

    private List<Integer> getCharDiffIndices(char[] slice1, char[] slice2) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < slice1.length; i++) {
            if (slice1[i] != slice2[i]) {
                result.add(i);
            }
        }
        return result;
    }
}