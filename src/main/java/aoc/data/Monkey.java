package aoc.data;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Monkey {

    public static int divisor = 3;
    public static int gcd = 1;

    private final int monkeyId;
    private final List<Long> items;
    private final String[] operation;
    private final int testDivisor;
    private final int monkeyTrue;
    private final int monkeyFalse;
    private int itemsInspected = 0;

    private Monkey(int monkeyId, List<Long> items, String[] operation, int testDivisor, int monkeyTrue, int monkeyFalse) {
        this.monkeyId = monkeyId;
        this.items = items;
        this.operation = operation;
        this.testDivisor = testDivisor;
        this.monkeyTrue = monkeyTrue;
        this.monkeyFalse = monkeyFalse;
        gcd *= testDivisor;
    }

    public static Monkey parse(List<String> lines) {
        Pattern pattern = Pattern.compile("Monkey (\\d+):");
        Matcher matcher = pattern.matcher(lines.get(0));
        matcher.find();
        int monkeyId = Integer.parseInt(matcher.group(1));
        List<Long> items = Arrays.stream(lines.get(1).replace(",", "").split(" ")).skip(4).map(Long::parseLong).collect(Collectors.toList());
        String[] operation = lines.get(2).substring(19).split(" ");
        int testDivisor = Integer.parseInt(lines.get(3).substring(8).split(" ")[2]);

        pattern = Pattern.compile(".*If true: throw to monkey (\\d+)");
        matcher = pattern.matcher(lines.get(4));
        matcher.find();
        int monkeyTrue = Integer.parseInt(matcher.group(1));

        pattern = Pattern.compile(".*If false: throw to monkey (\\d+)");
        matcher = pattern.matcher(lines.get(5));
        matcher.find();
        int monkeyFalse = Integer.parseInt(matcher.group(1));
        return new Monkey(monkeyId, items, operation, testDivisor, monkeyTrue, monkeyFalse);
    }

    public Pair<Integer, Long> getMonkeyItemPair() {
        itemsInspected++;
        long value = items.remove(0);
        long newWorryLevel = newWorryLevel(value);

        newWorryLevel %= gcd;//;

        int newMonkey = getNewMonkey(newWorryLevel);
        return Pair.of(newMonkey, newWorryLevel);
    }

    private int getNewMonkey(long newWorryLevel) {
        if (newWorryLevel % testDivisor == 0) {
            return monkeyTrue;
        }
        return monkeyFalse;
    }

    public boolean hasItemsLeft() {
        return !items.isEmpty();
    }

    private long newWorryLevel(long level) {
        if (divisor == 1) {
            return calc(level, operation[0], operation[2], operation[1].charAt(0));
        }
        return calc(level, operation[0], operation[2], operation[1].charAt(0)) / divisor;
    }

    private long calc(long value, String s1, String s2, char operator) {
        long first = s1.equals("old") ? value : Integer.parseInt(s1);
        long second = s2.equals("old") ? value : Integer.parseInt(s2);

        if (operator == '+') {
            return first + second;
        } else if (operator == '*') {
            return first * second;
        }
        throw new RuntimeException("Error");
    }

    public int getTestDivisor() {
        return testDivisor;
    }

    public int getMonkeyId() {
        return monkeyId;
    }

    public void addItem(long item) {
        items.add(item);
    }

    public int getItemsInspected() {
        return itemsInspected;
    }
}
