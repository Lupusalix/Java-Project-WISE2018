package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Philipp
 * @version 1.0
 * <p>
 * The Position class is meant to safe and work with positions on the board.
 */
public class Position {
    /**
     * x: the position on the x-axis.
     * y: the position on the y-axis.
     */
    private final int x;
    private final int y;

    /**
     * @return the position on the x-axis.
     */
    public int getX() {
        return x;
    }

    /**
     * @return the position on the x-axis.
     */
    public int getY() {
        return y;
    }

    /**
     * Constructor.
     * @param x the value on the x-axis.
     * @param y the value on the y-axis.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * creating a random position within boundaries x and y.
     * @param x the x boundary.
     * @param y the y boundary.
     * @return the randomly generated position.
     */
    //creating a random position within boundaries x and y
    public static Position ranPos(int x, int y) {
        Random rand = new Random();
        return new Position(rand.nextInt(x), rand.nextInt(y));
    }

    /**
     * returns the distance between the current position and another position
     * @param d the other position to which we compare our current position
     * @return double value containing the distance
     */
    public double getDistance(Position d) {
        return Math.sqrt(Math.pow(d.getX() - x, 2) + Math.pow(d.getY() - y, 2));
    }

    /**
     * returns the distance between our current position and the submitted pos.
     *
     * @param a x-Axis value to compare to.
     * @param b y-Axis value to compare to.
     * @return double containing the distance.
     */
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

    /**
     * //creating a random position within boundaries x and y
     *
     * @return ArrayList
     */

    public ArrayList getSurrroundingPositions() {
        ArrayList<Position> erg = new ArrayList<>();
        int[] size = BoardManager.getSize();
        if (this.x - 1 >= 0) erg.add(new Position(this.x - 1, this.y));
        if (this.x + 1 < size[0]) erg.add(new Position(this.x + 1, this.y));
        if (this.y - 1 >= 0) erg.add(new Position(this.x, this.y - 1));
        if (this.y + 1 < size[1]) erg.add(new Position(this.x, this.y + 1));
        return erg;
    }

    /**
     * returns adjacent Positions without Predator-positions.
     * @return ArrayList.
     */
    public ArrayList getSurrroundingPositionsPred() {
        ArrayList<Position> erg = getSurrroundingPositions();
        for (int i = 0; i < erg.size(); i++) {
            try {
                if (BoardManager.bGetPos(erg.get(i)) instanceof Predator) erg.remove(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return erg;
    }

    /**
     * return adjacent Positions without Predator and Prey-positions.
     *
     * @return ArrayList.
     */
    public ArrayList getSurrroundingPositionsPrey() {
        ArrayList<Position> erg = getSurrroundingPositionsPred();
        for (int i = 0; i < erg.size(); i++) {
            if (BoardManager.bGetPos(erg.get(i)) instanceof Prey) erg.remove(i);
        }
        return erg;
    }

}
