package aoc.days;

import aoc.util.FileUtil;
import aoc.data.Pair;

import java.util.ArrayList;
import java.util.List;

public class Day03 extends Day {
    protected void initialize() {
    }

    protected String getPart1Solution() throws Exception {
        final List<Pair<String, String>> input = FileUtil.readFileToSplitStringPair(getDay());

        int result = 0;
        for (Pair<String, String> p : input) {
            char c = findCommonLetter(p.getFirst(), p.getSecond());
            result += getLetterValue(c);
        }
        return "" + result;
    }

    protected String getPart2Solution() throws Exception {
        final List<List<String>> input = FileUtil.readFileToStringInGroupsOf(getDay(), 3);

        int result = 0;
        for (List<String> strings : input) {
            char c = findCommonLetter(strings.get(0), strings.get(1), strings.get(2));
            result += getLetterValue(c);
        }
        return "" + result;
    }

    private char findCommonLetter(String first, String second) throws Exception {
        for (int i = 0; i < first.length(); i++) {
            char c = first.charAt(i);
            if (second.contains("" + c)) {
                return c;
            }
        }
        throw new Exception("Error");
    }

    private int getLetterValue(char c) {
        if (c < 97) {
            return (c - 38);
        } else {
            return (c - 96);
        }
    }

    private char findCommonLetter(String s1, String s2, String s3) throws Exception {
        List<Character> commonLetter = findAllCommonLetters(s1, s2);
        return findLetterInString(s3, commonLetter);
    }

    private List<Character> findAllCommonLetters(String s1, String s2) {
        List<Character> characters = new ArrayList<>();
        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            if (s2.contains("" + c)) {
                characters.add(c);
            }
        }
        return characters;
    }

    private char findLetterInString(String s, List<Character> characters) throws Exception {
        for (Character c : characters) {
            if (s.contains("" + c)) {
                return c;
            }
        }
        throw new Exception("Error");
    }
}