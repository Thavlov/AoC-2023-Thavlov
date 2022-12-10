package aoc.days;

import aoc.data.Directory;
import aoc.util.FileUtil;

import java.util.List;
import java.util.Set;

public class Day07 extends Day {
    private Directory superDirectory;

    protected void initialize() throws Exception {
        final List<String> instructions = FileUtil.readFileToStrings(getDay());
        superDirectory = Directory.buildDirectoryStructure(instructions);
    }

    protected String getPart1Solution() {
        long result = 0;

        final Set<Directory> allSubdir = superDirectory.getAllSubdir();
        for (Directory directory : allSubdir) {
            if (directory.hasSmallerSizeThan(100000L)) {
                result += directory.getSize();
            }
        }
        return "" + result;
    }

    protected String getPart2Solution() {
        long usedMemory = superDirectory.getSize();
        long availableMemory = 70000000L - usedMemory;
        long memoryToFree = 30000000 - availableMemory;

        final Set<Directory> allSubdir = superDirectory.getAllSubdir();

        long result = allSubdir.stream()
                .filter(dir -> dir.hasSmallerBiggerThan(memoryToFree))
                .map(Directory::getSize)
                .mapToInt(Long::intValue)
                .min()
                .getAsInt();

        return "" + result;
    }
}