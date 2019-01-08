
package project.engine.util;

import project.engine.MainLoop;
import project.engine.tile.Tile;
import project.engine.tile.TileAnimal;
import project.engine.tile.TileEmpty;

public class BoardUtil {

    private BoardUtil() {
    }


    public static boolean isMoveInGrid(Point2 pos){

        return (pos.x >= 0 && pos.x < MainLoop.board.getSizeX()) && (pos.y >= 0 && pos.y < MainLoop.board.getSizeY());

    }

    public static boolean isEmpty(Point2 pos) {
        return MainLoop.board.getGrid()[pos.x][pos.y] instanceof TileEmpty;
    }


    public static boolean move(Point2 pos, Vector2 vec){

        Tile[][] board = MainLoop.board.getGrid();

        Tile oldTile = board[pos.x][pos.y];
        Point2 newPos = pos.addVector2(vec);

        if(isMoveInGrid(newPos)) {
            Tile newTile = board[newPos.x][newPos.y];

            board[pos.x][pos.y] = newTile;
            board[newPos.x][newPos.y] = oldTile;

            if(oldTile instanceof TileAnimal) {
                ((TileAnimal) oldTile).setPosition(newPos);
            }

            if(newTile instanceof TileAnimal){
                ((TileAnimal) newTile).setPosition(pos);
            }
            return true;
        }
        return false;
    }

    public static boolean move(Point2 pos, Point2 newPos){

        Tile[][] board = MainLoop.board.getGrid();

        Tile oldTile = board[pos.x][pos.y];

        if(isMoveInGrid(newPos)) {
            Tile newTile = board[newPos.x][newPos.y];

            board[pos.x][pos.y] = newTile;
            board[newPos.x][newPos.y] = oldTile;

            if(oldTile instanceof TileAnimal) {
                ((TileAnimal) oldTile).setPosition(newPos);
            }

            if(newTile instanceof TileAnimal){
                ((TileAnimal) newTile).setPosition(pos);
            }
            return true;
        }
        return false;
    }


    public static void moveRandom(Point2 pos, int speed){

        while(true) {
            Vector2 vec = new Vector2(Point2.randomDirection(), speed);
            Point2 newPosition = pos.addVector2(vec);


            if(BoardUtil.move(pos, new Vector2(Point2.randomDirection(), speed))){
                break;
            }

        }
    }

}
