

package java.project.engine.util;

public class Point2 {

    /*
    Custom Vector2D class.
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

}
