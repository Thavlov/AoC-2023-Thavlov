package aoc.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConjunctionModule implements PulseReceiver {
    private final String name;
    private final List<PulseReceiver> outputs = new ArrayList<>();
    private final Map<String, Pulse> receivedPulses = new HashMap<>();

    public ConjunctionModule(String name) {
        this.name = name;
    }

    @Override
    public void receive(Pulse pulse, String source, int round) {
        if (!receivedPulses.containsKey(source)) {
            throw new RuntimeException();
        }
        receivedPulses.replace(source, pulse);
    }

    @Override
    public List<PulseAction> update(PulseAction pulseAction) {
        List<PulseAction> result = new LinkedList<>();

        final Pulse receivedPulse = pulseAction.getPulse();

        receivedPulses.replace(pulseAction.getSource(), receivedPulse);

        if (receivedPulses.values().stream().allMatch(Pulse.HIGH::equals)) {
            for (PulseReceiver output : outputs) {
                result.add(PulseAction.of(name, output, Pulse.LOW));
            }
        } else {
            for (PulseReceiver output : outputs) {
                result.add(PulseAction.of(name, output, Pulse.HIGH));
            }
        }
        return result;
    }

    @Override
    public String getType() {
        return "Conjunction";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addOutput(PulseReceiver pulseReceiver) {
        outputs.add(pulseReceiver);
    }

    public void addInput(PulseReceiver pulseReceiver) {
        receivedPulses.put(pulseReceiver.getName(), Pulse.LOW);
    }
}
