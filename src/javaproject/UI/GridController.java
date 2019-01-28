package javaproject.UI;

// import com.sun.java.util.jar.pack.Instruction;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javaproject.BoardManager;
import javaproject.tiles.EmptyTile;
import javaproject.tiles.Predator;
import javaproject.tiles.Prey;

/**
 * @author Henry.
 * the controller class for the grid as defined in our grid.fxml.
 *
 */
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
    /**
     * the elements of the Simulation GUI.
     */
    @FXML
    private Slider sleepSlider;

    @FXML
    private GridPane root;

    @FXML
    private Text killsAvrg;

    @FXML
    private Text predAlive;

    @FXML
    private Text preyAlive;

    @FXML
    private Text killsTotal;

    @FXML
    private CheckBox showGrid;

    @FXML
    private TextArea nutritionIntake;

    @FXML
    private RadioButton spawnPrey;

    @FXML
    private TextField spawnNumber;

    @FXML
    private ComboBox<String> colorScheme;

    /**
     * sets the color scheme of the simulation to the selected option.
     * @param event on choosing an option in the drop-down-menu.
     */

    @FXML
    private void onSelect(ActionEvent event){
        switch (colorScheme.getValue()){

            case "Default":
                preyColor = Color.GREEN;
                predatorColor = Color.RED;
                medPreyColor = Color.DARKGREEN;
                bigPreyColor = Color.DARKBLUE;
                break;
            case "Prey:Red Predator:Blue":
                predatorColor = Color.BLUE;
                preyColor = Color.INDIANRED;
                medPreyColor = Color.RED;
                bigPreyColor = Color.DARKRED;
                break;
            case "prey:Pink Predator:Black":
                predatorColor = Color.BLACK;
                preyColor = Color.LIGHTPINK;
                medPreyColor = Color.PINK;
                bigPreyColor = Color.HOTPINK;
        }
    }

    /**
     * sets the number of prey that spawns in everyTick to the entered value.
     * @param event on the press of the Enterkey, this function is called.
     */

    @FXML
    void handleSpawnNumber(ActionEvent event) {
        if (isInt(spawnNumber)) b.setGenPrey(Integer.parseInt(spawnNumber.getText()));
    }


    /**
     * Sets the respawn of pry during the simulation to 'true' or 'false'.
     *
     * @param event if the user inetracts with the radiobutton.
     */
    @FXML
    void toggleSpawn(ActionEvent event) {
        if(spawnPrey.isSelected()){
            b.setGeneratePrey(true);
        }else b.setGeneratePrey(false);
    }

    /**
     * checks if the entered  value is an integer.
     * @param text the textfield to check.
     * @return 'true' if it is an int, false if not.
     */

    private boolean isInt(TextField text) {
        int x;
        try {
            x = Integer.parseInt(text.getText());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * handles the speed of the Simulation.
     *
     * @param event on movement of the slider.
     */
    @FXML
    void onDroped(MouseEvent event) {
        double x= ((sleepSlider.getValue()));
        setSleep(((int) x));
    }

    /**
     * setter methode.
     * @param sleep new sleep.
     */
    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    /**
     * is called everytime the user interacts with the checkbox and turns the gridlines on/off.
     *
     * @param event on checkbox interaction.
     */
    @FXML
    void enableLines(ActionEvent event) {
        root.setGridLinesVisible(showGrid.isSelected());


    }

    /**
     * sets the statistics-textfields in the GUI.
     *
     * @param predKilled killed predators.
     * @param preyKilled killed prey.
     * @param nutKilled Nutrition gained.
     * @param it    number of completed iterations of BoardManager.tick.
     *      @see BoardManager .
     * @param preyA number of alive prey.
     * @param predA number of alive pred.
     */

    @FXML
    void setStatistics(int predKilled, int preyKilled,int nutKilled,int it,int preyA,int predA){
        killsTotal.setText(Integer.toString((predKilled+preyKilled)));
        double killAvrg=( (double)predKilled+(double)predKilled)/it;
        killsAvrg.setText(Double.toString(killAvrg));
        preyAlive.setText(Integer.toString(preyA));
        predAlive.setText(Integer.toString(predA));
    }

    /**
     * handles the generation of the simulation/gridPane.
     *
     * @param size size of one side of the Gridpane.
     * @param predator number of predators to spawn-
     * @param prey number of prey to spawn.
     * @throws Exception .
     */
    public void onGenerate(int size, int predator, int prey, boolean spawn) throws Exception {

        colorScheme.getItems().addAll(
                "Default",
                "Prey:Red Predator:Blue",
                "prey:Pink Predator:Black"
        );

        if(spawn)spawnPrey.setSelected(true);

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

        b = new BoardManager(size, size, prey, predator, 10, 1,spawn);

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

    /**
     * the loop that is done for every iteration of our program.
     *
     */
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

    private void displayStatistics(int predKilled,int preyKilled,int nutritionPI,int iterations){


    }

    /**
     * this methgod resembles our connection between the gridpane and our board, it checks every position on our Board
     * and changes the color of the matching place on our gridpane thus making the grid resemble our actual Board.
     * @see BoardManager .
     */
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

                setStatistics(b.getPredKilled(),b.getPreyKilled(),b.getNutritionPerTick(),b.getIteration(),b.getPreySize(),b.getPredSize());

            }


        });
    }
}