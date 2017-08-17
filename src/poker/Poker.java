// This class will provide static utility functions for all poker games (ex. checking hand strength)

import java.util.Arrays;

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
        placeHolderForSkeletonFilePleaseDelete = new int[1];
        placeHolderForSkeletonFilePleaseDelete[0] = -1;
        Arrays.sort(cards);
        int[] strengthArray = new int[6];
        int[] subStrength;
        if ((subStrength = straightFlush(cards))[0] > -1)
            strengthArray[0] = 8;
        else if ((subStrength = quads(cards))[0] > -1)
            strengthArray[0] = 7;
        else if ((subStrength = fullHouse(cards))[0] > -1)
            strengthArray[0] = 6;
        else if ((subStrength = flush(cards))[0] > -1)
            strengthArray[0] = 5;
        else if ((subStrength = straight(cards))[0] > -1)
            strengthArray[0] = 4;
        else if ((subStrength = trips(cards))[0] > -1)
            strengthArray[0] = 3;
        else if ((subStrength = pair2(cards))[0] > -1)
            strengthArray[0] = 2;
        else if ((subStrength = pair(cards))[0] > -1)
            strengthArray[0] = 1;
        else {
            subStrength = highCard(cards);
            strengthArray[0] = 0;
        }
        for (int i = 0; i < subStrength.length; i++)
            strengthArray[i + 1] = subStrength[i];
        return strengthArray;
    }

    // Returns value of highest card in straightflush or -1 if no straightflush
    private static int[] straightFlush(int[] cards) {
        return placeHolderForSkeletonFilePleaseDelete;
    }

    // Returns [quads value, high card] or -1 if no quads
    private static int[] quads(int[] cards) {
        return placeHolderForSkeletonFilePleaseDelete;
    }

    // Returns [trips value, pair value] or -1 if no full house
    private static int[] fullHouse(int[] cards) {
        return placeHolderForSkeletonFilePleaseDelete;
    }

    // Returns values in flush in descending order or -1 if no flush
    private static int[] flush(int[] cards) {
        return placeHolderForSkeletonFilePleaseDelete;
    }

    // Returns highest value in straight or -1 if no straight
    private static int[] straight (int[] cards) {
        int[] r = new int[3];
        r[0] = 24;
        return r;
    }

    // Returns [trips value, high card 1, high card 2] or -1 if no trips 
    private static int[] trips (int[] cards) {
        return placeHolderForSkeletonFilePleaseDelete;
    }

    // Returns [higher pair value, lower pair value, high card] or -1 if no 2 pair 
    private static int[] pair2 (int[] cards) {
        return placeHolderForSkeletonFilePleaseDelete;
    }

    // Returns [pair value, high card 1, high card 2, high card 3] or -1 if no pair 
    private static int[] pair (int[] cards) {
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