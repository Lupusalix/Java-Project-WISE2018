package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;

public class Prey extends Animal {

    private int nutrition;
    private int size;

    public int getSize() {
        return size;
    }

    public int getNutrition() {
        return this.nutrition;
    }

    public Prey(Position pos, int sight) {
        super(pos, sight);
        this.nutrition = 10;
        if (Math.random() > 0.9) this.size = 3;
        else if (Math.random() > 0.7) this.size = 2;
        else this.size = 1;
        if (this.size > 1) this.nutrition *= this.size;
    }

    public void attack(Predator pred) {
        if (Math.random() > pred.getDefenceChance()) {
            kill(pred);
            BoardManager.statisticsPredatorsKilled(1);
        } else pred.attacked(this);
    }

    @Override
    public Position act() {
        //Check if Predators are in sight


        /*if (this.size > 1 && predInSight.size() > 0) {
            //TODO: Large Prey Attacking Logic
        }*/

        Animal runFrom = getNearest(false);
        if (runFrom != null) {
            return followTarget(runFrom, false, false);
        }
        return super.act();
    }


}
