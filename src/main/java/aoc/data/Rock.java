package aoc.data;

import aoc.days.Coordinate;

import java.util.Arrays;

public class Rock {
    private Coordinate location; //Upper left coordinate
    private final Coordinate[] rock;

    private Rock(Coordinate location, Coordinate[] rock) {
        this.rock = rock;
        this.location = location.getRelativeY(-getHeight());
    }

    public static Rock getType(int type, Coordinate location) {
        switch (type) {
            case 0:
                return new Rock(location, new Coordinate[]{Coordinate.of(0, 0), Coordinate.of(1, 0), Coordinate.of(2, 0), Coordinate.of(3, 0)});
            case 1:
                return new Rock(location, new Coordinate[]{Coordinate.of(1, 0), Coordinate.of(0, 1), Coordinate.of(1, 1), Coordinate.of(2, 1), Coordinate.of(1, 2)});
            case 2:
                return new Rock(location, new Coordinate[]{Coordinate.of(2, 0), Coordinate.of(2, 1), Coordinate.of(0, 2), Coordinate.of(1, 2), Coordinate.of(2, 2)});
            case 3:
                return new Rock(location, new Coordinate[]{Coordinate.of(0, 0), Coordinate.of(0, 1), Coordinate.of(0, 2), Coordinate.of(0, 3)});
            case 4:
                return new Rock(location, new Coordinate[]{Coordinate.of(0, 0), Coordinate.of(1, 0), Coordinate.of(0, 1), Coordinate.of(1, 1)});
            default:
                throw new RuntimeException("Unknown type");
        }
    }

    public int getHeight() {
        return Arrays.stream(rock).mapToInt(Coordinate::getY).max().getAsInt();
    }

    public void moveRockDown() {
        location = location.getRelativeY(1);
    }


    public void  moveRockRight() {
        location = location.getRelativeX(1);
    }

    public void moveRockLeft() {
        location = location.getRelativeX(-1);
    }

    public Coordinate[] getRockCoordinates() {
        return getRockCoordinatesRelativeTo(location);
    }

    public Coordinate[] getRockCoordinatesBelow() {
        return getRockCoordinatesRelativeTo(location.getRelativeY(1));
    }

    public Coordinate[] getRockCoordinatesRight() {
        return getRockCoordinatesRelativeTo(location.getRelativeX(1));
    }

    public Coordinate[] getRockCoordinatesLeft() {
        return getRockCoordinatesRelativeTo(location.getRelativeX(-1));
    }

    private Coordinate[] getRockCoordinatesRelativeTo(Coordinate other) {
        return Arrays.stream(rock).map(c -> c.add(other)).toArray(Coordinate[]::new);
    }
}
