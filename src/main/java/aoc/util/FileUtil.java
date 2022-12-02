package aoc.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static aoc.util.AoCConstants.RUN_EXAMPLE;

public final class FileUtil {
    private FileUtil() {
        // EMPTY
    }

    public static List<String> readFileToStrings(int day) throws Exception {
        Path path = loadInputFileFromDay(day);

        try (final Stream<String> lines = Files.lines(path)) {
            return lines.collect(Collectors.toList());
        }
    }

    public static int[] readFileToIntArray(int day) throws Exception {
        final Path path = loadInputFileFromDay(day);

        try (final Stream<String> lines = Files.lines(path)) {
            return lines.mapToInt(Integer::parseInt).toArray();
        }
    }

    public static boolean[] readFileToBooleanArray(final String line, char identifier) {
        final Boolean[] booleans = line.chars().mapToObj(ch -> ch == identifier).toArray(Boolean[]::new);
        boolean[] result = new boolean[booleans.length];
        for (int i = 0; i < booleans.length; i++) {
            result[i] = booleans[i];
        }
        return result;
    }

    public static boolean[][] readFileToBooleanMapWithIdentifier(final List<String> lines, char identifier) {
        boolean[][] result = new boolean[lines.size()][];

        for (int i = 0; i < result.length; i++) {
            result[i] = readFileToBooleanArray(lines.get(i), identifier);
        }
        return result;
    }

    public static boolean[][] readFileToBooleanMap(final List<String> lines) {
        int maxXSize = lines.stream().map(s -> s.split(",")[0]).mapToInt(Integer::parseInt).max().getAsInt();
        int maxYSize = lines.stream().map(s -> s.split(",")[1]).mapToInt(Integer::parseInt).max().getAsInt();
        boolean[][] map = new boolean[maxYSize + 1][maxXSize + 1];

        for (String line : lines) {
            final String[] split = line.split(",");
            int i = Integer.parseInt(split[0]);
            int j = Integer.parseInt(split[1]);
            map[j][i] = true;
        }
        return map;
    }

    public static int[][] readFileToIntMap(int day) throws Exception {
        final List<String> strings = readFileToStrings(day);
        int[][] map = new int[strings.size()][];
        for (int i = 0; i < map.length; i++) {
            map[i] = strings.get(i).chars().map(Character::getNumericValue).toArray();
        }
        return map;
    }

    public static List<Pair<String, String>> readFileToStringPair(int day) throws Exception {
        return readFileToPair(day, FileUtil::toStringPair);
    }

    public static List<Pair<String, Integer>> readFileToStringIntPair(int day) throws Exception {
        return readFileToPair(day, FileUtil::toStringIntPair);
    }

    private static Pair<String, Integer> toStringIntPair(String line) {
        final String[] split = line.split(" ");
        return new Pair<>(split[0], Integer.parseInt(split[1]));
    }

    private static Pair<String, String> toStringPair(String line) {
        final String[] split = line.split(" ");
        return new Pair<>(split[0], split[1]);
    }

    public static <T> List<T> readFileToObjects(int day, Function<String, T> mapper) throws Exception {
        Path path = loadInputFileFromDay(day);

        try (final Stream<String> lines = Files.lines(path)) {
            return lines.map(mapper).collect(Collectors.toList());
        }
    }

    private static <T, R> List<Pair<T, R>> readFileToPair(int day, Function<String, Pair<T, R>> mapper) throws Exception {
        final Path path = loadInputFileFromDay(day);

        try (final Stream<String> lines = Files.lines(path)) {
            return lines.map(mapper).collect(Collectors.toList());
        }
    }

    public static Path loadInputFileFromDay(int day) throws Exception {
        if (RUN_EXAMPLE) {
            return Paths.get(String.format(AoCConstants.INPUT_FILE_FOLDER, day), AoCConstants.EXAMPLE_FILE_NAME);
        }

        final Path inputFile = Paths.get(String.format(AoCConstants.INPUT_FILE_FOLDER, day), AoCConstants.INPUT_FILE_NAME);

        if (inputFile.toFile().exists()) {
            return inputFile;
        }
        return downloadInputFileFromDay(day);
    }

    private static Path downloadInputFileFromDay(int day) throws Exception {
        final String urlAsString = String.format(AoCConstants.INPUT_URL, day);
        final URL url = new URL(urlAsString);

        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        con.setRequestProperty("Cookie", AoCConstants.SESSION_ID_STRING);

        final Path outputDirectory = Paths.get(String.format(AoCConstants.INPUT_FILE_FOLDER, day));
        if (!outputDirectory.toFile().exists()) {
            Files.createDirectories(outputDirectory);
        }

        final Path outputFile = Paths.get(String.format(AoCConstants.INPUT_FILE_FOLDER, day), AoCConstants.INPUT_FILE_NAME);

        try (final InputStream in = con.getInputStream()) {
            Files.copy(in, outputFile);
        }
        return outputFile;
    }
}
