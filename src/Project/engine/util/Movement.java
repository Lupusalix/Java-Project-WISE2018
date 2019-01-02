
package Project.engine.util;

import java.util.Random;

public class Movement {

    private Movement() {
    }

    public static Point2 randomDirection() {
        Random rand = new Random();

        switch (rand.nextInt(2)) {

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


}
