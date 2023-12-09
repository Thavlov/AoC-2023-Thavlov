package aoc.days;

import aoc.util.FileUtil;
import aoc.util.StringUtil;

import java.util.*;

public class Day09 extends Day {
    private List<String> input;

    protected void initialize() throws Exception {
        input = FileUtil.readFileToStrings(getDay());
    }

    protected Object getPart1Solution() {
        long result = 0;

        for (String s : input) {
            result += parseEnds(s);

        }

        return result;
    }

    protected Object getPart2Solution() {
        long result = 0;

        for (String s : input) {
            result += parseStart(s);

        }

        return result;
    }

    private long parseEnds(final String string) {
        Integer[] array = StringUtil.parseListOfIntegers(string);

        final List<Integer> ends = new ArrayList<>();
        ends.add(array[array.length - 1]);

        while (notOnlyZero(array)) {
            array = getDiffs(array);
            ends.add(array[array.length - 1]);
        }

        long result = 0;
        for (int i = ends.size() - 1; i >= 0; i--) {
            result += ends.get(i);
        }

        return result;
    }

    private long parseStart(final String string) {
        Integer[] array = StringUtil.parseListOfIntegers(string);

        final List<Integer> starts = new ArrayList<>();
        starts.add(array[0]);

        while (notOnlyZero(array)) {
            array = getDiffs(array);
            starts.add(array[0]);
        }

        long result = 0;
        for (int i = starts.size() - 1; i >= 0; i--) {
            result = starts.get(i) - result;
        }

        return result;
    }

    private boolean notOnlyZero(Integer[] ints) {
        return Arrays.stream(ints).anyMatch(i -> i != 0);
    }

    private Integer[] getDiffs(Integer[] ints) {
        Integer[] result = new Integer[ints.length - 1];

        for (int i = 0; i < ints.length - 1; i++) {
            result[i] = ints[i + 1] - ints[i];
        }
        return result;
    }
}