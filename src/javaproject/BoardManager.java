package javaproject;

import javaproject.tiles.*;

import java.util.ArrayList;

import java.util.Collections;

public class BoardManager implements Runnable {

    private static EmptyTile[][] board;
    private static ArrayList<Animal> animals;
    private static ArrayList<Prey> prey;
    private static ArrayList<Predator> predators;
    private int genereatePrey;
    private int genereteXSeconds;
    private Thread thread;
    private static int nutritionPerTick;
    private static int preyKilled;
    private static int predKilled;


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

    public BoardManager(int x, int y, int numPrey, int numPred, int genereatePrey, int genereteXSeconds) throws Exception {
        if (numPred + numPrey > x * y) { //Throw Error if the number of Animals is bigger than the field
            throw new Exception("Too Many Animals for the Field!");
        }
        board = new EmptyTile[x][y];
        animals = new ArrayList<>();
        predators = new ArrayList<>();
        prey = new ArrayList<>();
        nutritionPerTick = 0;
        predKilled = 0;
        preyKilled = 0;

        initialize(numPrey, numPred); //initialize the field


        this.genereatePrey = genereatePrey;
        this.genereteXSeconds = genereteXSeconds;
        if (genereteXSeconds > 0) {
            this.thread = new Thread(this);
            thread.setDaemon(true);
            thread.start();

        }
    }

    public static void delete(Animal animal) {
        board[animal.getPos().getX()][animal.getPos().getY()] = new EmptyTile();
        animals.remove(animal);
        prey.remove(animal);
        predators.remove(animal);
    }

    //Initialize the Board with specified Number of Prey and Predators at random positions
    private void initialize(int numPrey, int numPred) {
        generatePredator(numPred);
        generatePrey(numPrey);
        Collections.sort(animals);
    }

    public static void generatePredator(int numPred) {
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
    }

    public static void generatePrey(int numPrey) {
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
    }


    public void tick() {
        //TODO: Get Rid of Thread
        nutritionPerTick = 0;
        predKilled = 0;
        preyKilled = 0;
        for (int i = 0; i < animals.size(); i++) {
            Animal an = animals.get(i);
            an.setSpeed(an.getSpeedMax());
            do {
                Position pos = an.act();
                if (pos.getX() < board.length && pos.getX() >= 0 && pos.getY() < board[0].length && pos.getY() >= 0) {
                    if (bGetPos(pos) instanceof Prey && an instanceof Predator) {
                        ((Predator) an).kill((Prey) bGetPos(pos));

                    }
                    move(pos, an);
                    an.setSpeed(an.getSpeed() - 1);
                }
            } while (an.getSpeed() > 0);

            if (an instanceof Predator) ((Predator) an).starve();

        }
        System.out.println("Pred : " + predators.size() + "Prey: " + prey.size() + "Predators killed: " + predKilled + "Prey eaten / nutriton intake: " + preyKilled + "/" + nutritionPerTick);
    }

    //simply moves the animal to the new position
    public static void move(Position pos, Animal an) {
        board[an.getPos().getX()][an.getPos().getY()] = new EmptyTile();
        board[pos.getX()][pos.getY()] = an;
        an.setPos(pos);
    }

    public static void statisticsNutritionIntake(int nut) {
        nutritionPerTick += nut;
    }

    public static void statisiticsPreysKilled(int a) {
        preyKilled += a;
    }

    public static void statisticsPredatorsKilled(int a) {
        predKilled += a;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(genereteXSeconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BoardManager.generatePrey(genereatePrey);
        }
    }

}
