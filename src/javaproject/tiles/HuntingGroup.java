package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;

/**
 * @author Philipp.
 * @author Henry.
 * <p>
 * This Class contains the logic for finding, building and hunting in and with groups.
 */
public class HuntingGroup {


    /**
     * groupMember: An Arraylist containing all the members of the Huntinggroup.
     * groupRadius: the arrea in which the gorup has vision.
     * groupTarget: the (large) prey the group is currently hunting.
     * position: the position of the hunting group.
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
    private boolean grpFull;

    /**
     * Concsturctor creating a HuntingGroup object with the given parameters and then updating its position and relative position to the target.
     *
     * @param member The member of the group.
     * @param radius The groupradius.
     * @param target The target the group is hunting.
     */
    public HuntingGroup(ArrayList<Predator> member, int radius, Prey target) {
        this.groupMember = member;
        this.groupRadius = radius;
        this.groupTarget = target;
        updateGrpPos();

        grpFull = groupMember.size() > 2;

        this.relPos = getRelPos();
    }

    /**
     * Placeholder for determining the readiness of the subgroups.
     * It is overridden by the subgroup.
     * @return false.
     */
    public boolean isRdy() {
        return false;
    }

    /**
     * Getter for the group position.
     * @return the actual group position.
     */
    public Position getPosition() {
        return this.position;
    }

    public boolean isGrpFull() {
        return grpFull;
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
     * This method updates the group position according to its member or subgroup positions.
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

    /**
     *This method forms the subgroups.
     */
    private void buildSubgroups() {
        grpFull = true;
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
        for (SubGroup s : subGroups) {
            s.calculateTargetPosition();
        }
    }

    /**
     * This method checks the predators of the group and deletes not living predators from the group.
     */
    protected void checkPreds() {
        ArrayList<Predator> deadPreds = new ArrayList<>();
        for (Predator p : groupMember) {
            if (!p.isAlive()) deadPreds.add(p);
        }
        for (Predator p : deadPreds) {
            delPred(p);
        }
    }

    /**
     * The method first checks if the target is still valid; if it is not valid the group is dissolved.
     * It updates then the group position.
     * After that it checks if is has enough members to form subgroups and if subgroups exist it updates the subgroups.
     * If no subgroup exist and enough members are in a group it builds the subgroups.
     * It also ensures that the group is dissolved if no members exist in either itself or the subgroups.
     */
    public void update() {
        checkTarget();
        if (groupMember.size() > 0 && subGroups == null) {
            checkPreds();
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
            if (this.groupMember.size() > 2) {
                this.subGroups = new ArrayList<SubGroup>();
                buildSubgroups();
                for (int i = 0; i < subGroups.size(); i++) {
                    subGroups.get(i).update();
                }
            }
        } else {
            attack = true;
            for (int i = 0; i < subGroups.size(); i++) {
                if (!subGroups.get(i).isRdy()) attack = false;
            }
        }
    }

    /**
     *This method checks if the target is alive. If it is not alive this group is deleted.
     */
    protected void checkTarget() {
        if (!groupTarget.isAlive()) {
            this.delete();
        }
    }

    /**
     * This method joins predators inside the groupradius to this group.
     */
    private void joinPredInRad() {
        ArrayList<Predator> preds = this.position.inSight(false, groupRadius);
        if (preds.size() > 0) {
            for (Predator x : preds) {
                x.joinGrp(this);
            }
        }

    }

    /**
     * This method calculates the relative  position indicating to which border the prey is hunted.
     * @return an integer standing for which border to chase.
     */
    private int getRelPos() {

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

    /**
     * Deletes a given predator from the group.
     * @param pred the predator to delete from the group.
     */
    public void delPred(Predator pred) {
        groupMember.remove(pred);
    }

    /**
     * Placeholde method is overriden by the subgroup.
     * @param predator the predator which wants a position.
     * @return the position the predator should go.
     */
    public Position getPredPos(Predator predator) {
        checkTarget();
        return null;

    }

    /**
     * This method deletes the given subgroup.
     * @param subGroup the subgroup to delete.
     */
    protected void delSubGroup(SubGroup subGroup) {
        subGroups.get(subGroups.indexOf(subGroup)).delSub();
        this.subGroups.remove(subGroup);
    }

    /**
     * This method returns the size of the group.
     * @return The size of the group.
     */
    public int getSize() {
        int size = 0;
        if (subGroups != null) {
            for (SubGroup s : subGroups) {
                size += s.getSize();
            }
            return size;
        } else return this.groupMember.size();
    }

    /**
     * This method deletes the group.
     */
    protected void delete() {
        for (Predator p : groupMember) {
            p.setHuntingGroup(null);
        }
        if (this.subGroups != null)
            for (int i = 0; i < subGroups.size(); i++) {
                delSubGroup(subGroups.get(i));
                i--;
            }
        BoardManager.delGrp(this);
    }

    /**
     * This method feeds every predator of the group the nutrition given by the parameter.
     * @param nutrition the nutrition to feed the predators.
     */
    public void eat(int nutrition) {
        for (SubGroup s : subGroups) {
            for (Predator p : s.groupMember) {
                p.eat(nutrition);
            }

        }
    }
}
