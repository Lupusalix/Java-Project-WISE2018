/*
 * Copyright (c) 2018 Max Henkes
 */

package com.maxhenkes.projectai.engine;

import com.maxhenkes.projectai.engine.util.Point2;

public interface IAnimal {

    /*
    How many cells the animal can move in 1 turn
     */
    int getSpeed();

    int getDetectionRadius();


    void move();

    void setPosition(Point2 pos);

    Point2 getPosition();

}
