// Heads up no limit hold 'em

import java.util.List;
import java.util.ArrayList;


public class HoldEmMatch extends PokerMatch {
    
    private int handsRemaining;
    private Deck deck;

    int[] board; 
    
    public HoldEmMatch(int numHands, int numPlayers, int initialChipCounts) {
        this.super(numHands);
        resetCards();
        board = new int[5];
    }

    private void resetBoard() {
        for (int i = 0; i < 5; i++) {
            board[i] = -1;
        }
    }
    
    private void processStreet(int street) {
        switch (street) {
            case 0: // preflop
                resetBoard();
                deal(2);
                break;
            case 1: // flop
                deck.next(); // burn
                board[0] = deck.next();
                board[1] = deck.next();
                board[2] = deck.next();
                output("f:" + Deck.getAbbr(board[0]) +  Deck.getAbbr(board[1]) + Deck.getAbbr(board[2]));                
                break;
            case 2: // turn
                deck.next(); // burn
                board[3] = deck.next();
                output("t:" + Deck.getAbbr(board[4]));
                break;
            case 3: // river
                deck.next(); // burn
                board[4] = deck.next();
                output("r:" + Deck.getAbbr(board[4]));
                break;
        }
    }

    private int showdown() {
        
    }
}
