package javaproject.tiles;


import javaproject.BoardManager;

public class Animal extends EmptyTile implements Comparable<Animal> {

    protected Position pos;
    protected int sight;
    protected final double initiative;
    protected int speed;
    protected boolean alive;


    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getInitiative() {
        return this.initiative;
    }

    public Animal(Position pos, int sight) {
        this.pos = pos;
        this.initiative = Math.random();
        this.sight = sight;
        this.alive = true;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public double getSight() {
        return sight;
    }

    public void setSight(int sight) {
        this.sight = sight;
    }


    public Position act() {
        return pos.getRandMovement();
    }

    public void killed() {
        this.alive = false;
        BoardManager.delete(this);
    }


    public void kill(Animal an) {}

    //Comparable Method for sorting descending by the initiative.
    @Override
    public int compareTo(Animal o) {
        return Double.compare(o.getInitiative(), this.initiative);
    }
}
