package javaproject.tiles;


import javaproject.BoardManager;

import java.util.ArrayList;

public class Animal extends EmptyTile implements Comparable<Animal> {

    protected Position pos;
    protected int sight;
    protected final double initiative;
    protected int speed;
    protected boolean alive;


    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getInitiative() {
        return this.initiative;
    }

    public Animal(Position pos, int sight) {
        this.pos = pos;
        this.initiative = Math.random();
        this.sight = sight;
        this.alive = true;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public double getSight() {
        return sight;
    }

    public void setSight(int sight) {
        this.sight = sight;
    }


    public Position act() {
        return pos.getRandMovement();
    }

    public void kill(Animal an) {
        an.killed();
    }

    public void killed() {
        this.alive = false;
        BoardManager.delete(this);
    }

    //Returns Arraylist of prey or predators according to boolean
    public ArrayList inSight(boolean isprey) {

        ArrayList erg = new ArrayList();
        //Set Sightrectangle inside the board
        int startx, endx, starty, endy;

        if (pos.getX() - sight < 0) startx = 0;
        else startx = pos.getX() - sight;
        if (pos.getX() + sight > BoardManager.getBoard().length) endx = BoardManager.getBoard().length - 1;
        else endx = pos.getX() + sight;

        if (pos.getY() - sight < 0) starty = 0;
        else starty = pos.getY() - sight;
        if (pos.getY() + sight > BoardManager.getBoard()[0].length) endy = BoardManager.getBoard().length - 1;
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


    //Comparable Method for sorting descending by the initiative.
    @Override
    public int compareTo(Animal o) {
        return Double.compare(o.getInitiative(), this.initiative);
    }
}
