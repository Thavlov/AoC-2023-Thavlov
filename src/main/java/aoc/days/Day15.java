package aoc.days;

import aoc.data.Range;
import aoc.data.Sensor;
import aoc.util.FileUtil;

import java.util.*;

public class Day15 extends Day {
    private List<Sensor> sensors;

    protected void initialize() throws Exception {
        sensors = FileUtil.readFileToObjects(getDay(), Sensor::parse);
    }

    protected String getPart1Solution() {
        final Set<Coordinate> emptyCoordinates = new HashSet<>();

        int y = 2000000;
        for (Sensor sensor : sensors) {
            emptyCoordinates.addAll(sensor.getCoordinatesWithinDistanceAlong(y));
        }

        for (Sensor sensor : sensors) {
            emptyCoordinates.remove(sensor.getLocation());
            emptyCoordinates.remove(sensor.getBeacon());
        }

        return "" + emptyCoordinates.size();
    }

    protected String getPart2Solution() {
        List<Range> ranges = new ArrayList<>();

        int yMin = 0;
        long yMax = 4000000L;
        int y;

        for (y = yMin; y <= yMax; y++) {
            for (Sensor sensor : sensors) {
                Optional<Range> rangeOfCoordinatesWithinDistanceAlong = sensor.getRangeOfCoordinatesWithinDistanceAlong(y);
                if (rangeOfCoordinatesWithinDistanceAlong.isPresent()) {
                    ranges.add(rangeOfCoordinatesWithinDistanceAlong.get());
                }
            }
            ranges = Range.mergeRanges(ranges);
            if (ranges.size() > 1) {
                break;
            }
            ranges.clear();
        }
        return "" + ((ranges.get(0).getTo() + 1L) * yMax + y);
    }
}