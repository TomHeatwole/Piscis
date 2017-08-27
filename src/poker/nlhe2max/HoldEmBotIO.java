public class HoldEmBotIO extends BotIO {

    public void sendToBot(String s) {
        if (botNumber == 1)
            ((Bot1)bot).processMatchInfo(s);
        else
            ((Bot2)bot).processMatchInfo(s);
    } 
    
    public Object createBot() {
        if (botNumber == 1)
            return (new Bot1());
        return (new Bot2());
    }

}
