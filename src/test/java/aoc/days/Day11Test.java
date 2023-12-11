package aoc.days;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static aoc.util.AoCConstants.RUN_EXAMPLE;
import static org.assertj.core.api.Assertions.assertThat;

class Day11Test {
    private static Day dayToTest;

    @BeforeAll
    static void initializeTest() {
        dayToTest = new Day11();
    }

    @BeforeEach
    void initializeDay() throws Exception {
        dayToTest.initialize();
    }

    @Test
    void testPart1Solution() throws Exception {
        final String part1Solution = dayToTest.solvePart1();

        if (RUN_EXAMPLE) {
            assertThat(part1Solution).isEqualTo("374");
        } else {
            assertThat(part1Solution).isEqualTo("9522407");
        }
    }

    @Test
    void testPart2Solution() throws Exception {
        final String part2Solution = dayToTest.solvePart2();

        if (RUN_EXAMPLE) {
            assertThat(part2Solution).isEqualTo("82000210");
        } else {
            assertThat(part2Solution).isEqualTo("544723432977");
        }
    }
}