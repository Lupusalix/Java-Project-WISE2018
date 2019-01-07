
import Tiles.Animal;
import Tiles.EmptyTile;
import Tiles.Position;
import Tiles.Predator;

import java.util.ArrayList;

import java.util.Collections;

public class BoardManager implements Runnable {

    private EmptyTile[][] board;
    ArrayList<Animal> animals;
    ArrayList<Animal> prey;
    ArrayList<Predator> predators;

    public BoardManager(int x, int y, int numPrey, int numPred) throws Exception {
        if (numPred + numPrey > x * y) { //Throw Error if the number of Animals is bigger than the field
            throw new Exception("Too Many Animals for the Field!");
        }
        this.board = new EmptyTile[x][y];
        this.animals = new ArrayList<>();
        this.predators = new ArrayList<>();
        this.prey = new ArrayList<>();
        initialize(numPrey, numPred); //initialize the field
    }

    //Initialize the Board with specified Number of Prey and Predators at random positions
    private void initialize(int numPrey, int numPred) {
        for (int i = 0; i < numPred; i++) {
            Position pos = new Position();
            while (true) {
                pos = pos.ranPos(board.length, board[0].length); //Get random Pos
                if (!(board[pos.getX()][pos.getY()] instanceof Animal)) { //Check if Position is free
                    Predator pred = new Predator(pos, 10); //Create Predator
                    board[pos.getX()][pos.getY()] = pred; //Place Predator
                    animals.add(pred);
                    predators.add(pred); //Add Pred to the lists
                    break;
                }
            }
        }
        for (int i = 0; i < numPrey; i++) {
            Position pos = new Position();
            while (true) {
                pos = pos.ranPos(board.length, board[0].length);//Get random Pos
                if (!(board[pos.getX()][pos.getY()] instanceof Animal)) {//Check if Position is free
                    Animal an = new Animal(pos, 10);//Create Animal
                    board[pos.getX()][pos.getY()] = an;//Place Animal
                    animals.add(an);
                    prey.add(an); //Add Prey to the lists
                    break;
                }
            }
        }

    }

    //just a testing Method for sorting and printing the field without gui
    public void test() {
        Collections.sort(animals);
        for (Animal a : animals) {
            System.out.println(a.getInitiative());
        }
        System.out.println();

        for (EmptyTile[] aBoard : board) {
            for (int j = 0; j < aBoard.length; j++) {
                if (aBoard[j] instanceof Animal) {
                    if (aBoard[j] instanceof Predator) System.out.print("P");
                    else System.out.print("A");
                } else System.out.print("E");

            }
            System.out.print("\n");
        }
    }


    public EmptyTile[][] getTiles(){
        return board;
    }

    @Override
    public void run() {

    }
}
