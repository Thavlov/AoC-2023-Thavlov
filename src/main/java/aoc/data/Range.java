package aoc.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Range {
    private final int from;
    private final int to;

    public Range(int from, int to) {
        if (from > to) {
            throw new RuntimeException("Error creating range: from should be less than to.");
        }
        this.from = from;
        this.to = to;
    }

    public static Range of(int from, int to) {
        return new Range(from, to);
    }

    public static Range parse(String string) {
        final String[] split = string.split("-");
        return new Range(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    public static Pair<Range, Range> parsePair(String s) {
        String[] split = s.split(",");
        return new Pair<>(Range.parse(split[0]), Range.parse(split[1]));
    }

    public static List<Range> mergeRanges(final List<Range> ranges) {
        List<Range> result = new ArrayList<>();

        List<Range> sortedRanges = List.copyOf(ranges).stream().sorted(Comparator.comparingInt(Range::getFrom)).collect(Collectors.toList());

        Range range;

        for (int i = 0; i < sortedRanges.size(); i++) {
            range = sortedRanges.get(i);
            for (int j = i + 1; j < sortedRanges.size(); j++) {
                if (isOverlapping(range, sortedRanges.get(j))) {
                    range = mergeOverlappingRanges(range, sortedRanges.get(j));
                    i++;
                }
            }
            result.add(range);
        }

        return result;
    }

    private static Range mergeOverlappingRanges(Range first, Range second) {
        if (!isOverlapping(first, second)) {
            throw new RuntimeException("Error merging ranges: ranges are not overlapping.");
        }

        return new Range(Math.min(first.from, second.from), Math.max(first.to, second.to));
    }

    public static boolean isInRange(Pair<Range, Range> pair) {
        return pair.getFirst().isInRangeOther(pair.getSecond()) || pair.getSecond().isInRangeOther(pair.getFirst());
    }

    private static boolean isOverlapping(Range first, Range second) {
        return first.isValueInRange(second.getFrom()) || first.isValueInRange(second.getTo()) || first.isCompletelyOverlappedBy(second) || second.isCompletelyOverlappedBy(first);
    }

    public static boolean isNotOverlapping(Pair<Range, Range> pair) {
        return isNotOverlapping(pair.getFirst(), pair.getSecond());
    }

    private static boolean isNotOverlapping(Range first, Range second) {
        return first.from <= second.to && second.from <= first.to;
    }

    public boolean isInRangeOther(Range other) {
        return this.from <= other.from && this.to >= other.to;
    }

    private boolean isValueInRange(int value) {
        return from <= value && value <= to;
    }

    private boolean isCompletelyOverlappedBy(Range other) {
        return other.from <= from && to <= other.to;
    }


    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
