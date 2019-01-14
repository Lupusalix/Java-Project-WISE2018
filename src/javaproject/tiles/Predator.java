package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;

/**
 * @author Philipp.
 * @version 1.0.
 * @see Animal .
 * @see Prey .
 */
public class Predator extends Animal {

    /**
     * starvation: meant to measure the starvation count of the pred.
     * health: meant to represent how physcly healthy an animal is, not to confuse with starvation.
     * defenceChance: chance to defend against the attack of another animal.
     * target: the Prey that the pred is trying to eat at the moment.
     * huntingGroup: the hunting group that the pred belongs to.
     * attacked: a boolean value that determines of the animal is infight or not (?).
     * attackedby: the prey that is currently attacking the pred.
     */
    private int starvation;
    private int health;
    private double defenceChance;
    private Prey target;
    private HuntingGroup huntingGroup;
    private boolean attacked;
    private Prey atttackedby;

    /**
     * @return current starvation as an int.
     */
    public int getStarvation() {
        return starvation;
    }

    /**
     * checks the current starvationlevel and deletes the predator if it starved.
     */

    public void starve() {
        this.starvation -= 1;
        if (this.starvation == 0) {
            this.killed();
        }
    }

    /**
     * @return current health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param health the new health we intend to set the predator to.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     *
     * @return the defence value.
     */
    public double getDefenceChance() {
        return defenceChance;
    }

    /**
     *
     * @param defenceChance sets the ne defchance.
     */
    public void setDefenceChance(double defenceChance) {
        this.defenceChance = defenceChance;
    }

    /**
     *
     * @param pos .
     * @param sight .
     */
    public Predator(Position pos, int sight) {
        this(pos, sight, 250, 0.7);
    }

    /**
     *
     * @param x .
     * @param y .
     * @param sight .
     */
    public Predator(int x, int y, int sight) {
        this(new Position(x, y), sight, 250, 0.7);
    }

    /**
     *
     * @param pos .
     * @param sight .
     * @param health .
     * @param defenceChance .
     */
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

    /**
     * This function is used to kill prey, extracted the nutration, clear the pred target
     * and call the preys .killed function
     * @see Prey .
     * @param an the prey that is killed.
     */
    public void kill(Prey an) {

        this.starvation += an.getNutrition();
        BoardManager.statisticsNutritionIntake(an.getNutrition());
        BoardManager.statisiticsPreysKilled(1);
        this.target = null;
        an.killed();
    }

    /**
     * The function is meant to help the pred decide what to do next. it generates a random movement if its health
     * is equal to its starvation. if it doesn't has a target its starting to look for a new one or helpes it look for
     * .an hunting group
     *
     * @return Position the position the pred intents to go to next.
     */
    @Override
    public Position act() {
        boolean foundTarget = false;
        if (attacked) {
            escape();
        }

        if (starvation == health) {
            return pos.getRandMovement();
        }
        //if no target search one
        if (!hasTarget()) {
            //search new Target
            searchTarget();
        } else if (!target.isAlive()) { //if target isn't alive search one
            target = null;
            searchTarget();
        }
        if (huntingGroup == null) {
            if (hasTarget()) {
                return followTarget(this.target, true, true);
            } else return this.pos.getRandMovement();
        } else {
            //TODO:Logic when in Group
        }

        return pos.getRandMovement();
    }

    /**
     *
     * @param an
     */

    public void attacked(Prey an) {
        if (this.attacked) this.killed();
        this.attacked = true;
        this.atttackedby = an;
        if (this.pos.getSurrroundingPositionsPrey().size() > 1) {
            BoardManager.move((Position) this.pos.getSurrroundingPositionsPrey().get(0), this);
        } else this.starvation /= 2;

    }

    /**
     *
     * @return the new position of the animal after it was attacked
     * @see Animal .
     */
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

    /**
     *
     * @return 'true' if the pred has a target and 'false' if not
     */
    private boolean hasTarget() {
        return (this.target != null);
    }
}

