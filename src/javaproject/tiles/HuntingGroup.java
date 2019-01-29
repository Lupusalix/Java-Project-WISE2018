package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;

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
    protected int groupRadius;
    protected Prey groupTarget;
    protected Position position;
    private ArrayList<SubGroup> subGroups;
    protected boolean attack = true;
    protected int relPos;

    public boolean isRdy() {
        return false;
    }

    public Position getPosition() {
        return this.position;
    }

    public HuntingGroup(ArrayList<Predator> member, int radius, Prey target) {
        this.groupMember = member;
        this.groupRadius = radius;
        this.groupTarget = target;
        updateGrpPos();
        this.relPos = getRelPos();
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

    private void buildSubgroups() {
        this.subGroups = new ArrayList<>();
        ArrayList<Predator> preda = new ArrayList<>(), predb = new ArrayList<>(), predc = new ArrayList<>();
        for (int i = 0; i < groupMember.size(); i++) {
            int a = i % 3;
            switch (a) {
                case 0:
                    preda.add(groupMember.get(i));
                    break;
                case 1:
                    predb.add(groupMember.get(i));
                    break;
                default:
                    predc.add(groupMember.get(i));
                    break;
            }
        }
        groupMember.clear();
        subGroups.add(new SubGroup(preda, this.groupRadius, this.groupTarget, 0, this));
        subGroups.add(new SubGroup(predb, this.groupRadius, this.groupTarget, 1, this));
        subGroups.add(new SubGroup(predc, this.groupRadius, this.groupTarget, 2, this));
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
            buildSubgroups();
            for (int i = 0; i < subGroups.size(); i++) {
                subGroups.get(i).update();
            }
        } else {
            attack = true;
            for (SubGroup s : subGroups) {
                if (!s.rdy) attack = false;
            }
        }
    }

    protected void checkTarget() {
        if (!groupTarget.isAlive()) {
            this.delete();
        }
    }

    private void joinPredInRad() {
        ArrayList<Predator> preds = this.position.inSight(false, groupRadius);
        if (preds.size() > 0) {
            for (Predator x : preds) {
                x.joinGrp(this);
            }
        }

    }

    private int getRelPos() {

        //TODO:@HENRY unten,links,oben,rechts int 0-3

        int targetx = this.groupTarget.getPos().getX();
        int targety = this.groupTarget.getPos().getY();

        int x = Math.abs(targetx - this.position.getX());
        int y = Math.abs(targety - this.position.getY());

        if (x > y) {
            if (this.position.getX() < targetx) return 3;
            else return 1;
        } else {
            if (this.position.getY() < targety) return 0;
            else return 2;
        }
    }

    public void delPred(Predator pred) {
        groupMember.remove(pred);
    }

    public Position getPredPos(Predator predator) {
        checkTarget();
        return null;

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


    public void eat(int nutrition) {
        for (SubGroup s : subGroups) {
            for (Predator p : s.groupMember) {
                p.eat(nutrition);
            }

        }
    }
}
