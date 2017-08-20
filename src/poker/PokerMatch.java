// Heads up no limit hold 'em

import java.util.*;

public abstract class PokerMatch implements Match {
    
    private int handsRemaining;
    private List<PokerPlayer> players;
    private Map<PokerPlayer,Integer> net; 
    private Deck deck;
    private int button; // position of the button 
    private int initialChipCounts;
 
    public PokerMatch(int numHands, int numPlayers, int initialChipCounts) {
        this.handsRemaining = numHands;
        this.deck = new Deck();
        this.net = new HashMap<PokerPlayer,Integer>();
        this.initialChipCounts = initialChipCounts; 
        initializePlayers(numPlayers, initialChipCounts); 
        play();
    }
    
    private void play() {
        button = (int)(Math.random() * players.size()); 
        while (handsRemaining > 0){
            deck.shuffle();
            Map<PokerPlayer,Integer> results = processHand(players, deck, button); 
            updateNetAndResetCounts(results); 
            button = (button + 1) % players.size(); 
            handsRemaining--; 
        }
    }
    
    //abstract since this is where different poker variants differ 
    public abstract Map<PokerPlayer,Integer> processHand(List<PokerPlayer> players, Deck deck, int button);

    private void updateNetAndResetCounts(Map<PokerPlayer,Integer> results){
        for(Map.Entry<PokerPlayer,Integer> entry: results.entrySet()){
            PokerPlayer player = entry.getKey();
            int result = entry.getValue();
            net.put(player, net.get(player) + result); 
        }
        for(PokerPlayer p: players){
            p.setChips(initialChipCounts);
        } 
    }
 
    private void initializePlayers(int numPlayers, int initialChipCounts){ 
        for(int i = 0; i < numPlayers; i++){
            this.players.add(new PokerPlayer("TODO Actual name", initialChipCounts, i));
        } 
        for(PokerPlayer p: players){
            this.net.put(p,0); 
        } 
    }

    //TODO: Change implementation to output to whatever platform the game runs on
    private void output (String s) {
        System.out.println(s);
    }
    
}
