package aoc.days;

import aoc.data.Pair;
import aoc.data.Valve;
import aoc.util.FileUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day16 extends Day {
    private Map<String, Valve> valves;

    protected void initialize() throws Exception {
        valves = FileUtil.readFileToObjects(getDay(), Valve::parse).stream().collect(Collectors.toMap(Valve::getValveId, Function.identity()));
    }

    protected String getPart1Solution() {
        final Map<String, List<Integer>> pathsMap = new HashMap<>();

        Valve v = valves.get("AA");
        pathsMap.put(v.getValveId(), Collections.singletonList(0));

        List<Integer> flowRates;
        String newPath;
        String previousNode = "";

        for (int i = 1; i < 30; i++) {
            HashSet<String> paths = new HashSet<>(pathsMap.keySet());
            for (String path : paths) {
                flowRates = new ArrayList<>(pathsMap.remove(path));
                int currentFlowRate = flowRates.get(flowRates.size() - 1);
                String currentValveId = path.substring(path.length() - 2);
                if (path.length() > 2) {
                    previousNode = path.substring(path.length() - 4, path.length() - 2);
                }
                Valve currentValve = valves.get(currentValveId);

                Map<String, Integer> connections = currentValve.getConnections();

                for (String connection : connections.keySet()) {
                    if (connection.equals(previousNode)) {
                        continue;
                    }
                    newPath = path + connection;
                    List<Integer> newFlowRates = new ArrayList<>(flowRates);
                    if (connection.equals(currentValveId)) {
                        if (isValveOpen(currentValveId, path)) {
                            continue;
                        }

                        if (doesNotContainsPath(newPath, paths)) {
                            newFlowRates.add(currentFlowRate + currentValve.getFlowRate());
                            pathsMap.put(newPath, newFlowRates);
                        }

                    } else if (doesNotContainsPath(newPath, paths)) {
                        newFlowRates.add(currentFlowRate);
                        pathsMap.put(newPath, newFlowRates);
                    }
                }
            }

            //Clean up and remove paths with low flow rates
            Set<String> highestFlows = new HashSet<>();
            for (String valveId : valves.keySet()) {
                List<String> pathsThatEndAtValveId = pathsMap.keySet().stream().filter(s -> s.endsWith(valveId)).collect(Collectors.toList());

                Map<String, Integer> pathFlowSum = new HashMap<>();

                for (String path : pathsThatEndAtValveId) {
                    int sum = pathsMap.get(path).stream().mapToInt(Integer::intValue).sum();
                    pathFlowSum.put(path, sum);
                }

                highestFlows.addAll(getNHighestKeys(pathFlowSum, 3));

                Optional<String> pathsThatEndAtValveIdAndCameFromSelf = pathsMap.keySet().stream().filter(s -> s.endsWith(valveId.repeat(2))).findAny();
                pathsThatEndAtValveIdAndCameFromSelf.ifPresent(highestFlows::add);
            }
            pathsMap.keySet().retainAll(highestFlows);
        }

        int maxSum = 0;
        for (String s : pathsMap.keySet()) {
            int sum = pathsMap.get(s).stream().mapToInt(Integer::intValue).sum();
            if (sum > maxSum) {
                maxSum = sum;
            }
        }
        return "" + maxSum;
    }

    protected String getPart2Solution() {
        final Map<Pair<String, String>, Pair<List<Integer>, List<Integer>>> map = new HashMap<>();

        Valve v = valves.get("AA");
        int maxFlowRate = valves.values().stream().mapToInt(Valve::getFlowRate).sum();
        System.out.println(maxFlowRate);
        List<Integer> flowRatesMe;
        List<Integer> flowRatesElephant;
        map.put(Pair.of(v.getValveId(), v.getValveId()), Pair.of(Collections.singletonList(0), Collections.singletonList(0)));

        String newPathMe;
        String newPathElephant;
        String previousNodeMe = "";
        String previousNodeElephant = "";

        int steps = 1;
        for (; steps < 26; steps++) {
            int keyLength = 2 * steps;
            map.keySet().removeIf(p -> p.getFirst().length() < keyLength);
            HashSet<Pair<String, String>> paths = new HashSet<>(map.keySet());
            for (Pair<String, String> path : paths) {
                Pair<List<Integer>, List<Integer>> flowRates = map.remove(path);
                flowRatesMe = new ArrayList<>(flowRates.getFirst());
                flowRatesElephant = new ArrayList<>(flowRates.getSecond());
                int currentFlowRateMe = flowRatesMe.get(flowRatesMe.size() - 1);
                int currentFlowRateElephant = flowRatesElephant.get(flowRatesElephant.size() - 1);
                String currentValveIdMe = path.getFirst().substring(path.getFirst().length() - 2);
                String currentValveIdElephant = path.getSecond().substring(path.getSecond().length() - 2);
                if (path.getFirst().length() > 2) {
                    previousNodeMe = path.getFirst().substring(path.getFirst().length() - 4, path.getFirst().length() - 2);
                }

                if (path.getSecond().length() > 2) {
                    previousNodeElephant = path.getSecond().substring(path.getSecond().length() - 4, path.getSecond().length() - 2);
                }
                Valve currentValveMe = valves.get(currentValveIdMe);
                Valve currentValveElephant = valves.get(currentValveIdElephant);

                Map<String, Integer> connectionsMe = currentValveMe.getConnections();
                Map<String, Integer> connectionsElephant = currentValveElephant.getConnections();

                for (String connectionMe : connectionsMe.keySet()) {
                    if (connectionMe.equals(previousNodeMe)) {
                        continue;
                    }
                    for (String connectionElephant : connectionsElephant.keySet()) {
                        if (connectionElephant.equals(previousNodeElephant)) {
                            continue;
                        }
                        newPathMe = path.getFirst() + connectionMe;
                        newPathElephant = path.getSecond() + connectionElephant;

                        List<Integer> newFlowRatesMe = new ArrayList<>(flowRatesMe);
                        List<Integer> newFlowRatesElephant = new ArrayList<>(flowRatesElephant);
                        if (!currentValveIdMe.equals(currentValveIdElephant) && (connectionMe.equals(currentValveIdMe) || connectionElephant.equals(currentValveIdElephant))) {
                            if (isValveOpen(currentValveIdMe, path) || !connectionMe.equals(currentValveIdMe)) {
                                newFlowRatesMe.add(currentFlowRateMe);
                            } else {
                                newFlowRatesMe.add(currentFlowRateMe + currentValveMe.getFlowRate());
                            }
                            if (isValveOpen(currentValveIdElephant, path) || !connectionElephant.equals(currentValveIdElephant)) {
                                newFlowRatesElephant.add(currentFlowRateElephant);
                            } else {
                                newFlowRatesElephant.add(currentFlowRateElephant + currentValveElephant.getFlowRate());
                            }
                            map.put(Pair.of(newPathMe, newPathElephant), Pair.of(newFlowRatesMe, newFlowRatesElephant));
                        } else {
                            newFlowRatesMe.add(currentFlowRateMe);
                            newFlowRatesElephant.add(currentFlowRateElephant);
                            map.put(Pair.of(newPathMe, newPathElephant), Pair.of(newFlowRatesMe, newFlowRatesElephant));
                        }
                    }
                }
            }

            //Clean up and remove paths with low flow rates
            Set<Pair<String, String>> highestFlows = new HashSet<>();
            for (String valveIdMe : valves.keySet()) {
                for (String valveIdElephant : valves.keySet()) {
                    List<String> pathsThatEndAtValveIdMe = map.keySet().stream().map(Pair::getFirst).filter(s -> s.endsWith(valveIdMe)).collect(Collectors.toList());
                    List<String> pathsThatEndAtValveIdElephant = map.keySet().stream().filter(p -> p.getFirst().endsWith(valveIdMe)).map(Pair::getSecond).filter(s -> s.endsWith(valveIdElephant)).collect(Collectors.toList());
                    Map<Pair<String, String>, Integer> pathFlowSum = new HashMap<>();

                    for (String pathMe : pathsThatEndAtValveIdMe) {
                        for (String pathElephant : pathsThatEndAtValveIdElephant) {
                            Pair<String, String> pair = Pair.of(pathMe, pathElephant);
                            if (!map.containsKey(pair)) {
                                    continue;
                            }

                            int sumMe = map.get(pair).getFirst().stream().mapToInt(Integer::intValue).max().getAsInt();
                            int sumElephant = map.get(pair).getSecond().stream().mapToInt(Integer::intValue).max().getAsInt();

                            pathFlowSum.put(pair, sumMe + sumElephant);
                        }
                    }

                    highestFlows.addAll(getNHighestKeysPair(pathFlowSum, 50));

                    Optional<Pair<String, String>> pathsThatEndAtValveIdAndCameFromSelfMe = map.keySet().stream().filter(s -> s.getFirst().endsWith(valveIdMe.repeat(2))).findAny();
                    Optional<Pair<String, String>> pathsThatEndAtValveIdAndCameFromSelfElephant = map.keySet().stream().filter(s -> s.getSecond().endsWith(valveIdElephant.repeat(2))).findAny();
                    pathsThatEndAtValveIdAndCameFromSelfMe.ifPresent(highestFlows::add);
                    pathsThatEndAtValveIdAndCameFromSelfElephant.ifPresent(highestFlows::add);
                }
            }
            map.keySet().retainAll(highestFlows);
        }

        System.out.println("----- Round " + 10 + " / " + steps + " ------");
        for (Pair<String, String> stringStringPair : map.keySet()) {
            System.out.println(stringStringPair.getFirst() + " : " + map.get(stringStringPair).getFirst());
            System.out.println(stringStringPair.getSecond() + " : " + map.get(stringStringPair).getSecond());
            System.out.println();
        }

        int maxSum = 0;
        for (Pair<String, String> pair : map.keySet()) {
            int sumMe = map.get(pair).getFirst().stream().mapToInt(Integer::intValue).sum();
            int sumElephant = map.get(pair).getSecond().stream().mapToInt(Integer::intValue).sum();

            int sum = sumMe + sumElephant;
            if (sum > maxSum) {
                maxSum = sum;
            }
        }
        return "" + maxSum;
    }

    private boolean doesNotContainsPath(String newPath, Set<String> allPaths) {
        return allPaths.stream().noneMatch(s -> s.contains(newPath));
    }

    private boolean isValveOpen(String valveId, String path) {
        return valves.get(valveId).hasPositiveFlowRate() && path.contains(valveId.repeat(2));
    }

    private boolean isValveOpen(String valveId, Pair<String, String> path) {
        return valves.get(valveId).hasPositiveFlowRate() && (path.getFirst().contains(valveId.repeat(2)) || path.getSecond().contains(valveId.repeat(2)));
    }

    private Set<String> getNHighestKeys(Map<String, Integer> map, int n) {
        if (map.size() <= n) {
            return map.keySet();
        }
        List<Integer> sums = new ArrayList<>(map.values());
        Collections.sort(sums);

        int sumSplitPoint = sums.get(sums.size() - n);

        Set<String> result = new HashSet<>();
        int counter = 0;
        for (String path : map.keySet()) {
            if (map.get(path) >= sumSplitPoint) {
                result.add(path);
                if (counter++ > n) {
                    break;
                }
            }
        }
        return result;
    }

    private Set<Pair<String, String>> getNHighestKeysPair(Map<Pair<String, String>, Integer> map, int n) {
        if (map.size() <= n) {
            return map.keySet();
        }
        List<Integer> sums = new ArrayList<>(map.values());
        Collections.sort(sums);

        int sumSplitPoint = sums.get(sums.size() - n);

        Set<Pair<String, String>> result = new HashSet<>();
        int counter = 0;
        for (Pair<String, String> path : map.keySet()) {
            if (map.get(path) >= sumSplitPoint) {
                result.add(path);
                if (counter++ > n) {
                    break;
                }
            }
        }
        return result;
    }
}