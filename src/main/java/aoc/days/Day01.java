package aoc.days;

import aoc.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class Day01 extends Day {
    private List<Integer> calories;

    protected void initialize() throws Exception {
        calories = new ArrayList<>();
        List<String> strings = FileUtil.readFileToStrings(getDay());
        int calorie = 0;
        for (String s : strings) {
            if (s.isEmpty()) {
                calories.add(calorie);
                calorie = 0;
                continue;
            }
            calorie += Integer.parseInt(s);
        }
    }

    protected String getPart1Solution() {
        int result = calories.stream().mapToInt(Integer::intValue).max().orElseThrow();
        return "" + result;
    }

    protected String getPart2Solution() {
        int caloriesSize = calories.size();
        int result = calories.stream().mapToInt(Integer::intValue).sorted().skip(caloriesSize - 3).sum();
        return "" + result;
    }
}