package Tiles;

import java.util.Random;

public class Position {
    private int x;
    private int y;

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

    public Position() {
    }


    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //creating a random position within boundaries x and y
    public Position ranPos(int x, int y) {
        Random rand = new Random();
        return new Position(rand.nextInt(x), rand.nextInt(y));
    }

    public double getDistance(Position d) {
        return Math.sqrt(Math.pow(d.getX() - x, 2) + Math.pow(d.getY() - y, 2));
    }

    public double getDistance(int a, int b) {
        return Math.sqrt(Math.pow(a - x, 2) + Math.pow(b - y, 2));
    }

}
