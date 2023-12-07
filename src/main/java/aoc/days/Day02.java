package aoc.days;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aoc.util.FileUtil;
import aoc.util.StringUtil;

public class Day02 extends Day {
    private List<String> input;

    protected void initialize() throws Exception {
        input = FileUtil.readFileToStrings(getDay());
    }

    protected Object getPart1Solution() throws Exception {
        return input.stream().mapToInt(this::getGameIdIfValid).sum();
    }

    protected Object getPart2Solution() throws Exception {
        return input.stream().mapToInt(this::getMaxCubesOfColor).sum();
    }

    private int getGameIdIfValid(String string) {
        if (getMaxCubesOfColor(string, "red") > 12) {
            return 0;
        }

        if (getMaxCubesOfColor(string, "green") > 13) {
            return 0;
        }

        if (getMaxCubesOfColor(string, "blue") > 14) {
            return 0;
        }

        final String regexGame = "Game (\\d+):";
        final Pattern pattern = Pattern.compile(regexGame);
        final Matcher matcher = pattern.matcher(string);

        matcher.find();
        return Integer.parseInt(matcher.group(1));

    }

    private int getMaxCubesOfColor(String string) {
        int maxRed = getMaxCubesOfColor(string, "red");
        int maxGreen = getMaxCubesOfColor(string, "green");
        int maxBlue = getMaxCubesOfColor(string, "blue");

        return maxRed * maxGreen * maxBlue;
    }

    private int getMaxCubesOfColor(String string, String color) {
        String regex = " (\\d+) " + color;
        String[] split = string.split(StringUtil.DELIMITER_SEMICOLON);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        int max = 0;
        for (String s : split) {
            matcher = pattern.matcher(s);
            if (matcher.find()) {
                max = Math.max(max, Integer.parseInt(matcher.group(1)));
            }
        }
        return max;
    }
}