package project.engine.tile;

import javafx.application.Platform;
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
    private int health = 15;
    private int defence;
    private TilePredator me;


    public TilePredator(Point2 pos) {
        super(pos, 2, 7);
        MainLoop.board.predatorList.add(this);
        me = this;
    }

    @Override
    public void turn() {

        if (target != null) {
            if (target.isAlive()) {
                BoardUtil.move(pos, move());
            } else {
                target = null;
            }
        } else {

            TilePrey prey = findTarget();

            if (prey == null) {
                BoardUtil.moveRandom(pos, speed);
            } else {
                target = prey;
                BoardUtil.move(pos, move());
            }
        }

        hunger();
    }

    private void hunger() {
        health--;
        if (health <= 0) {
            alive = false;
        }

    }

    private Point2 move() {

        List<Point2> moveList = MathUtil.reverseBFS(pos, target.getPosition());
        System.out.println(moveList);
        if (moveList == null) {
            System.out.println("move was null");
            return null;
        } else if (moveList.size() < speed * 2) {
            System.out.println("move was target");

            target.setAlive(false);
            target = null;
            health = 100;
            return moveList.get(moveList.size() - 1);

        } else {
            return moveList.get(speed);
        }
    }

    private TilePrey findTarget() {
        ArrayList<Point2> box = MathUtil.gridBox(pos, detectionRadius);

        System.out.println("Finding Target");

        for (Point2 p : box) {
            if (MainLoop.board.getGrid()[p.x][p.y] instanceof TilePrey) {
                TilePrey prey = (TilePrey) MainLoop.board.getGrid()[p.x][p.y];
                if (prey.isAlive()) {
                    return prey;
                }
                return null;
            }
        }

        return null;
    }

}
