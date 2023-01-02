package aoc.days;

import aoc.data.Pair;
import aoc.util.FileUtil;

import java.util.List;

public class Day25 extends Day {
    private List<String> input;

    protected void initialize() throws Exception {
        input = FileUtil.readFileToStrings(getDay());
    }

    protected String getPart1Solution() {
        long sum = input.stream().mapToLong(this::parseSnafu).sum();
        return "" + parseIntegerToSnafu(sum);
    }

    protected String getPart2Solution() {
        return "";
    }

    private long parseSnafu(final String snafu) {
        long number = 0;
        for (int i = 0; i < snafu.length(); i++) {
            number += (Math.pow(5, i) * toBase10(snafu.charAt(snafu.length() - 1 - i)));
        }
        return number;
    }

    private String parseIntegerToSnafu(long value) {
        int maxSnafuPower = findMaxSnafuPower(value);

        final StringBuilder snafu = new StringBuilder();
        for (int i = 0; i < maxSnafuPower; i++) {
            Pair<Character, Long> reduce = reduce(value, maxSnafuPower - i);
            snafu.append(reduce.getFirst());
            value = reduce.getSecond();
        }
        return snafu.toString();
    }

    private Pair<Character, Long> reduce(long value, int power) {
        long snarfu = parseSnafu("1" + "2".repeat(power - 1));
        if (snarfu < value) {
            return Pair.of('2', value - parseSnafu("2" + "0".repeat(power - 1)));
        }

        snarfu = parseSnafu("0" + "2".repeat(power - 1));
        if (snarfu < value) {
            return Pair.of('1', value - parseSnafu("1" + "0".repeat(power - 1)));
        }


        snarfu = parseSnafu("-" + "2".repeat(power - 1));
        if (snarfu < value) {
            return Pair.of('0', value - parseSnafu("0".repeat(power)));
        }

        snarfu = parseSnafu("=" + "2".repeat(power - 1));
        if (snarfu < value) {
            return Pair.of('-', value - parseSnafu("-" + "0".repeat(power - 1)));
        }

        snarfu = parseSnafu("=".repeat(power));
        if (snarfu < value) {
            return Pair.of('=', value - parseSnafu("=" + "0".repeat(power - 1)));
        }
        throw new RuntimeException("Error!");
    }

    private int findMaxSnafuPower(long value) {
        int n = 1;
        while (n < 100) {
            long upper = parseSnafu("2".repeat(n));
            if (upper > value) {
                return n;
            }
            n++;
        }
        throw new RuntimeException("Error!");
    }

    private int toBase10(char c) {
        if (Character.isDigit(c)) {
            return Integer.parseInt("" + c);
        }
        if (c == '-') {
            return -1;
        }
        if (c == '=') {
            return -2;
        }
        throw new RuntimeException("Error!");
    }
}