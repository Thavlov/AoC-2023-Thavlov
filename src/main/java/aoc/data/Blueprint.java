package aoc.data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Blueprint {
    private int step;
    private int oreRobots = 1;
    private int clayRobots = 0;
    private int obsidianRobots = 0;
    private int geodeRobots = 0;

    private final int blueprintId;
    private final int orePrOre;
    private final int orePrClay;
    private final int orePrObsidian;
    private final int clayPrObsidian;
    private final int orePrGeode;
    private final int obsidianPrGeode;

    private int maxConsumableOrePrTurn;
    private int maxConsumableClayPrTurn;
    private int maxConsumableObsidianPrTurn;

    private int ore = 0;
    private int clay = 0;
    private int obsidian = 0;
    private int geode = 0;

    private boolean couldCreateOreRobotLastTurnButDidNot = false;
    private boolean couldCreateClayRobotLastTurnButDidNot = false;
    private boolean couldCreateObsidianRobotLastTurnButDidNot = false;

    private Blueprint(int blueprintId, int orePrOre, int orePrClay, int orePrObsidian, int clayPrObsidian, int orePrGeode, int obsidianPrGeode) {
        this.blueprintId = blueprintId;
        this.orePrOre = orePrOre;
        this.orePrClay = orePrClay;
        this.orePrObsidian = orePrObsidian;
        this.clayPrObsidian = clayPrObsidian;
        this.orePrGeode = orePrGeode;
        this.obsidianPrGeode = obsidianPrGeode;
        this.step = 0;

        this.maxConsumableOrePrTurn = Math.max(Math.max(orePrOre, orePrClay), Math.max(orePrObsidian, orePrGeode));
        this.maxConsumableClayPrTurn = clayPrObsidian;
        this.maxConsumableObsidianPrTurn= obsidianPrGeode;
    }

    private Blueprint(int blueprintId, int orePrOre, int orePrClay, int orePrObsidian, int clayPrObsidian, int orePrGeode, int obsidianPrGeode, int ore, int clay, int obsidian, int geode, int oreRobots, int clayRobots, int obsidianRobots, int geodeRobots, int step) {
        this(blueprintId, orePrOre, orePrClay, orePrObsidian, clayPrObsidian, orePrGeode, obsidianPrGeode);
        this.ore = ore;
        this.clay = clay;
        this.obsidian = obsidian;
        this.geode = geode;
        this.oreRobots = oreRobots;
        this.clayRobots = clayRobots;
        this.obsidianRobots = obsidianRobots;
        this.geodeRobots = geodeRobots;
        this.step = step;

        this.maxConsumableOrePrTurn = Math.max(Math.max(orePrOre, orePrClay), Math.max(orePrObsidian, orePrGeode));
        this.maxConsumableClayPrTurn = clayPrObsidian;
        this.maxConsumableObsidianPrTurn= obsidianPrGeode;
    }

    public static Blueprint parse(String line) {
        Pattern pattern = Pattern.compile("Blueprint (\\d+):");
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        int blueprintId = Integer.parseInt(matcher.group(1));

        pattern = Pattern.compile(".*Each ore robot costs (\\d+) ore.");
        matcher = pattern.matcher(line);
        matcher.find();
        int orePrOre = Integer.parseInt(matcher.group(1));

        pattern = Pattern.compile(".*Each clay robot costs (\\d+) ore.");
        matcher = pattern.matcher(line);
        matcher.find();
        int orePrClay = Integer.parseInt(matcher.group(1));

        pattern = Pattern.compile(".*Each obsidian robot costs (\\d+) ore and (\\d+) clay.");
        matcher = pattern.matcher(line);
        matcher.find();
        int orePrObsidian = Integer.parseInt(matcher.group(1));
        int clayPrObsidian = Integer.parseInt(matcher.group(2));

        pattern = Pattern.compile(".* Each geode robot costs (\\d+) ore and (\\d+) obsidian.");
        matcher = pattern.matcher(line);
        matcher.find();
        int orePrGeode = Integer.parseInt(matcher.group(1));
        int obsidianPrGeode = Integer.parseInt(matcher.group(2));

        return new Blueprint(blueprintId, orePrOre, orePrClay, orePrObsidian, clayPrObsidian, orePrGeode, obsidianPrGeode);
    }

    public int simulateAndGetMaxGeodes(int steps) {
        Set<Blueprint> blueprintPermutations = new HashSet<>();
        blueprintPermutations.add(this.clone());
        Set<Blueprint> tempList = new HashSet<>();
        for (int turn = 0; turn < steps; turn++) {
            for (Blueprint blueprint : blueprintPermutations) {
                tempList.addAll(blueprint.iterate());
            }
            blueprintPermutations = tempList;
            tempList = new HashSet<>();
        }
        return blueprintPermutations.stream().mapToInt(Blueprint::getGeode).max().getAsInt();
    }

    public Set<Blueprint> iterate() {
        this.step++;

        // Decide
        boolean canCreateGeodeRobot = canCreateGeodeRobotAndIsFeasible(); //flyt rundt; ore, clay, obs, geode
        boolean canCreateObsidianRobot = canCreateObsidianRobotAndIsFeasible();
        boolean canCreateClayRobot = canCreateClayRobotAndIsFeasible();
        boolean canCreateOreRobot = canCreateOreRobotAndIsFeasible();

        // Collect
        collect();

        //Build robots and spawn copies
        final Set<Blueprint> permutatedSolutions = new HashSet<>();

        Blueprint permutatedBlueprint;

        if (canCreateGeodeRobot) {
            permutatedBlueprint = this.clone();
            permutatedBlueprint.createGeodeRobot();
            permutatedSolutions.add(permutatedBlueprint);
            return permutatedSolutions;
        }

        permutatedBlueprint = this.clone();
        permutatedBlueprint.setCouldCreateOreRobotLastTurnButDidNot(canCreateOreRobot);
        permutatedBlueprint.setCouldCreateClayRobotLastTurnButDidNot(canCreateClayRobot);
        permutatedBlueprint.setCouldCreateObsidianRobotLastTurnButDidNot(canCreateObsidianRobot);
        permutatedSolutions.add(permutatedBlueprint);

        /*if (canCreateGeodeRobot) {
            permutatedBlueprint = this.clone();
            permutatedBlueprint.createGeodeRobot();
            permutatedSolutions.add(permutatedBlueprint);
        }*/
        if (canCreateObsidianRobot) {
            permutatedBlueprint = this.clone();
            permutatedBlueprint.createObsidianRobot();
            permutatedSolutions.add(permutatedBlueprint);
        }
        if (canCreateClayRobot) {
            permutatedBlueprint = this.clone();
            permutatedBlueprint.createClayRobot();
            permutatedSolutions.add(permutatedBlueprint);
        }
        if (canCreateOreRobot) {
            permutatedBlueprint = this.clone();
            permutatedBlueprint.createOreRobot();
            permutatedSolutions.add(permutatedBlueprint);
        }
        return permutatedSolutions;
    }

    private void collect() {
        ore += oreRobots;
        clay += clayRobots;
        obsidian += obsidianRobots;
        geode += geodeRobots;
    }

    private boolean canCreateOreRobotAndIsFeasible() {
        return oreRobots <= maxConsumableOrePrTurn && ore >= orePrOre && !couldCreateOreRobotLastTurnButDidNot;
    }

    private boolean createOreRobot() {
        if (ore >= orePrOre) {
            oreRobots++;
            ore -= orePrOre;
            return true;
        }
        return false;
    }

    private boolean canCreateClayRobotAndIsFeasible() {
        return clayRobots <= maxConsumableClayPrTurn && ore >= orePrClay && !couldCreateClayRobotLastTurnButDidNot;//&& (24-step) > clayPrObsidian;
    }

    private boolean createClayRobot() {
        if (ore >= orePrClay) {
            clayRobots++;
            ore -= orePrClay;
            return true;
        }
        return false;
    }

    private boolean canCreateObsidianRobotAndIsFeasible() {
        return obsidianRobots <= maxConsumableObsidianPrTurn && ore >= orePrObsidian && clay >= clayPrObsidian &&  !couldCreateObsidianRobotLastTurnButDidNot;// && (24-step) > obsidianPrGeode;
    }

    private boolean createObsidianRobot() {
        if (ore >= orePrObsidian && clay >= clayPrObsidian) {
            obsidianRobots++;
            ore -= orePrObsidian;
            clay -= clayPrObsidian;
            return true;
        }
        return false;
    }

    private boolean canCreateGeodeRobotAndIsFeasible() {
        return ore >= orePrGeode && obsidian >= obsidianPrGeode;// && !couldCreateGeodeRobotLastTurnButDidNot;
    }

    private boolean createGeodeRobot() {
        if (ore >= orePrGeode && obsidian >= obsidianPrGeode) {
            geodeRobots++;
            ore -= orePrGeode;
            obsidian -= obsidianPrGeode;
            return true;
        }
        return false;
    }

    public int getGeode() {
        return geode;
    }

    public int getBlueprintId() {
        return blueprintId;
    }

    public Blueprint clone() {
        return new Blueprint(blueprintId, orePrOre, orePrClay, orePrObsidian, clayPrObsidian, orePrGeode, obsidianPrGeode, ore, clay, obsidian, geode, oreRobots, clayRobots, obsidianRobots, geodeRobots, step);
    }

    public void setCouldCreateOreRobotLastTurnButDidNot(boolean couldCreateOreRobotLastTurnButDidNot) {
        this.couldCreateOreRobotLastTurnButDidNot = couldCreateOreRobotLastTurnButDidNot;
    }

    public void setCouldCreateClayRobotLastTurnButDidNot(boolean couldCreateClayRobotLastTurnButDidNot) {
        this.couldCreateClayRobotLastTurnButDidNot = couldCreateClayRobotLastTurnButDidNot;
    }

    public void setCouldCreateObsidianRobotLastTurnButDidNot(boolean couldCreateObsidianRobotLastTurnButDidNot) {
        this.couldCreateObsidianRobotLastTurnButDidNot = couldCreateObsidianRobotLastTurnButDidNot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blueprint blueprint = (Blueprint) o;
        return step == blueprint.step && oreRobots == blueprint.oreRobots && clayRobots == blueprint.clayRobots && obsidianRobots == blueprint.obsidianRobots && geodeRobots == blueprint.geodeRobots && blueprintId == blueprint.blueprintId && orePrOre == blueprint.orePrOre && orePrClay == blueprint.orePrClay && orePrObsidian == blueprint.orePrObsidian && clayPrObsidian == blueprint.clayPrObsidian && orePrGeode == blueprint.orePrGeode && obsidianPrGeode == blueprint.obsidianPrGeode && ore == blueprint.ore && clay == blueprint.clay && obsidian == blueprint.obsidian && geode == blueprint.geode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(step, oreRobots, clayRobots, obsidianRobots, geodeRobots, blueprintId, orePrOre, orePrClay, orePrObsidian, clayPrObsidian, orePrGeode, obsidianPrGeode, ore, clay, obsidian, geode);
    }
}
