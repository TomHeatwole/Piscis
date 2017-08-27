public class Driver {
    public static void main (String[] args) {
        IO io = new IO("BotToMatch1.txt", "MatchToBot1.txt");
        io.output("What is going on");
        io.output("Tell this to the bot please");
        io.output("action Tell this then expect some input from the bot");
        System.out.println("Here is what bot1 just told me: " + io.input());
    }
}
