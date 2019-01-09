package project.engine.tile;

import project.engine.GameLoop;
import project.engine.util.BoardUtil;
import project.engine.util.Point2;

public class TilePrey extends TileAnimal {

    private boolean isHunted = false;
    private int size;


    public TilePrey(Point2 pos) {
        this(pos, 1);
    }

    public TilePrey(Point2 pos, int size) {
        super(pos, 2, 4);
        this.size = size;
        GameLoop.board.preyList.add(this);
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
            GameLoop.board.preyList.remove(this);
            GameLoop.board.setTile(pos, new TileEmpty());
        }
    }
}
