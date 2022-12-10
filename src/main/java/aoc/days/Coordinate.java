package aoc.days;

import java.util.Objects;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isAdjacentTo(final Coordinate other) {
        return Math.abs(this.x - other.getX()) <= 1 && Math.abs(this.y - other.getY()) <= 1;
    }

    public boolean shareOneCoordinate(final Coordinate other) {
        return this.x == other.getX() || this.y == other.getY();
    }

    public boolean isTwoAwaySingleAdjTo(final Coordinate other) {
        return Math.abs(this.x - other.getX()) > 0 && Math.abs(this.y - other.getY()) > 0;
    }

    public Coordinate getCoordinateAdjacentTo(final Coordinate other) {
        if (other.isAdjacentTo(new Coordinate(x + 1, y))) {
            return new Coordinate(x + 1, y);
        }
        if (other.isAdjacentTo(new Coordinate(x - 1, y))) {
            return new Coordinate(x - 1, y);
        }
        if (other.isAdjacentTo(new Coordinate(x, y - 1))) {
            return new Coordinate(x, y - 1);
        }
        if (other.isAdjacentTo(new Coordinate(x, y + 1))) {
            return new Coordinate(x, y + 1);
        }
        throw new RuntimeException("Error: No direct coordinate.");
    }

    public Coordinate getDiagonalCoordinateAdjacentTo(final Coordinate other) {
        if (other.isAdjacentTo(new Coordinate(x + 1, y + 1))) {
            return new Coordinate(x + 1, y + 1);
        }
        if (other.isAdjacentTo(new Coordinate(x - 1, y - 1))) {
            return new Coordinate(x - 1, y - 1);
        }
        if (other.isAdjacentTo(new Coordinate(x + 1, y - 1))) {
            return new Coordinate(x + 1, y - 1);
        }
        if (other.isAdjacentTo(new Coordinate(x - 1, y + 1))) {
            return new Coordinate(x - 1, y + 1);
        }
        throw new RuntimeException("Error: No diagonal coordinate.");
    }

    public Coordinate copyOf() {
        return new Coordinate(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
