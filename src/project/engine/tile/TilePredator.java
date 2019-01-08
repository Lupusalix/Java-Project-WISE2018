package project.engine.tile;

import project.engine.HuntingGroup;
import project.engine.MainLoop;
import project.engine.util.BoardUtil;
import project.engine.util.MathUtil;
import project.engine.util.Point2;

import java.util.ArrayList;
import java.util.List;

public class TilePredator extends TileAnimal {

    private HuntingGroup huntingGroup;
    private TilePrey target = null;
    private int health;
    private int defence;


    public TilePredator(Point2 pos) {
        super(pos, 2, 5);
        MainLoop.board.predatorList.add(this);
    }

    @Override
    public void turn() {

        if(target != null){
            BoardUtil.move(pos, move());
        }else{

            TilePrey prey = findTarget();

            if(prey == null){
                BoardUtil.moveRandom(pos, speed);
            }else{
                target = prey;
                BoardUtil.move(pos, move());
            }
        }
    }

    private void hunger(){
        health--;
        if(health <= 0){
            //TODO: KILL
        }
    }

    private Point2 move(){

        List<Point2> moveList = MathUtil.reverseBFS(pos, target.getPosition());
        System.out.println(moveList);
        if(moveList == null || speed-1 > moveList.size()){
            System.out.println("move was null");
            return null;
        }else{
            System.out.println("move was found");
            return moveList.get(speed-1);
        }
    }

    private TilePrey findTarget(){
        ArrayList<Point2> box = MathUtil.gridBox(pos, detectionRadius);

        System.out.println("Finding Target");

        for(Point2 p: box){
            if(MainLoop.board.getGrid()[p.x][p.y] instanceof TilePrey){
                TilePrey prey = (TilePrey) MainLoop.board.getGrid()[p.x][p.y];
                if(prey.isAlive()){
                    return prey;
                }
                return null;
            }
        }

        return null;
    }

}
