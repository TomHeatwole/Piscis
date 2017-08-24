import java.util.*;

public abstract class PokerMatch implements Match {
    
    private int handsRemaining;
    private int numStreets; 
    public PokerPlayer[] players;
    private List<PokerPlayer> playersInHand;
    private Map<PokerPlayer,Integer> net; 
    private Map<PokerPlayer,Integer> handResults;
    public Deck deck;
    private int button; // position of the button 
    private int initialChipCounts;
    private boolean isHandOver; 
    private int potSize;
    private int bigBlind;
    private int smallBlind;
    private int ante;

    public PokerMatch(int numHands, String[] playerNames, int initialChipCounts, int numStreets, int[] structure) {
        this.handsRemaining = numHands;
        this.deck = new Deck();
        this.net = new HashMap<PokerPlayer,Integer>();
        this.initialChipCounts = initialChipCounts; 
        this.numStreets = numStreets;
        this.playersInHand = new ArrayList<PokerPlayer>();
        this.handResults = new HashMap<PokerPlayer,Integer>();
        initializePlayers(playerNames, initialChipCounts);
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
                io.output("hand results: " + handResults);
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
            handResults.put(p, handResults.get(p) + amountEachPlayerWins);
            io.output("Player " + p + " wins " + amountEachPlayerWins);
        } 
    }

    public void processBetting(int street){
        Map<PokerPlayer, Integer> streetResults = new HashMap<PokerPlayer, Integer>();
        for(PokerPlayer p: playersInHand){
            streetResults.put(p,(street == 0 ? ante * -1 : 0));
        }
        int blindsToPost = 0;
        if(smallBlind > 0 && bigBlind > 0 && street == 0)
            blindsToPost = 2;
        boolean headsUpPreflop = (street == 0 && players.length == 2);
        int posToAct = (button + 1) % players.length;
        if(headsUpPreflop)
            posToAct = button;
        int lastPosToAct = (posToAct + blindsToPost - 1 + players.length) % players.length;
        boolean rotationComplete = false; 
        while(!(playersInHand.size() == 1 || rotationComplete && valsAreAllTheSame(streetResults))){
            if(headsUpPreflop && blindsToPost == 0){
                if(posToAct != button)
                    rotationComplete = true;
            }
            else if(posToAct == lastPosToAct && blindsToPost == 0)
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
                io.output("Player " + playerToAct + " posts small blind.");
                continue;
            }
            if(blindsToPost == 1){
                posToAct = (posToAct + 1) % players.length;
                streetResults.put(playerToAct, streetResults.get(playerToAct) - bigBlind);
                potSize += bigBlind;
                blindsToPost--;
                io.output("Player " + playerToAct + " posts big blind.");
                continue;
            } 
            String input = getInput(streetResults, playerToAct);
            io.output("Player " + playerToAct + " did " + input);
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
                    handResults.put(playerToAct, handResults.get(playerToAct) + streetResults.get(playerToAct));
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
            io.output("Player " + playersInHand.get(0) + " wins pot of " + potSize);
        }
        for(Map.Entry<PokerPlayer,Integer> entry: streetResults.entrySet()){
            handResults.put(entry.getKey(), handResults.get(entry.getKey()) + entry.getValue());
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
    
    public int getSecondMinVal(Map<PokerPlayer,Integer>m){
        int secondMin = 100000000;
        int min = 100000000;
        for(Map.Entry<PokerPlayer,Integer> entry: m.entrySet()){
            if(entry.getValue() < min){
                secondMin = min;
                min = entry.getValue();
            }
            else if(entry.getValue() < secondMin){
                secondMin = entry.getValue();
            }
        } 
        return secondMin;
    }

    public String getInput(Map<PokerPlayer,Integer> streetResults, PokerPlayer playerToAct){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter input for player " + playerToAct);
        String rawInp = s.nextLine(); 
        return processInput(rawInp, streetResults, playerToAct);
    } 

    public String processInput(String rawInp, Map<PokerPlayer,Integer> streetResults, PokerPlayer playerToAct){
        String action = rawInp.split(" ")[0]; 
        int maxBetOrRaise = playerToAct.getChips() + handResults.get(playerToAct);
        switch(action){
            case "bet":
                if(valsAreAllTheSame(streetResults)){
                    int amount = Integer.parseInt(rawInp.split(" ")[1]);
                    if(amount >= maxBetOrRaise){
                        if(maxBetOrRaise > 0)
                            return "bet " + maxBetOrRaise;
                        return "check";
                    }
                    if(amount >= bigBlind){
                        return rawInp;
                    }
                    return "check"; 
                }
                return processInput(rawInp.replace("bet","raise"), streetResults, playerToAct);
            case "call":
                if(!valsAreAllTheSame(streetResults))
                    return rawInp;
                return "check"; 
            case "raise":
                if(!valsAreAllTheSame(streetResults)){
                    int amount = Integer.parseInt(rawInp.split(" ")[1]);
                    if(amount >= maxBetOrRaise){
                        if(getMinVal(streetResults) > -1 * maxBetOrRaise)
                            return "raise " + maxBetOrRaise;
                        return "call";
                    }    
                    if(getMinVal(streetResults) - -1 * amount >= bigBlind && getMinVal(streetResults) - -1 * amount >= getSecondMinVal(streetResults) - getMinVal(streetResults)){
                        return rawInp;
                    }
                    return "call";
                }
                return processInput(rawInp.replace("raise","bet"), streetResults, playerToAct); 
            case "fold":
                return rawInp;
            default:
                if(valsAreAllTheSame(streetResults))
                    return "check";
                return "fold";
        }
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
        for(Map.Entry<PokerPlayer,Integer> entry: handResults.entrySet()){
            PokerPlayer p = entry.getKey();
            int result = entry.getValue();
            net.put(p, net.get(p) + result);
        }
        io.output("net profits up to this point: " + net);
        for(PokerPlayer p: players){
            p.setChips(initialChipCounts);
            handResults.put(p,0);
        } 
        potSize = 0;
    }
 
    private void initializePlayers(String[] playerStrs, int initialChipCounts){ 
        this.players = new PokerPlayer[playerStrs.length];
        for(int i = 0; i < playerStrs.length; i++){
            this.players[i] = new PokerPlayer(playerStrs[i], initialChipCounts, i);
        } 
        for(PokerPlayer p: players){
            this.net.put(p,0); 
            this.handResults.put(p,0);
        } 
    }

    public void deal(int n) {
        for (PokerPlayer p: players){
            String outputStr = "Player " + p + "'s hand: ";
            for (int i = 0; i < n; i++){
                int next = deck.next();
                p.dealCard(next);
                outputStr += Deck.abbr(next); 
            }
            io.output(outputStr);
        }
    }

}
