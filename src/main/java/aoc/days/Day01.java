package aoc.days;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aoc.util.FileUtil;
import aoc.util.StringUtil;

public class Day01 extends Day {
    private List<String> input;

    @Override
    protected void initialize() throws Exception {
        input = FileUtil.readFileToStrings(getDay());
    }

    @Override
    protected Object getPart1Solution() throws Exception {
        return input.stream().mapToInt(this::parse).sum();
    }

    @Override
    protected Object getPart2Solution() throws Exception {
        return input.stream().mapToInt(this::parse2).sum();
    }

    private int parse(String s) {
        final String regex1 = "\\D*(\\d)\\w*";
        Pattern pattern = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        matcher.find();
        String s1 = matcher.group(1);

        final String regex2 = "\\w*(\\d)\\D*";
        pattern = Pattern.compile(regex2, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(s);
        matcher.find();
        String s2 = matcher.group(1);

        return Integer.parseInt(s1 + s2);
    }

    private int parse2(String s) {
        final String regex1 = "\\D?(one|two|three|four|five|six|seven|eight|nine|\\d)\\w?";

        Pattern pattern = Pattern.compile(regex1);
        Matcher matcher = pattern.matcher(s);
        matcher.find();
        int i1 = StringUtil.parseNumberFromString(matcher.group(1));

        final String regex2 = "\\w*(one|two|three|four|five|six|seven|eight|nine|\\d)\\D?";
        pattern = Pattern.compile(regex2);
        matcher = pattern.matcher(s);
        matcher.find();
        int i2 = StringUtil.parseNumberFromString(matcher.group(1));

        return 10 * i1 + i2;
    }
}