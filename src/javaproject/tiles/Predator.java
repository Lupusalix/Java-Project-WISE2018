package javaproject.tiles;

import javafx.geometry.Pos;
import javaproject.BoardManager;

import java.util.ArrayList;

public class Predator extends Animal {

    private int starvation;
    private int health;
    private double defenceChance;
    private Prey target;
    private HuntingGroup huntingGroup;
    private boolean attacked;


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

    public Predator(Position pos, int sight) {
        this(pos, sight, 20, 0.7);
    }

    public Predator(int x, int y, int sight) {
        this(new Position(x, y), sight, 20, 0.7);
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
        //placeholder
        this.target = null;
        an.killed();
    }


    @Override
    public Position act() {
        if (attacked) return escape();

        if (starvation == health) {
            return pos.getRandMovement();
        }

        //if no target search one
        if (target == null) {
            //search new Target
            searchTarget();
        } else if (!target.isAlive()) { //if target isn't alive search one
            target = null;
            searchTarget();
        }
        if (huntingGroup == null) {
            //search adjacent position that is the nearest to the target
            ArrayList<Position> surPos = pos.getSurrroundingPositions();
            Position erg;
            for (int i = 0; i < surPos.size(); i++) {
                if (i == 0) erg = surPos.get(i);
                if (surPos.get(i).getDistance(target.getPos()) < erg.getDistance(target.getPos())) erg = surPos.get(i);
            }
            return erg;
        } else {
            //TODO:Logic when in Group
        }

        return pos.getRandMovement();
    }

    private Position escape() {
        //TODO: escaping algorithm
        return null;
    }

    //sets the target of predator to the nearest Prey
    private void searchTarget() {
        ArrayList<Prey> targets = this.inSight(true);

        for (int i = 0; i < targets.size(); i++) {
            if (i == 0) this.target = targets.get(i);
            else {
                if (targets.get(i).pos.getDistance(this.pos) < target.pos.getDistance(this.pos)) {
                    this.target = targets.get(i);
                }
            }
        }

    }
}

