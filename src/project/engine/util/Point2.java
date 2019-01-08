

package project.engine.util;

import java.util.ArrayList;
import java.util.Random;

public class Point2{

    /*
    Custom Point2D class
    Represents a location on the grid
     */

    public final int x;
    public final int y;

    public Point2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point2 add(Point2 b) {
        return new Point2(x + b.x, y + b.y);
    }

    public Point2 sub(Point2 b) {
        return new Point2(x - b.x, y - b.y);
    }

    public Point2 mult(Point2 b) {
        return new Point2(x * b.x, y * b.y);
    }

    public Point2 div(Point2 b) {
        return new Point2(x / b.x, y / b.y);
    }

    public Point2 mult1D(int i) {
        return new Point2(x * i, y * i);
    }


    public Point2 addVector2(Vector2 vec) {
        return this.add(vec.getDirection().mult1D(vec.getMagnitude()));
    }

    public static Point2 randomDirection() {
        Random rand = new Random();

        switch (rand.nextInt(4)) {

            case 0:
                return new Point2(1, 0);
            case 1:
                return new Point2(0, 1);
            case 2:
                return new Point2(-1, 0);
            case 3:
                return new Point2(0, -1);
        }
        return new Point2(0, 0);

    }

    @Override
    public boolean equals(Object obj) {

        if(obj == this)return true;

        if(!(obj instanceof Point2))return false;


        return ((Point2) obj).x == x && ((Point2) obj).y == y;
    }

    @Override
    public int hashCode() {
        return x*y;
    }

    public Point2 moveUp(){
        return new Point2(x, y+1);
    }
    public Point2 moveDown(){
        return new Point2(x, y-1);
    }
    public Point2 moveLeft(){
        return new Point2(x-1, y);
    }
    public Point2 moveRight(){
        return new Point2(x+1, y);
    }

    public ArrayList<Point2> getNeighbours(){
        ArrayList<Point2> points = new ArrayList<>();
        points.add(moveLeft());
        points.add(moveRight());
        points.add(moveUp());
        points.add(moveDown());
    return points;
    }

    @Override
    public String toString() {

        return "[Point|x:"+x+"|y:" + y+"]";
    }
}
