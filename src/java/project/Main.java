

package java.project;

import java.project.engine.MainLoop;
import java.project.engine.tile.EmptyTile;
import java.project.engine.tile.Predator;
import java.project.engine.tile.Prey;
import java.project.engine.tile.Tile;
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
    private Color floorColor = Color.SADDLEBROWN;
    private Color predatorColor = Color.RED;

    private int sleep = 50;


    //TODO: Board size, change to take input from UI
    private final int size = 100;


    public void start(Stage primaryStage) {

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
        MainLoop mainLoop = new MainLoop();

        /*
        Starts the simulation
         */
        mainLoop.startSimulation(size, size, 1, 0);

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
                Tile tile = MainLoop.board.getTiles()[row][col];
                Rectangle square = board[row][col];

                if (tile instanceof EmptyTile) {
                    square.setFill(floorColor);
                } else if (tile instanceof Prey) {
                    square.setFill(preyColor);
                } else if (tile instanceof Predator) {
                    square.setFill(predatorColor);
                }
            }
        }
    }



/*    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ui/sample2.fxml"));
        primaryStage.setTitle("Simulation");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }*/


}
