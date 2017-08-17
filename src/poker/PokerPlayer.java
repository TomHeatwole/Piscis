public class PokerPlayer implements Player {

    public String name;
    public int chipCount;
    public PokerPlayer() {
        name = "";
        chipCount = 0;
    }

    public PokerPlayer(String name, int chipCount) {
        this.name = name;
        this.chipCount = chipCount;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return chipCount;
    }

    public int addChips(int c) {
        chipCount += c;
    } 

    public int removeChips(int c) {
        chipCount -= c;
    }
}
