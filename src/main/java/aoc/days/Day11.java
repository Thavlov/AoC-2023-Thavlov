package aoc.days;

import aoc.data.Monkey;
import aoc.data.Pair;
import aoc.util.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day11 extends Day {
    private HashMap<Integer, Monkey> monkeys;

    protected void initialize() throws Exception {
        List<String> input = FileUtil.readFileToStrings(getDay());
        monkeys = new HashMap<>();
        List<String> monkeyLines = new ArrayList<>();
        for (String s : input) {
            if ("".equals(s)) {
                Monkey monkey = Monkey.parse(monkeyLines);
                monkeys.put(monkey.getMonkeyId(), monkey);
                monkeyLines.clear();
                continue;
            }
            monkeyLines.add(s);
        }
        Monkey monkey = Monkey.parse(monkeyLines);
        monkeys.put(monkey.getMonkeyId(), monkey);
    }

    protected String getPart1Solution() {
        int round = 0;

        do {
            for (Integer monkeyId : monkeys.keySet()) {
                Monkey monkey = monkeys.get(monkeyId);
                while (monkey.hasItemsLeft()) {
                    Pair<Integer, Long> monkeyItemPair = monkey.getMonkeyItemPair();
                    monkeys.get(monkeyItemPair.getFirst()).addItem(monkeyItemPair.getSecond());
                }
            }
            round++;
        } while (round < 20);

        int monkeyNum = monkeys.size();
        int[] numberOfItemsInspected = monkeys.values().stream().mapToInt(Monkey::getItemsInspected).sorted().skip(monkeyNum - 2).toArray();

        return "" + numberOfItemsInspected[0] * numberOfItemsInspected[1];
    }

    protected String getPart2Solution() {
        int round = 0;

        Monkey.divisor = 1;
        int gcd = 1;
        for (Integer monkeyId : monkeys.keySet()) {
            gcd *= monkeys.get(monkeyId).getTestDivisor();
        }
        Monkey.gcd = gcd;

        do {
            for (Integer monkeyId : monkeys.keySet()) {
                Monkey monkey = monkeys.get(monkeyId);
                while (monkey.hasItemsLeft()) {
                    Pair<Integer, Long> monkeyItemPair = monkey.getMonkeyItemPair();
                    monkeys.get(monkeyItemPair.getFirst()).addItem(monkeyItemPair.getSecond());
                }
            }
            round++;
        } while (round < 10000);

        int monkeyNum = monkeys.size();
        long[] numberOfItemsInspected = monkeys.values().stream().mapToLong(Monkey::getItemsInspected).sorted().skip(monkeyNum - 2).toArray();

        return "" + numberOfItemsInspected[0] * numberOfItemsInspected[1];
    }
}