package aoc.days;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static aoc.util.AoCConstants.RUN_EXAMPLE;
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
        final String part1Solution = dayToTest.getPart1Solution().toString();

        if (RUN_EXAMPLE) {
            assertThat(part1Solution).isEqualTo("62");
        } else {
            assertThat(part1Solution).isEqualTo("70026");
        }
    }

    @Test
    void testPart2Solution() throws Exception {
        final String part2Solution = dayToTest.getPart2Solution().toString();

        if (RUN_EXAMPLE) {
            assertThat(part2Solution).isEqualTo("952408144115");
        } else {
            assertThat(part2Solution).isEqualTo("68548301037382");
        }
    }
}