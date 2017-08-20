public class PokerPlayer implements Player {

    public String name;
    public int chipCount;
    public int seatNumber;

    public PokerPlayer() {
        name = "";
        chipCount = 0;
        seatNumber = 0;
    }

    public PokerPlayer(String name, int chipCount, int seatNumber) {
        this.name = name;
        this.chipCount = chipCount;
        this.seatNumber = seatNumber;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return chipCount;
    }

    public int getSeatNumber(){
        return seatNumber;
    }

    public void addChips(int c) {
        chipCount += c;
    } 

    public void removeChips(int c) {
        chipCount -= c;
    }
}
