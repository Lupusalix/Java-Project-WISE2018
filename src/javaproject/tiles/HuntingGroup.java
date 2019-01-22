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

    /**
     * the function determines in which sector the prey is standing and based on the result delivers one of two hunting
     * approaches.
     *
     * @return 'true' if the preds should chase the target to a corner or 'false' if they should chase it to a wall.
     */

    //public boolean getTactic() {
    //   int tarX = groupTarget.getPos().getX();
    //   int tarY = groupTarget.getPos().getY();
    //   Position middle = grpMid();
    //   int grpX = middle.getX();
    //   int grpY = middle.getY();
    //   int distanceX,distanceY,distanceMid=0,temp1,temp2;
    //
    //   if (tarX > grpX) {//right sight
    //
    //       if (tarY > grpY) {//upper right sight
    //           distanceX = tarY-grpY;//distance to the imaginary X-axis
    //           if(distanceX<0){
    //               distanceX=distanceX*-1;
    //           }
    //           distanceY = tarX-grpX;//distance to the imaginary Y-Axis
    //           if(distanceY<0){
    //               distanceY=distanceX*-1;
    //           }
    //           temp1=grpX-tarX;
    //           temp2=grpX-tarX;
    //
    //           if(temp1<temp2){
    //               grpX=+temp1;
    //               grpY=+temp1;
    //               distanceMid=tarX-grpX; //distance to the imaginary Middle-Axis
    //           }else{
    //
    //               if (temp2<temp1) {
    //                   grpX = +temp2;
    //                   grpY = +temp2;
    //                    distanceMid = tarX - grpX; //distance to the imaginary Middle-Axis
    //                }
    //           }
    //           if(distanceMid<=distanceX){
    //               if(distanceMid<=distanceY){
    //                   System.out.println("Chase to corner"); //Debug
    //                   return true;
    //
    //               }
    //           }
    //           System.out.println("Chase to wall"); //Debug
    //           return false;
    //
    //
    //       } else {//lower right sight
    //
    //       }
    //   } else {//left sight
    //       if (tarY > grpY) {//upper left sight
    //
    //       } else {//lower left sight
    //       }
    //   }
    //
    public void tatic() {
        int groupSize = groupMember.size();


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
