package javaproject.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * this is the first class that is beeing called by our programm and it opens up an input window.
 *
 * @author Henry
 *
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("Input_style.fxml"));
        primaryStage.setTitle("Input");
        primaryStage.setScene(new Scene(root, 700, 405));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

