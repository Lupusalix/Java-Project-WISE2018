

package project.engine.tile;


import project.engine.util.BoardUtil;
import project.engine.util.Point2;
import project.engine.IAnimal;
import project.engine.MainLoop;
import project.engine.util.Vector2;

public class TilePrey extends Tile implements IAnimal {

    private final int speed = 2;
    private boolean isHunted = false;
    private int detectionRadius = 6;
    private int size;

    private boolean active;

    private Point2 pos;

    public TilePrey(Point2 pos) {
        this(pos, 1);
    }

    public TilePrey(Point2 pos, int size) {
        this(pos, size, false);
    }

    public TilePrey(Point2 pos, int size, boolean active) {
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
            Vector2 vec = new Vector2(BoardUtil.randomDirection(), speed);
            Point2 newPosition = pos.addVector2(vec);


                if(BoardUtil.move(pos, new Vector2(BoardUtil.randomDirection(), speed))){
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
