package javaproject;

import javaproject.tiles.Animal;
import javaproject.tiles.EmptyTile;
import javaproject.tiles.Position;
import javaproject.tiles.Predator;

import java.util.ArrayList;

import java.util.Collections;

public class BoardManager {

    private static EmptyTile[][] board;
    ArrayList <Animal> animals;
    ArrayList <Animal> prey;
    ArrayList <Predator> predators;


    public static EmptyTile[][] getBoard() {
        return board;
    }

    public BoardManager(int x, int y, int numPrey, int numPred) throws Exception {
        if (numPred + numPrey > x * y) { //Throw Error if the number of Animals is bigger than the field
            throw new Exception("Too Many Animals for the Field!");
        }
        board = new EmptyTile[x][y];
        this.animals = new ArrayList <>();
        this.predators = new ArrayList <>();
        this.prey = new ArrayList <>();
        initialize(numPrey, numPred); //initialize the field

    }

    public static void delete(Animal animal) {

        //delete animal from the lists
    }

    //Initialize the Board with specified Number of Prey and Predators at random positions
    private void initialize(int numPrey, int numPred) {
        for (int i = 0; i < numPred; i++) {
            while (true) {
                Position pos = Position.ranPos(board.length, board[0].length); //Get random Pos
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
            while (true) {
                Position pos = Position.ranPos(board.length, board[0].length);//Get random Pos
                if (!(board[pos.getX()][pos.getY()] instanceof Animal)) {//Check if Position is free
                    Animal an = new Animal(pos, 10);//Create Animal
                    board[pos.getX()][pos.getY()] = an;//Place Animal
                    animals.add(an);
                    prey.add(an); //Add Prey to the lists
                    break;
                }
            }
        }

        Collections.sort(animals);

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


    public void tick() {
        System.out.println("Pred : " + predators.size() + "Prey: " + prey.size());
        for (int i = 0; i < animals.size(); i++) {
            Position pos = animals.get(i).act();

            if (pos.getX() < board.length && pos.getX() >= 0 && pos.getY() < board[0].length && pos.getY() >= 0) {

                move(pos, animals.get(i));
            }
            /* Placeholder for kill logic
            if (false){
               pred.kill(board[x][y]);
               move to killed
            }*/
        }

    }

    private void move(Position pos, Animal an) {
        board[an.getPos().getX()][an.getPos().getY()] = new EmptyTile();

        //here check if killed or attacked
        board[pos.getX()][pos.getY()] = an;

        an.setPos(pos);
    }


}
