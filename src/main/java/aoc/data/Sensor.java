package aoc.data;

import aoc.days.Coordinate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sensor {
    private static final String REGEX = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private final Coordinate location;
    private final Coordinate beacon;

    private Sensor(Coordinate location, Coordinate beacon) {
        this.location = location;
        this.beacon = beacon;
    }

    public static Sensor parse(String string) {
        final Matcher matcher = PATTERN.matcher(string);

        if (!matcher.find()) {
            throw new RuntimeException(String.format("Unable to parse string: %s.", string));
        }

        int locationX = Integer.parseInt(matcher.group(1));
        int locationY = Integer.parseInt(matcher.group(2));
        int beaconX = Integer.parseInt(matcher.group(3));
        int beaconY = Integer.parseInt(matcher.group(4));
        return new Sensor(Coordinate.of(locationX, locationY), Coordinate.of(beaconX, beaconY));
    }

    public Set<Coordinate> getCoordinatesWithinDistanceAlong(int y) {
        final Set<Coordinate> result = new HashSet<>();
        int distanceToBeacon = location.manhattanDistanceTo(beacon);

        Coordinate temp;
        for (int dx = -distanceToBeacon; dx <= distanceToBeacon; dx++) {
            temp = Coordinate.of(location.getX() + dx, y);
            if (location.manhattanDistanceTo(temp) <= distanceToBeacon) {
                result.add(temp);
            }
        }
        result.remove(location);
        result.remove(beacon);
        return result;
    }

    public Optional<Range> getRangeOfCoordinatesWithinDistanceAlong(int y) {
        int distanceToBeacon = location.manhattanDistanceTo(beacon);
        int halfWidth = distanceToBeacon - Math.abs(y - location.getY());
        if (halfWidth < 0) {
            return Optional.empty();
        }
        return Optional.of(Range.of(location.getX() - halfWidth, location.getX() + halfWidth));
    }

    public Coordinate getLocation() {
        return location;
    }

    public Coordinate getBeacon() {
        return beacon;
    }
}
