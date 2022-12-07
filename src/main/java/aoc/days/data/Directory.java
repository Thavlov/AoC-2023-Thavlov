package aoc.days.data;

import aoc.util.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Directory {
    final String name;
    final Directory superDirectory;
    final HashMap<String, Directory> subDirectories;
    final List<Pair<String, Long>> files;

    public Directory(String name, Directory superDirectory) {
        this.name = name;
        this.superDirectory = superDirectory;
        this.subDirectories = new HashMap<>();
        this.files = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addDirectory(String name) {
        if (subDirectories.containsKey(name)) {
            return;
        }
        subDirectories.put(name, new Directory(name, this));
    }

    public Directory getSuperDirectory() {
        return superDirectory;
    }

    public Directory getSubDirectory(String name) {
        return subDirectories.get(name);
    }

    public void addFile(String name, Long size) {
        files.add(new Pair<>(name, size));
    }

    public boolean hasSmallerSizeThan(long size) {
        return getSize() <= size;
    }

    public boolean hasSmallerBiggerThan(long size) {
        return getSize() >= size;
    }

    public Set<Directory> getAllSubdir() {
        final Set<Directory> values = new HashSet<>(subDirectories.values());
        final List<Directory> temp = new ArrayList<>();
        for (Directory value : values) {
            temp.addAll(value.getAllSubdir());
        }
        values.addAll(temp);
        return values;
    }

    public long getSize() {
        long result = 0;
        for (Pair<String, Long> file : files) {
            result += file.getSecond();
        }
        for (Directory s : subDirectories.values()) {
            result += s.getSize();
        }
        return result;
    }

    public static Directory buildDirectoryStructure(List<String> instructions) {
        final Directory topNode = new Directory("/", null);
        Directory selectedDirectory = topNode;

        final String regex = "(\\d+) (.*)";
        final Pattern pattern = Pattern.compile(regex);

        for (String instruction : instructions) {
            if (instruction.charAt(0) == '$') {
                if (instruction.startsWith("cd", 2)) {
                    if (instruction.substring(5).equals("..")) {
                        selectedDirectory = selectedDirectory.getSuperDirectory();
                    } else if (instruction.substring(5).equals("/")) {

                    } else {
                        selectedDirectory = selectedDirectory.getSubDirectory(instruction.substring(5));
                    }
                } else if (instruction.startsWith("ls", 2)) {

                }
            } else if (instruction.startsWith("dir")) {
                selectedDirectory.addDirectory(instruction.substring(4));
            } else if (Character.isDigit(instruction.charAt(0))) {
                Matcher matcher = pattern.matcher(instruction);
                if (matcher.find()) {
                    long size = Long.parseLong(matcher.group(1));
                    selectedDirectory.addFile(matcher.group(2), size);
                }
            }
        }
        return topNode;
    }
}
