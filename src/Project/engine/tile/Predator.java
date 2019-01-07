

package Project.engine.tile;

import Project.engine.util.Point2;
import Project.engine.IAnimal;

public class Predator extends Tile implements IAnimal {

    private Point2 position;

    public Predator(Point2 pos) {
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
