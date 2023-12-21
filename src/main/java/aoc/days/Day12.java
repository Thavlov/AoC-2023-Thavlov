package aoc.days;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import aoc.data.Pair;
import aoc.util.FileUtil;
import aoc.util.StringUtil;

public class Day12 extends Day {

    private List<String> input;
    private static final Map<Pair<String, List<Integer>>, Long> map = new HashMap<>();

    protected void initialize() throws Exception {
        input = FileUtil.readFileToStrings(getDay());
        map.clear();
    }

    protected Object getPart1Solution() {
        long result = 0;

        for (String in : input) {
            result += getCombinationsWithRepeat(in, 1);
        }

        return result;
    }

    protected Object getPart2Solution() {
        long result = 0;

        for (String in : input) {
            result += getCombinationsWithRepeat(in, 5);
        }

        return result;
    }

    private long getCombinations(String in) {
        final String[] split = in.split(StringUtil.SPACE);
        final String pattern = split[0];
        final List<Integer> nums = StringUtil.parseListOfIntegers(split[1], StringUtil.DELIMITER_COMMA);

        return getCombinations(pattern, nums);
    }

    private long getCombinationsWithRepeat(String in, int repeat) {
        final String[] split = in.split(StringUtil.SPACE);
        String pattern =(split[0] + "?").repeat(repeat);
        pattern = pattern.substring(0, pattern.length() - 1);
        String integers = (split[1] + ",").repeat(repeat);
        integers = integers.substring(0, integers.length() - 1);
        final List<Integer> nums = StringUtil.parseListOfIntegers(integers, StringUtil.DELIMITER_COMMA);
        return getCombinationsWithCache(pattern, nums);
    }

    private long getCombinations(String pattern, List<Integer> nums) {
        if (map.containsKey(Pair.of(pattern, nums))) {
            return map.get(Pair.of(pattern, nums));
        }

        int indexOf = pattern.indexOf("?");
        if (indexOf >= 0) {
            long result = getCombinations(pattern.replaceFirst("\\?", "#"), nums) + getCombinations(pattern.replaceFirst("\\?", "."), nums);
            map.put(Pair.of(pattern, nums), result);
            return result;
        }

        return fulfillPattern(pattern, nums) ? 1 : 0;
    }

    private boolean fulfillPattern(String pattern, List<Integer> matches) {
        if (pattern.contains("?")) {
            throw new RuntimeException();
        }
        final List<String> split = Arrays.stream(pattern.split("\\.")).filter(s -> s.length() > 0).collect(Collectors.toList());

        if (split.size() != matches.size()) {
            return false;
        }

        for (int i = 0; i < split.size(); i++) {
            if (split.get(i).length() != matches.get(i)) {
                return false;
            }
        }
        return true;
    }

    private long getCombinationsWithCache(String pattern, List<Integer> matches) {
        if (StringUtil.isNullOrEmpty(pattern)) {
            return matches.isEmpty() ? 1 : 0;
        }

        final Pair<String, List<Integer>> key = Pair.of(pattern, matches);
        if (map.containsKey(key)) {
            return map.get(key);
        }

        char firstChar = pattern.charAt(0);
        long result = 0;
        if (firstChar == '.') {
            result = getCombinationsWithCache(pattern.substring(1), matches);
        } else if (firstChar == '?') {
            result = getCombinationsWithCache("." + pattern.substring(1), matches) + getCombinationsWithCache("#" + pattern.substring(1), matches);
        } else {
            if (!matches.isEmpty()) {
                int match = matches.get(0);
                if (match <= pattern.length() && pattern.chars().limit(match).allMatch(c -> c == '#' || c == '?')) {
                    List<Integer> newGroups = matches.subList(1, matches.size());
                    if (match == pattern.length()) {
                        result = newGroups.isEmpty() ? 1 : 0;
                    } else if (pattern.charAt(match) == '.') {
                        result = getCombinationsWithCache(pattern.substring(match + 1), newGroups);
                    } else if (pattern.charAt(match) == '?') {
                        result = getCombinationsWithCache("." + pattern.substring(match + 1), newGroups);
                    }
                }
            }
        }
        map.put(key, result);
        return result;
    }
}
