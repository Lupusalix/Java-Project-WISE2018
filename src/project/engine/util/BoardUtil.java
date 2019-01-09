
package project.engine.util;

import project.engine.GameLoop;
import project.engine.tile.Tile;
import project.engine.tile.TileAnimal;
import project.engine.tile.TileEmpty;

import java.util.Random;

public class BoardUtil {

    private BoardUtil() {
    }


    public static boolean isMoveInGrid(Point2 pos){

        return (pos.x >= 0 && pos.x < GameLoop.board.getSizeX()) && (pos.y >= 0 && pos.y < GameLoop.board.getSizeY());

    }

    public static boolean isEmpty(Point2 pos) {
        return GameLoop.board.getGrid()[pos.x][pos.y] instanceof TileEmpty;
    }

    public static boolean move(Point2 pos, Point2 newPos){

        Tile[][] board = GameLoop.board.getGrid();

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

            Random rand = new Random();

            if(BoardUtil.move(pos, pos.getNeighbours().get(rand.nextInt(4)))){
                break;
            }

        }
    }

        public static Point2 getFreePosition() {


        while (true) {
            Random rand = new Random();

            int x = rand.nextInt(GameLoop.board.getSizeX());
            int y = rand.nextInt(GameLoop.board.getSizeY());

            Point2 position = new Point2(x, y);

            if (BoardUtil.isEmpty(position)) {
                return position;
            }
        }
    }

}
