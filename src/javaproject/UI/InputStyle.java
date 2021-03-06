package javaproject.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * the controller class for our initial input-window.
 * @author Henry
 */

public class InputStyle {


    @FXML
    private Label PredHGError;

    @FXML
    private TextField tfSize;

    @FXML
    private TextField preyMaxSizeTf;

    @FXML
    private TextField tfPred;

    @FXML
    private Label PredSightError;

    @FXML
    private RadioButton rbSpawn;

    @FXML
    private TextField preyMoveTf;

    @FXML
    private Label preySizeError;

    @FXML
    private Label preyMoveError;


    @FXML
    private Label predError;

    @FXML
    private TextField PredMovementTf;

    @FXML
    private Label preyError;

    @FXML
    private TextField tfPrey;

    @FXML
    private Button generate;

    @FXML
    private Label predMoveError;

    @FXML
    private Label errorMessage;

    @FXML
    private TextField predMovementTf;

    @FXML
    private Label predSightError;

    @FXML
    private TextField predSightTf;

    @FXML
    private Label predHGError;

    @FXML
    private TextField hgSightTf;

    @FXML
    private TextField initialPreySpawnTf;

    @FXML
    private Label initialPreySpawnError;

    @FXML
    private TextField predStarveTf;

    @FXML
    private Label predStarveError;

    int size = 0, predator = 0, prey = 0, preyMove = 0, predStarve = 0, predMove = 0, predSight = 0, hgSight = 0, initialPreySpawn = 0;

    /**
     * this method calls isInt to check if the given value is an valid Integer, if it is it returns the corrct value, if not it returns-1 .
     * @param textField the text field to check.
     * @param error the error associated with the textfield.
     * @return the integer entered or -1 if it wasn't a valid integer.
     */
    private int setValue(TextField textField,Label error){
        int toSet;
        if (isInt(textField)) {
            error.setVisible(false);
            toSet = Integer.parseInt(textField.getText());
            if(toSet<0)error.setVisible(true);
            return toSet;

        }else{
            error.setVisible(true);
            return -1;
        }
    }


    /**
     * checks if the enetered string is an integer.
     *
     * @param text the textfield to check.
     * @return false if not an integer.
     */

    private boolean isInt(TextField text) {
        int x;
        try {
            errorMessage.setVisible(false);
            x = Integer.parseInt(text.getText());
            return true;
        } catch (Exception e) {
            errorMessage.setText("One or more entered values are not valid Numbers");
            errorMessage.setVisible(true);
            return false;
        }
    }

    @FXML
    void showSpawnTf(ActionEvent event) {
        initialPreySpawnTf.setVisible(rbSpawn.isSelected());
    }
    /**
     * this method is called as soon as our user hits the 'generate'-button.
     * it opens up a new window and calls the "onGenerate" Method from the gridControllerclass
     * @see GridController .
     * @param event hitting the GenerateButton.
     * @throws Exception .
     */
    @FXML
    void handle_generate(ActionEvent event) throws Exception {

        boolean spawn= rbSpawn.isSelected();

        if (isInt(tfSize)) {
            size = Integer.parseInt(tfSize.getText());
        }

        prey =setValue(tfPrey,preyError);
        preyMove=setValue(preyMoveTf,preyMoveError);
        predator =setValue(tfPred,predError);
        predMove=setValue(predMovementTf,predMoveError);
        predSight =setValue(predSightTf,predSightError);
        hgSight =setValue(hgSightTf,predHGError);
        predStarve = setValue(predStarveTf, predStarveError);
        if(spawn){
            initialPreySpawn =setValue(initialPreySpawnTf,initialPreySpawnError);
        }else initialPreySpawn=10;


        if (size > 0 && predator > -1 && prey > -1 && preyMove > -1 && predMove > -1 && predSight > -1 && hgSight > -1 && initialPreySpawn > -1 && predStarve > -1) {

            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root3 = fxmlLoader.load(getClass().getResource("Grid.fxml").openStream());
            GridController controller = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root3, 1600, 800));
            stage.setTitle("Simulation");
            stage.setOnCloseRequest(e -> System.exit(1));
            stage.show();
            controller.onGenerate(size, predator, prey, spawn, preyMove, predStarve, predMove, predSight, hgSight, initialPreySpawn);
            generate.getScene().getWindow().hide();

        }


    }


}