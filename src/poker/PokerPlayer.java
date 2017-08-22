public class PokerPlayer implements Player {

    private String name;
    private int chipCount;
    private int seatNumber;
    private int[] hand;
    private int numCards;

    public PokerPlayer() {
        this.name = "";
        this.chipCount = 0;
        this.seatNumber = 0;
        this.hand = new int[50]; 
        this.numCards = 0;      
    }

    public PokerPlayer(String name, int chipCount, int seatNumber) {
        this.name = name;
        this.chipCount = chipCount;
        this.seatNumber = seatNumber;
        this.hand = new int[50];
        this.numCards = 0;
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

    public int getSeatNum(){
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

    public void dealCard(int c) {
        hand[numCards] = c;
        numCards++;
    }

    public void resetHand() {
        numCards = 0;
    }
}
