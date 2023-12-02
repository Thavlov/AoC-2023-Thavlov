package aoc.days;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import static aoc.util.AoCConstants.PRINT_EXECUTION_TIME;
import static aoc.util.AoCConstants.COPY_TO_CLIPBOARD;

public abstract class Day {

    public void solveAndPrint() {
        try {
            long startTime = System.currentTimeMillis();
            solveAndPrintPart1();
            if (PRINT_EXECUTION_TIME) {
                System.out.printf("Part 1 solved in %d ms.%n", System.currentTimeMillis() - startTime);
            }
            startTime = System.currentTimeMillis();
            solveAndPrintPart2();
            if (PRINT_EXECUTION_TIME) {
                System.out.printf("Part 2 solved in %d ms.%n", System.currentTimeMillis() - startTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void solveAndPrintPart1() throws Exception {
        final String solution = solvePart1();
        System.out.printf(" > Solution 1 to day %02d: %s%n", getDay(), solution);
        terminate(solution);
    }

    protected String solvePart1() throws Exception {
        initialize();
        return getPart1Solution().toString();
    }

    public void solveAndPrintPart2() throws Exception {
        final String solution = solvePart2();
        System.out.printf(" > Solution 2 to day %02d: %s%n", getDay(), solution);
        terminate(solution);
    }

    protected String solvePart2() throws Exception {
        initialize();
        return getPart2Solution().toString();
    }

    protected abstract void initialize() throws Exception;

    protected abstract Object getPart1Solution() throws Exception;

    protected abstract Object getPart2Solution() throws Exception;

    protected int getDay() {
        return Integer.parseInt(this.getClass().getSimpleName().substring(3));
    }

    protected void terminate(String solution) {
        if (COPY_TO_CLIPBOARD) {
            final Clipboard clipboardObj = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboardObj.setContents(new StringSelection(solution), null);
        }
    }
}
