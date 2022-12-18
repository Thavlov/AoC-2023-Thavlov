package aoc.days;

import aoc.data.Coordinate3D;
import aoc.util.FileUtil;
import aoc.util.SolutionUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day18 extends Day {
    public static void main(String[] args) {
        SolutionUtil.solveDay(Day18.class);
    }

    private Set<Coordinate3D> coordinates;

    protected void initialize() throws Exception {
        coordinates = new HashSet<>(FileUtil.readFileToObjects(getDay(), Coordinate3D::parse));
    }

    protected String getPart1Solution() {
        int result = 0;

        for (Coordinate3D coordinate : coordinates) {
            Coordinate3D[] adjacentCoordinates = coordinate.getAdjacentCoordinates();

            for (Coordinate3D adjacentCoordinate : adjacentCoordinates) {
                if (coordinates.contains(adjacentCoordinate)) {
                    continue;
                }
                result++;
            }
        }

        return "" + result;
    }

    protected String getPart2Solution() {
        final Set<Coordinate3D> surfaceCoordinates = new HashSet<>();
        for (Coordinate3D coordinate : coordinates) {
            Coordinate3D[] adjacentCoordinates = coordinate.getAdjacentCoordinates();

            for (Coordinate3D adjacentCoordinate : adjacentCoordinates) {
                if (coordinates.contains(adjacentCoordinate)) {
                    continue;
                }
                surfaceCoordinates.add(adjacentCoordinate);
            }
        }

        List<Set<Coordinate3D>> surfaceGroups = new ArrayList<>();
        Set<Coordinate3D> group = new HashSet<>();

        Set<Coordinate3D> nextCoordinates = new HashSet<>();


        while (!surfaceCoordinates.isEmpty()) {
            Coordinate3D surfaceCoordinate = surfaceCoordinates.stream().findFirst().get();
            surfaceCoordinates.remove(surfaceCoordinate);
            nextCoordinates.add(surfaceCoordinate);

            while (!nextCoordinates.isEmpty()) {
                Coordinate3D nextCoordinate = nextCoordinates.stream().findFirst().get();
                nextCoordinates.remove(nextCoordinate);

                Coordinate3D[] adjacentCoordinatesIncludingDiagonally = nextCoordinate.getAdjacentCoordinatesIncludingDiagonally();
                for (Coordinate3D adjacentCoordinate : adjacentCoordinatesIncludingDiagonally) {
                    if (!isReachable(nextCoordinate, adjacentCoordinate)) {
                        continue;
                    }
                    if (surfaceCoordinates.contains(adjacentCoordinate)) {
                        surfaceCoordinates.remove(adjacentCoordinate);
                        nextCoordinates.add(adjacentCoordinate);
                    }
                }

                group.add(nextCoordinate);
            }
            surfaceGroups.add(group);
            group = new HashSet<>();
        }

        Set<Coordinate3D> externalCoordinates = new HashSet<>();

        for (Set<Coordinate3D> surfaceGroup : surfaceGroups) {
            if (surfaceGroup.size() > externalCoordinates.size()) {
                externalCoordinates = surfaceGroup;
            }
        }

        int result = 0;

        for (Coordinate3D coordinate : coordinates) {
            Coordinate3D[] adjacentCoordinates = coordinate.getAdjacentCoordinates();

            for (Coordinate3D adjacentCoordinate : adjacentCoordinates) {
                if (coordinates.contains(adjacentCoordinate)) {
                    continue;
                }
                if (externalCoordinates.contains(adjacentCoordinate)) {
                    result++;
                }
            }
        }

        return "" + result;
    }

    private boolean isReachable(Coordinate3D from, Coordinate3D to) {
        if (from.isInSamePlanAs(to)) {
            Coordinate3D sharedCoordinate1;
            Coordinate3D sharedCoordinate2;
            switch (from.getCommonPlane(to)) {
                case 'x':
                    sharedCoordinate1 = Coordinate3D.of(from.getX(), from.getY(), to.getZ());
                    sharedCoordinate2 = Coordinate3D.of(to.getX(), to.getY(), from.getZ());

                    break;
                case 'y':
                    sharedCoordinate1 = Coordinate3D.of(to.getX(), from.getY(), from.getZ());
                    sharedCoordinate2 = Coordinate3D.of(from.getX(), to.getY(), to.getZ());
                    break;
                case 'z':
                    sharedCoordinate1 = Coordinate3D.of(from.getX(), to.getY(), from.getZ());
                    sharedCoordinate2 = Coordinate3D.of(to.getX(), from.getY(), to.getZ());
                    break;
                default:
                    throw new RuntimeException();
            }
            return !(coordinates.contains(sharedCoordinate1) && coordinates.contains(sharedCoordinate2));
        }
        return false;
    }
}