package aoc.days;

import aoc.data.Packet;
import aoc.data.Pair;
import aoc.util.FileUtil;
import aoc.util.StringUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Day13 extends Day {
    private List<Pair<Packet, Packet>> packages;

    protected void initialize() throws Exception {
        List<String> input = FileUtil.readFileToStrings(getDay());
        List<List<String>> separatedInput = StringUtil.splitInGroupsSeparatedByEmptyLine(input);
        packages = separatedInput.stream().map(s -> new Pair<>(Packet.from(s.get(0)), Packet.from(s.get(1)))).collect(Collectors.toList());
    }

    protected String getPart1Solution() {
        int result = 0;

        for (int i = 0; i < packages.size(); i++) {
            Pair<Packet, Packet> aPackage = packages.get(i);
            if (Packet.isInRightOrder(aPackage)) {
                result += (i + 1);
            }
        }

        return "" + result;
    }

    protected String getPart2Solution() {
        List<Packet> allPackages = packages.stream().map(Pair::getFirst).collect(Collectors.toList());
        allPackages.addAll(packages.stream().map(Pair::getSecond).collect(Collectors.toList()));
        Packet decoder1 = Packet.from("[[2]]");
        Packet decoder2 = Packet.from("[[6]]");
        allPackages.add(decoder1);
        allPackages.add(decoder2);

        List<Packet> allPackagesSorted = allPackages.stream().sorted().collect(Collectors.toList());

        int i1 = allPackagesSorted.indexOf(decoder1)+1;
        int i2 = allPackagesSorted.indexOf(decoder2)+1;

        return "" + (i1*i2);
    }
}