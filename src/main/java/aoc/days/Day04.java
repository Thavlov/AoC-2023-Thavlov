package aoc.days;

import aoc.util.FileUtil;
import aoc.data.Pair;
import aoc.data.Range;

import java.util.List;

public class Day04 extends Day {
    private List<Pair<Range, Range>> input;

    protected void initialize() throws Exception {
        input = FileUtil.readFileToObjects(getDay(), Range::parsePair);
    }

    protected String getPart1Solution() {
        long count = input.stream().filter(Range::isInRange).count();
        return "" + count;
    }

    protected String getPart2Solution() {
        long count = input.stream().filter(Range::isNotOverlapping).count();
        return "" + count;
    }

}