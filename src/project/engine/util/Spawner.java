
package project.engine.util;

import project.engine.MainLoop;

import java.util.Random;

public class Spawner {

    private Spawner() {
    }

    public static Point2 getFreePosition() {


        while (true) {
            Random rand = new Random();

            int x = rand.nextInt(MainLoop.board.getSizeX());
            int y = rand.nextInt(MainLoop.board.getSizeY());

            Point2 position = new Point2(x, y);

            if (BoardUtil.isEmpty(position)) {
                return position;
            }
        }
    }
}
