public class HoldEmBotIO extends BotIO {

    public HoldEmBotIO(int botNumber) {
        super(botNumber);
    }

    public String sendToBot(String s) {
        if (botNumber == 1)
            return ((Bot1)bot).processMatchInfo(s);
        return ((Bot2)bot).processMatchInfo(s);
    } 
    
    public Object createBot() {
        if (botNumber == 1)
            return (new Bot1());
        return (new Bot2());
    }

}
