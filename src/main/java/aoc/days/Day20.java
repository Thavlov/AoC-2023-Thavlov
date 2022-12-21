package aoc.days;

import aoc.data.Link;
import aoc.util.FileUtil;

import java.util.List;

public class Day20 extends Day {
    private int listSize;
    private Link<Integer> first;

    protected void initialize() throws Exception {
        List<Integer> input = FileUtil.readFileToObjects(getDay(), Integer::parseInt);
        listSize = input.size();
        first = new Link<>(input.get(0));
        Link<Integer> next;
        Link<Integer> temp = first;
        for (int i = 1; i < input.size(); i++) {
            next = new Link<>(input.get(i));
            temp.setNext(next);
            temp = next;
        }

        temp.setNext(first);
    }

    protected String getPart1Solution() {
        Link<Integer>[] values = new Link[listSize];
        Link<Integer> temp = first;
        for (int i = 0; i < listSize; i++) {
            values[i] = temp;
            temp = temp.getNext();
        }

        Link<Integer> currentLink;

        for (int link = 0; link < listSize; link++) {
            currentLink = values[link];
            int movesForward = currentLink.getValue();

            while (movesForward < 0) {
                movesForward += (listSize - 1);
            }

            for (int i = 0; i < movesForward; i++) {
                currentLink.moveForward();
            }
        }

        long v1 = getValue(1000);
        long v2 = getValue(2000);
        long v3 = getValue(3000);

        return "" + (v1 + v2 + v3);
    }

    protected String getPart2Solution() {
        long multiplier = 811589153L;

        Link<Integer>[] values = new Link[listSize];
        Link<Integer> temp = first;

        for (int i = 0; i < listSize; i++) {
            values[i] = temp;
            temp = temp.getNext();
        }

        Link<Integer> currentLink;

        for (int time = 0; time < 10; time++) {
            for (int link = 0; link < listSize; link++) {
                currentLink = values[link];
                long movesForward = multiplier * currentLink.getValue();

                movesForward %= (listSize - 1);

                while (movesForward < 0) {
                    movesForward += listSize - 1;
                }

                for (int i = 0; i < movesForward; i++) {
                    currentLink.moveForward();
                }
            }
        }

        long v1 = multiplier * getValue(1000);
        long v2 = multiplier * getValue(2000);
        long v3 = multiplier * getValue(3000);

        return "" + (v1 + v2 + v3);
    }

    private long getValue(int n) {
        Link<Integer> temp = first;
        while (temp.getValue() != 0) {
            temp = temp.getNext();
        }
        for (int i = 0; i < n; i++) {
            temp = temp.getNext();
        }
        return temp.getValue();
    }
}