// Heads up no limit hold 'em

import java.util.List;
import java.util.ArrayList;

public abstract class PokerMatch implements Match {
    
    private int handsRemaining;
    private List<Player> players;
    private Map<Player,int> net; 
    private Deck deck;
    private int button; // position of the button 
    private int initialChipCounts;
 
    public PokerMatch(int numHands, int numPlayers, int initialChipCounts) {
        this.handsRemaining = numHands;
        this.deck = new Deck();
        this.net = new HashMap<Player,int>();
        this.initialChipCounts = initialChipCounts; 
        intializePlayers(numPlayers, initialChipCounts); 
        play();
    }
    
    private void play() {
        button = Math.random() * players.size(); 
        while (handsRemaining > 0){
            deck.shuffle();
            Map<Player,int> results = processHand(players, deck, button); 
            updateNetAndResetCounts(results); 
            button = (button + 1) % players.size(); 
            handsRemaining--; 
        }
    }
    
    //abstract since this is where different poker variants differ 
    public abstract Map<Player,int> processHand(List<Player> players, Deck deck, int button){}; 

    private void updateNetAndResetCounts(Map<Player,int> results){
        for(Map.Entry<Player,int> entry: results.entrySet()){
            player = entry.getKey();
            result = entry.getValue();
            net.put(player, net.get(player) + result); 
        }
        for(Player p: players){
            p.setChips(initialChipCounts);
        } 
    }
 
    private void initializePlayers(int numPlayers, int initialChipCounts){ 
        for(int i = 0; i < numPlayers; i++){
            this.players.add(new Player("TODO Actual name", initialChipCounts, i));
        } 
        for(Player p: players){
            this.net.put(p,0); 
        } 
    }

    //TODO: Change implementation to output to whatever platform the game runs on
    private void output (String s) {
        System.out.println(s);
    }
    
}
