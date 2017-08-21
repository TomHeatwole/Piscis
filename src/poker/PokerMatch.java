import java.util.*;

public abstract class PokerMatch implements Match {
    
    private int handsRemaining;
    private int numStreets; 
    public PokerPlayer[] players;
    private List<PokerPlayer> playersInHand;
    private Map<PokerPlayer,Integer> net; 
    private Map<PokerPlayer,Integer> results;
    public Deck deck;
    private int button; // position of the button 
    private int initialChipCounts;
    private boolean isHandOver; 
    private int potSize;
    private int bigBlind;
    private int smallBlind;
    private int ante;

    public PokerMatch(int numHands, int numPlayers, int initialChipCounts, int numStreets, int[] structure) {
        this.handsRemaining = numHands;
        this.deck = new Deck();
        this.net = new HashMap<PokerPlayer,Integer>();
        this.initialChipCounts = initialChipCounts; 
        this.numStreets = numStreets;
        this.playersInHand = new ArrayList<PokerPlayer>();
        this.results = new HashMap<PokerPlayer,Integer>();
        initializePlayers(numPlayers, initialChipCounts);
        this.smallBlind = structure[0];
        this.bigBlind = structure[1];
        this.ante = structure[2]; 
    }
    
    public void play() {
        button = (int)(Math.random() * players.length); 
        while (handsRemaining > 0){
            isHandOver = false;
            deck.shuffle();
            for(PokerPlayer p: players){
                playersInHand.add(p);
            }
            for(int street = 0; street < numStreets+1; street++){
                processStreet(street); 
                processBetting(street); 
                if(isHandOver){//everyone folded except one
                    break; 
                }
            }
            if(!isHandOver){
                processShowdown();
            }
            updateNetAndResetCounts(); 
            button = (button + 1) % players.length; 
            handsRemaining--; 
        }
    }
    
    public abstract List<PokerPlayer> showdown();

    public void processShowdown(){
        List<PokerPlayer> winners = showdown(); 
        int amountEachPlayerWins = potSize / winners.size();
        for(PokerPlayer p: winners){
            results.put(p, results.get(p) + amountEachPlayerWins);
        } 
    }

    public void processBetting(int street){
        Map<PokerPlayer, Integer> streetResults = new HashMap<PokerPlayer, Integer>();
        for(PokerPlayer p: playersInHand){
            streetResults.put(p,ante * -1);
        }
        int blindsToPost = 0;
        if(smallBlind > 0 && bigBlind > 0 && street == 0)
            blindsToPost = 2;
        boolean headsUpPreflop = (street == 0 && players.length == 2);
        int posToAct = (button + 1) % players.length;
        if(headsUpPreflop)
            posToAct = button;
        int initialPosToAct = (posToAct + blindsToPost) % players.length;
        boolean rotationComplete = false; 
        while(!(rotationComplete && (playersInHand.size() == 1 || valsAreAllTheSame(streetResults)))){
            if(headsUpPreflop && blindsToPost == 0){
                if(posToAct != button)
                    rotationComplete = true;
            }
            else if(posToAct == initialPosToAct && blindsToPost == 0)
                rotationComplete = true;
            PokerPlayer playerToAct = players[posToAct]; 
            if(!playersInHand.contains(playerToAct)){
                posToAct = (posToAct + 1) % players.length;
                continue;
            }
            if(blindsToPost == 2){
                posToAct = (posToAct + 1) % players.length;
                streetResults.put(playerToAct, streetResults.get(playerToAct) - smallBlind);
                potSize += smallBlind;
                blindsToPost--;
                output("Player " + playerToAct.getSeatNum() + " posts small blind.");
                continue;
            }
            if(blindsToPost == 1){
                posToAct = (posToAct + 1) % players.length;
                streetResults.put(playerToAct, streetResults.get(playerToAct) - bigBlind);
                potSize += bigBlind;
                blindsToPost--;
                output("Player " + playerToAct.getSeatNum() + " posts big blind.");
                continue;
            } 
            String input = processInput(playerToAct);
            if(!validateInput(input, streetResults, playerToAct)){
                //what should we do if the input is wrong?
            }
            output("Player " + playerToAct.getSeatNum() + " did " + input);
            String[] splitInp = input.split(" ");
            String action = splitInp[0];
            switch(action){
                case "check":
                    break;
                case "bet":
                    streetResults.put(playerToAct, streetResults.get(playerToAct) - Integer.parseInt(splitInp[1]));
                    potSize += Integer.parseInt(splitInp[1]); 
                    break;
                case "call":
                    int minVal = getMinVal(streetResults);
                    potSize += streetResults.get(playerToAct) - minVal;
                    streetResults.put(playerToAct, minVal);
                    break;
                case "raise":
                    potSize += Integer.parseInt(splitInp[1]) + streetResults.get(playerToAct);
                    streetResults.put(playerToAct, -1 * Integer.parseInt(splitInp[1]));
                    break;
                case "fold":
                    results.put(playerToAct, results.get(playerToAct) + streetResults.get(playerToAct));
                    playersInHand.remove(playerToAct);
                    streetResults.remove(playerToAct);
                default:
                    break;
            } 
            posToAct = (posToAct + 1) % players.length;
        }
        if(playersInHand.size() == 1){
            streetResults.put(playersInHand.get(0), streetResults.get(playersInHand.get(0)) + potSize);
            isHandOver = true;
        }
        for(Map.Entry<PokerPlayer,Integer> entry: streetResults.entrySet()){
            results.put(entry.getKey(), results.get(entry.getKey()) + entry.getValue());
        }
    }  

