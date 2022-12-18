package aoc.data;

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

    public T getFirst() {
        return first;
    }

    public T1 getSecond() {
        return second;
    }
}
