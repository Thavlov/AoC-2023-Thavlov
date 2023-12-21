package aoc.data;

public class PulseAction {
    private final String source;
    private final PulseReceiver destination;
    private final Pulse pulse;

    private PulseAction(String source, PulseReceiver destination, Pulse pulse) {
        this.source = source;
        this.destination = destination;
        this.pulse = pulse;
    }

    public static PulseAction of(String source, PulseReceiver destination, Pulse pulse) {
        return new PulseAction(source, destination, pulse);
    }

    public String getSource() {
        return source;
    }

    public PulseReceiver getDestination() {
        return destination;
    }

    public Pulse getPulse() {
        return pulse;
    }
}
