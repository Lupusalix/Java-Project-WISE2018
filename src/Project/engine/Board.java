
package Project.engine;


import Project.engine.tile.EmptyTile;
import Project.engine.tile.Predator;
import Project.engine.tile.Prey;
import Project.engine.tile.Tile;
import Project.engine.util.Point2;
import Project.engine.util.Spawn;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int x;
    private final int y;
    private int predatorCount;
    private int preyCount;

    /*
    Tile is our custom board piece, both Predator and Prey extend Tile. Can be replaced with any other Object.
     */
    private Tile[][] board;

    /*
    Currently active Prey and Predator Lists to make updating and moving easier.
     */
    public List<Prey> preyList = new ArrayList<>();
    public List<Predator> predatorList = new ArrayList<>();

    public Board(int x, int y, int prey, int predator) {
        this.x = x;
        this.y = y;
        predatorCount = predator;
        preyCount = prey;
    }

    public void init() {

        buildBoard();

        /*
        Currently spawns prey with random position
         */
        for (int i = 0; i < preyCount; i++) {
            new Prey(Spawn.getFreePosition());
        }

        /*
        Not implemented yet
         */
        for (int i = 0; i < predatorCount; i++) {
            new Predator(Spawn.getFreePosition());
        }
    }

    /*
            Builds the board with empty tiles.
    */
    private void buildBoard() {
        board = new Tile[x][y];
        for (int i = 0; i < x; i++) {
            for (int f = 0; f < y; f++) {
                board[i][f] = new EmptyTile();
            }
        }
    }



    public int getSizeX() {
        return x;
    }

    public int getSizeY() {
        return y;
    }


    /*
    Only used during init
     */
    public void setTile(Point2 pos, Tile tile) {
        board[pos.x][pos.y] = tile;
    }



    /*
    Moves tiles, WIP. Just used for testing. Tiles should ONLY check if its valid and move themselves.

     */
    public void moveTile(Point2 posOld, Point2 posNew) {
        Tile old = board[posOld.x][posOld.y];

        Point2 newPos = new Point2((posOld.x + posNew.x) % x, (posOld.y + posNew.y) % y);

        Tile newT = board[newPos.x][newPos.y];

        board[posOld.x][posOld.y] = newT;
        board[newPos.x][newPos.y] = old;

        if (old instanceof Prey) {
            ((Prey) old).setPosition(newPos);
        }
    }

    public boolean isEmpty(Point2 pos) {
        return board[pos.x][pos.y] instanceof EmptyTile;
    }

    public Tile[][] getTiles() {
        return board;
    }

    public void turnPrey() {
        for (Prey prey : preyList) {
            prey.move();
        }
    }

    public void turnPredator() {

    }

    public boolean moveValid(Point2 pos) {
        return (pos.x > x && pos.x >= 0) || (pos.y > y && pos.y >= 0);
    }

}
