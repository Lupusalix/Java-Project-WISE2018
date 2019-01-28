package javaproject.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * the controller class for our initial input-window.
 * @author Henry
 */

public class InputStyle {


    @FXML
    private TextField tfSize;

    @FXML
    private TextField tfPred;

    @FXML
    private TextField tfPrey;

    @FXML
    private RadioButton rbSpawn;

    @FXML
    private Button generate;

    /**
     * checks if the enetered string is an integer.
     *
     * @param text the textfield to check.
     * @return false if not an integer.
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
     * this method is called as soon as our user hits the 'generate'-button.
     * it opens up a new window and calls the "onGenerate" Method from the gridControllerclass
     * @see GridController .
     * @param event hitting the GenerateButton.
     * @throws Exception .
     */
    @FXML
    void handle_generate(ActionEvent event) throws Exception {

        Parent root2 = FXMLLoader.load(getClass().getResource("Grid.fxml"));

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root3 = fxmlLoader.load(getClass().getResource("Grid.fxml").openStream());
        GridController controller = fxmlLoader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root3, 1720, 1080));
        stage.setTitle("Simulation");
        stage.show();

        int size = 0, predator = 0, prey = 0;
        boolean spawn= rbSpawn.isSelected();

        if (isInt(tfSize)) {
            size = Integer.parseInt(tfSize.getText());
        }

        if (isInt(tfPred)) {
            predator = Integer.parseInt(tfPred.getText());

        }

        if (isInt(tfPrey)) {
            prey = Integer.parseInt(tfPrey.getText());
        }


        if(size != 0 || predator != 0 || prey != 0) {
            controller.onGenerate(size, predator, prey, spawn);
        }


    }


}