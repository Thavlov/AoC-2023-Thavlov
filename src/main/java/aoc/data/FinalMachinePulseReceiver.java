package aoc.data;

import java.util.Collections;
import java.util.List;

public class FinalMachinePulseReceiver implements PulseReceiver {
    private final String name;
    public FinalMachinePulseReceiver(String name) {
        this.name = name;
    }

    @Override
    public void receive(Pulse pulse, String source, int round) {    }

    @Override
    public List<PulseAction> update(PulseAction pulseAction) {
        return Collections.emptyList();
    }

    @Override
    public String getType() {
        return "Blank";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addOutput(PulseReceiver pulseReceiver) {   }
}
