package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;
import java.util.HashMap;

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

    protected ArrayList<Predator> groupMember;
    private ArrayList<SubGroup> subGroups;
    protected int groupRadius;
    protected Prey groupTarget;
    protected Position position;
    protected HashMap<Predator, Boolean> ready = new HashMap<>();
    protected HashMap<Predator, Position> targetPos = new HashMap<>();

    public Position getPosition() {
        return this.position;
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

    /**
     * the function determines in which sector the prey is standing and based on the result delivers one of two hunting
     * approaches.
     *
     * @return returns an integer 1-8 to determine where to chase the animal to depending on it's relativ position.
     *
     * 1: chase to top middle.
     * 2:chase to top-right-corner.
     * 3:chase to right middle.
     * 4:chas eto bottom-left-corner.
     * 5:chase to bottom middle.
     * 6:chase to bottom left corner.
     * 7:chase to left middle.
     * 8:chase to top-left corner.
     */

    //TODO: think about returning an integer designated to chasing the prey a certain way instead of a boolean
    public int getTactic() {


        int tarX = groupTarget.getPos().getX();
        int tarY = groupTarget.getPos().getY();
        updateGrpPos();
        int grpX = this.position.getX();
        int grpY = this.position.getY();
        int distanceX, distanceY, distanceMid = 0, temp1, temp2;

        if (tarY == grpY){
            if(tarY<grpY)return 5;
            else return 1;
        }
        if (tarX == grpX) {
            if(tarX<grpY) return 7;
            else return 3;
        }

        if (tarX > grpX) {//right sight

            if (tarY > grpY) {//upper right sight

                distanceX = tarY - grpY;//distance to the imaginary X-axis
                if (distanceX < 0) {
                    distanceX = distanceX * -1;
                }
                distanceY = tarX - grpX;//distance to the imaginary Y-Axis
                if (distanceY < 0) {
                    distanceY = distanceY * -1;
                }
                temp1 = tarX - grpX;

                grpY = +temp1;
                distanceMid = tarY - grpY; //distance to the imaginary Middle-Axis

                if (distanceMid < 0) {
                    distanceMid = distanceX * -1;
                }
                if (distanceMid <= distanceX) {
                    if (distanceMid <= distanceY) {
                        System.out.println("Chase to corner"); //Debug
                        return 2;

                    }
                }else{
                    if(distanceX<distanceY)return 3;
                }return 1;



            } else {//lower right sight

                distanceX = grpY - tarY;

                distanceY = tarX - grpX;

                temp1 = distanceX + grpX;
                distanceMid = tarX - temp1;

                if (distanceMid < 0) distanceMid = distanceMid * -1;

                if (distanceMid <= distanceX) {
                    if (distanceMid <= distanceY) {
                        System.out.println("Chase to corner"); //Debug
                        return 4;

                    }
                }else{
                    if(distanceX<distanceY)return 3;
                }return 5;



            }
        } else {//left sight

            if (tarY > grpY) {//upper left sight

                distanceY = grpX - grpY;

                distanceX = tarY - grpY;

                temp1 = grpY + distanceY;
                distanceMid = tarY - temp1;
                if (distanceMid < 0) distanceMid = distanceMid * -1;

                if (distanceMid < 0) {
                    distanceMid = distanceX * -1;
                }
                if (distanceMid <= distanceX) {
                    if (distanceMid <= distanceY) {
                        System.out.println("Chase to corner"); //Debug
                        return 8;

                    }
                }else{
                    if(distanceX<distanceY)return 7;
                }return 1;
            } else {//lower left sight

                distanceX = grpY - tarY;

                distanceY = grpX - tarX;

                temp1 = tarY - distanceY;
                distanceMid = temp1 - tarX;
                if (distanceMid < 0) distanceMid = distanceMid * -1;

                if (distanceMid <= distanceX) {
                    if (distanceMid <= distanceY) {
                        System.out.println("Chase to corner"); //Debug
                        return 6;

                    }
                }else{
                    if(distanceX<distanceY)return 7;
                }return 5;
            }
        }

    }


    public void tatic() {
        int groupSize = groupMember.size();


    }

    protected void updateGrpPos() {
        if (groupMember.size() > 0) {
            int x = 0, y = 0, i;
            for (i = 0; i < groupMember.size(); i++) {
                x += groupMember.get(i).getPos().getX();
                y += groupMember.get(i).getPos().getY();
            }
            x /= (i + 1);
            y /= (i + 1);
            this.position = new Position(x, y);
        } else {
            int x = 0, y = 0, i;
            for (i = 0; i < subGroups.size(); i++) {
                x += subGroups.get(i).getPosition().getX();
                y += subGroups.get(i).getPosition().getY();
            }
            x /= (i + 1);
            y /= (i + 1);
        }
    }

    public void update() {
        if (groupMember.size() > 1) {
            updateGrpPos();
            joinPredInRad();
            //TODO:Subgroups @philipp
            //TODO:Upodate tactics getrelativepostion -> Update subgroup target pos @ühilipp

        } else BoardManager.delGrp(this);
        //TODO:Delete Subgroups @ophilipp
    }

    private void joinPredInRad() {

        //TODO:if subgroups -> no @philipp
        ArrayList<Predator> preds = this.position.inSight(false, groupRadius);
        for (Predator x : preds) {
            x.joinGrp(this);
        }
    }

    public void delPred(Predator pred) {
        groupMember.remove(pred);
    }

    private void formSubGroups() {
        //TODO: form Subgroups put Predator to its corresponding subgroup @henry


    }

    public Position getPredPos(Predator predator) {
        int[] a = BoardManager.getSize();
        return Position.ranPos(a[0], a[1]);

        //TODO @Henry  prey.getpos for actual prey position !!! NO PREY Positioninf for the preds in the update method
        //TODO: @henry Grouppredator positioning for the predator X!
        /*

        for pred in member ( index of)

        pred i move to x
         */
    }

    protected void delSubGroup(SubGroup subGroup) {
        this.subGroups.remove(subGroup);
    }
}
