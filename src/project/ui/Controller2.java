/*
 * Copyright (c) 2018 Max Henkes
 */

package project.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller2 implements Initializable, Runnable {


    private Rectangle[][] board;

    private Thread thread;

    private Color preyColor = Color.GREEN;
    private Color floorColor = Color.RED;
    private Color predatorColor = Color.RED;

    private int sleep = 50;

    private final int size = 8;

    @FXML
    GridPane grid = new GridPane();


    private void initializeBoard() {

        board = new Rectangle[size][size];

//        for (int i = 0; i < size; i++) {
//            for (int f = 0; f < size; f++) {
//                Rectangle tile = new Rectangle();
//                tile.setFill(floorColor);
//                board[i][f] = tile;
//               tile.widthProperty().bind(grid.widthProperty().divide(size));
//               tile.heightProperty().bind(grid.heightProperty().divide(size));
//                grid.add(tile, i, f);
//            }
//        }

        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeBoard();
    }

    @Override
    public void run() {

    }
}
