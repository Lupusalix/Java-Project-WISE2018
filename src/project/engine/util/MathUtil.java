package project.engine.util;

import java.util.*;

public class MathUtil {

    private MathUtil() {
    }

    /*
   Returns an Array of Point2 positions around the specified position defined by the radius.
     */
    public static ArrayList<Point2> gridBox(Point2 pos, int radius) {

        ArrayList<Point2> list = new ArrayList<>();

        Point2 corner = new Point2(pos.x - radius, pos.y - radius);

        int steps = radius * 2 + 1;

        for (int i = 0; i < steps; i++) {
            for (int f = 0; f < steps; f++) {

                Point2 point = new Point2(corner.x + i, corner.y + f);

                if (point != pos && BoardUtil.isMoveInGrid(point)) {
                    list.add(point);
                }
            }
        }

        return list;
    }
}
