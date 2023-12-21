package aoc.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Direction {
    private Coordinate getNext() {
        Coordinate result;
        switch (dir) {
            case UP:
                result = Coordinate.of(0, -1);
                break;
            case DOWN:
                result = Coordinate.of(0, 1);
                break;
            case LEFT:
                result = Coordinate.of(-1, 0);
                break;
            case RIGHT:
                result = Coordinate.of(1, 0);
                break;
            default:
                throw new RuntimeException();
        }
        return result;
    }

    public Coordinate getNext(Coordinate coordinate) {
        return coordinate.add(getNext());
    }

    public List<Coordinate> getCoordinateToTheRight(Coordinate coordinate, char value) {
        List<Coordinate> result = new ArrayList<>();
        switch (dir) {
            case UP:
                result.add(coordinate.add(Coordinate.of(1, 0)));
                switch (value) {
                    case 'F':
                        break;
                    case '|':
                        break;
                    case '7':
                        result.add(coordinate.add(Coordinate.of(0, -1)));
                        break;
                    default:
                        throw new RuntimeException();
                }
                break;
            case DOWN:
                result.add(coordinate.add(Coordinate.of(-1, 0)));
                switch (value) {
                    case 'J':
                        break;
                    case '|':
                        break;
                    case 'L':
                        result.add(coordinate.add(Coordinate.of(0, 1)));
                        break;
                    default:
                        throw new RuntimeException();
                }
                break;
            case LEFT:
                result.add(coordinate.add(Coordinate.of(0, -1)));
                switch (value) {
                    case 'L':
                        break;
                    case '-':
                        break;
                    case 'F':
                        result.add(coordinate.add(Coordinate.of(-1, 0)));
                        break;
                    default:
                        throw new RuntimeException();
                }
                break;
            case RIGHT:
                result.add(coordinate.add(Coordinate.of(0, 1)));
                switch (value) {
                    case '7':
                        break;
                    case '-':
                        break;
                    case 'J':
                        result.add(coordinate.add(Coordinate.of(1, 0)));
                        break;
                    default:
                        throw new RuntimeException();
                }
                break;
            default:
                throw new RuntimeException();
        }
        return result;
    }

    public Coordinate moveToNextCoordinate(Coordinate coordinate, char value) {
        switch (dir) {
            case UP:
                if (value == 'F') {
                    dir = Directions.RIGHT;
                } else if (value == '7') {
                    dir = Directions.LEFT;
                }
                break;
            case DOWN:
                if (value == 'L') {
                    dir = Directions.RIGHT;
                } else if (value == 'J') {
                    dir = Directions.LEFT;
                }
                break;
            case LEFT:
                if (value == 'L') {
                    dir = Directions.UP;
                } else if (value == 'F') {
                    dir = Directions.DOWN;
                }
                break;
            case RIGHT:
                if (value == '7') {
                    dir = Directions.DOWN;
                } else if (value == 'J') {
                    dir = Directions.UP;
                }
                break;
            default:
                throw new RuntimeException();
        }
        return coordinate.add(getNext());

    }

    public enum Directions {UP, DOWN, LEFT, RIGHT}

    private Directions dir;

    public Direction(Directions dir) {
        this.dir = dir;
    }

    public static Direction of(Directions dir) {
        return new Direction(dir);
    }

    public Directions getDirection() {
        return dir;
    }

    public Direction getLeft() {
        switch (dir) {
            case UP:
                return Direction.of(Directions.LEFT);
            case DOWN:
                return Direction.of(Directions.RIGHT);
            case LEFT:
                return Direction.of(Directions.DOWN);
            case RIGHT:
                return Direction.of(Directions.UP);
            default:
                throw new RuntimeException();
        }
    }

    public Direction getRight() {
        switch (dir) {
            case UP:
                return Direction.of(Directions.RIGHT);
            case DOWN:
                return Direction.of(Directions.LEFT);
            case LEFT:
                return Direction.of(Directions.UP);
            case RIGHT:
                return Direction.of(Directions.DOWN);
            default:
                throw new RuntimeException();
        }
    }

    public Directions turnLeft() {
        switch (dir) {
            case UP:
                this.dir = Directions.LEFT;
                break;
            case DOWN:
                this.dir = Directions.RIGHT;
                break;
            case LEFT:
                this.dir = Directions.DOWN;
                break;
            case RIGHT:
                this.dir = Directions.UP;
                break;
            default:
                throw new RuntimeException();
        }
        return dir;
    }

    public Directions turnRight() {
        switch (dir) {
            case UP:
                this.dir = Directions.RIGHT;
                break;
            case DOWN:
                this.dir = Directions.LEFT;
                break;
            case LEFT:
                this.dir = Directions.UP;
                break;
            case RIGHT:
                this.dir = Directions.DOWN;
                break;
            default:
                throw new RuntimeException();
        }
        return dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Direction direction = (Direction) o;
        return dir == direction.dir;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dir);
    }
}
