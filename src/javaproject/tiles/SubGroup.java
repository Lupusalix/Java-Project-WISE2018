package javaproject.tiles;

import javaproject.BoardManager;

import java.util.ArrayList;
import java.util.HashMap;

public class SubGroup extends HuntingGroup {

    private HuntingGroup group;
    private Position subGroupTargetPosition;
    private HashMap<Predator,Position>waitingPosition;
    boolean side;

   // public boolean checkIfInPosition(){
   // }

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
