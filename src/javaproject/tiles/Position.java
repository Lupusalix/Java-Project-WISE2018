package javaproject.tiles;

import java.util.Random;

public class Position {
    private final int x;
    private final int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //creating a random position within boundaries x and y
    public static Position ranPos(int x, int y) {
        Random rand = new Random();
        return new Position(rand.nextInt(x), rand.nextInt(y));
    }

    public double getDistance(Position d) {
        return Math.sqrt(Math.pow(d.getX() - x, 2) + Math.pow(d.getY() - y, 2));
    }

    public double getDistance(int a, int b) {
        return Math.sqrt(Math.pow(a - x, 2) + Math.pow(b - y, 2));
    }

    //for testing purpose
    public Position getRandMovement() {
        Random ran = new Random();
        int a = ran.nextInt(4);
        switch (a) {
            case 1:
                return new Position(this.x + 1, this.y);
            case 2:
                return new Position(this.x, this.y + 1);
            case 3:
                return new Position(this.x - 1, this.y);
            default:
                return new Position(this.x, this.y - 1);

        }
    }

}
