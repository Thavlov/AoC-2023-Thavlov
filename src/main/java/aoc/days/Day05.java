package aoc.days;

import aoc.util.FileUtil;
import aoc.util.Pair;
import aoc.util.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Day05 extends Day {
    private List<Pair<Range, Range>> input;
    private List<Stack<String>> stacks = new ArrayList<>();

    protected void initialize() throws Exception {
        List<String> strings = FileUtil.readFileToStrings(getDay(), 8);
        String s = null;
        for (int i = 7; i >= 0; i--) {
            s = strings.get(i);
            int index = 0;
            char[] chars = s.toCharArray();
            for (char aChar : chars) {
                if (aChar == '[' || aChar == ']') {

                } else if (aChar == ' ') {

                } else {

                    if (stacks.size() <= (index/4)) {
                        stacks.add(new Stack<>());
                    }
                    stacks.get((index/4)).push("" + aChar);
                }
                index++;
            }


        }


        List<String> strings2 = FileUtil.readFileToStringsSkip(getDay(), 10);
    }

    protected String getPart1Solution() {
        int result = 0;
        return "" + result;
    }

    protected String getPart2Solution() {
        int result = 0;
        return "" + result;
    }

}