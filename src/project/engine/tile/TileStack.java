package project.engine.tile;

import java.util.ArrayList;
import java.util.List;

public class TileStack {

    /*
    Each board tile contains a TileStack.
    Multiple tiles can be in the same Position.
    Predator -> Larger Prey
     */

    private List<Tile> stack;


    public TileStack() {
        new TileStack(new TileEmpty());
    }

    public TileStack(Tile t){
        stack = new ArrayList();
        stack.add(t);
    }

    public List<Tile> getStack() {
        return stack;
    }
}
