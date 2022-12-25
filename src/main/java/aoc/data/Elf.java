package aoc.data;

import aoc.days.Coordinate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Elf {
    public static int PROPOSE_METHOD = 0;

    private enum Direction {NORTH, SOUTH, WEST, EAST}

    private Coordinate position;
    private Coordinate proposedPosition;

    public Elf(Coordinate position) {
        this.position = position;
    }

    public Coordinate proposePosition(final List<Coordinate> elvesLocations) {
        Direction[] directions = Direction.values();

        for (int i = 0; i < 4; i++) {
            switch (directions[(i + PROPOSE_METHOD) % 4]) {
                case NORTH:
                    final List<Coordinate> northCoordinates = getNorthCoordinates();
                    if (elvesLocations.stream().noneMatch(northCoordinates::contains)) {
                        proposedPosition = position.add(Coordinate.of(0, -1));
                        return proposedPosition;
                    }
                    break;
                case SOUTH:
                    final List<Coordinate> southCoordinates = getSouthCoordinates();
                    if (elvesLocations.stream().noneMatch(southCoordinates::contains)) {
                        proposedPosition = position.add(Coordinate.of(0, 1));
                        return proposedPosition;
                    }
                    break;
                case WEST:
                    final List<Coordinate> westCoordinates = getWestCoordinates();
                    if (elvesLocations.stream().noneMatch(westCoordinates::contains)) {
                        proposedPosition = position.add(Coordinate.of(-1, 0));
                        return proposedPosition;
                    }
                    break;
                case EAST:
                    final List<Coordinate> eastCoordinates = getEastCoordinates();
                    if (elvesLocations.stream().noneMatch(eastCoordinates::contains)) {
                        proposedPosition = position.add(Coordinate.of(1, 0));
                        return proposedPosition;
                    }
                    break;
                default:
                    throw new RuntimeException("adasd");
            }

        }
        proposedPosition = position.copyOf();
        return proposedPosition;
    }

    public void moveToProposedLocationIfFree(final Map<Coordinate, Long> proposedLocationCount) {
        if (proposedLocationCount.get(proposedPosition) == 1) {
            position = proposedPosition.copyOf();
            proposedPosition = null;
        } else {
            proposedPosition = null;
        }
    }

    public boolean isNextToOtherElf(List<Coordinate> elvesLocations) {
        final List<Coordinate> adjacentPositions = getAdjacentPositions();
        return elvesLocations.stream().anyMatch(adjacentPositions::contains);
    }

    private List<Coordinate> getNorthCoordinates() {
        return Arrays.stream(position.getCoordinateAdjacentIncludingDiagonal()).filter(c -> c.getY() < position.getY()).collect(Collectors.toList());
    }

    private List<Coordinate> getSouthCoordinates() {
        return Arrays.stream(position.getCoordinateAdjacentIncludingDiagonal()).filter(c -> c.getY() > position.getY()).collect(Collectors.toList());
    }

    private List<Coordinate> getEastCoordinates() {
        return Arrays.stream(position.getCoordinateAdjacentIncludingDiagonal()).filter(c -> c.getX() > position.getX()).collect(Collectors.toList());
    }

    private List<Coordinate> getWestCoordinates() {
        return Arrays.stream(position.getCoordinateAdjacentIncludingDiagonal()).filter(c -> c.getX() < position.getX()).collect(Collectors.toList());
    }

    public Coordinate getPosition() {
        return position;
    }

    private List<Coordinate> getAdjacentPositions() {
        return Arrays.asList(position.getCoordinateAdjacentIncludingDiagonal());
    }
}
