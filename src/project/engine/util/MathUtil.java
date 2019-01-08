package project.engine.util;

import java.util.*;

public class MathUtil {

    private MathUtil() {
    }

    /*
   Returns an Array of Point2 positions around the specified position defined by the radius.
     */
    public static ArrayList<Point2> gridBox(Point2 pos, int radius) {

        ArrayList<Point2> list = new ArrayList<>();

        Point2 corner = new Point2(pos.x - radius, pos.y - radius);

        int steps = radius * 2 + 1;

        for (int i = 0; i < steps; i++) {
            for (int f = 0; f < steps; f++) {

                Point2 point = new Point2(corner.x + i, corner.y + f);

                if (point != pos && BoardUtil.isMoveInGrid(point)) {
                    list.add(point);
                }
            }
        }

        return list;
    }

    private static HashMap<Point2, Point2> bfs(Point2 pos, Point2 target){

        Queue<Point2> queue = new LinkedList<>();
        ArrayList<Point2> explored = new ArrayList<>();
        HashMap<Point2, Point2> prev = new HashMap<>();

        queue.add(pos);
        explored.add(pos);
        System.out.println("Target: " + target);
        System.out.println("Start: " + pos);

        while(!queue.isEmpty()){
            Point2 current = queue.remove();
            System.out.println(current);
            explored.add(current);
            if(current.x == target.x && current.y == target.y){
                return prev;
            }else{
                for(Point2 p : current.getNeighbours()){
                    if(!explored.contains(p) && BoardUtil.isMoveInGrid(p) && (BoardUtil.isEmpty(p) || (p.x == target.x && p.y == target.y)) && !queue.contains(p)){
                        queue.add(p);
                        prev.put(p, current);
                        System.out.println(p + " Q:" + queue.size());
                    }
                }
            }

        }
        System.out.println("returned null");
        return null;
    }

    public static List<Point2> reverseBFS(Point2 pos, Point2 target){
        HashMap<Point2, Point2> prev = bfs(pos, target);

        System.out.println("reverse + " + prev);
        if(prev != null) {
            boolean found = false;
            ArrayList<Point2> way = new ArrayList<>();

            Point2 prevPos = prev.get(target);
            System.out.println("prev: " +prevPos);

            if (prevPos != null) {
                way.add(prevPos);
                while (!found) {
                    Point2 pre = prev.get(way.get(way.size()-1));
                    System.out.println(pre);
                    if (pre != null) {
                        way.add(prev.get(pos));
                    } else {
                        found = true;
                    }
                }
            }

            Collections.reverse(way);

            return way;
        }
        return null;
    }

}