    public int getMinVal(Map<PokerPlayer,Integer> m){
        int min = 100000000;
        for(Map.Entry<PokerPlayer,Integer> entry: m.entrySet()){
            if(entry.getValue() < min)
                min = entry.getValue();
        }
        return min;
    }

    //TODO: Actually validate input
    public boolean validateInput(String input, Map<PokerPlayer,Integer> streetResults, PokerPlayer playerToAct){
        return true;
    }

    //TODO: Make this not come form System.in
    public String processInput(PokerPlayer playerToAct){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter input for player " + playerToAct.getSeatNum());
        return s.nextLine(); 
    }

    public boolean valsAreAllTheSame(Map<PokerPlayer, Integer> m){
        int count = 0;
        int firstVal = 0; 
        for(Map.Entry<PokerPlayer,Integer> entry: m.entrySet()){
            count++;
            if(count == 1){
                firstVal = entry.getValue();
                continue;
            }
            if(entry.getValue() != firstVal){
                return false;
            }
        }
        return true;
    }

    public abstract void processStreet(int street);    

    private void updateNetAndResetCounts(){
        for(Map.Entry<PokerPlayer,Integer> entry: results.entrySet()){
            PokerPlayer p = entry.getKey();
            int result = entry.getValue();
            net.put(p, net.get(p) + result);
        }
        for(PokerPlayer p: players){
            p.setChips(initialChipCounts);
            results.put(p,0);
        } 
        potSize = 0;
    }
 
    private void initializePlayers(int numPlayers, int initialChipCounts){ 
        this.players = new PokerPlayer[numPlayers];
        for(int i = 0; i < numPlayers; i++){
            this.players[i] = new PokerPlayer("TODO Actual name", initialChipCounts, i);
        } 
        for(PokerPlayer p: players){
            this.net.put(p,0); 
            this.results.put(p,0);
        } 
    }

    public void deal(int n) {
        for (PokerPlayer p: players){
            String outputStr = "Player " + p.getSeatNum() + "'s hand: ";
            for (int i = 0; i < n; i++){
                int next = deck.next();
                p.dealCard(next);
                outputStr += Deck.abbr(next); 
            }
            output(outputStr);
        }
        // TODO: Add output
    }

    //TODO: Change implementation to output to whatever platform the game runs on
    public void output (String s) {
        System.out.println(s);
    }
    
}
