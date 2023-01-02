package aoc.days;

import aoc.data.CharArray;
import aoc.data.Elf;
import aoc.util.FileUtil;
import aoc.util.StringUtil;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day23 extends Day {
    private static final boolean PRINT_MAPS = false;
    private List<Elf> elves;

    protected void initialize() throws Exception {
        final List<String> input = FileUtil.readFileToStrings(getDay());
        final CharArray inputMap = CharArray.fromStrings(input);
        elves = inputMap.toCoordinates().stream().map(Elf::new).collect(Collectors.toList());
        Elf.PROPOSE_METHOD = 0;
    }

    protected String getPart1Solution() {
        for (int counter = 0; counter < 10; counter++) {
            List<Coordinate> elvesPositions = elves.stream().map(Elf::getPosition).collect(Collectors.toList());
            List<Elf> elvesWithMoves = elves.stream().filter(e -> e.isNextToOtherElf(elvesPositions)).collect(Collectors.toList());

            Map<Coordinate, Long> elvesProposedCoordinateCount = elvesWithMoves.stream().map(e -> e.proposePosition(elvesPositions)).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            elvesWithMoves.forEach(e -> e.moveToProposedLocationIfFree(elvesProposedCoordinateCount));
            Elf.PROPOSE_METHOD++;

            if (PRINT_MAPS) {
                System.out.println("Round " + (counter + 1));
                CharArray charArray = CharArray.fromCoordinates(elvesPositions);
                System.out.println();
                charArray.printArray();
            }
        }

        List<Coordinate> elvesPositions = elves.stream().map(Elf::getPosition).collect(Collectors.toList());
        int minX = elvesPositions.stream().mapToInt(Coordinate::getX).min().getAsInt();
        int maxX = elvesPositions.stream().mapToInt(Coordinate::getX).max().getAsInt();
        int minY = elvesPositions.stream().mapToInt(Coordinate::getY).min().getAsInt();
        int maxY = elvesPositions.stream().mapToInt(Coordinate::getY).max().getAsInt();

        int emptyTiles = ((maxX - minX + 1) * (maxY - minY + 1)) - elves.size();

        return "" + emptyTiles;
    }

    protected String getPart2Solution() {
        int counter = 0;
        while (true) {
            counter++;
            List<Coordinate> elvesPositions = elves.stream().map(Elf::getPosition).collect(Collectors.toList());
            List<Elf> elvesWithMoves = elves.stream().filter(e -> e.isNextToOtherElf(elvesPositions)).collect(Collectors.toList());
            if (elvesWithMoves.isEmpty()) {
                break;
            }

            Map<Coordinate, Long> elvesProposedCoordinateCount = elvesWithMoves.stream().map(e -> e.proposePosition(elvesPositions)).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            elvesWithMoves.forEach(e -> e.moveToProposedLocationIfFree(elvesProposedCoordinateCount));
            Elf.PROPOSE_METHOD++;
        }

        return "" + counter;
    }
}