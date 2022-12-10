package aoc.data;

public class Range {
    final int from;
    final int to;

    public Range(String s) {
        String[] split = s.split("-");
        this.from = Integer.parseInt(split[0]);
        this.to = Integer.parseInt(split[1]);
    }

    public static Pair<Range, Range> toPair(String s) {
        String[] split = s.split(",");
        return new Pair<>(new Range(split[0]), new Range(split[1]));
    }

    public static boolean isInRange(Pair<Range, Range> pair) {
        return pair.getFirst().isInRangeOther(pair.getSecond()) || pair.getSecond().isInRangeOther(pair.getFirst());
    }

    public static boolean isNotOverlapping(Pair<Range, Range> pair) {
        return pair.getFirst().from <= pair.getSecond().to && pair.getSecond().from <= pair.getFirst().to;
    }

    public boolean isInRangeOther(Range other) {
        return this.from <= other.from && this.to >= other.to;
    }
}
