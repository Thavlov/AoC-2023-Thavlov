package aoc.days;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static aoc.util.AoCConstants.RUN_EXAMPLE;
import static org.assertj.core.api.Assertions.assertThat;

class Day05Test {
    private static Day dayToTest;

    @BeforeAll
    static void initializeTest() {
        dayToTest = new Day05();
    }

    @BeforeEach
    void initializeDay() throws Exception {
        dayToTest.initialize();
    }

    @Test
    void testPart1Solution() throws Exception {
        final String part1Solution = dayToTest.solvePart1();

        if (RUN_EXAMPLE) {
            assertThat(part1Solution).isEqualTo("35");
        } else {
            assertThat(part1Solution).isEqualTo("379811651");
        }
    }

    @Test
    void testPart2Solution() throws Exception {
        final String part2Solution = dayToTest.solvePart2();

        if (RUN_EXAMPLE) {
            assertThat(part2Solution).isEqualTo("46");
        } else {
            assertThat(part2Solution).isEqualTo("27992443");
        }
    }
}