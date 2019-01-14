package javaproject.tiles;


import javaproject.BoardManager;

import java.util.ArrayList;

/**
 * This Class is the base.class from which the pred and the animal class derive it is meant to provide basic functions
 * for both of them.
 *
 * @version 1.0.
 * @Author Philipp .
 * @see EmptyTile .
 * @see Position .
 */
public class Animal extends EmptyTile implements Comparable<Animal> {

    /**
     * Initiative: value that helpes decide which animal is to act first.
     * pos the current position on the board.
     * sight: the area of sight.
     * speed: a kind of currency which is used to determine how many actions an animal can take during it's turn.
     * speedMax: the maximum number of actions that an animal can take per turn.
     * alive: a boolean value that is 'true' as long as the animal is alive and turns'false' if it dies.
     */
    protected final double initiative;
    protected Position pos;
    protected int sight;
    protected int speed;
    protected int speedMax;
    protected boolean alive;

    /**
     * Constuructor.
     *
     * @param pos   .
     * @param sight .
     * @see Position
     */

    public Animal(Position pos, int sight) {
        this(pos, sight, 5);
    }

    /**
     * Constructor .
     *
     * @param pos   .
     * @param sight .
     * @param speed .
     * @see Position
     */
    public Animal(Position pos, int sight, int speed) {
        this.pos = pos;
        this.initiative = Math.random();
        this.sight = sight;
        this.alive = true;
        this.speed = speed;
        this.speedMax = this.speed;
    }

    /**
     * check if the animal is alive.
     *
     * @return 'true' if alive, 'false' if dead.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     *
     * @param alive boolean para, that sets the status for alive or dead.
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     *
     * @return current init-value.
     */
    public double getInitiative() {
        return this.initiative;
    }

    /**
     *
     * @return current position
     */
    public Position getPos() {
        return pos;
    }

    /**
     * sets new position.
     * @param pos new position.
     */
    public void setPos(Position pos) {
        this.pos = pos;
    }

    /**
     * returns the sight.
     * @return sight as a double.
     */
    public double getSight() {
        return sight;
    }

    /**
     * sets sight to the param.
     * @param sight new sight.
     */
    public void setSight(int sight) {
        this.sight = sight;
    }

    /**
     * makes the animal move rnd.
     * @return new rnd position.
     */

    public Position act() {
        return pos.getRandMovement();
    }

    /**
     * kills the target animal.
     * @param an target to kill.
     */
    public void kill(Animal an) {
        an.killed();
    }

    /**
     * sets the animal alive value to 'false' and deletes the animal from the board
     * @see BoardManager .
     */
    public void killed() {
        this.alive = false;
        BoardManager.delete(this);
    }

    /**
     * gets the current speed value.
     * @return current speed as an int.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * sets speed to the new param.
     * @param speed new speed value.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * gets the max speed.
     * @return speedMax value.
     */
    public int getSpeedMax() {
        return speedMax;
    }

    //Reformatted Code(getting rid of Code Duplication) for Following or escaping target
    public Position followTarget(Animal target, boolean follow, boolean eatPrey) {
        if (eatPrey) {
            ArrayList<Position> surPos = pos.getSurrroundingPositionsPred();
            return fTUtil(target, follow, surPos);
        } else {
            ArrayList<Position> surPos = pos.getSurrroundingPositionsPrey();
            return fTUtil(target, follow, surPos);
        }
    }

    /**
     * the method returns depending on follow and eat prey a position back that follows a prey oder
     * tries to escape and avoid tiels with prey and pred on it.
     *
     * @param target the actor
     * @param follow
     * @param surPos a list of pred,prey and empty tiles surrounding the actor
     * @return returns the position to which the animal is moving now
     */
    private Position fTUtil(Animal target, boolean follow, ArrayList<Position> surPos) {
        if (surPos.size() > 0) {
            Position erg = surPos.get(0);
            for (int i = 0; i < surPos.size(); i++) {
                if (follow) {
                    if (surPos.get(i).getDistance(target.getPos()) < erg.getDistance(target.getPos()))
                        erg = surPos.get(i);
                } else if (surPos.get(i).getDistance(target.getPos()) > erg.getDistance(target.getPos()))
                    erg = surPos.get(i);
            }
            return erg;
        } else return this.pos;
    }

   /* private Animal searchTarget(boolean prey) {
        ArrayList predInSight = inSight(prey);
        //TODO:Working on Code duplicatiopn
        return Animal;
    }*/

    /**
     * checks according to the sight value of the andimal and
     * returns Arraylist of prey or predators according to boolean.
     * @param isprey .
     * @return and array list of prey or predators.
     */
    public ArrayList inSight(boolean isprey) {
        int[] size = BoardManager.getSize();
        ArrayList erg = new ArrayList();
        //Set Sightrectangle inside the board
        int startx, endx, starty, endy;

        if (pos.getX() - sight < 0) startx = 0;
        else startx = pos.getX() - sight;
        if (pos.getX() + sight > size[0]) endx = size[0];
        else endx = pos.getX() + sight;

        if (pos.getY() - sight < 0) starty = 0;
        else starty = pos.getY() - sight;
        if (pos.getY() + sight > size[1]) endy = size[1];
        else endy = pos.getY() + sight;

        //Search the Sightrectangle
        for (int i = startx; i < endx; i++) {
            for (int j = starty; j < endy; j++) {
                if (isprey) {
                    if (BoardManager.getBoard()[i][j] instanceof Prey) {
                        erg.add(BoardManager.getBoard()[i][j]);
                    }
                } else {
                    if (BoardManager.getBoard()[i][j] instanceof Predator) {
                        erg.add(BoardManager.getBoard()[i][j]);
                    }
                }
            }
        }
        return erg;
    }


    /**
     * Comparable Method for sorting descending by the initiative.
     *
     * @param o the animal to compare to.
     * @return the higher initive.
     * @see Animal .
     */
    @Override
    public int compareTo(Animal o) {
        return Double.compare(o.getInitiative(), this.initiative);
    }
}
