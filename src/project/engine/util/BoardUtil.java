
package project.engine.util;

import project.engine.IAnimal;
import project.engine.MainLoop;
import project.engine.tile.Tile;
import project.engine.tile.TileEmpty;

import java.util.Random;

public class BoardUtil {

    private BoardUtil() {
    }

    public static Point2 randomDirection() {
        Random rand = new Random();

        switch (rand.nextInt(3)) {

            case 0:
                return new Point2(1, 0);
            case 1:
                return new Point2(0, 1);
            case 2:
                return new Point2(-1, 0);
            case 3:
                return new Point2(0, -1);
        }
        return new Point2(0, 0);

    }


    public static boolean isMoveInGrid(Point2 pos){

        return (pos.x >= 0 && pos.x < MainLoop.board.getSizeX()) && (pos.y >= 0 && pos.y < MainLoop.board.getSizeY());

    }

    public static boolean isEmpty(Point2 pos) {
        return MainLoop.board.getGrid()[pos.x][pos.y] instanceof TileEmpty;
    }


    public static boolean move(Point2 pos, Vector2 vec){

        //TODO: Rewrite for TileStack?

        Tile[][] board = MainLoop.board.getGrid();

        Tile oldTile = board[pos.x][pos.y];
        Point2 newPos = pos.addVector2(vec);

        if(isMoveInGrid(newPos)) {
            Tile newTile = board[newPos.x][newPos.y];

            board[pos.x][pos.y] = newTile;
            board[newPos.x][newPos.y] = oldTile;

            if(oldTile instanceof IAnimal) {
                ((IAnimal) oldTile).setPosition(newPos);
            }

            if(newTile instanceof IAnimal){
                ((IAnimal) newTile).setPosition(pos);
            }
            return true;
        }
        return false;
    }


}
