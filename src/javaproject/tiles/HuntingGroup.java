package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;

public class HuntingGroup {
    private ArrayList<Predator> groupMember;
    private int groupRadius;
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

    public HuntingGroup(ArrayList<Predator> member, int radius, Prey target) {
        this.groupMember = member;
        this.groupRadius = radius;
        this.groupTarget = target;
    }

    public Prey getGroupTarget() {
        return groupTarget;
    }

    public void setGroupTarget(Prey groupTarget) {
        this.groupTarget = groupTarget;
    }

    public void setGroupRadius(int groupRadius) {
        this.groupRadius = groupRadius;
    }

    private void updateGrpPos() {
        if (groupMember.size() > 0) {
            int x = 0, y = 0, i;
            for (i = 0; i < groupMember.size(); i++) {
                x += groupMember.get(i).getPos().getX();
                y += groupMember.get(i).getPos().getY();
            }
            x /= (i + 1);
            y /= (i + 1);
            this.position = new Position(x, y);
        }
    }

    public void update() {
        //TODO: Update group members if other pred inside group radius join grp and dissolve group if all members dead/no member
        if (groupMember.size() > 1) {
            updateGrpPos();
            joinPredInRad();

        } else BoardManager.delGrp(this);
    }

    private void joinPredInRad() {
        ArrayList<Predator> preds = this.position.inSight(false, groupRadius);
        for (Predator x : preds) {
            x.joinGrp(this);
        }
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
