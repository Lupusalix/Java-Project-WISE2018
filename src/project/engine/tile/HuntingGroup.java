package project.engine.tile;

import project.engine.GameLoop;
import project.engine.util.PathFindingUtil;
import project.engine.util.Point2;

import java.util.ArrayList;
import java.util.List;

public class HuntingGroup {


    private Point2 groupCenter;
    private ArrayList<Point2> groupTiles;
    private ArrayList<TilePredator> members;
    private int groupRadius = 8;
    private TilePrey target;
    private int movespeed = 2;


    public HuntingGroup(TilePrey target){
        this.target = target;
        GameLoop.board.groupList.add(this);
    }


    private void findGroupMembers(){

    }

    public void turn(){

        //TODO: Move to target

        final List<Point2> pathList = PathFindingUtil.getPathList(groupCenter, target.pos);

        /*
        Logic
         */

        //THEN

        setGroupCenter();
        findGroupMembers();

    }

    private void setGroupCenter(){

    }

    public ArrayList<Point2> getTiles(){
        return groupTiles;
    }

    private void disband(){
        GameLoop.board.groupList.remove(this);
    }

    private void moveMembers(){
        for (TilePredator pred : members){
            //TODO: SET POSITION
        }
    }


}
