public class Main {


    public static void main(String[] args) {

        try {
            BoardManager b = new BoardManager(10, 10, 90, 10);
            b.test();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
