package aoc.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Packet implements Comparable<Packet> {

    private int[] packetContent;
    private List<Packet> innerPackets = new ArrayList<>();

    private Packet(final String packet) {
        if (hasNoInnerPacket(packet)) {
            String[] digits = packet.substring(1, packet.length() - 1).split(",");
            if (digits.length != 0 && !"".equals(digits[0])) {
                packetContent = Arrays.stream(digits).mapToInt(Integer::parseInt).toArray();
            } else {
                packetContent = new int[0];
            }
        } else {
            throw new RuntimeException(String.format("Unable to parse packet: %s.", packet));
        }
    }

    private Packet(int singlePacketContent) {
        this.packetContent = new int[]{singlePacketContent};
    }

    private Packet(List<Packet> innerPackets) {
        this.innerPackets = innerPackets;
    }

    public static Packet from(String packetString) {
        if (hasNoInnerPacket(packetString)) {
            return new Packet(packetString);
        }
        return parse(packetString);
    }

    public static boolean isInRightOrder(Pair<Packet, Packet> signals) {
        return isInRightOrder(signals.getFirst(), signals.second);
    }

    private static boolean isInRightOrder(Packet first, Packet second) {
        return first.isLowerThan(second) > 0;
    }

    private int isLowerThan(Packet other) {
        if (this.isArrayNode() && other.isArrayNode()) {
            return isListLowerThan(this.packetContent, other.packetContent);
        }

        if (isEmptyPacket()) {
            return 1;
        } else if (other.isEmptyPacket()) {
            return -1;
        }

        int maxLength = Math.max(innerPackets.size(), other.innerPackets.size());

        for (int i = 0; i < maxLength; i++) {
            Packet firstInnerPacket;
            Packet secondInnerPacket;
            try {
                firstInnerPacket = getInnerPacket(i);
            } catch (IndexOutOfBoundsException e) {
                return 1;
            }

            try {
                secondInnerPacket = other.getInnerPacket(i);
            } catch (IndexOutOfBoundsException e) {
                return -1;
            }

            int lowerThan = firstInnerPacket.isLowerThan(secondInnerPacket);
            if (lowerThan > 0) {
                return 1;
            }
            if (lowerThan < 0) {
                return -1;
            }
        }
        return 1;
    }

    private boolean isEmptyPacket() {
        return innerPackets.isEmpty() && (packetContent == null || packetContent.length == 0);
    }

    private Packet getInnerPacket(int i) {
        return innerPackets.size() > 0 ? innerPackets.get(i) : new Packet(packetContent[i]);
    }

    private static int isListLowerThan(int[] first, int[] second) {
        int minLength = Math.min(first.length, second.length);

        for (int i = 0; i < minLength; i++) {
            if (first[i] < second[i]) {
                return 1;
            }
            if (first[i] > second[i]) {
                return -1;
            }
        }

        if (first.length > second.length) {
            return -1;
        }
        if (first.length < second.length) {
            return 1;
        }
        return 0;

    }

    private static Packet parse(String packet) {
        final List<Packet> innerPackets = new ArrayList<>();

        char[] chars = packet.toCharArray();

        for (int i = 1; i < chars.length; i++) {
            if (Character.isDigit(chars[i])) {
                int value;
                if (Character.isDigit(chars[i + 1])) {
                    value = Integer.parseInt("" + chars[i] + chars[i + 1]);
                    i++;
                } else {
                    value = Integer.parseInt("" + chars[i]);
                }
                innerPackets.add(new Packet(value));
            } else if (chars[i] == '[') {
                int closingBracketIndex = findClosingBracketIndex(packet, i);
                innerPackets.add(Packet.from(packet.substring(i, closingBracketIndex + 1)));
                i = closingBracketIndex + 1;
            } else if (chars[i] == ']' || chars[i] == ',') {
            } else {
                throw new RuntimeException("Error parsing packet");
            }
        }
        return new Packet(innerPackets);
    }

    private static boolean hasNoInnerPacket(String packetString) {
        return packetString.lastIndexOf('[') == 0;
    }

    private static int findClosingBracketIndex(String packet, int fromIndex) {
        char[] text = packet.toCharArray();
        int closingBracketIndex = fromIndex;
        int counter = 1;
        while (counter > 0) {
            char c = text[++closingBracketIndex];
            if (c == '[') {
                counter++;
            } else if (c == ']') {
                counter--;
            }
        }
        return closingBracketIndex;
    }

    private boolean isArrayNode() {
        return packetContent != null;
    }

    @Override
    public int compareTo(Packet other) {
        return (-1 * this.isLowerThan(other));
    }
}
