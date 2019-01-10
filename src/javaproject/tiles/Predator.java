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
            //search adjacent position that is the nearest to the target
            if (hasTarget()) {
                ArrayList<Position> surPos = pos.getSurrroundingPositionsPred();
                if (surPos.size() > 0) {
                    Position erg = surPos.get(0);

                    for (int i = 0; i < surPos.size(); i++) {
                        try {
                            if (surPos.get(i).getDistance(target.getPos()) < erg.getDistance(target.getPos()))
                                erg = surPos.get(i);
                        } catch (Exception e) {
                            System.out.println(e.getCause());
                        }
                    }
                    return erg;
                } else return this.pos;

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
            ArrayList<Position> surPos = pos.getSurrroundingPositionsPrey();
            if (surPos.size() > 0) {
                Position erg = surPos.get(0);
                for (int i = 0; i < surPos.size(); i++) {
                    try {
                        if (surPos.get(i).getDistance(atttackedby.getPos()) > erg.getDistance(atttackedby.getPos()))
                            erg = surPos.get(i);
                    } catch (Exception e) {
                        System.out.println(e.getCause());
                    }
                }
                BoardManager.move(erg, this);
            } else BoardManager.move(this.pos, this);

            this.setSpeed(this.getSpeed() - 1);
        } while (this.speed > 1);
        ArrayList<Position> surPos = pos.getSurrroundingPositionsPrey();
        if (surPos.size() > 0) {
            Position erg = surPos.get(0);
            for (int i = 0; i < surPos.size(); i++) {
                try {
                    if (surPos.get(i).getDistance(atttackedby.getPos()) > erg.getDistance(atttackedby.getPos()))
                        erg = surPos.get(i);
                } catch (Exception e) {
                    System.out.println(e.getCause());
                }
            }
            return erg;
        } else return this.pos;

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

    private boolean hasTarget() {
        return this.target != null;
    }
}

