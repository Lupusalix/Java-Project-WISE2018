package tests;

import project.engine.util.Point2;

public class Tests {

    public static void main(String[] args){

        Point2 p1 = new Point2(2,2);
        Point2 p2 = new Point2(2,2);

        if(p1.equals(p2))System.out.println("Equals");
        if(p1==p2)System.out.println("==");


    }


}
