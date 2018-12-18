public abstract class Animal {

    private Position pos;
    private double sight;
    private Animal nearestEnemy;

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


    public Animal(Position pos, double sight) {
        this.pos = pos;
        this.sight = sight;
    }

    public abstract moveTo(Position p);

}
