package project;

import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import project.engine.GameLoop;
import project.engine.misc.Misc;
import project.engine.tile.TileEmpty;
import project.engine.tile.TilePredator;
import project.engine.tile.TilePrey;
import project.engine.tile.Tile;
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
    private StackPane[][] board;
    //To update our UI we need a thread
    private Thread thread;

    private Color preyColor = Color.GREEN;
    private Color floorColor = Color.GRAY;
    private Color predatorColor = Color.RED;
    private GameLoop gameLoop;

    public static int sleep = 500;


    //TODO: Board size, change to take input from UI
    private final int size = 12;


    public void start(Stage primaryStage) {

        board = new StackPane[size][size];


        /*
        Creates the initial blank board to be filled with "Actors". Currently still has issues when window is scaled.
         */
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                StackPane stack = new StackPane();
                Rectangle tile = new Rectangle();
                tile.setFill(floorColor);
                stack.getChildren().add(tile);
                board[row][col] = stack;
                root.add(stack, row, col);

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
        gameLoop = new GameLoop();

        /*
        Starts the simulation
         */
        gameLoop.startSimulation(size, size, 4, 5);

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
            Processes Logic
             */
            gameLoop.tick();

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

        if(Misc.debugLevel > 0){
            System.out.println("[RENDER]");
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
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
                        Tile tile = GameLoop.board.getGrid()[row][col];
                        StackPane stack = board[row][col];
                        Rectangle square = (Rectangle) board[row][col].getChildren().get(0);

                        if(stack.getChildren().size() > 1){
                            stack.getChildren().remove(1);
                        }
                        if (tile instanceof TileEmpty) {
                            square.setFill(floorColor);
                        } else if (tile instanceof TilePrey) {
                            square.setFill(preyColor);
                            Text t = new Text();
                            t.setFont(new Font(40));
                            t.setText(((TilePrey) tile).getSize() + "");
                            stack.getChildren().add(t);
                        } else if (tile instanceof TilePredator) {
                            square.setFill(predatorColor);
                        }
                    }
                }
            }
        });
    }
}
