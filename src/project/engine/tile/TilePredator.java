

package project.engine.tile;

import project.engine.util.Point2;
import project.engine.IAnimal;

public class TilePredator extends Tile implements IAnimal {

    private Point2 position;

    public TilePredator(Point2 pos) {
        position = pos;
    }

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public int getDetectionRadius() {
        return 0;
    }

    @Override
    public void move() {
    }

    @Override
    public void setPosition(Point2 pos) {

    }

    @Override
    public Point2 getPosition() {
        return null;
    }
}
