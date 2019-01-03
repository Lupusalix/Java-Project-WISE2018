import Tiles.Animal;
import Tiles.EmptyTile;
import Tiles.Position;
import Tiles.Predator;

public class Main {


    public static void main(String[] args) {
        EmptyTile[][] board = new EmptyTile[1][3];

        board[0][0] = new EmptyTile();
        board[0][2] = new Predator(0, 2, 3);
        board[0][1] = new Animal(new Position(0, 1), 2);


        for (EmptyTile x : board[0]) {


            if (x instanceof Animal) {
                if (x instanceof Predator)
                System.out.println("Predator");
                else System.out.println("Animal");

            }
            if (!(x instanceof Animal)) {
                System.out.println("Empty");
            }
//slökjdfglksdfjglöksdfjgl
        }

    }
}
