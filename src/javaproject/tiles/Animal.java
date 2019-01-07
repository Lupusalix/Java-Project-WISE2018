package javaproject.tiles;


public class Animal extends EmptyTile implements Comparable<Animal> {

    private Position pos;
    private double sight;
    private final double initiative;
    private int speed;
    private boolean alive;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getInitiative() {
        return this.initiative;
    }

    public Animal(Position pos, double sight) {
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

    public void setSight(double sight) {
        this.sight = sight;
    }


    public Position act() {
        return pos.getRandMovement();
    }


    //Comparable Method for sorting descending by the initiative.
    @Override
    public int compareTo(Animal o) {
        return Double.compare(o.getInitiative(), this.initiative);
    }
}
