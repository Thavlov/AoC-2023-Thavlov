package aoc.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Broadcaster implements PulseReceiver {

    private final List<PulseReceiver> outputs = new ArrayList<>();
    private final String name;

    public Broadcaster(String name) {
        this.name = name;
    }

    @Override
    public void receive(Pulse pulse, String source, int round) { }

    @Override
    public List<PulseAction> update(PulseAction pulseAction) {
        List<PulseAction> result = new LinkedList<>();

        final Pulse receivedPulse = pulseAction.getPulse();

        for (PulseReceiver output : outputs) {
            result.add(PulseAction.of(name, output, receivedPulse));
        }
        return result;
    }

    public void addOutput(PulseReceiver output) {
        outputs.add(output);
    }

    @Override
    public String getType() {
        return "Broadcaster";
    }

    @Override
    public String getName() {
        return name;
    }
}
