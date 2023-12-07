package aoc.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Range implements Comparable<Range> {
    private final long from;
    private final long to;

    public Range(long from, long to) {
        if (from > to || from < 0) {
            throw new RuntimeException("Error creating range: from should be less than to.");
        }
        this.from = from;
        this.to = to;
    }

    public static Range of(long from, long to) {
        return new Range(from, to);
    }

    public static Range from(Range other) {
        return new Range(other.getFrom(), other.getTo());
    }

    public static Range ofWidth(long from, long width) {
        return new Range(from, from + width - 1);
    }

    public static Range fromWithShift(Range range, long dx) {
        return new Range(range.getFrom() + dx, range.getTo() + dx);
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    public boolean isValueInRange(long value) {
        return from <= value && value <= to;
    }

    private boolean isValueInRangeExclusive(long value) {
        return from < value && value < to;
    }

    public List<Long> getAllValuesInRange() {
        return LongStream.range(from, to + 1).boxed().collect(Collectors.toList());
    }

    private boolean isOverlapping(Range other) {
        return this.isValueInRange(other.getFrom()) || this.isValueInRange(other.getTo()) || this.isCompletelyOverlappedBy(other) || other.isCompletelyOverlappedBy(this);
    }

    public boolean isCompletelyOverlappedBy(Range other) {
        return other.from <= from && to <= other.to;
    }

    public boolean isPartlyOverlappedBy(Range other) {
        return this.isValueInRange(other.getFrom()) || this.isValueInRange(other.getTo());
    }

    public Range getIntersectionWith(Range other) {
        if (this.isCompletelyOverlappedBy(other)) {
            return Range.from(this);
        }

        if (other.isCompletelyOverlappedBy(this)) {
            return Range.from(other);
        }

        if (this.isPartlyOverlappedBy(other)) {
            return Range.of(Math.max(this.getFrom(), other.getFrom()), Math.min(this.getTo(), other.getTo()));
        }

        throw new RuntimeException("Error finding range intersection: Ranges does not overlap.");
    }

    public List<Range> getDisjointWith(final Range other) {
        final List<Range> result = new ArrayList<>();
        if (this.isPartlyOverlappedBy(other)) {
            if (this.getFrom() != other.getFrom()) {
                result.add(Range.of(Math.min(this.getFrom(), other.getFrom()), Math.max(this.getFrom(), other.getFrom()) - 1));
            }
            if (this.getTo() != other.getTo()) {
                result.add(Range.of(Math.min(this.getTo(), other.getTo()) + 1, Math.max(this.getTo(), other.getTo())));
            }
            return result;
        }

        if (this.isCompletelyOverlappedBy(other)) {
            if (this.getFrom() != other.getFrom()) {
                result.add(Range.of(other.getFrom(), this.getFrom() - 1));
            }
            if (this.getTo() != other.getTo()) {
                result.add(Range.of(this.getTo() + 1, other.getTo()));
            }
            return result;
        }

        return Collections.emptyList();
    }

    public OptionalLong getIntersectionPoint(Range other) {
        if (this.isValueInRange(other.getFrom())) {
            return OptionalLong.of(other.getFrom());
        }
        if (this.isValueInRange(other.getTo())) {
            return OptionalLong.of(other.getTo());
        }
        return OptionalLong.empty();
    }

    public OptionalLong getIntersectionPointExclusive(Range other) {
        if (this.isValueInRangeExclusive(other.getFrom())) {
            return OptionalLong.of(other.getFrom());
        }
        if (this.isValueInRangeExclusive(other.getTo())) {
            return OptionalLong.of(other.getTo());
        }
        return OptionalLong.empty();
    }

    public List<Range> splitAround(long value) {
        return List.of(Range.of(from, value - 1), Range.of(value, to));
    }

    @Override
    public int compareTo(Range other) {
        return Long.compare(this.getFrom(), other.getFrom());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Range range = (Range) o;
        return from == range.from && to == range.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
