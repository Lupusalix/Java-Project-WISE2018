package project.engine.tile;

import project.engine.util.BoardUtil;
import project.engine.util.Point2;
import project.engine.MainLoop;
import project.engine.util.Vector2;

public class TilePrey extends TileAnimal {

    private boolean isHunted = false;
    private int size;


    public TilePrey(Point2 pos) {
        this(pos, 1);
    }

    public TilePrey(Point2 pos, int size) {
        super(pos, 2, 4);
        this.size = size;
        MainLoop.board.preyList.add(this);
    }


    /*
    Currently moves randomly. Should be replaced by actual logic later.
     */
    @Override
    public void turn() {

        BoardUtil.moveRandom(pos, speed);
    }

    public int size() {
        return size;
    }

    @Override
    public void setAlive(boolean alive) {
        super.setAlive(alive);
        if(!alive){
            MainLoop.board.preyList.remove(this);
            MainLoop.board.setTile(pos, new TileEmpty());
        }
    }
}
