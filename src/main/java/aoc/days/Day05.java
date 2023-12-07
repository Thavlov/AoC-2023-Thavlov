package aoc.days;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import aoc.util.FileUtil;
import aoc.util.StringUtil;

public class Day04 extends Day {
    private List<String> input;

    protected void initialize() throws Exception {
        input = FileUtil.readFileToStrings(getDay());
    }

    protected Object getPart1Solution() throws Exception {
        return input.stream()
                .map(this::getNumberOfMatches)
                .mapToInt(this::resolveScore)
                .sum();
    }

    protected Object getPart2Solution() throws Exception {
        final int[] cards = new int[input.size()];
        Arrays.fill(cards, 1);

        int card = 1;
        for (String string : input) {
            int matches = getNumberOfMatches(string);

            int numberOfCards = cards[card - 1];
            for (int i = card; i < card + matches; i++) {
                cards[i] += numberOfCards;
            }
            card++;
        }

        return Arrays.stream(cards).sum();
    }

    private int getNumberOfMatches(String string) {
        final String[] split = string.split(":");
        final String[] split1 = split[1].split("\\|");

        List<Integer> firstSet = parseNumberFromString(split1[0]);
        List<Integer> secondSet = parseNumberFromString(split1[1]);
        int matches = 0;
        for (Integer integer : secondSet) {
            if (firstSet.contains(integer)) {
                matches++;
            }
        }
        return matches;
    }

    private List<Integer> parseNumberFromString(String string) {
        return Arrays.stream(string.trim().split(" ")).filter(StringUtil::isNotNullOrEmpty).map(Integer::parseInt).collect(Collectors.toList());
    }

    private int resolveScore(int found) {
        if (found == 0) {
            return 0;
        }
        int score = 1;
        for (int i = 1; i < found; i++) {
            score *= 2;
        }
        return score;
    }
}