package aoc.util;

public interface AoCConstants {
    String SESSION_ID = "53616c7465645f5f23854e89b4659a1b99e09a7a77f4dc34c0e187fae5ad9542afbd5819bdbb8b1d0af9c49196f3b1f419d208c81e57b3d4282a70fe757a3c63";
    String SESSION_ID_STRING = String.format("session=%s", SESSION_ID);
    String INPUT_URL = "https://adventofcode.com/2022/day/%d/input";
    String INPUT_FILE_NAME = "input.txt";
    String EXAMPLE_FILE_NAME = "example.txt";
    String INPUT_FILE_FOLDER = "src/main/resources/day%02d";
    boolean PRINT_EXECUTION_TIME = false;
    boolean COPY_TO_CLIPBOARD = false;
    boolean RUN_EXAMPLE = false;
}
