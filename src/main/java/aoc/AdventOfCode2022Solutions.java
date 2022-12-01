package aoc;

import aoc.days.Day01;
import aoc.util.SolutionUtil;

public class AdventOfCode2022Solutions {
    public static void main(String[] args) {
        try {
            AdventOfCode2022Solutions.solveAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private static void solveAll() throws Exception {
        System.out.println("Advent of Code 2022 solutions:");

        SolutionUtil.solveDay(Day01.class);
    }
}