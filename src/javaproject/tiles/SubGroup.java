package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;

public class SubGroup extends HuntingGroup {

    private HuntingGroup group;

    boolean rdy = false;

    private Position subGroupTargetPosition;
    private int subGrpNr;

    public SubGroup(ArrayList<Predator> member, int radius, Prey target, int subGrpNr, HuntingGroup group) {
        super(member, radius, target);
        this.subGrpNr = subGrpNr;
        this.group = group;
    }

    @Override
    public boolean isRdy() {
        return rdy && this.attack;
    }

    public Position getSubGroupTargetPosition() {
        return subGroupTargetPosition;
    }


    protected void delSub() {
        for (Predator p : groupMember) {
            p.setHuntingGroup(null);
        }
    }

    @Override
    public void update() {
        checkTarget();
        if (groupMember.size() > 0) {
            for (Predator p : groupMember) {
                if (!p.isAlive()) this.delPred(p);
            }
            this.updateGrpPos();
        } else group.delSubGroup(this);
        checkInPos();
        calculateTargetPosition();
    }

    private void checkInPos() {
        ArrayList<Predator> preds = subGroupTargetPosition.inSight(false, 3);
        boolean a = true;
        for (Predator p : groupMember) {
            if (!preds.contains(p)) a = false;
        }
        this.rdy = a;
    }

    private void calculateTargetPosition() {
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

    private void calculateLeft() {
        int x = this.groupTarget.getPos().getX() - groupTarget.getSight() - 2;
        int y = this.groupTarget.getPos().getY();
        if (x < 0) x = 0;
        this.subGroupTargetPosition = new Position(x, y);
    }

    private void calculateTop() {
        int x = this.groupTarget.getPos().getX();
        int y = this.groupTarget.getPos().getY() - groupTarget.getSight() - 2;
        if (y < 0) y = 0;
        this.subGroupTargetPosition = new Position(x, y);
    }

    private void calculateRight() {
        int x = this.groupTarget.getPos().getX() + groupTarget.getSight() + 2;
        int y = this.groupTarget.getPos().getY();
        if (x > BoardManager.getSize()[0]) x = BoardManager.getSize()[0] - 1;
        this.subGroupTargetPosition = new Position(x, y);
    }

    private void calculateBottom() {
        int x = this.groupTarget.getPos().getX();
        int y = this.groupTarget.getPos().getY() + groupTarget.getSight() + 2;
        if (y > BoardManager.getSize()[1]) y = BoardManager.getSize()[1] - 1;
        this.subGroupTargetPosition = new Position(x, y);
    }

    @Override
    public void eat(int nutrition) {
        this.group.eat(nutrition);
    }

    @Override
    public Position getPredPos(Predator predator) {
        checkTarget();
        return subGroupTargetPosition;
    }

    @Override
    public void delete() {
        this.group.delete();
    }
}
