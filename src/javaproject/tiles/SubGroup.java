package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;

public class SubGroup extends HuntingGroup {

    private HuntingGroup group;

    boolean rdy = false;

    private Position subGroupTargetPosition;
    private int subGrpNr;

    /**
     * Constructor.
     *
     * @param member .
     * @param radius .
     * @param target .
     * @param subGrpNr .
     * @param group .
     */
    public SubGroup(ArrayList<Predator> member, int radius, Prey target, int subGrpNr, HuntingGroup group) {
        super(member, radius, target);
        this.subGrpNr = subGrpNr;
        this.group = group;
    }

    /**
     * method to check if this Pred is ready to attack.
     *
     * @return 'true' if it should attack,'false' if not.
     */
    @Override
    public boolean isRdy() {
        return rdy && this.attack;
    }


    public Position getSubGroupTargetPosition() {
        return subGroupTargetPosition;
    }

    /**
     *d
     * This mothed deletes the group from the predators
     */
    protected void delSub() {
        for (Predator p : groupMember) {
            p.setHuntingGroup(null);
        }
    }

    /**
     * The update method of the Subgroup.
     */
    @Override
    public void update() {
        checkTarget();
        if (groupMember.size() > 0) {
            ArrayList<Predator> deadPreds = new ArrayList<>();
            for (Predator p : groupMember) {
                if (!p.isAlive()) deadPreds.add(p);
            }
            for (Predator p : deadPreds) {
                delPred(p);
            }

            this.updateGrpPos();
        } else group.delSubGroup(this);
        checkInPos();
        calculateTargetPosition();
    }

    private void checkInPos() {
        ArrayList<Predator> preds = subGroupTargetPosition.inSight(false, 3);
        if (preds == null) {
            this.rdy = false;
        } else {
            boolean a = true;
            for (Predator p : groupMember) {
                if (!preds.contains(p)) a = false;
            }
            this.rdy = a;
        }
    }

    /**
     * This method calculates the target positions for the group.
     */
    protected void calculateTargetPosition() {
        switch (relPos) {
            case 0:
                switch (subGrpNr) {
                    case 0:
                        calculateLeft();
                        break;
                    case 1:
                        calculateTop();
                        break;
                    default:
                        calculateRight();
                        break;
                }
                break;
            case 1:
                switch (subGrpNr) {
                    case 0:
                        calculateTop();
                        break;
                    case 1:
                        calculateRight();
                        break;
                    default:
                        calculateBottom();
                        break;
                }
                break;
            case 2:
                switch (subGrpNr) {
                    case 0:
                        calculateLeft();
                        break;
                    case 1:
                        calculateBottom();
                        break;
                    default:
                        calculateRight();
                        break;
                }
                break;
            default:
                switch (subGrpNr) {
                    case 0:
                        calculateBottom();
                        break;
                    case 1:
                        calculateLeft();
                        break;
                    default:
                        calculateTop();
                        break;
                }
        }
    }

    /**
     * This method calculates a position left of the target and outside of its sight radius.
     */
    private void calculateLeft() {
        int x = this.groupTarget.getPos().getX() - groupTarget.getSight() - 2;
        int y = this.groupTarget.getPos().getY();
        if (x < 0) x = 0;
        this.subGroupTargetPosition = new Position(x, y);
    }

    /**
     * This method calculates a position top of the target and outside of its sight radius.
     */
    private void calculateTop() {
        int x = this.groupTarget.getPos().getX();
        int y = this.groupTarget.getPos().getY() - groupTarget.getSight() - 2;
        if (y < 0) y = 0;
        this.subGroupTargetPosition = new Position(x, y);
    }

    /**
     * This method calculates a position right of the target and outside of its sight radius.
     */
    private void calculateRight() {
        int x = this.groupTarget.getPos().getX() + groupTarget.getSight() + 2;
        int y = this.groupTarget.getPos().getY();
        if (x > BoardManager.getSize()[0]) x = BoardManager.getSize()[0] - 1;
        this.subGroupTargetPosition = new Position(x, y);
    }

    /**
     * This method calculates a position bottom of the target and outside of its sight radius.
     */
    private void calculateBottom() {
        int x = this.groupTarget.getPos().getX();
        int y = this.groupTarget.getPos().getY() + groupTarget.getSight() + 2;
        if (y > BoardManager.getSize()[1]) y = BoardManager.getSize()[1] - 1;
        this.subGroupTargetPosition = new Position(x, y);
    }

    /**
     * This method  feeds the groupmembers the nutrition provided as a parameter.
     *
     * @param nutrition the nutrition to feed the predators.
     */
    @Override
    public void eat(int nutrition) {
        this.group.eat(nutrition);
    }

    /**
     * This method returns the the position a predator shoul move to.
     * @param predator the predator which wants a position.
     * @return the position the predator needs to move to.
     */
    @Override
    public Position getPredPos(Predator predator) {
        checkTarget();
        return subGroupTargetPosition;
    }

    /**
     * This method deletes the group.
     */
    @Override
    public void delete() {
        this.group.delete();
    }
}
