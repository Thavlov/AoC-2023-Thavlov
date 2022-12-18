package aoc.days;

import aoc.util.AoCConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day18Test {

    private static Day dayToTest;

    @BeforeAll
    static void initializeTest() {
        dayToTest = new Day18();
    }

    @BeforeEach
    void initializeDay() throws Exception {
        dayToTest.initialize();
    }

    @Test
    void testPart1Solution() throws Exception {
        final String part1Solution = dayToTest.getPart1Solution();

        if (AoCConstants.RUN_EXAMPLE) {
            assertThat(part1Solution).isEqualTo("64");
        } else {
            assertThat(part1Solution).isEqualTo("3650");
        }
    }

    @Test
    void testPart2Solution() throws Exception {
        final String part2Solution = dayToTest.getPart2Solution();

        if (AoCConstants.RUN_EXAMPLE) {
            assertThat(part2Solution).isEqualTo("58");
        } else {
            assertThat(part2Solution).isEqualTo("2118");
        }
    }
}

