// Heads up no limit hold 'em

import java.util.List;
import java.util.ArrayList;


public class HoldEmMatch implements PokerMatch {
    
    private int handsRemaining;
    private Player p1;
    private Player p2;
    private Deck deck;
    private int score; // amount player 1 is up by. (Negative when player 2 is ahead)
    private boolean button; // true when player1 is on the button
    private boolean act; // true when player1 has the action
    private String state; 

    private int p1c1; // player 1 card 1
    private int p1c2; // player 1 card 2
    private int p2c1; // player 2 card 1
    private int p2c2; // player 2 card 2
    private int f1; // flop 1
    private int f2; // flop 2
    private int f3; // flop 3
    private int t; // turn
    private int r; // river
    
    
    public HoldEmMatch(int numHands, Player p1, Player p2) {
        handsRemaining = numHands;
        this.p1 = p1;
        this.p2 = p2;
        deck = new Deck();
        score = 0;
        button = (Math.random() > .5);
        state = "newHand";        
        resetcards(); 
        play();
    }
    private void resetCards() {
        p1c1 = -1;
        p1c2 = -1;
        p2c1 = -1;
        p2c2 = -1;
        f1 = -1;
        f2 = -1;
        f3 = -1;
        t = -1;
        r = -1;
    }
    private void play() {
        while (handsRemaining > 0)
            switch(state) {
                case "newHand":
                    deck.shuffle();
                    p1c1 = deck.next();
                    p1c2 = deck.next();
                    p2c1 = deck.next();
                    p2c2 = deck.next();
                    button = !button;
                    act = button;
                    state = "preflop";
                    break;
                case: "preflop":
                    break;
            }
    }
    
    public int numPlayers() {
        return 2;    
    }

    public List<Player> players() {
        ArrayList<Player> players = new ArrayList<Player>();
    }

    //TODO: Change implementation to output to whatever platform the game runs on
    private void output (String s) {
        System.out.println(s);
    }
    
}
