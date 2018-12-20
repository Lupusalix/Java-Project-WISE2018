/*
 * Copyright (c) 2018 Max Henkes
 */

package java.project.engine;

import java.project.engine.util.Point2;

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
