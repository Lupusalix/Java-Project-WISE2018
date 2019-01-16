package javaproject.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javaproject.BoardManager;
import javaproject.tiles.Animal;
import javaproject.tiles.EmptyTile;
import javaproject.tiles.Predator;

/**
 * @author Henry
 * @version 1.0
 * <p>
 * the controller class for our initial input window, it also contains die display of our program.
 */
public class Input_con implements Runnable {


    //2D Array of rectangles (easy to change color) that we match with our engine array
    private Rectangle[][] board;
    //To update our UI we need a thread
    private Thread thread;

    private javafx.scene.paint.Color preyColor = javafx.scene.paint.Color.GREEN;
    private javafx.scene.paint.Color floorColor = javafx.scene.paint.Color.GRAY;
    private javafx.scene.paint.Color predatorColor = Color.RED;

    private int sleep = 10;

    BoardManager b;

    @FXML
    private TextField size;

    @FXML
    private TextField predators;

    @FXML
    private TextField prey;

    @FXML
    private Button generate;

    GridPane root = new GridPane();

    /**
     * the method checks if the entered string is an integer
     *
     * @param text the Text Field to check
     * @return 'true' if string is an int
     */

    public boolean isInt(TextField text) {
        int x;
        try {
            x = Integer.parseInt(text.getText());
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Calls our main application on abutton press and hands it our inputs.
     *
     * @param event on clicking the button "generate"
     * @throws Exception
     */

    @FXML
    void handleButton(ActionEvent event) throws Exception {

        int s = 0;
        int x = 0;//prey needs rename
        int y = 0;//pred needs rename

        if (isInt((prey))) {
            x = Integer.parseInt(prey.getText());
        }

        if (isInt((predators))) {
            y = Integer.parseInt(predators.getText());
        }

        if (isInt(size)) {
            s = Integer.parseInt(size.getText());
        } else {
            System.out.println("error");
        }

        Stage stage = new Stage();
        stage.setTitle("Simulation");
        stage.setScene(new Scene(root, 450, 450));

        board = new Rectangle[s][s];


        /*
        Creates the initial blank board to be filled with "Actors". Currently still has issues when window is scaled.
         */
        for (int row = 0; row < s; row++) {
            for (int col = 0; col < s; col++) {
                Rectangle tile = new Rectangle();
                tile.setFill(floorColor);
                board[row][col] = tile;
                root.add(tile, row, col);

                /*
                Binds the size of the individual javaproject.tiles to the window size, has some issues
                 */
                tile.widthProperty().bind(root.widthProperty().divide(s));
                tile.heightProperty().bind(root.heightProperty().divide(s));
            }

        }

        stage.show();

        /*
        Create our initial gameloop object.
         */

        b = new BoardManager(s, s, x, y, 10, 1);

        //Sorts and prints
        //b.test();

        /*
        Starts the thread for updating
         */
        thread = new Thread(this);
        thread.setDaemon(true); //Threads not set to this cause the application to NOT quit when finished because they are still working.
        thread.start();

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
            b.tick();

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * the method applies the changes on our Board to the front end window and changes the colors on the board
     * accodingly.
     */

    private void renderChanges() {

        /*
        Doesn't check for changes, too slow. Simply sets the color of every rectangle to the one found in our Board.
         */
        int s = 0;

        if (isInt(size)) {
            s = Integer.parseInt(size.getText());
        }

        for (int row = 0; row < s; row++) {
            for (int col = 0; col < s; col++) {

                /*
                Replace with own Array from own mainloop.
                Safe to use from another thread but might cause weird rendering issues when the board changes mid update.
                Detection distance not implemented yet.
                 */
                EmptyTile tile = BoardManager.getBoard()[row][col];
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