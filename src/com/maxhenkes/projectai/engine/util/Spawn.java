/*
 * Copyright (c) 2018 Max Henkes
 */

package com.maxhenkes.projectai.engine.util;

import com.maxhenkes.projectai.engine.MainLoop;

import java.util.Random;

public class Spawn {

    private Spawn() {
    }

    public static Point2 getFreePosition() {


        while (true) {
            Random rand = new Random();

            int x = rand.nextInt(MainLoop.board.getSizeX() + 1);
            int y = rand.nextInt(MainLoop.board.getSizeY() + 1);

            Point2 position = new Point2(x, y);

            if (MainLoop.board.isEmpty(position)) {
                return position;
            }
        }
    }
}
