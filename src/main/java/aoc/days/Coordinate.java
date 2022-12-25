package aoc.days;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinate of(int x, int y) {
        return new Coordinate(x, y);
    }

    public static List<Coordinate> parseMultipleSeparatedBy(String string, String separator) {
        return Arrays.stream(string.split(separator)).map(Coordinate::parse).collect(Collectors.toList());
    }

    public static Coordinate parse(String string) {
        String[] split = string.trim().replace("(", "").replace(")", "").split(",");
        return new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    public static Coordinate[] getAllCoordinatesBetween(Coordinate c1, Coordinate c2) {
        int dx = Math.abs(c1.getX() - c2.getX());
        int dy = Math.abs(c1.getY() - c2.getY());

        Coordinate[] result = new Coordinate[Math.max(dx, dy) + 1];
        Coordinate temp;
        int counter = 0;
        if (dx == 0) {
            temp = c1.getY() < c2.getY() ? c1.copyOf() : c2.copyOf();
            do {
                result[counter++] = temp;
                temp = new Coordinate(temp.getX(), temp.getY() + 1);
            }
            while (counter <= dy);

        } else {
            temp = c1.getX() < c2.getX() ? c1.copyOf() : c2.copyOf();
            do {
                result[counter++] = temp;
                temp = new Coordinate(temp.getX() + 1, temp.getY());
            }
            while (counter <= dx);
        }
        return result;
    }

    public double distanceTo(Coordinate other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public int manhattanDistanceTo(Coordinate other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.abs(dx) + Math.abs(dy);
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

    public Coordinate[] getCoordinateAdjacent() {
        Coordinate[] result = new Coordinate[4];
        result[0] = new Coordinate(x + 1, y);
        result[1] = new Coordinate(x - 1, y);
        result[2] = new Coordinate(x, y - 1);
        result[3] = new Coordinate(x, y + 1);
        return result;
    }

    public Coordinate[] getCoordinateAdjacentIncludingDiagonal() {
        Coordinate[] result = new Coordinate[8];
        result[0] = new Coordinate(x + 1, y);
        result[1] = new Coordinate(x - 1, y);
        result[2] = new Coordinate(x, y - 1);
        result[3] = new Coordinate(x, y + 1);
        result[4] = new Coordinate(x - 1, y - 1);
        result[5] = new Coordinate(x + 1, y - 1);
        result[6] = new Coordinate(x - 1, y + 1);
        result[7] = new Coordinate(x + 1, y + 1);
        return result;
    }

    public Coordinate getCoordinateAdjacentTo(final Coordinate other) {
        for (Coordinate coordinate : getCoordinateAdjacent()) {
            if (other.isAdjacentTo(coordinate)) {
                return coordinate;
            }
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

    public Coordinate getRelativeX(int dx) {
        return Coordinate.of(x + dx, y);
    }

    public Coordinate getRelativeY(int dy) {
        return Coordinate.of(x, y + dy);
    }

    public Coordinate add(Coordinate other) {
        return Coordinate.of(this.getX() + other.getX(), this.getY() + other.getY());
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
