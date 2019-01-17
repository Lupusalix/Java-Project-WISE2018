package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;

public class HuntingGroup {
    private ArrayList<Predator> groupMember;
    private double groupRadius;
    private Prey groupTarget;
    private Position position;

    public ArrayList<Predator> getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(ArrayList<Predator> groupMember) {
        this.groupMember = groupMember;
    }

    public double getGroupRadius() {
        return groupRadius;
    }

    public void setGroupRadius(double groupRadius) {
        this.groupRadius = groupRadius;
    }

    public Prey getGroupTarget() {
        return groupTarget;
    }

    public void setGroupTarget(Prey groupTarget) {
        this.groupTarget = groupTarget;
    }

    public HuntingGroup(ArrayList<Predator> member, double radius, Prey target) {
        this.groupMember = member;
        this.groupRadius = radius;
        this.groupTarget = target;
    }

    private void updateGrpPos() {
        //TODO: update the group postion
    }

    public void update() {
        //TODO: Update group members if other pred inside group radius join grp
    }


    public Position getPredPos(Predator predator) {
        int[] a = BoardManager.getSize();
        return Position.ranPos(a[0], a[1]);


        //TODO:Grouppredator positioning for the predator X!
        /*

        for pred in member ( index of)

        pred i move to x
         */
    }
}
