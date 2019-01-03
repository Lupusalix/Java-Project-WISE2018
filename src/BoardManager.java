
import Tiles.Animal;
import Tiles.EmptyTile;
import Tiles.Position;
import Tiles.Predator;

import java.util.ArrayList ;

public class BoardManager implements Runnable {

    private EmptyTile[][] board;
    ArrayList<Animal> animals;
    ArrayList<Animal> prey;
    ArrayList<Predator> predators;

    public BoardManager(int x , int y, int numPrey, int numPred) {
        this.board = new EmptyTile[x][y];
        initialize(numPrey,numPred);
    }

    private void initialize(int numPrey,int numPred){
        for (int i = 0; i < numPred ; i++) {
           Position pos =new Position();
           while(true) {
               pos = pos.ranPos(board.length, board[0].length);
               if (!(board[pos.getX()][pos.getY()] instanceof Animal)) {
                   Predator pred = new Predator(pos, 10);
                   board[pos.getX()][pos.getY()] = pred;
                   animals.add(pred);
                   predators.add(pred);
                   break;
               }
           }
        }
        for (int i = 0; i < numPrey ; i++) {
            Position pos =new Position();
            while(true) {
                pos = pos.ranPos(board.length, board[0].length);
                if (!(board[pos.getX()][pos.getY()] instanceof Animal)) {
                    board[pos.getX()][pos.getY()] = new Animal(pos, 10);
                    break;
                }
            }
        }

    }

    @Override
    public void run() {

    }
}
