package project.sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project.engine.GameLoop;
import project.engine.misc.Misc;
import project.engine.tile.Tile;
import project.engine.tile.TileEmpty;
import project.engine.tile.TilePredator;
import project.engine.tile.TilePrey;

public class Controller implements Runnable{


    //To update our UI we need a thread
    private Thread thread;

    private Color preyColor = Color.GREEN;
    private Color floorColor = Color.GRAY;
    private Color predatorColor = Color.RED;
    private GameLoop gameLoop;
    private boolean started = false;

    public static int sleep = 500;



    /*
    Currently only renders simulation, no extra windows. Should be separated into proper classes and not run in main.
     */
    //Base Gridpane used for rendering
    GridPane root = new GridPane();

    //2D Array of rectangles (easy to change color) that we match with our engine array
    private StackPane[][] board;

    @FXML
    private Button generate;

    @FXML
    private TextField size;

    @FXML
    private TextField pred;

    @FXML
    private TextField prey;

    @FXML
    private RadioButton spawnPrey;


    @FXML
    void onGenerate(ActionEvent event) {

        int s = 0;

        try{
            s = Integer.parseInt(size.getText());
        }catch(Exception e){
            e.printStackTrace();
        }

        if(!started && s > 0 && !pred.getText().isEmpty() && !prey.getText().isEmpty()){

            board = new StackPane[s][s];


                    /*
        Creates the initial blank board to be filled with "Actors". Currently still has issues when window is scaled.
         */
            for (int row = 0; row < s; row++) {
                for (int col = 0; col < s; col++) {
                    StackPane stack = new StackPane();
                    Rectangle tile = new Rectangle();
                    tile.setFill(floorColor);
                    stack.getChildren().add(tile);
                    board[row][col] = stack;
                    root.add(stack, row, col);

                /*
                Binds the size of the individual tiles to the window size, has some issues
                 */
                    tile.widthProperty().bind(root.widthProperty().divide(s));
                    tile.heightProperty().bind(root.heightProperty().divide(s));
                }

            }



        /*
        Create our initial gameloop object.
         */
            gameLoop = new GameLoop();

        /*
        Starts the simulation
         */
            gameLoop.startSimulation(s, s, 4, 5);

        /*
        Starts the thread for updating
         */
            thread = new Thread(this);
            thread.setDaemon(true); //Threads not set to this cause the application to NOT quit when finished because they are still working.
            thread.start();

            Stage stage = new Stage();
            stage.setTitle("Simulation");
            stage.setScene(new Scene(root, 450,450));
            stage.show();
            System.out.println("Started");

            started = true;

        }



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

        Platform.runLater(() -> {
                   /*
    Doesn't check for changes, too slow. Simply sets the color of every rectangle to the one found in our Board.
     */

            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board.length; col++) {

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
                        //t.setText(((TilePrey) tile).getSize() + "");
                        // stack.getChildren().add(t);
                    } else if (tile instanceof TilePredator) {
                        square.setFill(predatorColor);
                    }
                }
            }
        });
    }






}
