package aoc.days;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Predicate;

import aoc.data.FinalMachinePulseReceiver;
import aoc.data.Broadcaster;
import aoc.data.Button;
import aoc.data.ConjunctionModule;
import aoc.data.FlipFlopModule;
import aoc.data.Pulse;
import aoc.data.PulseAction;
import aoc.data.PulseReceiver;
import aoc.util.FileUtil;
import aoc.util.MathUtil;

public class Day20 extends Day {
    private Button button;

    protected void initialize() throws Exception {
        final List<String> input = FileUtil.readFileToStrings(getDay());

        final Map<String, PulseReceiver> pulseReceiverMap = new HashMap<>();

        PulseReceiver p;

        final Map<String, String> temp = new HashMap<>();
        for (String s : input) {
            String[] split = s.split(" -> ");
            if (split[0].startsWith("%")) {
                p = new FlipFlopModule(split[0].substring(1));
                pulseReceiverMap.put(p.getName(), p);
                temp.put(split[0].substring(1), split[1]);
            } else if (split[0].startsWith("&")) {
                p = new ConjunctionModule(split[0].substring(1));
                pulseReceiverMap.put(p.getName(), p);
                temp.put(split[0].substring(1), split[1]);
            } else if (split[0].startsWith("broadcaster")) {
                p = new Broadcaster(split[0]);
                pulseReceiverMap.put(p.getName(), p);
                temp.put(split[0], split[1]);
            } else {
                throw new RuntimeException();
            }
        }

        for (String component : temp.keySet()) {
            PulseReceiver pulseReceiver = pulseReceiverMap.get(component);
            String connections = temp.get(component);

            for (String s : connections.split(",")) {
                PulseReceiver output = pulseReceiverMap.get(s.trim());

                if (output == null) {
                    output = new FinalMachinePulseReceiver(s.trim());
                    pulseReceiverMap.put(output.getName(), output);
                }

                pulseReceiver.addOutput(output);

                if (output.getType().equals("Conjunction")) {
                    ((ConjunctionModule) output).addInput(pulseReceiver);
                }
            }
        }
        button = new Button((Broadcaster) pulseReceiverMap.get("broadcaster"));
    }

    protected Object getPart1Solution() {
        long lowPulses = 0;
        long highPulses = 0;

        int counter = 0;

        while (counter < 1000) {
            PulseAction nextAction = button.push();
            Queue<PulseAction> actions = new LinkedList<>();
            actions.add(nextAction);

            while (!actions.isEmpty()) {
                nextAction = actions.poll();
                if (nextAction.getPulse() == Pulse.HIGH) {
                    highPulses++;
                } else {
                    lowPulses++;
                }
                PulseReceiver pulseReceiver = nextAction.getDestination();

                actions.addAll(pulseReceiver.update(nextAction));
            }
            counter++;
        }

        return highPulses * lowPulses;
    }

    protected Object getPart2Solution() throws Exception {
        long x1 = getIterationsToHighPulseFromNode("xc");
        long x2 = getIterationsToHighPulseFromNode("th");
        long x3 = getIterationsToHighPulseFromNode("pd");
        long x4 = getIterationsToHighPulseFromNode("bp");

        return MathUtil.findLCM(Arrays.asList(x1, x2, x3, x4));
    }

    private int getIterationsToHighPulseFromNode(String node) throws Exception {
        initialize();

        int counter = 1;
        while (true) {
            PulseAction nextAction = button.push();
            Queue<PulseAction> actions = new LinkedList<>();
            actions.add(nextAction);

            while (!actions.isEmpty()) {
                nextAction = actions.poll();
                PulseReceiver pulseReceiver = nextAction.getDestination();

                List<PulseAction> update = pulseReceiver.update(nextAction);
                if (update.stream().anyMatch(isNodeWithHighPulse(node))) {
                    return counter;
                }
                actions.addAll(update);
            }
            counter++;
        }
    }

    private Predicate<PulseAction> isNodeWithHighPulse(String nodeName) {
        return p -> p.getPulse() == Pulse.HIGH && nodeName.equals(p.getSource());
    }
}
