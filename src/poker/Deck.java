public class Deck {

    private int[] cards;
    private int pos;

    public Deck() {
        cards = new int[52];
        for (int i = 0; i < 52; i++) {
            cards[i] = i;
        }
        this.shuffle();
    }

    public void shuffle() {
        pos = 0;
        for (int i = 0; i < 52; i++) {
            int swapPos = (int)(Math.random() * 52);
            int temp = cards[i];
            cards[i] = cards[swapPos];
            cards[swapPos] = temp;
        }
    }

    public int next() {
        if (pos > 51)
            throw new java.lang.RuntimeException("Deck is empty");
        else
            return cards[pos++];
    }

    public boolean hasNext() {
        return (pos < 52);        
    }

    public int getPos() {
        return pos;
    }

    public static String abbr(int c) { // abbreviation
        String v = "" + (c%13 + 1);
        switch (c%13) {
            case 0:
                v = "A";
                break;
            case 9:
                v = "T";
                break;
            case 10:
                v = "J";
                break;
            case 11:
                v = "Q";
                break;
            case 12:
                v = "K";
                break;
        }
        if (c < 0 || c > 51)
            throw new java.lang.RuntimeException("Invalid card");        
        else if (c < 13)
            return v + "s";
        else if (c < 26)
            return v + "d";
        else if (c < 39)
            return v + "h";
        return v + "c";
    }

    public static String name(int c) {
        return name(abbr(c));
    }

    public static String name(String a) { // takes in abbreviation
        String ret = "" + a.charAt(0);
        switch(a.charAt(0)) {
            case 'A':
                ret += "ce";
                break;
            case 'T':
                ret += "en";
                break;
            case 'J':
                ret += "ack";
                break;
            case 'Q':
                ret += "ueen";
                break;
            case 'K':
                ret += "ing";
                break;
        }
        switch(a.charAt(1)) {
            case 's':
                ret +=  " of Spades";
                break; 
            case 'd':
                ret += " of Diamonds";
                break; 
            case 'h':
                ret += " of Hearts";
                break; 
            case 'c':
                ret += " of Clubs";
                break; 
        }        
        return ret;
    }

    // strength of card (2 --> 2, 3 --> 3, ... , J --> 11, ... A --> 14)
    public static int strength(int c) {
        if (c%13 == 0)
            return 14;
        return c%13 + 1;
    }

    // helpful utility function for comparisons in card games
    public static int suitNum(int c) {
        if (c < 0 || c > 51)
            throw new java.lang.RuntimeException("Invalid card");        
        else if (c < 13)
            return 1;
        else if (c < 26)
            return 2;
        else if (c < 39)
            return 3;
        return 4;
    }

    public static void main (String[] args) {
        int[] hand = {3, 6, 1, 7, 0};
        System.out.println(Poker.handStrengthArray(hand)[0]);
        System.out.println(Poker.handStrengthArray(hand)[1]);
        System.out.println(Poker.handStrengthArray(hand)[2]);
        System.out.println(Poker.handStrengthArray(hand)[3]);
        System.out.println(Poker.handStrengthArray(hand)[4]);
        System.out.println(Poker.handStrengthArray(hand)[5]);
    }
}
