package project.engine.util;

public class Vector2 {


    private int magnitude;
    private Point2 direction;


    public Vector2(Point2 direction, int magnitude){
        this.direction = direction;
        this.magnitude = magnitude;
    }

    public int getMagnitude() {
        return magnitude;
    }

    public Point2 getDirection() {
        return direction;
    }
}
