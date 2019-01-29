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
     * checks the current starvationlevel and deletes the predator if it starved.
     */

    public void starve() {
        this.starvation -= 1;
        if (this.starvation == 0) {
            this.killed();
        }
    }

    /**
     * @return the defence value.
     */
    public double getDefenceChance() {
        return defenceChance;
    }

    /**
     * @param pos   .
     * @param sight .
     */
    public Predator(Position pos, int sight) {
        this(pos, sight, 250, 0.7);
    }

    /**
     * @param pos           .
     * @param sight         .
     * @param health        .
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


    public void eat(int nutrition) {
        this.starvation += nutrition;
        BoardManager.statisticsNutritionIntake(nutrition);
    }

    public void kill(Prey an) {
        if (this.huntingGroup == null) {
            this.eat(an.getNutrition());

        } else {
            this.huntingGroup.eat(an.getNutrition());
            this.huntingGroup.delete();
        }
        BoardManager.statisiticsPreysKilled(1);
        this.target = null;
        an.killed();
    }

    @Override
    public void killed() {
        this.alive = false;
        if (huntingGroup != null) this.huntingGroup.delPred(this);
        BoardManager.delete(this);
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
        if (attacked) {
            escape();
        }
        //if no target search one
        if (!hasTarget()) {
            //search new Target
            this.target = getNearest(true);
            //searchTarget();
        } else if (!target.isAlive()) { //if target isn't alive search one
            target = getNearest(true);
        }
        if (huntingGroup == null) {

            if (starvation == health) {
                return pos.getRandMovement();
            }

            if (hasTarget()) {
                return followTarget(this.target.getPos(), true, true);
            } else return this.pos.getRandMovement();
        } else {
            //Group Block
            if (huntingGroup.isRdy()) {
                return followTarget(this.huntingGroup.getGroupTarget().getPos(), true, true);
            } else {
                Position grpWPos = this.huntingGroup.getPredPos(this);
                return followTarget(grpWPos, true, huntingGroup.getGroupTarget().getSight());
            }
        }
    }


    @Override
    protected Prey getNearest(boolean prey) {
        ArrayList<Prey> targets = inSight(prey);
        if (targets.size() > 0) {
            Prey erg = targets.get(0);
            if (erg.getSize() > 1) howl(erg);
            for (int i = 0; i < targets.size(); i++) {
                if (erg.getSize() > 1) howl(erg);
                if (targets.get(i).pos.getDistance(this.pos) < erg.pos.getDistance(this.pos)) {
                    erg = targets.get(i);
                }
            }
            return erg;
        }
        return null;
    }


    public HuntingGroup getHuntingGroup() {
        return huntingGroup;
    }

    public void setHuntingGroup(HuntingGroup huntingGroup) {
        this.huntingGroup = huntingGroup;
    }

    /**
     * @param gTarget
     * @see BoardManager buildGroup
     */
    private void howl(Prey gTarget) {
        if (this.huntingGroup == null) {
            BoardManager.buildGroup(this, gTarget);
        }
    }

    public void attacked(Prey an) {
        if (this.attacked) this.killed();
        this.attacked = true;
        this.atttackedby = an;
        if (this.pos.getSurrroundingPositionsPrey().size() > 1) {
            BoardManager.move((Position) this.pos.getSurrroundingPositionsPrey().get(0), this);
        } else this.starvation /= 2;

    }

    /**
     * @return the new position of the animal after it was attacked
     * @see Animal .
     */
    private Position escape() {
        do {
            BoardManager.move(followTarget(atttackedby.getPos(), false, false), this);
            this.setSpeed(this.getSpeed() - 1);
        } while (this.speed > 1);
        Animal at = this.atttackedby;
        this.atttackedby = null;
        this.attacked = false;
        return followTarget(at.getPos(), false, false);
    }

    private boolean hasTarget() {
        return (this.target != null);
    }


    public void joinGrp(HuntingGroup x) {
        this.huntingGroup = x;
    }
}