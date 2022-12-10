package aoc.days;

import aoc.util.FileUtil;
import aoc.data.Pair;

import java.util.List;

public class Day02 extends Day {
    private List<Pair<String, String>> input;

    protected void initialize() throws Exception {
        input = FileUtil.readFileToStringPair(getDay());
    }

    protected String getPart1Solution() throws Exception {
        int result = 0;
        for (Pair<String, String> p : input) {
            result += getPoint(p.getFirst(), p.getSecond());
        }
        return "" + result;
    }

    protected String getPart2Solution() throws Exception {
        int result = 0;

        for (Pair<String, String> p : input) {
            result += getPoint(p.getFirst(), getMyHandFromOutcome(p.getFirst(), p.getSecond()));
        }

        return "" + result;
    }

    private int getPoint(String elfHand, String myHand) throws Exception {
        boolean isRock = elfHand.equals("A");
        boolean isPaper = elfHand.equals("B");
        boolean isScissor = elfHand.equals("C");

        boolean myIsPaper = myHand.equals("Y");
        boolean myIsRock = myHand.equals("X");
        boolean myIsScissor = myHand.equals("Z");

        if (myIsRock && isRock) {
            return 1 + 3;
        } else if (myIsRock && isPaper) {
            return 1 + 0;
        } else if (myIsRock && isScissor) {
            return 1 + 6;
        } else if (myIsPaper && isScissor) {
            return 2 + 0;
        } else if (myIsPaper && isRock) {
            return 2 + 6;
        } else if (myIsPaper && isPaper) {
            return 2 + 3;
        } else if (myIsScissor && isScissor) {
            return 3 + 3;
        } else if (myIsScissor && isRock) {
            return 3 + 0;
        } else if (myIsScissor && isPaper) {
            return 3 + 6;
        } else {
            throw new Exception("Error!");
        }
    }

    private String getMyHandFromOutcome(String elfHand, String outcome) throws Exception {
        boolean isRock = elfHand.equals("A");
        boolean isPaper = elfHand.equals("B");
        boolean isScissor = elfHand.equals("C");

        boolean isLoose = outcome.equals("X");
        boolean isDraw = outcome.equals("Y");
        boolean isWin = outcome.equals("Z");

        if (isLoose && isRock) {
            return "Z";
        } else if (isLoose && isPaper) {
            return "X";
        } else if (isLoose && isScissor) {
            return "Y";
        } else if (isDraw && isScissor) {
            return "Z";
        } else if (isDraw && isRock) {
            return "X";
        } else if (isDraw && isPaper) {
            return "Y";
        } else if (isWin && isScissor) {
            return "X";
        } else if (isWin && isRock) {
            return "Y";
        } else if (isWin && isPaper) {
            return "Z";
        } else {
            throw new Exception("Error");
        }
    }
}