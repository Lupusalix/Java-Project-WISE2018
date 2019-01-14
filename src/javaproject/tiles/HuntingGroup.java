package javaproject.tiles;

import java.util.ArrayList;

public class HuntingGroup {
    private ArrayList<Predator> groupMember;
    private double groupRadius;
    private Prey groupTarget;

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

}
