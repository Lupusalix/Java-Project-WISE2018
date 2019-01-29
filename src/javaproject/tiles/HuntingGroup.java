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
    protected HashMap<Predator, Boolean> ready = new HashMap<>();
    protected int groupRadius;
    protected Prey groupTarget;
    protected Position position;
    protected HashMap<Predator, SubGroup> allocatedSubgroup = new HashMap<>();
    private ArrayList<SubGroup> subGroups;
    protected boolean attack = false;

    public Position getPosition() {
        return this.position;
    }

    public HuntingGroup(ArrayList<Predator> member, int radius, Prey target) {
        this.groupMember = member;
        this.groupRadius = radius;
        this.groupTarget = target;
    }

    public HashMap<Predator, SubGroup> getAllocatedSubgroup() {
        return allocatedSubgroup;
    }


    public HashMap<Predator, Boolean> getReady() {
        return ready;
    }

    public ArrayList<Predator> getGroupMember() {
        return groupMember;
    }

    public Prey getGroupTarget() {
        return groupTarget;
    }

    public void setGroupTarget(Prey groupTarget) {
        this.groupTarget = groupTarget;
    }

    public int getGroupRadius() {
        return groupRadius;
    }

    /**
     * the function determines in which sector the prey is standing and based on the result delivers one of two hunting
     * approaches.
     *
     * @return returns an integer 1-8 to determine where to chase the animal to depending on it's relativ position.
     * <p>
     * 1:chase to top middle.
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

        if (tarY == grpY) {
            if (tarY < grpY) return 5;
            else return 1;
        }
        if (tarX == grpX) {
            if (tarX < grpY) return 7;
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
                } else {
                    if (distanceX < distanceY) return 3;
                }
                return 1;


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
                } else {
                    if (distanceX < distanceY) return 3;
                }
                return 5;


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
                } else {
                    if (distanceX < distanceY) return 7;
                }
                return 1;
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
                } else {
                    if (distanceX < distanceY) return 7;
                }
                return 5;
            }
        }

    }

    public void neededSubgroups() {
        int i = getTactic();
        if ((i % 2) == 0) {
            formSubGroups(2, i);
        } else formSubGroups(3, i);
    }

    public void updateGrpPos() {
        if (groupMember.size() > 0) {
            int x = 0, y = 0, i;
            for (i = 0; i < groupMember.size(); i++) {
                x += groupMember.get(i).getPos().getX();
                y += groupMember.get(i).getPos().getY();
            }
            x /= (i + 1);
            y /= (i + 1);
            this.position = new Position(x, y);
        } else if (subGroups != null && this instanceof HuntingGroup) {
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
        checkTarget();
        if (groupMember.size() > 0 && subGroups == null) {
            for (int i = 0; i < groupMember.size(); i++) {
                if (!groupMember.get(i).isAlive()) {
                    this.delPred(groupMember.get(i));
                }
            }
            joinPredInRad();
            updateGrpPos();
        } else {
            if (subGroups != null) {
                for (int i = 0; i < this.subGroups.size(); i++) {
                    subGroups.get(i).update();
                }
                updateGrpPos();
            } else BoardManager.delGrp(this);
        }

        if (this.subGroups == null) {
            this.subGroups = new ArrayList<SubGroup>();
            neededSubgroups();
            for (int i = 0; i < subGroups.size(); i++) {
                subGroups.get(i).update();
            }
        }
    }

    protected void checkTarget() {
        if (!groupTarget.isAlive()) {
            this.delete();
        }
    }

    private void joinPredInRad() {

        //TODO:if subgroups -> no @philipp
        ArrayList<Predator> preds = this.position.inSight(false, groupRadius);
        if (preds.size() > 0) {
            for (Predator x : preds) {
                x.joinGrp(this);
            }
        }

    }


    public void delPred(Predator pred) {
        groupMember.remove(pred);
        //TODO: Del from SG
    }

    private void formSubGroups(int numberOfSubgroupsToForm, int relativePreyPos) {
        this.subGroups = new ArrayList<SubGroup>();
        //TODO: form Subgroups put Predator to its corresponding subgroup @henry

        //those contain the targetPositions starting from the left most positiobn moving
        //left->bottom->right->top
        Position tarOne;
        Position tarTwo;
        Position tarThree;
        ArrayList<Position> targetPositions = new ArrayList<>();
        ArrayList<Predator> subGroupmembers = new ArrayList<>();
        int side =0; //0: left,1:bottom,2:right,3:top

        switch (relativePreyPos) {
            case 1: //top middle
                tarOne = new Position(groupTarget.getPos().getX() - groupTarget.getSight() - 1, groupTarget.getPos().getY());//links
                targetPositions.add(tarOne);
                tarTwo = new Position(groupTarget.getPos().getX(), groupTarget.getPos().getY() - groupTarget.getSight() - 1);//unten
                targetPositions.add(tarTwo);
                tarThree = new Position(groupTarget.getPos().getX() + groupTarget.getSight() + 1, groupTarget.getPos().getY());//rechts
                targetPositions.add(tarThree);
                side=0;
                break;
            case 2://top right corner
                tarOne = new Position(groupTarget.getPos().getX() - groupTarget.getSight() - 1, groupTarget.getPos().getY());//links
                targetPositions.add(tarOne);
                tarTwo = new Position(groupTarget.getPos().getX(), groupTarget.getPos().getY() - groupTarget.getSight() - 1);//unten
                targetPositions.add(tarTwo);
                side=0;
                break;
            case 3://right middle
                tarOne = new Position(groupTarget.getPos().getX() - groupTarget.getSight() - 1, groupTarget.getPos().getY());//links
                targetPositions.add(tarOne);
                tarTwo = new Position(groupTarget.getPos().getX(), groupTarget.getPos().getY() - groupTarget.getSight() - 1);//unten
                targetPositions.add(tarTwo);
                tarThree = new Position(groupTarget.getPos().getX(), groupTarget.getPos().getY() - groupTarget.getSight() - 1);//oben
                targetPositions.add(tarThree);
                side=0;
                break;
            case 4: //right bottom corner
                tarOne = new Position(groupTarget.getPos().getX() - groupTarget.getSight() - 1, groupTarget.getPos().getY());//links
                targetPositions.add(tarOne);
                tarTwo = new Position(groupTarget.getPos().getX(), groupTarget.getPos().getY() - groupTarget.getSight() - 1);//oben
                targetPositions.add(tarTwo);
                side = 0;
                break;
            case 5: //Bottom middle
                tarOne = new Position(groupTarget.getPos().getX() - groupTarget.getSight() - 1, groupTarget.getPos().getY());//links
                targetPositions.add(tarOne);
                tarTwo = new Position(groupTarget.getPos().getX() + groupTarget.getSight() + 1, groupTarget.getPos().getY());//rechts
                targetPositions.add(tarTwo);
                tarThree = new Position(groupTarget.getPos().getX(), groupTarget.getPos().getY() - groupTarget.getSight() - 1);//oben
                targetPositions.add(tarThree);
                side = 0;
                break;
            case 6: //bottom left corner
                tarOne = new Position(groupTarget.getPos().getX() + groupTarget.getSight() + 1, groupTarget.getPos().getY());//rechts
                targetPositions.add(tarOne);
                tarTwo = new Position(groupTarget.getPos().getX(), groupTarget.getPos().getY() - groupTarget.getSight() - 1);//oben
                targetPositions.add(tarTwo);
                side = 2;
                break;
            case 7://left middle
                tarOne = new Position(groupTarget.getPos().getX(), groupTarget.getPos().getY() - groupTarget.getSight() - 1);//unten
                targetPositions.add(tarOne);
                tarTwo = new Position(groupTarget.getPos().getX() + groupTarget.getSight() + 1, groupTarget.getPos().getY());//rechts
                targetPositions.add(tarTwo);
                tarThree = new Position(groupTarget.getPos().getX(), groupTarget.getPos().getY() - groupTarget.getSight() - 1);//oben
                targetPositions.add(tarThree);
                side = 1;
                break;
            case 8: //left top, corner
                tarOne = new Position(groupTarget.getPos().getX(), groupTarget.getPos().getY() - groupTarget.getSight() - 1);//unten
                targetPositions.add(tarOne);
                tarTwo = new Position(groupTarget.getPos().getX() + groupTarget.getSight() + 1, groupTarget.getPos().getY());//rechts
                targetPositions.add(tarTwo);
                side = 1;
                break;
        }

        HashMap<Predator, ArrayList<Double>> distance = new HashMap<>(); //this hashmap contains an array list for every Predator containing it's distance to the targetpositions

        for (int i = 0; i < groupMember.size(); i++) {
            ArrayList<Double> temp = new ArrayList<>();
            for (int u = 1; u < numberOfSubgroupsToForm; u++) {
                temp.add(groupMember.get(i).getPos().getDistance(targetPositions.get(u)));
            }

            distance.put(groupMember.get(i), temp);
        }
        double shortest = 0;
        Predator nearest = groupMember.get(0);

        int subGroupSizeStandart = groupMember.size() / numberOfSubgroupsToForm;
        int subGroupSize=subGroupSizeStandart;
        int subGroupLeftOvers = groupMember.size() %numberOfSubgroupsToForm;



        //TODO DIESE SCHLEIFE IST EINE POTENZIELLE UND MASSIVE BUG-QUELLE, BITTE DRINGEND KORREKTURLESEN

        for (int count = 0; count < numberOfSubgroupsToForm; count++) {

            if(subGroupLeftOvers>0)subGroupSize=+1;
            else  subGroupSize=subGroupSizeStandart;

            for (int z = 0; z < subGroupSize; z++) {

                // targetPositions.get(count) waiting target ss
                for (int i = 0; i < groupMember.size(); i++) {
                    if (i == 0) nearest = groupMember.get(i);
                    else {
                        if (groupMember.get(i).getPos().getDistance(targetPositions.get(count)) < nearest.getPos().getDistance(targetPositions.get(count))) {
                            nearest = groupMember.get(i);
                        }
                    }
                }
                subGroupmembers.add(nearest);
                groupMember.remove(nearest);

            }
            subGroups.add(new SubGroup(subGroupmembers, getGroupRadius(), getGroupTarget(), targetPositions.get(count), this, side));
            subGroupmembers.clear();
            side=(side+1)%4;


            //if groubmemberSize%2 !=0  add remaining pred to any group
        }
        subGroupLeftOvers =-1;
    }

    public Position getPredPos(Predator predator) {
        checkTarget();
        if (getSize() != 0) {
            boolean ready = false;
            for (int i = 0; i < subGroups.size(); i++) {
                ready = subGroups.get(i).checkIfInPosition();
            }
            if (ready) {
                attack = true;
            } else {
                attack = false;
            }
            if (attack) {
                return groupTarget.getPos();
            } else {// holt die subgoup des predators aus der alocatedSubgorup, holt aus dieser subgorup seine zugewiesen waiting Position und returnd diese
                return allocatedSubgroup.get(predator).getWaitingPosition().get(predator);
            }
        } else {
            return predator.getPos();
        }

        //TODO @Henry  prey.getpos for actual prey position !!! NO PREY Positioninf for the preds in the update method
        //TODO: @Henry Grouppredator positioning for the predator X!
         /*

         for pred in member ( index of)

         pred i move to x
          */
    }

    protected void delSubGroup(SubGroup subGroup) {
        subGroups.get(subGroups.indexOf(subGroup)).delSub();
        this.subGroups.remove(subGroup);
    }

    public int getSize() {
        int size = 0;
        if (subGroups != null) {
            for (SubGroup s : subGroups) {
                size += s.getSize();
            }
            return size;
        } else return this.groupMember.size();
    }

    protected void delete() {
        for (Predator p : groupMember) {
            p.setHuntingGroup(null);
        }
        for (SubGroup s : subGroups) {
            delSubGroup(s);
        }
        BoardManager.delGrp(this);
    }
}
