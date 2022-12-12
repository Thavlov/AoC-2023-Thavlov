package aoc.days;

import aoc.data.Monkey;
import aoc.data.Pair;
import aoc.util.FileUtil;
import aoc.util.StringUtil;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day11 extends Day {
    private Map<Integer, Monkey> monkeys;

    protected void initialize() throws Exception {
        List<String> input = FileUtil.readFileToStrings(getDay());
        List<List<String>> lists = StringUtil.splitInGroupsSeparatedByEmptyLine(input);
        monkeys = lists.stream().map(Monkey::parse).collect(Collectors.toMap(Monkey::getMonkeyId, Function.identity()));
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