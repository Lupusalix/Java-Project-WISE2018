package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Philipp.
 * @Author Henry.
 * <p>
 * This Class contains the logic for finding, building and hunting in and with groups.
 */
public class HuntingGroup {

    /**
     * groupMember: An Arraylist containing all the members of the Huntinggroup.
     * groupRadius: the arrea in which the gorup has vision.
     * groupTarget: the (large) prey the group is currently hunting.
     * position:
     * ready: an Hashmap indicating for every predator if it arrived on its targetposition and if it is ready to attack.
     * targetPos: an Hashmap that contains the target position for every Predator.
     */

    private ArrayList<Predator> groupMember;
    private double groupRadius;
    private Prey groupTarget;
    private Position position;
    private HashMap <Predator, Boolean> ready = new HashMap <>();
    private HashMap <Predator, Position> targetPos = new HashMap <>();


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

    /**
     * determines the middle of the  group.
     *
     * @return the Position of the middle.
     */

    public Position grpMid() {
        int x = 0;
        int y = 0;

        for (int i = 1; i < groupMember.size(); i++) {

            x = +groupMember.get(i).getPos().getX();
            y = +groupMember.get(i).getPos().getY();
        }
        x = x / groupMember.size();
        y = y / groupMember.size();
        Position middle = new Position(x, y);
        return middle;

    }

    public void tatic() {
        int groupSize = groupMember.size();
        int tarX = groupTarget.getPos().getX();
        int tarY = groupTarget.getPos().getY();
        Position middle = grpMid();
        int grpX = middle.getX();
        int grpY = middle.getY();


        //determine the huning approach


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
