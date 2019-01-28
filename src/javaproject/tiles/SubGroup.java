package javaproject.tiles;

import java.util.ArrayList;
import java.util.HashMap;

public class SubGroup extends HuntingGroup {

    private HuntingGroup group;
    private Position subGroupTargetPosition;
    private HashMap<Predator,Position>waitingPosition;
    boolean side;


    public HashMap <Predator, Position> getWaitingPosition() {
        return waitingPosition;
    }

    /**
     * the method checks for every predator if there position is equal to there waitingposition and updates the"ready"
     * list ccordingly if yes "all ready" remains true, if not it changes to"false".
     *
     * @return if every pred is in position
     */


   public boolean checkIfInPosition(){
       boolean allReady=true;
       for(int i=0;i<groupMember.size();i++){
           if(groupMember.get(i).getPos()== waitingPosition.get(groupMember.get(i))){ //if pred at waitingPos
               group.getReady().put(groupMember.get(i),true);//set his key to true
           }else{
               group.getReady().put(groupMember.get(i),false);//if not, set his key to false
               allReady=false;//if someone is not in Position,, not everyone is in position, so 'allReady' is 'false'
           }
       }
       return allReady;
   }


    public void allocateWaitingPosition(){
        for(int i=0;i<group.getGroupMember().size();i++){
            if(i==0){
            waitingPosition.put(group.getGroupMember().get(i),subGroupTargetPosition);
            }else {
                if(side==false) {
                    //mögliche bugAnflligkeit bei zu großen gruppen
                    waitingPosition.put(group.getGroupMember().get(i),new Position(subGroupTargetPosition.getX(),subGroupTargetPosition.getY() - (-1 *2)));
                }else{waitingPosition.put(group.getGroupMember().get(i),new Position(subGroupTargetPosition.getX()-(-1*2),subGroupTargetPosition.getY()));

                }
            }
        }
    }

    public SubGroup(ArrayList<Predator> member, int radius, Prey target,Position waitingPosition, HuntingGroup group, boolean side) {
        super(member, radius, target);
        subGroupTargetPosition =waitingPosition;
        this.group = group;
        this.side=side;
        this.waitingPosition = new HashMap<Predator, Position>();
        allocateWaitingPosition();
        for(int i=0;i<member.size();i++) {
            group.getAllocatedSubgroup().put(member.get(i), this);
        }
    }


    @Override
    public void update() {
        if (groupMember.size() > 0) {
            for (Predator p : groupMember) {
                if (!p.isAlive()) this.delPred(p);
            }
            updateGrpPos();
        } else group.delSubGroup(this);
        allocateWaitingPosition();
        checkIfInPosition();
    }

}
