package aoc.days;

import java.util.List;

import aoc.util.FileUtil;
import aoc.util.StringUtil;

public class Day06 extends Day {
    private List<Long> times;
    private List<Long> distances;

    protected void initialize() throws Exception {
        final List<String> input = FileUtil.readFileToStrings(getDay());

        times = StringUtil.parseListOfNumbers(input.get(0).replace("Time: ", StringUtil.EMPTY));
        distances = StringUtil.parseListOfNumbers(input.get(1).replace("Distance: ", StringUtil.EMPTY));
    }

    protected Object getPart1Solution() throws Exception {
        long product = 1;
        for (int i = 0; i < times.size(); i++) {
            product *= getScore(i);
        }
        return product;
    }

    protected Object getPart2Solution() throws Exception {
        final List<String> input = FileUtil.readFileToStrings(getDay());

        long time = Long.parseLong(input.get(0).replace("Time: ", StringUtil.EMPTY).replace(StringUtil.SPACE, StringUtil.EMPTY));
        long distance = Long.parseLong(input.get(1).replace("Distance: ", StringUtil.EMPTY).replace(StringUtil.SPACE, StringUtil.EMPTY));

        return getScore(time, distance);
    }

    private long getScore(int index) {
        return getScore(times.get(index), distances.get(index));
    }

    private long getScore(long timeTotal, long recordDistance) {
        int score = 0;
        boolean foundFeasible = false;
        for (int time = 1; time < timeTotal; time++) {
            if (isFeasible(time, timeTotal, recordDistance)) {
                score++;
                foundFeasible = true;
            } else if (foundFeasible) {
                return score;
            }
        }
        return score;
    }

    private boolean isFeasible(long time, long timeTotal, long recordDistance) {
        long distance = time * (timeTotal - time);
        return recordDistance < distance;
    }
}