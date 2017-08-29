public class Driver {
    public static void main (String[] args) {
        int[] structure = {1,2,0};
        String[] names = {"Eli","Tom"};
        HoldEmMatch m = new HoldEmMatch(10, names, 200, structure, 500);
        m.play();
    }
}
