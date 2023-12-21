package aoc.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import aoc.util.FileUtil;
import aoc.util.StringUtil;

public class Day15 extends Day {

    private List<String> instructions;

    protected void initialize() throws Exception {
        String s = FileUtil.readFileToStrings(getDay()).get(0);
        instructions = Arrays.stream(s.split(StringUtil.DELIMITER_COMMA)).map(String::trim).collect(Collectors.toList());
    }

    protected Object getPart1Solution() {
        long result = 0;

        for (String s : instructions) {
            result += getHash(s);
        }

        return result;
    }

    protected Object getPart2Solution() {
        final Map<Integer, List<Lens>> boxes = new HashMap<>();

        for (String s : instructions) {
            if (s.contains("=")) {
                String[] split = s.split("=");
                Lens lens = new Lens(split[0], Integer.parseInt(split[1]));
                int box = getHash(lens.label);

                if (boxes.containsKey(box)) {
                    if (boxes.get(box).contains(lens)) {
                        for (Lens l : boxes.get(box)) {
                            if (l.equals(lens)) {
                                l.value = lens.value;
                                break;
                            }
                        }
                    } else {
                        boxes.get(box).add(lens);
                    }
                } else {
                    List<Lens> lenses = new ArrayList<>();
                    lenses.add(lens);
                    boxes.put(box, lenses);
                }
            } else {
                String[] split = s.split("-");
                Lens lens = new Lens(split[0], 0);
                int box = getHash(lens.label);
                List<Lens> lenses = boxes.get(box);
                if (lenses != null) {
                    lenses.remove(lens);
                }
            }
        }

        return getFocusingPower(boxes);
    }

    private int getHash(String s) {
        int result = 0;

        for (char c : s.toCharArray()) {
            result += c;
            result *= 17;
            result %= 256;
        }

        return result;
    }

    private long getFocusingPower(Map<Integer, List<Lens>> boxes) {
        long result = 0;
        for (Map.Entry<Integer, List<Lens>> box : boxes.entrySet()) {
            long boxNumber = box.getKey() + 1L;
            long counter = 1;
            for (Lens lens : box.getValue()) {
                result += boxNumber * counter * lens.value;
                counter++;
            }
        }

        return result;
    }

    private static class Lens {
        final String label;
        int value;

        public Lens(String label, int value) {
            this.label = label;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("%s %d", label, value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Lens lens = (Lens) o;
            return Objects.equals(label, lens.label);
        }

        @Override
        public int hashCode() {
            return Objects.hash(label);
        }
    }
}
