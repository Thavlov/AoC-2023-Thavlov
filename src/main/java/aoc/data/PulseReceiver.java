package aoc.data;

import java.util.List;

public interface PulseReceiver {
    void receive(Pulse pulse, String source, int round);
    List<PulseAction> update(PulseAction pulseAction);
    String getType();
    String getName();
    void addOutput(PulseReceiver pulseReceiver);
}
