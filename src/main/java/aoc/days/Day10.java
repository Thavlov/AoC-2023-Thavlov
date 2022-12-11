package aoc.days;

import aoc.util.FileUtil;

import java.util.Arrays;
import java.util.List;

public class Day10 extends Day {
    private List<String> instructions;

    protected void initialize() throws Exception {
        instructions = FileUtil.readFileToStrings(getDay());
    }

    protected String getPart1Solution() {
        int cycle = 0;
        int value = 1;
        int[] register = new int[2 * instructions.size()];
        register[cycle++] = value;

        for (String instruction : instructions) {
            String command = instruction.split(" ")[0];

            switch (command) {
                case "noop":
                    register[cycle++] = value;
                    break;
                case "addx":
                    register[cycle++] = value;
                    value += Integer.parseInt(instruction.split(" ")[1]);
                    register[cycle++] = value;
                    break;
                default:
                    throw new RuntimeException("Error!");
            }
        }

        int result = 20 * register[19] + 60 * register[59] + 100 * register[99] + 140 * register[139] + 180 * register[179] + 220 * register[219];
        return "" + result;
    }

    protected String getPart2Solution() {
        int cycle = 0;
        int value = 1;
        int[] register = new int[2 * instructions.size()];
        register[cycle++] = value;

        for (String instruction : instructions) {
            String command = instruction.split(" ")[0];

            switch (command) {
                case "noop":
                    register[cycle++] = value;
                    break;
                case "addx":
                    register[cycle++] = value;
                    value += Integer.parseInt(instruction.split(" ")[1]);
                    register[cycle++] = value;
                    break;
                default:
                    throw new RuntimeException("Error!");
            }
        }

        char[] crt = new char[240];
        for (cycle = 0; cycle < crt.length; cycle++) {
            int spritePosition = register[cycle];
            int pixelLocation = cycle % 40;
            int distFromSprite = Math.abs(spritePosition - pixelLocation);
            if (distFromSprite <= 1) {
                crt[cycle] = '#';
            } else {
                crt[cycle] = ' ';
            }
        }
        System.out.println(new String(Arrays.copyOfRange(crt, 0, 39)));
        System.out.println(new String(Arrays.copyOfRange(crt, 40, 79)));
        System.out.println(new String(Arrays.copyOfRange(crt, 80, 119)));
        System.out.println(new String(Arrays.copyOfRange(crt, 120, 159)));
        System.out.println(new String(Arrays.copyOfRange(crt, 160, 199)));
        System.out.println(new String(Arrays.copyOfRange(crt, 200, 239)));

        return "PHLHJGZA";
    }
}