package aoc.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static Path loadInputFileFromDay(int day) throws Exception {
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
