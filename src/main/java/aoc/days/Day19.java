package aoc.days;

import aoc.data.Blueprint;
import aoc.util.FileUtil;

import java.util.List;

public class Day19 extends Day {
    private List<Blueprint> blueprints;

    protected void initialize() throws Exception {
        blueprints = FileUtil.readFileToObjects(getDay(), Blueprint::parse);
    }

    protected String getPart1Solution() {
        int result = 0;
        for (Blueprint blueprint : blueprints) {
            int maxGeodes = blueprint.simulateAndGetMaxGeodes(24);
            result += (blueprint.getBlueprintId()*maxGeodes);
        }
        return "" + result;
    }

    protected String getPart2Solution() {
        int result = 1;
        for (int i = 0; i < 3; i++) {
            Blueprint blueprint = blueprints.get(i);
            int maxGeodes = blueprint.simulateAndGetMaxGeodes(32);
            result *= maxGeodes;
        }
        return "" + result;
    }
}