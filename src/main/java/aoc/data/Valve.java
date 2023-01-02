package aoc.data;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Valve {
    private static final String REGEX = "Valve (.+) has flow rate=(\\d+); tunnels? leads? to valves? (.*)";
    private static final Pattern PATTERN = Pattern.compile(REGEX);
    private final String valveId;
    private final int flowRate;
    private boolean isOpen;
    private final Set<String> connections;

    private Valve(String valveId, int flowRate, Set<String> connections) {
        this.valveId = valveId;
        this.flowRate = flowRate;
        this.connections = connections;
        if (!hasPositiveFlowRate()) {
            open();
        }
    }

    private static final Valve EMPTY_VALVE = new Valve(null, 0, Collections.emptySet());

    private static Valve emptyValve(String nameId) {
        return EMPTY_VALVE;
    }

    public static Valve parse(String string) {
        final Matcher matcher = PATTERN.matcher(string);
        if (!matcher.find()) {
            throw new RuntimeException(String.format("Unable to parse string: %s.", string));
        }

        final String valveId = matcher.group(1);
        final int flowRate = Integer.parseInt(matcher.group(2));

        final Map<String, Valve> valveMap = Arrays.stream(matcher.group(3).split(",")).map(String::trim).collect(Collectors.toMap(Function.identity(), Valve::emptyValve));

        return new Valve(valveId, flowRate, valveMap.keySet());
    }

    public String getValveId() {
        return valveId;
    }

    public Map<String, Integer> getConnections() {
        final Map<String, Integer> result = new HashMap<>();
        for (String connection : connections) {
            result.put(connection, 0);
        }
        if (hasPositiveFlowRate()) {
            result.put(valveId, flowRate);
        }
        return result;
    }

    public int getFlowRate() {
        return flowRate;
    }

    public void open() {
        isOpen = true;
    }

    public boolean hasPositiveFlowRate() {
        return flowRate > 0;
    }
}
