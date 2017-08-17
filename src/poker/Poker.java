// This class will provide static utility functions for all poker games (ex. checking hand strength)

public class Poker {

    public static int[] placeHolderForSkeletonFilePleaseDelete;    

    // Returns hand strength for a given set of cards
    public static String handStrength(int[] cards) {
        return "placeholder";
    }

    // Returns strength in first index (high card: 0, pair: 1, 2 pair: 2, etc)
    // Returns output of strength call in second+ index
    // Example: As jh 7s 7h 7c --> [3, 7, 13, 11]
    public static int[] handStrengthArray(int[] cards) {
        return placeHolderForSkeletonFilePleaseDelete;
    }

    // Returns value of highest card in straightflush or -1 if no straightflush
    public static int straightFlush(int[] cards) {
        return -1;
    }

    // Returns [quads value, high card] or -1 if no quads
    public static int[] quads(int[] cards) {
        return placeHolderForSkeletonFilePleaseDelete;
    }

    // Returns [trips value, pair value] or -1 if no full house
    public static int[] fullHouse(int[] cards) {
        return placeHolderForSkeletonFilePleaseDelete;
    }

    // Returns values in flush in descending order or -1 if no flush
    public static int[] flush(int[] cards) {
        return placeHolderForSkeletonFilePleaseDelete;
    }

    // Returns highest value in straight or -1 if no straight
    public static int straight (int[] cards) {
        return -1;
    }

    // Returns [trips value, high card 1, high card 2] or -1 if no trips 
    public static int[] trips (int[] cards) {
        return placeHolderForSkeletonFilePleaseDelete;
    }

    // Returns [higher pair value, lower pair value, high card] or -1 if no 2 pair 
    public static int[] pair2 (int[] cards) {
        return placeHolderForSkeletonFilePleaseDelete;
    }

    // Returns [pair value, high card 1, high card 2, high card 3] or -1 if no pair 
    public static int[] pair (int[] cards) {
        return placeHolderForSkeletonFilePleaseDelete;
    }

    // Returns 5 highest cards in descending order
    public static int[] highCard (int[] cards) {
        return placeHolderForSkeletonFilePleaseDelete;
    }

    // positive if cards1 wins, negative if cards 2 wins, 0 if tie
    // TODO: It may be necessary later to move this to a comparator 
    public static int compareHands(int[] cards1, int[] cards2) {
        return 0;
    }


}
