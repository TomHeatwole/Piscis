public class PokerPlayer implements Player {

    public String name;
    public int chipCount;
    public int seatNumber;
    public int[] hand;

    public PokerPlayer() {
        this.name = "";
        this.chipCount = 0;
        this.seatNumber = 0;
        this.hand = new int[0]; 
    }

    public PokerPlayer(String name, int chipCount, int seatNumber) {
        this.name = name;
        this.chipCount = chipCount;
        this.seatNumber = seatNumber;
        this.hand = new int[0];
    }

    public int[] getHand(){
        return hand;
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

    public void setChips(int c){
        chipCount = c;
    }
    public void setHand(int[] hand){
        this.hand = hand; 
    } 
}
