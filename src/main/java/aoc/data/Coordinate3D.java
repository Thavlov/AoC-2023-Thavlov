package aoc.data;

import java.util.Objects;

public class Coordinate3D {
    final int x;
    final int y;
    final int z;

    private Coordinate3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Coordinate3D of(int x, int y, int z) {
        return new Coordinate3D(x, y, z);
    }

    public static Coordinate3D parse(String string) {
        String[] split = string.trim().replace("(", "").replace(")", "").split(",");
        return Coordinate3D.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Coordinate3D[] getAdjacentCoordinates() {
        final Coordinate3D[] result = new Coordinate3D[6];
        result[0] = Coordinate3D.of(x + 1, y, z);
        result[1] = Coordinate3D.of(x - 1, y, z);
        result[2] = Coordinate3D.of(x, y + 1, z);
        result[3] = Coordinate3D.of(x, y - 1, z);
        result[4] = Coordinate3D.of(x, y, z + 1);
        result[5] = Coordinate3D.of(x, y, z - 1);
        return result;
    }

    public Coordinate3D[] getAdjacentCoordinatesExcludingDiagonally() {
        final Coordinate3D[] result = new Coordinate3D[18];
        int counter = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) {
                        continue;
                    }
                    if (Math.abs(dx) == 1 && Math.abs(dy) == 1 && Math.abs(dz) == 1) {
                        continue;
                    }
                    result[counter++] = Coordinate3D.of(x + dx, y + dy, z + dz);
                }
            }
        }
        return result;
    }

    public Coordinate3D[] getAdjacentCoordinatesIncludingDiagonally() {
        final Coordinate3D[] result = new Coordinate3D[26];
        int counter = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) {
                        continue;
                    }
                    result[counter++] = Coordinate3D.of(x + dx, y + dy, z + dz);
                }
            }
        }
        return result;
    }

    public boolean isInSamePlanAs(Coordinate3D other) {
        return this.x == other.x || this.y == other.y || this.z == other.z;
    }

    public char getCommonPlane(Coordinate3D other) {
        if (this.x == other.x) {
            return 'x';
        }
        if (this.y == other.y) {
            return 'y';
        }
        if (this.z == other.z) {
            return 'z';
        }
        throw new RuntimeException("The coordinates are not in the same plane.");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate3D that = (Coordinate3D) o;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
