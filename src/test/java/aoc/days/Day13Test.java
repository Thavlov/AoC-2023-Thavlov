package aoc.days;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static aoc.util.AoCConstants.RUN_EXAMPLE;
import static org.assertj.core.api.Assertions.assertThat;

class Day13Test {
    private static Day dayToTest;

    @BeforeAll
    static void initializeTest() {
        dayToTest = new Day13();
    }

    @BeforeEach
    void initializeDay() throws Exception {
        dayToTest.initialize();
    }

    @Test
    void testPart1Solution() throws Exception {
        final String part1Solution = dayToTest.getPart1Solution().toString();

        if (RUN_EXAMPLE) {
            assertThat(part1Solution).isEqualTo("405");
        } else {
            assertThat(part1Solution).isEqualTo("27505");
        }
    }

    @Test
    void testPart2Solution() throws Exception {
        final String part2Solution = dayToTest.getPart2Solution().toString();

        if (RUN_EXAMPLE) {
            assertThat(part2Solution).isEqualTo("400");
        } else {
            assertThat(part2Solution).isEqualTo("22906");
        }
    }
}