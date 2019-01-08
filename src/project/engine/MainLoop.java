
package project.engine;

public class MainLoop implements Runnable {

    public static Board board;
    private Thread thread;
    private boolean spawnPrey = true;
    private int sleepTimer = 100;
    private int preyInterval = 2;
    private int oneSecond = (preyInterval * 1000)/sleepTimer;
    private int preyTimer = 0;

    public MainLoop() {
    }

    /*
    Creates our board, assigns them to static variable so it can be accessed everywhere.
     */
    public void startSimulation(int x, int y, int preyCount, int predatorCount) {
        board = new Board(x, y, preyCount, predatorCount);
        board.init();

        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    /*
    Runs our main gameloop
     */
    @Override
    public void run() {

        while (true) {

            if(spawnPrey && preyTimer >= oneSecond){
                board.spawnPreyRandomly();
                preyTimer = 0;
            }
            preyTimer++;

            board.turnPrey();
            board.turnPredator();

            try {
                thread.sleep(sleepTimer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(Misc.debug) {
                System.out.println("Turn passed");
            }

        }
    }
}
