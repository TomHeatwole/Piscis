// Heads up no limit hold 'em

import java.util.List;
import java.util.ArrayList;

public abstract class PokerMatch implements Match {
    
    private int handsRemaining;
    private List<Player> players;
    private Deck deck;
    private int button; // position of the button 
    private int streetsToShowdown; 
    private int initialChipCounts; 
 
    public PokerMatch(int numHands, int numPlayers, int streetsToShowdown, int initialChipCounts) {
        this.handsRemaining = numHands;
        this.deck = new Deck();
        this.streetsToShowdown = streetsToShowdown; 
        intializePlayers(numPlayers, initialChipCounts); 
        play();
    }
    
    private void play() {
        while (handsRemaining > 0){
            /*pseudocode:
            for int i = 0 -> streetsToShowdown
                call abstract process street method; e.g. in hold em it would be implemented as processStreet(0)->deal two cards to each player, 1-> flop, 2->turn, 3->river
                call non-abstract method to process betting round
                if betting round results in end of hand, break.
            if didn't break out of for loop, call abstrat showdown method.
            Call method to assign chips to appropriate players given result.*/
            handsRemaining--; 
        }
    }
   
    private void initializePlayers(int numPlayers, int initialChipCounts){ 
        for(int i = 0; i < numPlayers; i++){
            this.players.add(new Player("TODO Actual name", initialChipCounts, i));
        } 
    }

    //TODO: Change implementation to output to whatever platform the game runs on
    private void output (String s) {
        System.out.println(s);
    }
    
}
