package aoc.data;

import java.util.Objects;

import aoc.util.StringUtil;

public class Triplet<T, T1, T2> {
    final T first;
    final T1 second;

    final T2 third;

    public Triplet(T p1, T1 p2, T2 p3) {
        this.first = p1;
        this.second = p2;
        this.third = p3;
    }

    public static <T, T1, T2> Triplet<T, T1, T2> of(T p1, T1 p2, T2 p3) {
        return new Triplet<>(p1, p2, p3);
    }

    public static Triplet<String, String, String> parse(String string) {
        final String[] split = string.replace("(", StringUtil.EMPTY).replace(")", StringUtil.EMPTY).split(StringUtil.DELIMITER_COMMA);
        return Triplet.of(split[0], split[1], split[2]);
    }

    public T getFirst() {
        return first;
    }

    public T1 getSecond() {
        return second;
    }

    public T2 getThird() {
        return third;
    }

    @Override
    public String toString() {
        return first.toString() + ":" + second.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Triplet<?, ?, ?> pair = (Triplet<?, ?, ?>) o;
        return first.equals(pair.first) && second.equals(pair.second) && third.equals(pair.third);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }
}
