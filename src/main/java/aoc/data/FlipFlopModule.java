package aoc.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FlipFlopModule implements PulseReceiver {
    private final String name;
    private boolean state; //Off false; On true
    private final List<PulseReceiver> outputs = new ArrayList<>();

    public FlipFlopModule(String name) {
        this.name = name;
        this.state = false;
    }

    @Override
    public void receive(Pulse pulse, String source, int round) { }

    @Override
    public List<PulseAction> update(PulseAction pulseAction) {
        List<PulseAction> result = new LinkedList<>();
        final Pulse receivedPulse = pulseAction.getPulse();

        if (receivedPulse == Pulse.HIGH) {
            return result;
        }

        if (state) {
            for (PulseReceiver output : outputs) {
                result.add(PulseAction.of(name, output, Pulse.LOW));
            }
        } else {
            for (PulseReceiver output : outputs) {
                result.add(PulseAction.of(name, output, Pulse.HIGH));
            }
        }
        state = !state;
        return result;
    }

    @Override
    public String getType() {
        return "FlipFlop";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addOutput(PulseReceiver pulseReceiver) {
        outputs.add(pulseReceiver);
    }
}
