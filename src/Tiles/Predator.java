package Tiles;

public class Predator extends Animal {

    private int starvation;
    private int health;
    private double defenceChance;
    private HuntingGroup huntingGroup;

    public int getStarvation() {
        return starvation;
    }

    public void setStarvation(int starvation) {
        this.starvation = starvation;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getDefenceChance() {
        return defenceChance;
    }

    public void setDefenceChance(double defenceChance) {
        this.defenceChance = defenceChance;
    }

    public Predator(Position pos, double sight) {
        this(pos, sight, 20, 0.7);
    }

    public Predator(int x, int y, double sight) {
        this(new Position(x, y), sight, 20, 0.7);
    }

    public Predator(Position pos, double sight, int health, double defenceChance) {
        super(pos, sight);
        this.starvation = health;
        this.health = health;
        this.defenceChance = defenceChance;
    }

    @Override
    public void act() {

    }


}
