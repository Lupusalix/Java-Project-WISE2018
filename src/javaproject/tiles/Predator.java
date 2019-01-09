package javaproject.tiles;

import javaproject.BoardManager;

public class Predator extends Animal {

    private int starvation;
    private int health;
    private double defenceChance;
    private Prey target;
    private HuntingGroup huntingGroup;


    public int getStarvation() {
        return starvation;
    }

    public void setStarvation(int starvation) {
        this.starvation = starvation;
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
        this(pos, sight, 20, 0.7);
    }

    public Predator(int x, int y, int sight) {
        this(new Position(x, y), sight, 20, 0.7);
    }

    public Predator(Position pos, int sight, int health, double defenceChance) {
        super(pos, sight);
        this.starvation = health;
        this.health = health;
        this.defenceChance = defenceChance;
        this.target=null;
    }

    public void kill(Prey an) {

        this.starvation+=an.getNutrition();
        //placeholder
        this.target=null;
        an.killed();
    }


    @Override
    public Position act(){
        if(target == null){
            //search new Target
            searchTarget();
        }else
            if(!target.isAlive()){
                target=null;
                searchTarget();
            }



            if(starvation==health) {
            return pos.getRandMovement();
        }
        return pos.getRandMovement();


    }


    private void searchTarget(){
        int startx,endx,starty,endy;
        Prey nearestPrey=null;

        if(pos.getX()-sight < 0 )startx=0;
        else startx=pos.getX()-sight;
        if(pos.getX()+sight>BoardManager.getBoard().length)endx=BoardManager.getBoard().length-1;
        else endx=pos.getX()+sight;
        if(pos.getY()-sight < 0 )starty=0;
        else starty=pos.getY()-sight;
        if(pos.getY()+sight>BoardManager.getBoard()[0].length)endy=BoardManager.getBoard().length-1;
        else endy=pos.getY()+sight;

        for (int i = startx; i < endx; i++) {
            for (int j = starty; j < endy; j++) {
                if (BoardManager.getBoard()[i][j] instanceof Prey){
                    if(nearestPrey == null)nearestPrey= (Prey) BoardManager.getBoard()[i][j];
                }
            }
        }

    }


   /* @Override
    public Position act() {



/*        for (EmptyTile[] aBoard : BoardManager.getBoard()) {
            for (int j = 0; j < aBoard.length; j++) {
                if (aBoard[j] instanceof Animal) {
                    if (aBoard[j] instanceof Predator) System.out.print("P");
                    else System.out.print("A");
                } else System.out.print("E");

            }
            System.out.print("\n");
        }

        System.out.print("\n");


        return null;
    }
    */

}

