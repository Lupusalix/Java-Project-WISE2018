package javaproject.UI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javaproject.BoardManager;
import javaproject.tiles.EmptyTile;
import javaproject.tiles.Predator;
import javaproject.tiles.Prey;

public class GridController implements Runnable{

/*
   Currently only renders simulation, no extra windows. Should be separated into proper classes and not run in main.
    */
    //Base Gridpane used for rendering


    //2D Array of rectangles (easy to change color) that we match with our engine array
    private StackPane[][] board;
    //To update our UI we need a thread
    private Thread thread;

    private Color preyColor = Color.GREEN;
    private Color floorColor = Color.GRAY;
    private Color predatorColor = Color.RED;
    private Color medPreyColor = Color.DARKGREEN;
    private Color bigPreyColor = Color.DARKBLUE;

    private int sleep = 100;

    int size = 0;

    BoardManager b;


    @FXML
    private GridPane root;


    public void onGenerate(int size, int predator, int prey) throws Exception {



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
                Binds the size of the individual javaproject.tiles to the window size, has some issues
                 */


                tile.widthProperty().bind(root.widthProperty().divide(size));
                tile.heightProperty().bind(root.heightProperty().divide(size));
            }

        }


        /*
        Create our initial gameloop object.
         */

        b = new BoardManager(size, size, prey, predator, 10, 1);

        //Sorts and prints
        //b.test();

        /*
        Starts the thread for updating
         */
        thread = new Thread(this);
        thread.setDaemon(true); //Threads not set to this cause the application to NOT quit when finished because they are still working.
        thread.start();

    }

    @Override
    public void run() {

        while (true) {

            /*
            Renders the changes to our board every x seconds.
             */
            renderChanges();
            b.tick(sleep);

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void renderChanges() {

        /*
        Doesn't check for changes, too slow. Simply sets the color of every rectangle to the one found in our Board.
         */

        Platform.runLater(new Runnable() {
            @Override
            public void run() {


                for (int row = 0; row < board.length; row++) {
                    for (int col = 0; col < board.length; col++) {

                            /*
                            Replace with own Array from own mainloop.
                            Safe to use from another thread but might cause weird rendering issues when the board changes mid update.
                            Detection distance not implemented yet.
                             */
                        EmptyTile tile = BoardManager.getBoard()[row][col];
                        StackPane stack = board[row][col];
                        Rectangle square = (Rectangle) board[row][col].getChildren().get(0);

                        if (stack.getChildren().size() > 1) {
                            stack.getChildren().remove(1);
                        }
                        if (tile instanceof Predator) {
                            square.setFill(predatorColor);
                        } else if (tile instanceof Prey) {
                            square.setFill(preyColor);
                            if (((Prey) tile).getSize() > 1) {
                                if (((Prey) tile).getSize() > 2) square.setFill(bigPreyColor);
                                else square.setFill(medPreyColor);
                                /*Text t = new Text();
                                t.setFont(new Font(40));
                                t.setText(((Prey) tile).getSize() + "");
                                stack.getChildren().add(t);*/
                            }
                        } else if (tile != null) {
                            square.setFill(floorColor);
                        }
                    }
                }
            }
        });
    }
}
