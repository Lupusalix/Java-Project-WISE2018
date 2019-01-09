package javaproject.tiles;

import java.util.ArrayList;

public class Prey extends Animal {

    private int nutrition;


    public int getNutrition() {
        return this.nutrition;
    }

    public Prey(Position pos, int sight) {
        super(pos, sight);
        this.nutrition = 10;
    }

    @Override
    public Position act() {
        //Check if Predators are in sight
        ArrayList<Predator> predInSight = inSight(false);
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
