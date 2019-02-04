package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;

/**
 * @author Philipp.
 * @author Henry (GUI addition).
 * @version 1.0.
 * @see Animal .
 * @see Predator .
 * <p>
 * The Class prey which derives from Animal is the lass meant as the second actor besides the predators, it contains
 * functions that enable them to act on our board.
 */
public class Prey extends Animal {

    /**
     * nutrition: the nutrition gained from eating the prey.
     * size:the size of the prey
     */
    private int nutrition;
    private int size;

    public int getSize() {
        return size;
    }

    /**
     * @return the current nutrition of the prey.
     */
    public int getNutrition() {
        return this.nutrition;
    }

    /**
     * constructor.
     *
     * @param pos   position of the animal to spawn.
     * @param sight sight of the animal to spawn.
     * @param speed the movement points of the prey
     */
    public Prey(Position pos, int sight, int speed) {
        super(pos, sight);
        this.speed=speed;
        this.nutrition = 10;
        if (Math.random() > 0.95) this.size = 3;
        else if (Math.random() > 0.85) this.size = 2;
        else this.size = 1;
        if (this.size > 1) this.nutrition *= this.size;
    }

    /**
     * method emant for attacking predators.
     *
     * @param pred the oredator to attack.
     */
    public void attack(Predator pred) {
        if (Math.random() > pred.getDefenceChance()) {
            kill(pred);
            BoardManager.statisticsPredatorsKilled(1);
        } else pred.attacked(this);
    }

    /**
     * Method to determine how the prey is going to act, if its trying to flee or attack the pred.
     *
     * @return the Position of the prey after it acted.
     */
    @Override
    public Position act() {
        //Check if Predators are in sight
        ArrayList preds = inSight(false);

        if (this.size > 1 && preds.size() > 0) {
            if (preds.size() < this.size) {
                return followTarget(getNearest(false).getPos(), true, false); //if Predators in Sight able to attack attack
            }
        }
        Animal runFrom = getNearest(false);
        if (runFrom != null) {
            return followTarget(runFrom.getPos(), false, false);
        }
        return super.act();
    }

}