// Heads up no limit hold 'em

import java.util.*;

public abstract class PokerMatch implements Match {
    
    private int handsRemaining;
    private int numStreets; 
    private List<PokerPlayer> players;
    private Map<PokerPlayer,Integer> net; 
    private Map<PokerPlayer,Integer> results;
    private Deck deck;
    private int button; // position of the button 
    private int initialChipCounts;
    private boolean isHandOver; 

    public PokerMatch(int numHands, int numPlayers, int initialChipCounts, int numStreets) {
        this.handsRemaining = numHands;
        this.deck = new Deck();
        this.net = new HashMap<PokerPlayer,Integer>();
        this.initialChipCounts = initialChipCounts; 
        this.numStreets = numStreets;
        initializePlayers(numPlayers, initialChipCounts); 
    }
    
    private void play() {
        button = (int)(Math.random() * players.size()); 
        while (handsRemaining > 0){
            isHandOver = false;
            deck.shuffle();
            for(PokerPlayer p: players)
                results.put(p,0); 
            for(int street = 0; street < numStreets+1; street++){
                processStreet(street); 
                processBetting(); 
                if(isHandOver){
                    break; 
                }
            }
            if(!isHandOver){
                processShowdown();
            }
            updateNetAndResetCounts(results); 
            button = (button + 1) % players.size(); 
            handsRemaining--; 
        }
    }
    
    private abstract int showdown(){};

    //TODO: call showdown, update results
    public void processShowdown(){
    }

    //TODO: process all betting
    public void processBetting(){
    }   

    public abstract void processStreet(int street);    

    //abstract since this is where different poker variants differ 
    public abstract int[] processHand(List<PokerPlayer> players, Deck deck, int button);

    private void updateNetAndResetCounts(Map<PokerPlayer,Integer> results){
        for(Map.Entry<PokerPlayer,Integer> entry: results.entrySet()){
            PokerPlayer p = entry.getKey();
            int result = entry.getValue();
            net.put(p, net.get(p) + result);
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
            this.results.put(p,0);
        } 
    }

    //TODO: Change implementation to output to whatever platform the game runs on
    private void output (String s) {
        System.out.println(s);
    }
    
}
