package aoc.util;

import aoc.days.Day;

public class SolutionUtil {
    private SolutionUtil() {
        //EMPTY
    }

    public static <T extends Day> void solveDay(final Class<T> dayClass) {
        try {
            dayClass.getDeclaredConstructor().newInstance().solveAndPrint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T extends Day> void solvePart1(final Class<T> dayClass) {
        try {
            dayClass.getDeclaredConstructor().newInstance().solveAndPrintPart1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T extends Day> void solvePart2(final Class<T> dayClass) {
        try {
            dayClass.getDeclaredConstructor().newInstance().solveAndPrintPart2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


