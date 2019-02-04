package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;

/**
 * @author Philipp.
 * @author Henry (GUI-additions).
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
    private int grpRad;

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


    public Predator(Position pos, int sight, int speed, int GrpRad, int predStarve) {
        this(pos, sight, speed, predStarve, 0.8, GrpRad);
    }


    public Predator(Position pos, int sight,int speed, int health, double defenceChance, int grpTad) {
        super(pos, sight,speed);
        this.starvation = health;
        this.health = health;
        this.defenceChance = defenceChance;
        this.target = null;
        this.attacked = false;
        this.huntingGroup = null;
        this.grpRad=grpRad;
//        this.speed =speed;
//        this.speedMax = this.speed;
    }

    /**
     * This method feeds the predator with the given nutrition .
     * @param nutrition the nutrition to feed the predator
     */

    public void eat(int nutrition) {
        this.starvation += nutrition;
        BoardManager.statisticsNutritionIntake(nutrition);
    }

    /**
     * this method kills the prey and calls the feeding method of the predator .
     * @param an the prey to kill .
     */
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

    /**
     * This method kills the predator and calls the according statistic methods from the Boardmanager
     */
    @Override
    public void killed() {
        this.alive = false;
        if (huntingGroup != null) {
            this.huntingGroup.delPred(this);
        }
        BoardManager.delete(this);
    }

    /**
     * This method returns the position the predater is moving to.
     * It asseces the state of the predator and chooses what is best for him.
     */
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
            return soloMove();
        } else {
            //Group Block#
            if (!this.huntingGroup.isGrpFull()) {
                return soloMove();
            } else {
                if (huntingGroup.isRdy()) {
                    return followTarget(this.huntingGroup.getGroupTarget().getPos(), true, true);
                } else {
                    Position grpWPos = this.huntingGroup.getPredPos(this);
                    if (grpWPos == null) {
                        return soloMove();
                    } else if (huntingGroup.getGroupTarget().getSight() > this.pos.getDistance(huntingGroup.getGroupTarget().getPos())) {
                        return followTarget(huntingGroup.getGroupTarget().getPos(), false, true);
                    } else
                        return followTarget(grpWPos, this.huntingGroup.getGroupTarget().getPos(), true, huntingGroup.getGroupTarget().getSight());
                }
            }
        }
    }

    /**
     * This method executes the solo movement of a predator
     * @return the new position of the predator
     */
    private Position soloMove() {
        if (starvation == health) {
            return pos.getRandMovement();
        }

        if (hasTarget()) {
            return followTarget(this.target.getPos(), true, true);
        } else return this.pos.getRandMovement();
    }

    /**
     * Overridden get Nearest from animal, since the predator needs to howl if a large prey is inside its sight radius.
     * @param prey boolean if to search for prey or predators
     * @return the nearest prey or predator according to the prey parameter
     * @see Animal getNearest .
     */

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
     * The howl method calls for near Predator to form a group.
     * @param gTarget The target that the formed group will have.
     * @see BoardManager buildGroup .
     */
    private void howl(Prey gTarget) {
        if (this.huntingGroup == null) {
            BoardManager.buildGroup(this, gTarget,grpRad);
        }
    }

    /**
     * This method is called when an predator is attacked by a big prey.
     * It is checking if the predator already has been attacked and if so it is killed.
     * If it is the first time being attacked it evades to a surrounding cell and its starvation is halved.
     * If it can not evade to another cell it is considered killed.
     * @param an the prey attacking the predator .
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
     * The escape method moves the predator away from its attacker.
     * @return the new position of the animal after it was attacked .
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

    /**
     * This method checks if the predator has a target.
     *
     * @return true if it has a target.
     */
    private boolean hasTarget() {
        return (this.target != null);
    }

    /**
     * This method joins the predator to a group.
     * @param x the group the predator joins .
     */
    public void joinGrp(HuntingGroup x) {
        this.huntingGroup = x;
    }
}