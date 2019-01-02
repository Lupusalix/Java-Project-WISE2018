package Tiles;


public class Animal extends EmptyTile {

    private Position pos;
    private double sight;
    private final double initiative;

    public Animal(Position pos, double sight){
        this.pos=pos;
        this.initiative=Math.random();
        this.sight=sight;
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


    public void act(){

    }
}
