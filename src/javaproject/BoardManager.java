package javaproject;

import javaproject.tiles.*;

import java.util.ArrayList;

import java.util.Collections;

public class BoardManager {

    private static EmptyTile[][] board;
    private static ArrayList<Animal> animals;
    private static ArrayList<Prey> prey;
    private static ArrayList<Predator> predators;


    public static EmptyTile[][] getBoard() {
        return board;
    }

    public static int[] getSize() {
        int[] a = new int[2];
        a[0] = board.length;
        a[1] = board[0].length;
        return a;
    }

    public static EmptyTile bGetPos(Position pos) {
        return board[pos.getX()][pos.getY()];
    }

    public BoardManager(int x, int y, int numPrey, int numPred) throws Exception {
        if (numPred + numPrey > x * y) { //Throw Error if the number of Animals is bigger than the field
            throw new Exception("Too Many Animals for the Field!");
        }
        board = new EmptyTile[x][y];
        animals = new ArrayList<>();
        predators = new ArrayList<>();
        prey = new ArrayList<>();
        initialize(numPrey, numPred); //initialize the field

    }

    public static void delete(Animal animal) {
        board[animal.getPos().getX()][animal.getPos().getY()] = new EmptyTile();
        animals.remove(animal);
        prey.remove(animal);
        predators.remove(animal);

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
                    Prey an = new Prey(pos, 10);//Create Animal
                    board[pos.getX()][pos.getY()] = an;//Place Animal
                    animals.add(an);
                    prey.add(an); //Add Prey to the lists
                    break;
                }
            }
        }

        Collections.sort(animals);

    }


    public void tick() {

        for (int i = 0; i < animals.size(); i++) {
            Animal an = animals.get(i);
            an.setSpeed(an.getSpeedMax());
            do {
                Position pos = an.act();
                if (pos.getX() < board.length && pos.getX() >= 0 && pos.getY() < board[0].length && pos.getY() >= 0) {
                    if (bGetPos(pos) instanceof Prey && an instanceof Predator) {
                        ((Predator) an).kill((Prey) bGetPos(pos));
                        System.out.println("Prey killed");
                    }
                    move(pos, an);
                    an.setSpeed(an.getSpeed() - 1);
                }
            } while (an.getSpeed() > 0);

            if (an instanceof Predator) ((Predator) an).starve();

        }
        System.out.println("Pred : " + predators.size() + "Prey: " + prey.size());
    }

    //simply moves the animal to the new position
    private void move(Position pos, Animal an) {
        board[an.getPos().getX()][an.getPos().getY()] = new EmptyTile();
        board[pos.getX()][pos.getY()] = an;
        an.setPos(pos);
    }


}
