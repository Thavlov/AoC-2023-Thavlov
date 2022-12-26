package aoc.data;

import aoc.days.Coordinate;

public class Blizzard {
    public static int MAX_WIDTH;
    public static int MAX_HEIGHT;

    private final Coordinate position;
    private final char direction;

    public Blizzard(Coordinate position, char direction) {
        this.position = position;
        this.direction = direction;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void move() {
        switch (direction) {
            case '>':
                if (position.getX() == MAX_WIDTH) {
                    this.position.setX(1);
                } else {
                    this.position.setX(position.getX() + 1);
                }
                break;
            case '<':
                if (position.getX() == 1) {
                    this.position.setX(MAX_WIDTH);
                } else {
                    this.position.setX(position.getX() - 1);
                }
                break;
            case '^':
                if (position.getY() == 1) {
                    this.position.setY(MAX_HEIGHT);
                } else {
                    this.position.setY(position.getY() - 1);
                }
                break;
            case 'v':
                if (position.getY() == MAX_HEIGHT) {
                    this.position.setY(1);
                } else {
                    this.position.setY(position.getY() + 1);
                }
                break;
            default:
                throw new RuntimeException("Error: Unknown direction.");
        }
    }
}
