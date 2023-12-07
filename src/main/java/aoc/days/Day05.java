package aoc.days;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import aoc.data.Range;
import aoc.util.FileUtil;
import aoc.util.StringUtil;

public class Day05 extends Day {

    private List<Long> seeds;
    private Map<Range, Long> seedToSoilMap;
    private Map<Range, Long> soilToFertilizerMap;
    private Map<Range, Long> fertilizerToWaterMap;
    private Map<Range, Long> waterToLightMap;
    private Map<Range, Long> lightToTemperatureMap;
    private Map<Range, Long> temperatureToHumidityMap;
    private Map<Range, Long> humidityToLocationMap;

    protected void initialize() throws Exception {
        final List<List<String>> input = FileUtil.readFileGroupByEmptyLine(getDay());

        seeds = StringUtil.parseListOfNumbers(input.get(0).get(0).replace("seeds:", ""));

        seedToSoilMap = parseMapAndInsertMissingRanges(input.get(1));
        soilToFertilizerMap = parseMapAndInsertMissingRanges(input.get(2));
        fertilizerToWaterMap = parseMapAndInsertMissingRanges(input.get(3));
        waterToLightMap = parseMapAndInsertMissingRanges(input.get(4));
        lightToTemperatureMap = parseMapAndInsertMissingRanges(input.get(5));
        temperatureToHumidityMap = parseMapAndInsertMissingRanges(input.get(6));
        humidityToLocationMap = parseMapAndInsertMissingRanges(input.get(7));
    }

    protected Object getPart1Solution() throws Exception {
        List<Long> values = getMappedValues(seeds, seedToSoilMap);
        values = getMappedValues(values, soilToFertilizerMap);
        values = getMappedValues(values, fertilizerToWaterMap);
        values = getMappedValues(values, waterToLightMap);
        values = getMappedValues(values, lightToTemperatureMap);
        values = getMappedValues(values, temperatureToHumidityMap);
        values = getMappedValues(values, humidityToLocationMap);

        return values.stream().mapToLong(Long::valueOf).min().getAsLong();
    }

    protected Object getPart2Solution() throws Exception {
        List<Range> values = createSeedRanges(seeds);
        values = getMappedRanges(values, seedToSoilMap);
        values = getMappedRanges(values, soilToFertilizerMap);
        values = getMappedRanges(values, fertilizerToWaterMap);
        values = getMappedRanges(values, waterToLightMap);
        values = getMappedRanges(values, lightToTemperatureMap);
        values = getMappedRanges(values, temperatureToHumidityMap);
        values = getMappedRanges(values, humidityToLocationMap);

        return values.stream().mapToLong(Range::getFrom).min().getAsLong();
    }

    private List<Long> getMappedValues(List<Long> seeds, Map<Range, Long> map) {
        return seeds.stream().map(s -> getMappedValue(s, map)).collect(Collectors.toList());
    }

    private Long getMappedValue(Long seed, Map<Range, Long> map) {
        for (Range range : map.keySet()) {
            if (range.isValueInRange(seed)) {
                return seed + map.get(range);
            }
        }
        return seed;
    }

    private List<Range> createSeedRanges(List<Long> seeds) {
        List<Range> result = new ArrayList<>();

        for (int i = 0; i < seeds.size(); i += 2) {
            result.add(Range.ofWidth(seeds.get(i), seeds.get(i + 1)));
        }
        return result;
    }

    private List<Range> getMappedRanges(List<Range> seeds, Map<Range, Long> map) {
        return seeds.stream().map(s -> getMappedRange(s, map)).flatMap(List::stream).collect(Collectors.toList());
    }

    private List<Range> getMappedRange(Range range, Map<Range, Long> map) {
        List<Range> ranges = cropRange(range, map);
        return shiftRanges(ranges, map);
    }

    private List<Range> cropRange(Range range, Map<Range, Long> map) {
        final List<Range> result = new ArrayList<>();
        for (Range mapRange : map.keySet()) {
            if (range.isCompletelyOverlappedBy(mapRange)) {
                result.add(range);
            } else if (range.isPartlyOverlappedBy(mapRange)) {
                result.add(range.getIntersectionWith(mapRange));
            }
        }
        return result;
    }

    private List<Range> shiftRanges(List<Range> ranges, Map<Range, Long> map) {
        List<Range> result = new ArrayList<>();
        for (Range range : ranges) {
            for (Range mapRange : map.keySet()) {
                if (range.isCompletelyOverlappedBy(mapRange)) {
                    long shift = map.get(mapRange);
                    result.add(Range.fromWithShift(range, shift));
                    break;
                }
            }
        }
        return result;
    }

    private Map<Range, Long> parseMapAndInsertMissingRanges(List<String> strings) {
        Map<Range, Long> result = new TreeMap<>();
        for (String string : strings.subList(1, strings.size())) {
            List<Long> integers = StringUtil.parseListOfNumbers(string);
            Range range = Range.of(integers.get(1), integers.get(1) + integers.get(2) - 1);
            Long offset = integers.get(0) - integers.get(1);
            result.put(range, offset);
        }

        long value = -1;
        for (Range range : new TreeSet<>(result.keySet())) {
            if (value + 1 != range.getFrom()) {
                Range missingRange = Range.of(value + 1, range.getFrom() - 1);
                result.put(missingRange, 0L);
            }
            value = range.getTo();
        }
        result.put(Range.of(value + 1, Long.MAX_VALUE), 1L);
        return result;
    }
}