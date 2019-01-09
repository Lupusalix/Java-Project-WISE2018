package javaproject.tiles;

import javafx.geometry.Pos;

public class Prey extends Animal {

    private int nutrition;


    private boolean targetted;

    public int getNutrition() {
        return this.nutrition;
    }

    public Prey(Position pos, int sight) {
        super(pos, sight);
        this.targetted = false;
        this.nutrition = 10;
    }

    @Override
    public Position act() {


        return super.act();
    }


}
