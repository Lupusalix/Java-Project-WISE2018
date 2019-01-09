
package project.engine;


import project.engine.misc.Misc;
import project.engine.tile.TileEmpty;
import project.engine.tile.TilePredator;
import project.engine.tile.TilePrey;
import project.engine.tile.Tile;
import project.engine.util.BoardUtil;
import project.engine.util.Point2;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int sizeX;
    private final int sizeY;
    private int predatorCount;
    private int preyCount;

    /*
    The game board object.
     */
    private Tile[][] board;

    /*
    Currently active TilePrey and TilePredator Lists to make updating and moving easier.
     */
    public List<TilePrey> preyList = new ArrayList<>();
    public List<TilePredator> predatorList = new ArrayList<>();

    public Board(int x, int y, int prey, int predator) {
        this.sizeX = x;
        this.sizeY = y;
        predatorCount = predator;
        preyCount = prey;
    }

    public void init() {
        buildBoard();
        initPrey(preyCount);
        initPredator(predatorCount);
    }


    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }


    public Tile[][] getGrid() {
        return board;
    }

    public void turnPrey() {
        for (TilePrey prey : preyList) {
            prey.turn();
        }
    }

    public void turnPredator() {
        for (TilePredator predator : predatorList) {
            predator.turn();
        }

    }



    /*
    Init stuff
     */

    private void initPredator(int x) {

        for (int i = 0; i < x; i++) {
            new TilePredator(BoardUtil.getFreePosition());
        }
    }

    private void initPrey(int x) {

        /*
        Currently spawns prey with random position
         */
        for (int i = 0; i < x; i++) {
            new TilePrey(BoardUtil.getFreePosition());
            if(Misc.debug){
            }
        }
        System.out.println("Spawned Prey");
    }

    public void spawnPreyRandomly() {

        initPrey(15);
    }

    /*
        Builds the board with empty tiles.
    */
    private void buildBoard() {
        board = new Tile[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int f = 0; f < sizeY; f++) {
                board[i][f] = new TileEmpty();
            }
        }
    }

    /*
    Only used during init
    */
    public void setTile(Point2 pos, Tile tile) {
        board[pos.x][pos.y] = tile;
    }

    public void checkStarvation(){
        for(TilePredator p : predatorList){
            if (!p.isAlive()){
                predatorList.remove(p);
                setTile(p.getPosition(), new TileEmpty());
            }
        }
    }

}
