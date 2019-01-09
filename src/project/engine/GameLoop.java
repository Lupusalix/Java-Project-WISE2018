
package project.engine;

import project.Main;
import project.engine.misc.Misc;

public class GameLoop {

    public static Board board;
    private boolean spawnPrey = true;
    private int preyInterval = 2;
    private int oneSecond = (preyInterval * 1000) / Main.sleep;
    private int preyTimer = 0;

    public GameLoop() {
    }

    /*
    Creates our board, assigns them to static variable so it can be accessed everywhere.
     */
    public void startSimulation(int x, int y, int preyCount, int predatorCount) {
        board = new Board(x, y, preyCount, predatorCount);
        board.init();
    }

    /*
    Runs our main gameLoop
     */
    public void tick() {

        if (spawnPrey && preyTimer >= oneSecond) {
            board.spawnPreyRandomly();
            preyTimer = 0;
        }
        preyTimer++;

        board.turnPrey();
        board.turnPredator();
//        board.checkStarvation();

        if (Misc.debug) {
            System.out.println("Turn passed");
        }

    }

}
