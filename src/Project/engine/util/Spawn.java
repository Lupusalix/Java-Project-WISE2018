
package project.engine.util;

import project.engine.MainLoop;

import java.util.Random;

public class Spawn {

    private Spawn() {
    }

    public static Point2 getFreePosition() {


        while (true) {
            Random rand = new Random();

            int x = rand.nextInt(MainLoop.board.getSizeX());
            int y = rand.nextInt(MainLoop.board.getSizeY());

            Point2 position = new Point2(x, y);

            if (MainLoop.board.isEmpty(position)) {
                return position;
            }
        }
    }
}
