package javaproject.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * @author Henry.
 * @version 1.0.
 * @see input_con.java .
 *
 * calls our initial input window
 */
import static javafx.application.Application.launch;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("UI_Style.fxml"));
        primaryStage.setTitle("Just a test");
        primaryStage.setScene(new Scene(root, 700, 405));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
