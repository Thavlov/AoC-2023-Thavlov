package aoc.data;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RangeTest {

    @Test
    void getIntersectionWith1() {
        Range range1 = Range.of(1, 10);
        Range range2 = Range.of(7, 10);

        Range intersectionWith = range1.getIntersectionWith(range2);

        assertThat(intersectionWith.getFrom()).isEqualTo(7);
        assertThat(intersectionWith.getTo()).isEqualTo(10);
    }

    @Test
    void getIntersectionWith2() {
        Range range1 = Range.of(3, 10);
        Range range2 = Range.of(3, 17);

        Range intersectionWith = range1.getIntersectionWith(range2);

        assertThat(intersectionWith.getFrom()).isEqualTo(3);
        assertThat(intersectionWith.getTo()).isEqualTo(10);
    }

    @Test
    void getIntersectionWith3() {
        Range range1 = Range.of(5, 10);
        Range range2 = Range.of(1, 17);

        Range intersectionWith = range1.getIntersectionWith(range2);

        assertThat(intersectionWith.getFrom()).isEqualTo(5);
        assertThat(intersectionWith.getTo()).isEqualTo(10);
    }

    @Test
    void getIntersectionWith4() {
        Range range1 = Range.of(3, 10);
        Range range2 = Range.of(13, 17);

        assertThrows(RuntimeException.class, () -> range1.getIntersectionWith(range2));
    }

    @Test
    void getDisjointWith1() {
        Range range1 = Range.of(5, 10);
        Range range2 = Range.of(1, 17);

        List<Range> getDisjointWith = range1.getDisjointWith(range2);

        assertThat(getDisjointWith).hasSize(2);

        assertThat(getDisjointWith.get(0).getFrom()).isEqualTo(1);
        assertThat(getDisjointWith.get(0).getTo()).isEqualTo(4);
        assertThat(getDisjointWith.get(1).getFrom()).isEqualTo(11);
        assertThat(getDisjointWith.get(1).getTo()).isEqualTo(17);
    }

    @Test
    void getDisjointWith2() {
        Range range1 = Range.of(1, 10);
        Range range2 = Range.of(7, 10);

        List<Range> getDisjointWith = range1.getDisjointWith(range2);

        assertThat(getDisjointWith).hasSize(1);
        assertThat(getDisjointWith.get(0).getFrom()).isEqualTo(1);
        assertThat(getDisjointWith.get(0).getTo()).isEqualTo(6);
    }

    @Test
    void getDisjointWith3() {
        Range range1 = Range.of(3, 10);
        Range range2 = Range.of(3, 17);

        List<Range> getDisjointWith = range1.getDisjointWith(range2);

        assertThat(getDisjointWith).hasSize(1);
        assertThat(getDisjointWith.get(0).getFrom()).isEqualTo(11);
        assertThat(getDisjointWith.get(0).getTo()).isEqualTo(17);
    }

    @Test
    void getDisjointWith4() {
        Range range1 = Range.of(5, 10);
        Range range2 = Range.of(1, 17);

        List<Range> getDisjointWith = range1.getDisjointWith(range2);

        assertThat(getDisjointWith).hasSize(2);
        assertThat(getDisjointWith.get(0).getFrom()).isEqualTo(1);
        assertThat(getDisjointWith.get(0).getTo()).isEqualTo(4);
        assertThat(getDisjointWith.get(1).getFrom()).isEqualTo(11);
        assertThat(getDisjointWith.get(1).getTo()).isEqualTo(17);
    }

    @Test
    void getDisjointWith5() {
        Range range1 = Range.of(3, 10);
        Range range2 = Range.of(13, 17);

        List<Range> getDisjointWith = range1.getDisjointWith(range2);

        assertThat(getDisjointWith).hasSize(0);
    }
}