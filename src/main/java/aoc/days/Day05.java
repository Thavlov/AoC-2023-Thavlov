package aoc.days;

import aoc.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day05 extends Day {
    private final List<Stack<String>> stacks = new ArrayList<>();

    protected void initialize() throws Exception {
        final List<String> strings = FileUtil.readFileToStrings(getDay(), 8);

        for (int i = 7; i >= 0; i--) {
            String s = strings.get(i);
            int index = 0;
            for (char aChar : s.toCharArray()) {
                if (Character.isLetter(aChar)) {
                    if (stacks.size() <= (index / 4)) {
                        stacks.add(new Stack<>());
                    }
                    stacks.get((index / 4)).push("" + aChar);
                }
                index++;
            }
        }
    }

    protected String getPart1Solution() throws Exception {
        final List<String> instructions = FileUtil.readFileToStringsSkip(getDay(), 10);
        final String regex = "move (\\d+) from (\\d+) to (\\d+)";
        final Pattern pattern = Pattern.compile(regex);

        for (String s : instructions) {
            Matcher matcher = pattern.matcher(s);
            matcher.find();

            int howMany = Integer.parseInt(matcher.group(1));
            int from = Integer.parseInt(matcher.group(2)) - 1;
            int to = Integer.parseInt(matcher.group(3)) - 1;

            for (int i = 0; i < howMany; i++) {
                stacks.get(to).push(stacks.get(from).pop());
            }
        }

        final StringBuilder result = new StringBuilder();
        for (Stack<String> stack : stacks) {
            result.append(stack.pop());

        }
        return result.toString();
    }

    protected String getPart2Solution() throws Exception {
        final List<String> instructions = FileUtil.readFileToStringsSkip(getDay(), 10);
        final String regex = "move (\\d+) from (\\d+) to (\\d+)";
        final Pattern pattern = Pattern.compile(regex);

        final Stack<String> tempStack = new Stack<>();
        for (String s : instructions) {
            Matcher matcher = pattern.matcher(s);
            matcher.find();

            int howMany = Integer.parseInt(matcher.group(1));
            int from = Integer.parseInt(matcher.group(2)) - 1;
            int to = Integer.parseInt(matcher.group(3)) - 1;

            for (int i = 0; i < howMany; i++) {
                tempStack.push(stacks.get(from).pop());
            }

            for (int i = 0; i < howMany; i++) {
                stacks.get(to).push(tempStack.pop());
            }
        }

        final StringBuilder result = new StringBuilder();
        for (Stack<String> stack : stacks) {
            result.append(stack.pop());

        }
        return result.toString();
    }

}