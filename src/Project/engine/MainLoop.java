
package Project.engine;

public class MainLoop implements Runnable {

    public static Board board;
    private Thread thread;

    public MainLoop() {
    }

    /*
    Creates our board, assigns them to static variables so it can be accessed everywhere.
     */
    public void startSimulation(int x, int y, int preyCount, int predatorCount) {
        board = new Board(x, y, preyCount, predatorCount);
        board.init();

        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }


    @Override
    public void run() {

        while (true) {

//         TODO: Loop/*
//            1. Prey move
//            2. Predator move
//             Repeat */

            try {
                thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Turn");


            board.turnPrey();
            board.turnPredator();


        }
    }
}
