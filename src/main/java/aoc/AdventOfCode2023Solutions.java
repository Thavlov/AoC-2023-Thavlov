package aoc;

import aoc.days.*;
import aoc.util.SolutionUtil;

public class AdventOfCode2023Solutions {
    public static void main(String[] args) {
        try {
            AdventOfCode2023Solutions.solveAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private static void solveAll() {
        System.out.println("Advent of Code 2023 solutions:");

        SolutionUtil.solveDay(Day01.class);
        SolutionUtil.solveDay(Day02.class);
        SolutionUtil.solveDay(Day03.class);
        SolutionUtil.solveDay(Day04.class);
        SolutionUtil.solveDay(Day05.class);
        SolutionUtil.solveDay(Day06.class);
        SolutionUtil.solveDay(Day07.class);
        SolutionUtil.solveDay(Day08.class);
        SolutionUtil.solveDay(Day09.class);
        SolutionUtil.solveDay(Day10.class);
        SolutionUtil.solveDay(Day11.class);
        SolutionUtil.solveDay(Day12.class);
        SolutionUtil.solveDay(Day13.class);
        SolutionUtil.solveDay(Day14.class);
        SolutionUtil.solveDay(Day15.class);
        SolutionUtil.solveDay(Day16.class);
        SolutionUtil.solveDay(Day17.class);
        SolutionUtil.solveDay(Day18.class);
        SolutionUtil.solveDay(Day19.class);
        SolutionUtil.solveDay(Day20.class);
        SolutionUtil.solveDay(Day21.class);
    }
}
