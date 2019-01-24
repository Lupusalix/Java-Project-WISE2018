package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;

public class SubGroup extends HuntingGroup {

    private HuntingGroup group;
    private Position subGroupTargetPosition;

    public SubGroup(ArrayList<Predator> member, int radius, Prey target, HuntingGroup group) {
        super(member, radius, target);
        this.group = group;
    }

    @Override
    public Position getPredPos(Predator predator) {
        int[] a = BoardManager.getSize();
        return Position.ranPos(a[0], a[1]);


        //TODO: @Henry Grouppredator positioning for the predator X!
        /*

        for pred in member ( index of)

        pred i move to x
         */
    }


}
