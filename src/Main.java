import Tiles.Animal;
import Tiles.EmptyTile;
import Tiles.Predator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application implements Runnable {

    /*
    Currently only renders simulation, no extra windows. Should be separated into proper classes and not run in main.
     */
    //Base Gridpane used for rendering
    GridPane root = new GridPane();

    //2D Array of rectangles (easy to change color) that we match with our engine array
    private Rectangle[][] board;
    //To update our UI we need a thread
    private Thread thread;

    private Color preyColor = Color.GREEN;
    private Color floorColor = Color.GRAY;
    private Color predatorColor = Color.RED;

    private int sleep = 50;

    BoardManager b;


    //TODO: Board size, change to take input from UI
    private final int size = 100;


    public void start(Stage primaryStage) throws Exception {

        board = new Rectangle[size][size];


        /*
        Creates the initial blank board to be filled with "Actors". Currently still has issues when window is scaled.
         */
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Rectangle tile = new Rectangle();
                tile.setFill(floorColor);
                board[row][col] = tile;
                root.add(tile, row, col);

                /*
                Binds the size of the individual tiles to the window size, has some issues
                 */
                tile.widthProperty().bind(root.widthProperty().divide(size));
                tile.heightProperty().bind(root.heightProperty().divide(size));
            }

        }

        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();

        /*
        Create our initial gameloop object.
         */

        b = new BoardManager(size, size, 90, 10);

        //Sorts and prints
        //b.test();

        /*
        Starts the thread for updating
         */
        thread = new Thread(this);
        thread.setDaemon(true); //Threads not set to this cause the application to NOT quit when finished because they are still working.
        thread.start();

    }


    public static void main(String[] args) {
        launch(args);
    }



    /*
    IRUNNABLE thread interface.
     */
    @Override
    public void run() {

        while (true) {

            /*
            Renders the changes to our board every x seconds.
             */
            renderChanges();

            try {
                thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    private void renderChanges() {

        /*
        Doesn't check for changes, too slow. Simply sets the color of every rectangle to the one found in our Board.
         */

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {

                /*
                Replace with own Array from own mainloop.
                Safe to use from another thread but might cause weird rendering issues when the board changes mid update.
                Detection distance not implemented yet.
                 */
                EmptyTile tile = b.getTiles()[row][col];
                Rectangle square = board[row][col];

                if (tile instanceof Predator) {
                    square.setFill(predatorColor);
                } else if (tile instanceof Animal) {
                    square.setFill(preyColor);
                } else if (tile != null) {
                    square.setFill(floorColor);
                }
            }
        }
    }

}