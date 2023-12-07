package aoc.days;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static aoc.util.AoCConstants.RUN_EXAMPLE;
import static org.assertj.core.api.Assertions.assertThat;

class Day06Test {
    private static Day dayToTest;

    @BeforeAll
    static void initializeTest() {
        dayToTest = new Day06();
    }

    @BeforeEach
    void initializeDay() throws Exception {
        dayToTest.initialize();
    }

    @Test
    void testPart1Solution() throws Exception {
        final String part1Solution = dayToTest.solvePart1();

        if (RUN_EXAMPLE) {
            assertThat(part1Solution).isEqualTo("288");
        } else {
            assertThat(part1Solution).isEqualTo("2612736");
        }
    }

    @Test
    void testPart2Solution() throws Exception {
        final String part2Solution = dayToTest.solvePart2();

        if (RUN_EXAMPLE) {
            assertThat(part2Solution).isEqualTo("71503");
        } else {
            assertThat(part2Solution).isEqualTo("29891250");
        }
    }
}