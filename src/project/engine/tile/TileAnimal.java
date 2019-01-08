package project.engine.tile;

import project.engine.MainLoop;
import project.engine.util.Point2;

public abstract class TileAnimal extends Tile{

    Point2 pos;
    final int speed;
    final int detectionRadius;
    boolean alive = true;


    TileAnimal(Point2 pos, int speed, int detectionRadius){
    this.pos = pos;
    this.speed = speed;
    this.detectionRadius = detectionRadius;
    MainLoop.board.setTile(pos, this);
    }

    public int getSpeed() {
        return speed;
    }

    public int getDetectionRadius() {
        return detectionRadius;
    }

    public void setPosition(Point2 pos){
        this.pos = pos;
    }

    public Point2 getPosition(){
        return pos;
    }

    abstract void turn();


    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }


}
