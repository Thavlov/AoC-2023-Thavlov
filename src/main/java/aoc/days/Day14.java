package aoc.days;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import aoc.data.CharArray;
import aoc.util.FileUtil;

public class Day14 extends Day {

    private CharArray map;

    protected void initialize() throws Exception {
        map = CharArray.fromStrings(FileUtil.readFileToStrings(getDay()));
    }

    protected Object getPart1Solution() {
        tiltNorth(map);
        return getTotalLoad(map);
    }

    protected Object getPart2Solution() {
        Map<Long, Integer> loadCount = new HashMap<>();

        long cycles = 0;
        long loopLength;
        long missingCycle;

        while (true) {
            tiltNorth(map);
            tiltWest(map);
            tiltSouth(map);
            tiltEast(map);
            cycles++;

            long load = getTotalLoad(map);

            if (loadCount.getOrDefault(load, 0) == 4) {
                loopLength = loadCount.values().stream().filter(count -> count == 4).mapToInt(Integer::intValue).count();
                missingCycle = (1000000000 - cycles) % loopLength;
                break;
            }

            loadCount.putIfAbsent(load, 1);
            loadCount.computeIfPresent(load, (k, v) -> v + 1);
        }

        for (int i = 0; i < missingCycle; i++) {
            tiltNorth(map);
            tiltWest(map);
            tiltSouth(map);
            tiltEast(map);
        }

        return getTotalLoad(map);

    }

    private void tiltNorth(CharArray map) {
        int n = map.getWidth();
        for (int i = 0; i < n; i++) {
            char[] chars = handleSliceTiltNorthOrWest(map.getVerticalSlice(i));
            map.setVerticalSlice(i, chars);
        }
    }

    private void tiltSouth(CharArray map) {
        int n = map.getWidth();
        for (int i = 0; i < n; i++) {
            char[] chars = handleSliceTiltSouthOrEast(map.getVerticalSlice(i));
            map.setVerticalSlice(i, chars);
        }
    }

    private void tiltEast(CharArray map) {
        int n = map.getHeight();
        for (int i = 0; i < n; i++) {
            char[] chars = handleSliceTiltSouthOrEast(map.getHorizontalSlice(i));
            map.setHorizontalSlice(i, chars);
        }
    }

    private void tiltWest(CharArray map) {
        int n = map.getHeight();
        for (int i = 0; i < n; i++) {
            char[] chars = handleSliceTiltNorthOrWest(map.getHorizontalSlice(i));
            map.setHorizontalSlice(i, chars);
        }
    }

    private long getTotalLoad(CharArray map) {
        long result = 0;
        int n = map.getWidth();
        for (int i = 0; i < n; i++) {
            char[] chars = map.getVerticalSlice(i);
            for (int j = 0; j < chars.length; j++) {
                if (chars[j] == 'O') {
                    result += n - j;
                }
            }
        }
        return result;
    }

    private char[] handleSliceTiltNorthOrWest(char[] slice) {
        char[] result = new char[slice.length];
        Queue<Integer> lastFreePositions = new LinkedList<>();

        for (int i = 0; i < slice.length; i++) {
            if (isFree(i, slice)) {
                lastFreePositions.add(i);
                result[i] = '.';
            } else if (isCubeRock(i, slice)) {
                result[i] = '#';
                lastFreePositions = new LinkedList<>();
            } else if (isRoundRock(i, slice)) {
                if (lastFreePositions.isEmpty()) {
                    result[i] = 'O';
                } else {
                    result[lastFreePositions.poll()] = 'O';
                    result[i] = '.';
                    lastFreePositions.add(i);
                }
            }
        }
        return result;
    }

    private char[] handleSliceTiltSouthOrEast(char[] slice) {
        char[] result = new char[slice.length];
        Queue<Integer> lastFreePositions = new LinkedList<>();

        for (int i = slice.length - 1; i >= 0; i--) {
            if (isFree(i, slice)) {
                lastFreePositions.add(i);
                result[i] = '.';
            } else if (isCubeRock(i, slice)) {
                result[i] = '#';
                lastFreePositions = new LinkedList<>();
            } else if (isRoundRock(i, slice)) {
                if (lastFreePositions.isEmpty()) {
                    result[i] = 'O';
                } else {
                    result[lastFreePositions.poll()] = 'O';
                    result[i] = '.';
                    lastFreePositions.add(i);
                }
            }
        }
        return result;
    }

    private boolean isFree(int i, char[] slice) {
        return slice[i] == '.';
    }

    private boolean isCubeRock(int i, char[] slice) {
        return slice[i] == '#';
    }

    private boolean isRoundRock(int i, char[] slice) {
        return slice[i] == 'O';
    }
}
