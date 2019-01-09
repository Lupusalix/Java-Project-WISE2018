package javaproject.tiles;

import javafx.geometry.Pos;

public class Prey extends Animal {

    private int nutrition;


    private boolean targetted=false;

    public Prey(Position pos, int sight) {
        super(pos, sight);
        this.nutrition= 10;
    }

    @Override
    public Position act() {
        return super.act();
    }

    @Override
    public void kill(Animal an){
        an.killed();
    }

    public int getNutrition() {
        return this.nutrition;
    }
}
