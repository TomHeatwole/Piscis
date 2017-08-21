// Heads up no limit hold 'em

import java.util.List;
import java.util.ArrayList;


public class HoldEmMatch extends PokerMatch {
    
    private int handsRemaining;
    private Deck deck;

    private int[] board; 
    
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
    
    public void processStreet(int street) {
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
                output("f:" + Deck.abbr(board[0]) +  Deck.abbr(board[1]) + Deck.abbr(board[2]));                
                break;
            case 2: // turn
                deck.next(); // burn
                board[3] = deck.next();
                output("t:" + Deck.abbr(board[4]));
                break;
            case 3: // river
                deck.next(); // burn
                board[4] = deck.next();
                output("r:" + Deck.abbr(board[4]));
                break;
        }
    }

    public List<Integer> showdown() {
        List<Integer> w  = new ArrayList<Integer>();
        int result = Poker.compareHands(Players.get(0), Players.get(1));
        if (result == 1)
            w.add(0);
        else if (result == -1)
            w.add(1);
        else {
            w.add(0);
            w.add(1);
        }
        return w;
        
    }
    
    private int[] bestHand (Player p) {
        int[] best = Poker.handStrengthArray(board);
        int[] next = Poker.handStrengthArray(board[0], board[1], board[2], board[3], p.getHand()[0]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[0], board[1], board[2], board[3], p.getHand()[1]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[0], board[1], board[2], board[4], p.getHand()[0]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[0], board[1], board[2], board[4], p.getHand()[1]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[0], board[1], board[3], board[4], p.getHand()[0]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[0], board[1], board[3], board[4], p.getHand()[1]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[0], board[2], board[3], board[4], p.getHand()[0]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[0], board[2], board[3], board[4], p.getHand()[1]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[1], board[2], board[3], board[4], p.getHand()[0]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[1], board[2], board[3], board[4], p.getHand()[1]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;

        int[] next = Poker.handStrengthArray(board[0], board[1], board[2], p.getHand()[0], p.getHand()[1]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[0], board[1], board[3], p.getHand()[0], p.getHand()[1]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[0], board[1], board[4], p.getHand()[0], p.getHand()[1]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[0], board[2], board[3], p.getHand()[0], p.getHand()[1]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[0], board[2], board[4], p.getHand()[0], p.getHand()[1]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[0], board[3], board[4], p.getHand()[0], p.getHand()[1]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[1], board[2], board[3], p.getHand()[0], p.getHand()[1]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[1], board[2], board[4], p.getHand()[0], p.getHand()[1]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[1], board[3], board[4], p.getHand()[0], p.getHand()[1]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
        int[] next = Poker.handStrengthArray(board[2], board[3], board[4], p.getHand()[0], p.getHand()[1]);
        best = (Poker.compareHands(best, next) == 1) ? best : next;
    
        return best;
    }

    public List<PokerPlayer> getPlayers() {
        return players;
    }

    public int[] processHand(List<PokerPlayer> players, Deck deck, int button) {
        return (new int[0]);
    }
}
