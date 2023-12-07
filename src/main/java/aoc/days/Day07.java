package aoc.days;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import aoc.util.FileUtil;
import aoc.util.StringUtil;

public class Day07 extends Day {

    private static final List<Character> CARD_ORDER = Arrays.asList('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
    private Map<String, Integer> hands;

    protected void initialize() throws Exception {
        hands = new HashMap<>();
        final List<String> input = FileUtil.readFileToStrings(getDay());
        for (String in : input) {
            String[] split = in.split(StringUtil.SPACE);
            hands.put(split[0], Integer.parseInt(split[1]));
        }
    }

    protected Object getPart1Solution() {
        return getScoreUsingComparator(hands, this::compareHands);
    }

    protected Object getPart2Solution() {
        return getScoreUsingComparator(hands, this::compareCardsWithJoken);
    }

    private long getScoreUsingComparator(final Map<String, Integer> hands, final Comparator<String> comparator) {
        long result = 0;
        Set<String> sets = new TreeSet<>(comparator);
        sets.addAll(hands.keySet());

        int i = sets.size();

        for (String s : sets) {
            result += (long) i * hands.get(s);
            i--;
        }
        return result;
    }

    private int compareHands(String hand1, String hand2) {
        int handRank1 = getHandRank(hand1);
        int handRank2 = getHandRank(hand2);
        if (handRank1 == handRank2) {
            return compareHandsByHighestCardUsingMapper(hand1, hand2, this::getCardStrengt);
        }
        return handRank1 > handRank2 ? 1 : -1;
    }

    private int compareCardsWithJoken(String hand1, String hand2) {
        int cardRank1 = getHandRankWithJoker(hand1);
        int cardRank2 = getHandRankWithJoker(hand2);
        if (cardRank1 == cardRank2) {
            return compareHandsByHighestCardUsingMapper(hand1, hand2, this::getCardStrengtWithJoken);
        }
        return cardRank1 > cardRank2 ? 1 : -1;
    }

    private int getHandRankWithJoker(String hand) {

        int index = hand.indexOf('J');

        if (index < 0) {
            return getHandRank(hand);
        }
        String replacedHand;
        int min = 10;
        for (Character card : CARD_ORDER) {
            if (card == 'J') {
                continue;
            }
            replacedHand = hand.substring(0, index) + card + hand.substring(index + 1);
            min = Math.min(min, getHandRankWithJoker(replacedHand));
            if (min == 1) {
                return min;
            }
        }
        return min;
    }

    private int getHandRank(String string) {
        if (five(string)) {
            return 1;
        }
        if (four(string)) {
            return 2;
        }
        if (fullHouse(string)) {
            return 3;
        }
        if (three(string)) {
            return 4;
        }
        if (twoPair(string)) {
            return 5;
        }
        if (onePair(string)) {
            return 6;
        }
        return 7;
    }

    private boolean five(String string) {
        return getNumberOfCharsInString(string.charAt(0), string) == 5;
    }

    private boolean four(String string) {
        return (getNumberOfCharsInString(string.charAt(0), string) == 4) || (getNumberOfCharsInString(string.charAt(1), string) == 4);
    }

    private boolean fullHouse(String string) {
        Map<String, Long> numGroups = getCharCounts(string);
        return numGroups.containsValue(2L) && numGroups.containsValue(3L);
    }

    private boolean three(String string) {
        return (getNumberOfCharsInString(string.charAt(0), string) == 3) || (getNumberOfCharsInString(string.charAt(1), string) == 3) || (getNumberOfCharsInString(string.charAt(2), string) == 3);
    }

    private boolean twoPair(String string) {
        Map<String, Long> numGroups = getCharCounts(string);
        return numGroups.size() == 3 && numGroups.containsValue(2L) && numGroups.containsValue(1L);
    }

    private boolean onePair(String string) {
        Map<String, Long> numGroups = getCharCounts(string);
        return numGroups.size() >= 3 && numGroups.containsValue(2L) && numGroups.containsValue(1L);
    }

    private int compareHandsByHighestCardUsingMapper(String card1, String card2, Function<Character, Integer> mapper) {
        List<Integer> collect1 = getCardStrengtsCollect(card1, mapper);
        List<Integer> collect2 = getCardStrengtsCollect(card2, mapper);

        for (int i = 0; i < card1.toCharArray().length; i++) {
            if (Objects.equals(collect1.get(i), collect2.get(i))) {
                continue;
            }
            return collect1.get(i) > collect2.get(i) ? 1 : -1;

        }
        return 0;
    }

    private List<Integer> getCardStrengtsCollect(String hand, Function<Character, Integer> mapper) {
        return Arrays.stream(hand.split(StringUtil.EMPTY)).map(s -> s.charAt(0)).map(mapper).collect(Collectors.toList());
    }

    private long getNumberOfCharsInString(char c, String string) {
        return string.chars().filter(ch -> ch == c).count();
    }

    private Map<String, Long> getCharCounts(String string) {
        return Arrays.stream(string.split(StringUtil.EMPTY)).collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    }

    private int getCardStrengt(char card) {
        return CARD_ORDER.indexOf(card);
    }

    private int getCardStrengtWithJoken(char card) {
        return card == 'J' ? 13 : CARD_ORDER.indexOf(card);
    }

}