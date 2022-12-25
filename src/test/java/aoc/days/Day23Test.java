package aoc.days;

import aoc.util.AoCConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day23Test {

    private static Day dayToTest;

    @BeforeAll
    static void initializeTest() {
        dayToTest = new Day23();
    }

    @BeforeEach
    void initializeDay() throws Exception {
        dayToTest.initialize();
    }

    @Test
    void testPart1Solution() throws Exception {
        final String part1Solution = dayToTest.getPart1Solution();

        if (AoCConstants.RUN_EXAMPLE) {
            assertThat(part1Solution).isEqualTo("101");
        } else {
            assertThat(part1Solution).isEqualTo("3864");
        }
    }

    @Test
    void testPart2Solution() throws Exception {
        final String part2Solution = dayToTest.getPart2Solution();

        if (AoCConstants.RUN_EXAMPLE) {
            assertThat(part2Solution).isEqualTo("20");
        } else {
            assertThat(part2Solution).isEqualTo("946");
        }
    }
}

