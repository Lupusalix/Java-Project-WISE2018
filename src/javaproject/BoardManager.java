package javaproject;

import javaproject.tiles.*;

import java.util.ArrayList;
import java.util.Collections;


/**
 * @author Philipp.
 * @author Henry (Interface-interaction).
 * <p>
 * The Boardmanager is the HUB for all actions of our board, he calls the Methods of Predators and pray. He is the
 * center of our statemaschine.
 * @see EmptyTile .
 * @see Animal .
 * @see HuntingGroup .
 * @see Predator .
 * @see Prey .
 */
public class BoardManager {

    private static EmptyTile[][] board;
    private static ArrayList<Animal> animals;
    private static ArrayList<Prey> prey;
    private static ArrayList<Predator> predators;
    private static ArrayList<HuntingGroup> groups;
    private int genPrey;
    private int genereteXSeconds;
    private int preyTimer;
    private static int nutritionPerTick = 0;
    private static int preyKilled;
    private static int predKilled;
    private int iteration = 0;
    private boolean generatePrey;


    public void setGenPrey(int genPrey) {
        this.genPrey = genPrey;
    }


    public void setGeneratePrey(boolean generatePrey) {
        this.generatePrey = generatePrey;
    }

    public static int getNutritionPerTick() {
        return nutritionPerTick;
    }

    public static int getPreyKilled() {
        return preyKilled;
    }

    public static int getPredKilled() {
        return predKilled;
    }

    public int getIteration() {
        return iteration;
    }

    public int getPreySize() {
        return prey.size();
    }

    public BoardManager(int x, int y, int numPrey, int numPred, int genereatePrey, int genereteXSeconds, boolean genP) throws Exception {
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
        this.generatePrey = genP;// sets the boolean if the programm should generate prey

        initialize(numPrey, numPred); //initialize the field
        this.genPrey = genereatePrey;
        this.genereteXSeconds = genereteXSeconds;
        groups = new ArrayList<>();
    }


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

    public static void buildGroup(Predator predator, Prey target) {
        int grpRad = 5;
        ArrayList<Predator> member = predator.inSight(false, grpRad);
        member.add(predator);
        HuntingGroup hg = new HuntingGroup(member, grpRad, target);
        groups.add(hg);
        for (int i = 0; i < member.size(); i++) {
            member.get(i).joinGrp(hg);
        }
        predator.joinGrp(hg);
        hg.updateGrpPos();
        hg.update();
    }


    public static void delete(Animal animal) {
        board[animal.getPos().getX()][animal.getPos().getY()] = new EmptyTile();
        animals.remove(animal);
        prey.remove(animal);
        predators.remove(animal);
    }

    public static void delGrp(HuntingGroup grp) {
        groups.remove(grp);
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
                    Predator pred = new Predator(pos, 12); //Create Predator
                    board[pos.getX()][pos.getY()] = pred; //Place Predator
                    animals.add(pred);
                    predators.add(pred); //Add Pred to the lists
                    break;
                }
            }
        }
    }

    public void generatePrey(int numPrey) {
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

    public int getPredSize() {
        return predators.size();
    }


    //simply moves the animal to the new position
    public static void move(Position pos, Animal an) {
        board[an.getPos().getX()][an.getPos().getY()] = new EmptyTile();
        board[pos.getX()][pos.getY()] = an;
        an.setPos(pos);
    }

    public void tick(int sleep) {
        int oneSecond = (genereteXSeconds * 1000) / sleep;

        if (generatePrey == true) {
            if (genPrey > 0 && preyTimer >= oneSecond) {
                generatePrey(genPrey);
                Collections.sort(animals);
                preyTimer = 0;
            }
        }
        preyTimer++;
        iteration++;


        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).update(); //updating the groups
        }


        for (int i = 0; i < animals.size(); i++) {
            Animal an = animals.get(i);
            an.setSpeed(an.getSpeedMax());
            do {
                Position pos = an.act();
                if (pos.getX() < board.length && pos.getX() >= 0 && pos.getY() < board[0].length && pos.getY() >= 0) {
                    //TODO: @Philipp Group kills
                    if (bGetPos(pos) instanceof Prey && an instanceof Predator) {
                        if (((Predator) an).getHuntingGroup() != null) {
                            if (((Prey) bGetPos(pos)).getSize() > 1 && ((Predator) an).getHuntingGroup().getSize() > ((Prey) bGetPos(pos)).getSize()) {
                                ((Predator) an).kill((Prey) bGetPos(pos));
                            } else ((Prey) bGetPos(pos)).attack((Predator) an);
                        }
                    }
                    if (bGetPos(pos) instanceof Predator && an instanceof Prey) {
                        ((Prey) an).attack((Predator) bGetPos(pos));
                    }


                    move(pos, an);
                    an.setSpeed(an.getSpeed() - 1);
                }
            } while (an.getSpeed() > 0);

            if (an instanceof Predator) ((Predator) an).starve();

        }
        System.out.println("Pred : " + predators.size() + "Prey: " + prey.size() + "Predators killed: " + predKilled + "Prey eaten / nutriton intake: " + preyKilled + "/" + nutritionPerTick);
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

}
