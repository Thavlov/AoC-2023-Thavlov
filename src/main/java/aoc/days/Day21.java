package aoc.days;

import aoc.util.FileUtil;
import aoc.util.StringUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 extends Day {
    private final static Pattern OPERATION_PATTERN = Pattern.compile("(.+) ([+-/*=]?) (.+)");
    private Map<String, Long> resolvedMonkeys;
    private Map<String, String> unresolvedMonkeys;


    protected void initialize() throws Exception {
        resolvedMonkeys = new HashMap<>();
        unresolvedMonkeys = new HashMap<>();
        List<String> input = FileUtil.readFileToStrings(getDay());
        Pattern pattern = Pattern.compile("(.+): (.+)");
        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            String monkeyId = matcher.group(1);
            String operation = matcher.group(2);
            if (StringUtil.isInteger(operation)) {
                resolvedMonkeys.put(monkeyId, Long.parseLong(operation));
            } else {
                unresolvedMonkeys.put(monkeyId, operation);
            }
        }
    }

    protected String getPart1Solution() {
        return "" + calculateValueOfRootNode();
    }

    protected String getPart2Solution() {
        unresolvedMonkeys.compute("root", (k, v) -> v.replace("+", "="));

        List<String> keysFromRootToHuman = new ArrayList<>();
        String valueToFind = "humn";

        while (!valueToFind.equals("root")) {
            keysFromRootToHuman.add(valueToFind);
            valueToFind = findKeyUsingValue(valueToFind);
        }
        keysFromRootToHuman.add(valueToFind);
        Collections.reverse(keysFromRootToHuman);


        String knownValueKey;
        String operation;
        Matcher matcher;

        long result = 0;
        long knownValue;
        long unknownValue;

        for (String key : keysFromRootToHuman) {
            if ("humn".equals(key)) {
                break;
            }
            operation = unresolvedMonkeys.get(key);
            matcher = OPERATION_PATTERN.matcher(operation);
            matcher.find();

            if (keysFromRootToHuman.contains(matcher.group(1))) {
                knownValueKey = matcher.group(3);
            } else {
                knownValueKey = matcher.group(1);
            }

            knownValue = calculateValueOfNode(knownValueKey, keysFromRootToHuman);
            boolean isKnowValueLeftOperand = operation.startsWith(knownValueKey);


            switch (matcher.group(2)) {
                case "=":
                    unknownValue = knownValue;
                    break;
                case "+":
                    unknownValue = result - knownValue;
                    break;
                case "-":
                    if (isKnowValueLeftOperand) {
                        unknownValue = knownValue - result;
                    } else {
                        unknownValue = knownValue + result;
                    }
                    break;
                case "*":
                    unknownValue = result / knownValue;
                    break;
                case "/":
                    if (isKnowValueLeftOperand) {
                        unknownValue = knownValue / result;
                    } else {
                        unknownValue = result * knownValue;
                    }
                    break;
                default:
                    throw new RuntimeException("Error: Unknown operation.");
            }
            result = unknownValue;
        }

        return "" + result;
    }

    private long calculateValueOfRootNode() {
        return calculateValueOfNode("root", Collections.emptyList());
    }

    private long calculateValueOfNode(String node, List<String> keysToBeExcluded) {
        long value;
        if (resolvedMonkeys.containsKey(node)) {
            return resolvedMonkeys.get(node);
        }
        while (true) {
            String nextKey = findNextKey(keysToBeExcluded).orElseThrow();
            String operation = unresolvedMonkeys.remove(nextKey);
            Matcher matcher = OPERATION_PATTERN.matcher(operation);
            matcher.find();
            switch (matcher.group(2)) {
                case "+":
                    value = resolvedMonkeys.get(matcher.group(1)) + resolvedMonkeys.get(matcher.group(3));
                    resolvedMonkeys.put(nextKey, value);
                    break;
                case "-":
                    value = resolvedMonkeys.get(matcher.group(1)) - resolvedMonkeys.get(matcher.group(3));
                    resolvedMonkeys.put(nextKey, value);
                    break;
                case "*":
                    value = resolvedMonkeys.get(matcher.group(1)) * resolvedMonkeys.get(matcher.group(3));
                    resolvedMonkeys.put(nextKey, value);
                    break;
                case "/":
                    value = resolvedMonkeys.get(matcher.group(1)) / resolvedMonkeys.get(matcher.group(3));
                    resolvedMonkeys.put(nextKey, value);
                    break;
                default:
                    throw new RuntimeException("Error: Unknown operation.");
            }
            if (nextKey.equals(node)) {
                break;
            }
        }
        return value;
    }

    private String findKeyUsingValue(String value) {
        for (String key : unresolvedMonkeys.keySet()) {
            if (unresolvedMonkeys.get(key).contains(value)) {
                return key;
            }
        }
        throw new RuntimeException("Error: no key uses value: " + value);
    }

    private Optional<String> findNextKey(List<String> keysToBeExcluded) {
        for (String key : unresolvedMonkeys.keySet()) {
            if (keysToBeExcluded.contains(key)) {
                continue;
            }
            Matcher matcher = OPERATION_PATTERN.matcher(unresolvedMonkeys.get(key));
            matcher.find();

            if (resolvedMonkeys.containsKey(matcher.group(1)) && resolvedMonkeys.containsKey(matcher.group(3))) {
                return Optional.of(key);
            }
        }
        return Optional.empty();
    }
}