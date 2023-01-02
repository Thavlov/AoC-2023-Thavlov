package aoc.days;

import aoc.util.AoCConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Day22Test {

    private static Day dayToTest;

    @BeforeAll
    static void initializeTest() {
        dayToTest = new Day22();
    }

    @BeforeEach
    void initializeDay() throws Exception {
        dayToTest.initialize();
    }

    @Test
    void testPart1Solution() throws Exception {
        final String part1Solution = dayToTest.getPart1Solution();

        if (AoCConstants.RUN_EXAMPLE) {
            assertThat(part1Solution).isEqualTo("6032");
        } else {
            assertThat(part1Solution).isEqualTo("80392");
        }
    }

    @Test
    void testPart2Solution() throws Exception {
        if (AoCConstants.RUN_EXAMPLE) {
            assertThatThrownBy(() -> dayToTest.getPart2Solution()).isInstanceOf(ArrayIndexOutOfBoundsException.class);
        } else {
            final String part2Solution = dayToTest.getPart2Solution();
            assertThat(part2Solution).isEqualTo("19534");
        }
    }
}

