package aoc.data;

import java.util.Objects;

import aoc.util.StringUtil;

public class Pair<T, T1> {
    final T first;
    final T1 second;

    public Pair(T p1, T1 p2) {
        this.first = p1;
        this.second = p2;
    }

    public static <T, T1> Pair<T, T1> of(T p1, T1 p2) {
        return new Pair<T, T1>(p1, p2);
    }

    public static Pair<String, String> parse(String string) {
        final String[] split = string.replace("(", StringUtil.EMPTY).replace(")", StringUtil.EMPTY).split(StringUtil.DELIMITER_COMMA);
        return Pair.of(split[0], split[1]);
    }

    public T getFirst() {
        return first;
    }

    public T1 getSecond() {
        return second;
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
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return first.equals(pair.first) && second.equals(pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
