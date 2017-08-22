// This class will provide static utility functions for all poker games (ex. checking hand strength)

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Poker {

    private static final int[] notFound = {-1};    

    // Returns hand strength for a given set of cards
    public static String handStrength(int[] cards) {
        int[] handStrengthArr = handStrengthArray(cards);
        return handStrengthArrayToHandStrength(handStrengthArr);
    }

    public static String handStrengthArrayToHandStrength(int[] handStrengthArr) {
        String ret = "";
        char abbrStrength = Deck.abbr(handStrengthArr[1]-1).charAt(0);
        char secondaryAbbrStrength; 
        switch (handStrengthArr[0]){
            case 8: //straight flush
                return abbrStrength + "-high straight flush of "; // + Deck.suitName(cards[0]); TODOL say suit of straightflush
            case 7: //quads
                return "Quad " + abbrStrength + "s";
            case 6: //full house
                secondaryAbbrStrength = Deck.abbr(handStrengthArr[2]-1).charAt(0);
                return abbrStrength + "s full of " + secondaryAbbrStrength + "s";
            case 5: //flush
                return abbrStrength + "-high flush of "; // + Deck.suitName(cards[0]); TODO: say suit of flush 
            case 4: //straight
                return abbrStrength + "-high straight";
            case 3: //trips
                return "trip " + abbrStrength + "s";
            case 2: //two pair
                secondaryAbbrStrength = Deck.abbr(handStrengthArr[2]-1).charAt(0);
                return "two pair: " + abbrStrength + "s and " + secondaryAbbrStrength + "s";
            case 1: //pair
                return "pair of " + abbrStrength + "s";
            case 0: //high card
                return "high card: " + abbrStrength;
            default:
                throw new java.lang.RuntimeException("Unexpected input for handStrength: ");
        }
    }

    // Returns strength in first index (high card: 0, pair: 1, 2 pair: 2, etc)
    // Returns output of tiebreakersi (aka subStrength) in second+ index
    // Example: As jh 7s 7h 7c --> [3, 7, 14, 11]
    public static int[] handStrengthArray(int[] cards) {
        int[] strengthArray = new int[6];
        int[] subStrength = new int[5];
        int[] valCount = new int[15];
        for (int i = 0; i < 5; i++) 
            valCount[Deck.strength(cards[i])]++;
        int numUnique = 0;
        int trips = -1; // This will help distinguish between trips & 2 pair
        int quads = -1; // This will help distinguish between quads & full house
        for (int i = 2; i < 15; i++)
            if (valCount[i] != 0) {
                numUnique++;
                if (valCount[i] == 3)
                    trips = i;
                else if (valCount[i] == 4)
                    quads = i;
            }
        switch (numUnique) {
            case 2: // 2 unique cards: quads, full house
                if (quads != -1) { 
                    strengthArray[0] = 7; // quads
                    subStrength[0] = quads;
                    for (int i = 14; i >= 2; i--)
                        if (valCount[i] == 1)
                            subStrength[1] = i;
                } else {
                    strengthArray[0] = 6; // full house
                    for (int i = 14; i >= 2; i--)
                        if (valCount[i] == 3)
                            subStrength[0] = i;
                        else if (valCount[i] == 2)
                            subStrength[1] = i;
                }
                break;
            case 3: // 3 unique cards: trips, 2 pair
                if (trips != -1) {
                    strengthArray[0] = 3; // trips
                    subStrength[0] = trips;
                    int pos = 1;
                    for (int i = 14; i >= 2; i--) {
                        if (valCount[i] == 1) {
                            subStrength[pos] = i;
                            pos++; 
                        }  
                    }
                } else {
                    strengthArray[0] = 2; // 2 pair
                    int pos = 0;
                    for (int i = 14; i >= 2; i--)
                        if (valCount[i] == 2) {
                            subStrength[pos] = i;
                            pos++;
                        } else if (valCount[i] == 1) {
                            subStrength[2] = i;
                        }
                }
                break;
            case 4: // 4 unique cards: pair
                strengthArray[0] = 1; // pair
                subStrength = new int[4]; // [Pair value, high card 1, high card 2, high card 3]
                for (int i = 2; i < 15; i++)
                    if (valCount[i] == 2)
                        subStrength[0] = i;
                int pos = 1;
                for (int i = 14; i >= 2; i--)
                    if (valCount[i] == 1) {
                        subStrength[pos] = i;
                        pos++;
                    }
                break;
            case 5: // 5 unique cards: straightflush, flush, straight, high card
                int[] s = straight(cards);
                int[] f = flush(cards);
                if (s[0] > -1 && f[0] > -1) {
                    strengthArray[0] = 8; // straightflush
                    subStrength = s; // Highest card in straight will be same as straightflush
                } else if (f[0] > -1) {
                    strengthArray[0] = 5; // flush
                    subStrength = f;
                } else if (s[0] > -1) {
                    strengthArray[0] = 4; // straight
                    subStrength = s;
                } else {
                    strengthArray[0] = 0; // high card
                    subStrength = highCard(cards);
                }
                break;

        }
        for (int i = 0; i < subStrength.length; i++)
            strengthArray[i + 1] = subStrength[i];
        return strengthArray;
    }

    public static int[] handStrengthArray(int a, int b, int c, int d, int e) {
        int[] cards = {a, b, c, d, e};
        return handStrengthArray(cards);
    }


    // Returns values in flush in descending order or -1 if no flush
    private static int[] flush(int[] cards) {
        for (int i = 0; i < cards.length - 1; i++)
            if (Deck.suitNum(cards[i]) != Deck.suitNum(cards[i+1]))
                return notFound;
        return highCard(cards); // Order of substrength is same for flush and high card
    }

    // Returns highest value in straight or -1 if no straight
    private static int[] straight (int[] cards) {
        int[] vals = new int[5];
        for (int i = 0; i < 5; i++)
            vals[i] = Deck.strength(cards[i]);
        Arrays.sort(vals);
        for (int i = 0; i < 4; i++)
            if (vals[i] != vals[i+1] - 1)
                if (i != 3 || vals[3] != 5 || vals[4] != 14) // check for wheel
                    return notFound;
                else {
                    int[] r = {5};
                    return r;
                }
        int[] r = {vals[4]};
        return r;
    }

    // Returns 5 highest cards in descending order
    private static int[] highCard (int[] cards) {
        int[] r = new int[5];
        for (int i = 0; i < 5; i++)
            r[i] = Deck.strength(cards[i]);
        Arrays.sort(r); 
        reverse(r);
        return r; 
    }

    // Array reversal is an important utility function for this class
    private static void reverse(int[] r) {
        for (int i = 0; i < r.length / 2; i++) {
            int temp = r[i];
            r[i] = r[r.length - 1 - i];
            r[r.length - 1 - i] = temp;
        }
    }

    // positive if cards1 wins, negative if cards 2 wins, 0 if tie
    // TODO: It may be necessary later to move this to a comparator 
    public static int compareHands(int[] cards1, int[] cards2) {
        int[] s1 = handStrengthArray(cards1);
        int[] s2 = handStrengthArray(cards2);
        for (int i = 0; i < s1.length; i++)
            if (s1[i] > s2[i])
                return 1;
            else if (s1[i] < s2[i])
                return -1;
        return 0;
    }

    public static void generateCombinations(int arr[], int data[], int start,
                                int end, int index, int r, List<int[]> combos) {
        if (index == r) {
            int[] combo = new int[r];
            for (int j = 0; j < r; j++)
                combo[j] = data[j];
            combos.add(combo);
            return;
        }
        for (int i=start; i<=end && end-i+1 >= r-index; i++) {
            data[index] = arr[i];
            generateCombinations(arr, data, i+1, end, index+1, r, combos);
        }
    }
}
