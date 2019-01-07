

package Project.engine.tile;


import Project.engine.util.Movement;
import Project.engine.util.Point2;
import Project.engine.IAnimal;
import Project.engine.MainLoop;

public class Prey extends Tile implements IAnimal {

    private final int speed = 2;
    private boolean isHunted = false;
    private int detectionRadius = 6;
    private int size;

    private boolean active;

    private Point2 pos;

    public Prey(Point2 pos) {
        this(pos, 1);
    }

    public Prey(Point2 pos, int size) {
        this(pos, size, false);
    }

    public Prey(Point2 pos, int size, boolean active) {
        this.size = size;
        this.active = active;
        this.pos = pos;
        MainLoop.board.setTile(pos, this);
        MainLoop.board.preyList.add(this);
    }


    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getDetectionRadius() {
        return detectionRadius;
    }


    /*
    Currently moves randomly. Should be replaced by actual logic later.
     */
    @Override
    public void move() {

        while(true) {
            Point2 newPosition1 = Movement.randomDirection().mult1D(speed);//Get Rand Direction and multiply with Speed
            Point2 newPosition = newPosition1.add(pos); // move to new Pos

            if (MainLoop.board.isEmpty(newPosition)) {
                MainLoop.board.moveTile(pos, newPosition);
                break;
            }
        }
    }

    @Override
    public void setPosition(Point2 pos) {
        this.pos = pos;
    }

    @Override
    public Point2 getPosition() {
        return pos;
    }

    public int size() {
        return size;
    }

}