/*
 * Copyright (c) 2018 Max Henkes
 */

package project.ui;///*
// * Copyright (c) 2018 Max Henkes
// */
//
//package com.maxhenkes.com.maxhenkes.projectai.ui;
//
//import com.maxhenkes.com.maxhenkes.projectai.engine.IBoardListener;
//import com.maxhenkes.GameLoop;
//import com.maxhenkes.TileEmpty;
//import com.maxhenkes.TilePrey;
//import com.maxhenkes.Tile;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.Pane;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class Controller implements Initializable, IBoardListener, Runnable {
//
//
//    private Tile[][] currentBoard;
//
//    /*
//    Color constants
//     */
//
//    private final String COLORPREY = "-fx-background-color: green";
//    private final String COLORPREDATOR = "-fx-background-color: red";
//    private final String COLOREMPTY = "-fx-background-color: grey";
//
//
//    public static Tile[][] updateBoard;
//
//
//    private void checkChanged() {
//        for (int i = 0; i <= currentBoard.length; i++) {
//            for (int f = 0; f <= currentBoard[i].length; i++) {
//                if (currentBoard[i][f] != updateBoard[i][f]) {
//                    /*
//                    TODO: Render changes
//                     */
//                }
//            }
//        }
//    }
//
//
//    private boolean update = true;
//
//    public static Tile[][] board;
//
//    public AnchorPane gridBase;
//    @FXML
//    Button buttonGenerate;
//    @FXML
//    public TextField boardX;
//    @FXML
//    public TextField boardY;
//
//    public static GameLoop mainLoop;
//
//    @FXML
//    public GridPane grid;
//
//
//    @FXML
//    private void buttonGeneratePress(ActionEvent event) {
//
//        int x = 0, y = 0;
//
//        try {
//            x = Integer.parseInt(boardX.getText());
//            y = Integer.parseInt(boardY.getText());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (x != 0 && y != 0) {
//            grid = new GridPane();
//
//
//            x--;
//            y--;
//
//            mainLoop.startSimulation(x, y, 2, 0);
//
//            GameLoop.board.addListener(this);
//
//            grid.setHgap(0);
//            grid.setVgap(0);
//            gridBase.getChildren().add(grid);
//            grid.prefWidthProperty().bind(gridBase.widthProperty());
//            grid.prefHeightProperty().bind(gridBase.heightProperty());
//            grid.setAlignment(Pos.CENTER);
//            grid.setGridLinesVisible(true);
//
//
//            for (int i = 0; i <= x; i++) {
//                for (int f = 0; f <= y; f++) {
//                    Pane pane = new Pane();
//                    if (GameLoop.board.getGrid()[i][f] instanceof TileEmpty) {
//                        pane.setStyle(COLOREMPTY);
//                    } else if (GameLoop.board.getGrid()[i][f] instanceof TilePrey) {
//                        pane.setStyle(COLORPREY);
//                    }
//
//                    pane.setPrefSize(35, 35);
//                    grid.add(pane, i, f);
//                    System.out.println("Set pane at " + i + " and " + f);
//                }
//            }
//
//
//        }
//    }
//
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        mainLoop = new GameLoop();
//    }
//
//    @Override
//    public void onChanged() {
//
//        int x = GameLoop.board.getSizeX();
//        int y = GameLoop.board.getSizeY();
//
//        for (int i = 0; i <= x; i++) {
//            for (int f = 0; f <= y; f++) {
//
//                Pane pane = new Pane();
//                if (GameLoop.board.getGrid()[i][f] instanceof TileEmpty) {
//                    pane.setStyle(COLOREMPTY);
//                } else if (GameLoop.board.getGrid()[i][f] instanceof TilePrey) {
//                    pane.setStyle(COLORPREY);
//                }
//
//                pane.setPrefSize(35, 35);
//                grid.add(pane, i, f);
//                System.out.println("Set pane at " + i + " and " + f);
//            }
//        }
//
//    }
//
//
//    @Override
//    public void run() {
//
//    }
//}