package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;

public class Prey extends Animal {

    private int nutrition;
    private int size;


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
        ArrayList<Predator> predInSight = inSight(false);

        if (this.size > 1 && predInSight.size() > 0) {
            //TODO: Large Prey Attacking Logic
        }

        if (predInSight.size() > 0) {
            Predator targettedby = predInSight.get(0);
            for (int i = 0; i < predInSight.size(); i++) {
                if (targettedby.getPos().getDistance(this.pos) > predInSight.get(i).getPos().getDistance(this.pos))
                    targettedby = predInSight.get(i);
            }
            //get possible position as far away from nearest pred
            ArrayList<Position> surPos = pos.getSurrroundingPositionsPrey();
            if (surPos.size() > 0) {
                Position erg = surPos.get(0);
                for (int i = 0; i < surPos.size(); i++) {
                    try {
                        if (surPos.get(i).getDistance(targettedby.getPos()) > erg.getDistance(targettedby.getPos()))
                            erg = surPos.get(i);
                    } catch (Exception e) {
                        System.out.println(e.getCause());
                    }
                }
                return erg;
            } else return this.pos;
        }
        return super.act();
    }


}
