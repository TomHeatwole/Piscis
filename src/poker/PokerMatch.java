import java.util.*;

public abstract class PokerMatch implements Match {
    
    public IO[] io;
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
    public int timePerMove;

    public PokerMatch(int numHands, String[] playerNames, int initialChipCounts, int numStreets, int[] structure, int timePerMove) {
        this.io = new IO[playerNames.length];
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
        this.timePerMove = timePerMove;
    }
    
    public void play() {
        sendStartOfMatchInfo();
        button = (int)(Math.random() * players.length); 
        while (handsRemaining > 0){
            sendStartOfHandInfo();
            isHandOver = false;
            deck.shuffle();
            for(PokerPlayer p: players){
                playersInHand.add(p);
            }
            for(int street = 0; street < numStreets+1; street++){
                processStreet(street); 
                processBetting(street); 
                output("hand results: " + handResults);
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
    
    private void sendStartOfHandInfo(){
        output("handsLeft " + handsRemaining);
        output("button p" + button);
    }

    private void sendStartOfMatchInfo(){
        output("timePerMove " + timePerMove);
        output("smallBlind " + smallBlind);
        output("bigBlind " + bigBlind);
        output("ante " + ante);
        output("hands " + handsRemaining);
        output("initialChipCounts " + initialChipCounts);
        for(int i = 0; i < players.length; i++){
            output("yourSeatNumber " + i, i);
        }
    }

    public abstract List<PokerPlayer> showdown();

    public void processShowdown(){
        List<PokerPlayer> winners = showdown(); 
        int amountEachPlayerWins = potSize / winners.size();
        for(PokerPlayer p: winners){
            handResults.put(p, handResults.get(p) + amountEachPlayerWins);
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
                output("p" + posToAct + " small");
                posToAct = (posToAct + 1) % players.length;
                streetResults.put(playerToAct, streetResults.get(playerToAct) - smallBlind);
                potSize += smallBlind;
                blindsToPost--;
                continue;
            }
            if(blindsToPost == 1){
                output("p" + posToAct + " big");
                posToAct = (posToAct + 1) % players.length;
                streetResults.put(playerToAct, streetResults.get(playerToAct) - bigBlind);
                potSize += bigBlind;
                blindsToPost--;
                continue;
            } 
            String input = getInput(streetResults, playerToAct);
            output("p" + posToAct + " " + input);
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
            output(playersInHand.get(0) + " wins pot of " + potSize);
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

    private int getToCall(Map<PokerPlayer,Integer> streetResults, PokerPlayer playerToAct){
        return getMinVal(streetResults) * -1 + streetResults.get(playerToAct);  
    }

    private int getMinRaise(Map<PokerPlayer,Integer> streetResults, PokerPlayer playerToAct){
        int minRaise = getSecondMinVal(streetResults) - getMinVal(streetResults) * 2;
        minRaise = (minRaise < bigBlind - getMinVal(streetResults) ? bigBlind - getMinVal(streetResults) : minRaise);
        return minRaise;
    }

    public String getInput(Map<PokerPlayer,Integer> streetResults, PokerPlayer playerToAct){
        String outputStr = "action toCall " + getToCall(streetResults,playerToAct) + " minToRaise " + getMinRaise(streetResults, playerToAct) + " pot " + potSize; 
        output(outputStr,playerToAct.getSeatNum());
        //check-fold if they don't respond in time
        String rawInp = "check"; 
        long startMillis = System.currentTimeMillis();
        while(System.currentTimeMillis() - startMillis <= timePerMove){
            String inp = input(playerToAct.getSeatNum());    
            if(inp != ""){
                rawInp = inp;
                break;
            }
        }
        String processedInp = processInput(rawInp, streetResults, playerToAct);
        return processedInp;
    } 

    public String processInput(String rawInp, Map<PokerPlayer,Integer> streetResults, PokerPlayer playerToAct){
        int inputLength = rawInp.split(" ").length;
        String action = rawInp.split(" ")[0]; 
        int maxBetOrRaise = playerToAct.getChips() + handResults.get(playerToAct);
        switch(action){
            case "bet":
                if(valsAreAllTheSame(streetResults)){
                    if(inputLength < 2){ 
                        int minBet = bigBlind;    
                        return processInput("bet " + minBet, streetResults, playerToAct);
                    }
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
                    if(inputLength < 2){
                        int minRaise = getMinRaise(streetResults, playerToAct);
                        return processInput("raise " + minRaise, streetResults, playerToAct); 
                    }
                    int amount = Integer.parseInt(rawInp.split(" ")[1]);
                    if(amount >= maxBetOrRaise){
                        if(getMinVal(streetResults) > -1 * maxBetOrRaise)
                            return "raise " + maxBetOrRaise;
                        return "call";
                    }    
                    if(getMinVal(streetResults) + amount >= bigBlind && getMinVal(streetResults) + amount >= getSecondMinVal(streetResults) - getMinVal(streetResults)){
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
        String handResultStr = "handResult";
        String matchTotalStr = "matchTotal";
        for(Map.Entry<PokerPlayer,Integer> entry: handResults.entrySet()){
            PokerPlayer p = entry.getKey();
            int result = entry.getValue();
            net.put(p, net.get(p) + result);
            handResultStr += " p" + p.getSeatNum() + " " + result;
            matchTotalStr += " p" + p.getSeatNum() + " " + net.get(p);
        }
        output(handResultStr);
        output(matchTotalStr);
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
        for (int i = 0; i < io.length; i++)
            io[i] = new IO(i+1);
    }

    public void deal(int n) {
        for (PokerPlayer p: players){
            String outputStr = "yourHand";
            for (int i = 0; i < n; i++){
                int next = deck.next();
                p.dealCard(next);
                outputStr += Deck.abbr(next); 
            }
            output(outputStr, p.getSeatNum());
        }
    }

    public String input(int p){
        return io[p].input(); 
    }

    public void output(String s, int p) {
        io[p].output(s);
    }

    public void output(String s) {
        for (int i = 0; i < io.length; i++)
            io[i].output(s);
    }

}
