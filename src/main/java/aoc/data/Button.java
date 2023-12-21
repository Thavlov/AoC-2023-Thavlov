package aoc.data;

import java.util.Collections;
import java.util.List;

public class Button implements PulseReceiver {

    private final String name = "Button";
    private final Broadcaster broadcaster;

    public Button(Broadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    public PulseAction push() {
        return PulseAction.of(name, broadcaster, Pulse.LOW);
    }

    @Override
    public void receive(Pulse pulse, String source, int round) {
        broadcaster.receive(Pulse.LOW, name, round);
    }

    @Override
    public List<PulseAction> update(PulseAction pulseAction) {
        return Collections.emptyList();
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void addOutput(PulseReceiver pulseReceiver) {  }
}
