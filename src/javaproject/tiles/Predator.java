package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;

public class Predator extends Animal {

    private int starvation;
    private int health;
    private double defenceChance;
    private Prey target;
    private HuntingGroup huntingGroup;
    private boolean attacked;
    private Prey atttackedby;


    public int getStarvation() {
        return starvation;
    }

    public void starve() {
        this.starvation -= 1;
        if (this.starvation == 0) {
            this.killed();
        }
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

    public Predator(Position pos, int sight) {
        this(pos, sight, 250, 0.7);
    }

    public Predator(int x, int y, int sight) {
        this(new Position(x, y), sight, 250, 0.7);
    }

    public Predator(Position pos, int sight, int health, double defenceChance) {
        super(pos, sight);
        this.starvation = health;
        this.health = health;
        this.defenceChance = defenceChance;
        this.target = null;
        this.attacked = false;
        this.huntingGroup = null;
        this.speed -= 2;
        this.speedMax = this.speed;
    }

    public void kill(Prey an) {

        this.starvation += an.getNutrition();
        BoardManager.statisticsNutritionIntake(an.getNutrition());
        BoardManager.statisiticsPreysKilled(1);
        this.target = null;
        an.killed();
    }


    @Override
    public Position act() {
        if (attacked) {
            escape();
        }
        //if no target search one
        if (!hasTarget()) {
            //search new Target
            this.target = (Prey) getNearest(true);
            //searchTarget();
        } else if (!target.isAlive()) { //if target isn't alive search one
            target = (Prey) getNearest(true);
        }
        if (huntingGroup == null) {

            if (starvation == health) {
                return pos.getRandMovement();
            }

            if (hasTarget()) {
                return followTarget(this.target, true, true);
            } else return this.pos.getRandMovement();
        } else {
            //TODO:Logic when in Group
        }
        return pos.getRandMovement();
    }

    public void attacked(Prey an) {
        if (this.attacked) this.killed();
        this.attacked = true;
        this.atttackedby = an;
        if (this.pos.getSurrroundingPositionsPrey().size() > 1) {
            BoardManager.move((Position) this.pos.getSurrroundingPositionsPrey().get(0), this);
        } else this.starvation /= 2;

    }

    private Position escape() {
        do {
            BoardManager.move(followTarget(atttackedby, false, false), this);
            this.setSpeed(this.getSpeed() - 1);
        } while (this.speed > 1);
        Animal at = this.atttackedby;
        this.atttackedby = null;
        this.attacked = false;
        return followTarget(at, false, false);
    }

    private boolean hasTarget() {
        return (this.target != null);
    }

}