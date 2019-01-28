package javaproject.tiles;

import javaproject.BoardManager;

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
       for(int i=0;i<super.getGroupMember().size();i++){
           if(super.getGroupMember().get(i).getPos()== waitingPosition.get(super.getGroupMember().get(i))){ //if pred at waitingPos
               group.getReady().put(super.getGroupMember().get(i),true);//set his key to true
           }else{
               group.getReady().put(super.getGroupMember().get(i),false);//if not, set his key to false
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
        allocateWaitingPosition();
        for(int i=0;i<member.size();i++) {
            group.getAllocatedSubgroup().put(member.get(i), this);
        }
    }


    @Override
    public Position getPredPos(Predator predator) {
        int[] a = BoardManager.getSize();
        return Position.ranPos(a[0], a[1]);


        //TODO: @Henry Grouppredator positioning for the predator X!
        /*

        for pred in member ( index of)

        pred i move to x
         */
    }


}
