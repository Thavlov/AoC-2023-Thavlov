package aoc.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import aoc.data.Pair;
import aoc.util.FileUtil;
import aoc.util.MathUtil;
import aoc.util.StringUtil;

public class Day08 extends Day {
    private String dir;
    private Map<String, Pair<String, String>> map;

    protected void initialize() throws Exception {
        final List<String> input = FileUtil.readFileToStrings(getDay());

        dir = input.get(0);
        map = new HashMap<>();

        for (int i = 2; i < input.size(); i++) {
            String[] split = input.get(i).split(StringUtil.SPACE);
            map.put(split[0], Pair.parse(split[2] + split[3]));
        }
    }

    protected Object getPart1Solution() {
        int i = 0;
        int n = dir.length();

        String key = "AAA";
        while (!"ZZZ".equals(key)) {
            char d = dir.charAt(i % n);
            key = getNewLocation(key, d, map);
            i++;
        }
        return i;
    }

    protected Object getPart2Solution() {
        final String[] startNodes = getStartNodes(map);
        List<Long> distancesToFirstZNode = new ArrayList<>();

        for (String startNode : startNodes) {
            distancesToFirstZNode.add(getStepsToFirstEndNode(startNode, dir, map));
        }

        return MathUtil.findLCM(distancesToFirstZNode);
    }

    private String getNewLocation(String s, char dir, Map<String, Pair<String, String>> map) {
        if (dir == 'L') {
            return getLeft(s, map);
        }
        if (dir == 'R') {
            return getRight(s, map);
        }
        throw new RuntimeException();
    }

    private long getStepsToFirstEndNode(String start, String dir, Map<String, Pair<String, String>> map) {
        final int n = dir.length();

        long steps = 0;
        String current = start;

        while (isNotEndNode(current)) {
            char d = dir.charAt((int) (steps % n));
            current = getNewLocation(current, d, map);
            steps++;
        }
        return steps;
    }

    private String getLeft(String s, Map<String, Pair<String, String>> map) {
        return map.get(s).getFirst();
    }

    private String getRight(String s, Map<String, Pair<String, String>> map) {
        return map.get(s).getSecond();
    }

    private String[] getStartNodes(Map<String, Pair<String, String>> map) {
        return map.keySet().stream().filter(isStartNode()).toArray(String[]::new);
    }

    private Predicate<String> isStartNode() {
        return s -> s.endsWith("A");
    }

    private boolean isNotEndNode(String s) {
        return !s.endsWith("Z");
    }
}