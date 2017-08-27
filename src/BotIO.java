import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
    
public abstract class BotIO {

    private FileInputStream in;
    private FileOutputStream out;
    private boolean play = true;
    public Object bot;
    public int botNumber;

    public BotIO (int botNumber) {        
        try {
            this.botNumber = botNumber;
            bot = createBot(); 
            in = new FileInputStream("MatchToBot" + botNumber + ".txt");
            out = new FileOutputStream("BotToMatch" + botNumber + ".txt");
            play = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            while (play) {
                while (in.available() == 0){}
                String s = input();
                String[] lines = s.split("\n");
                for (int i = 0; i < lines.length; i++)
                    processInput(lines[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void output(String s) {
        s = s + "\n";
        byte[] bytes = s.getBytes();
        try {
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String input() {
        try {
            LinkedList<Byte> bytes = new LinkedList<Byte>(); // This needs to be a list not an Array of size 
                                                             // in.available because a bot could theoretically
                                                             // print output while we are reading output
            for (byte b = 0; (b = (byte)(in.read())) != -1; )
                bytes.add(b);
            byte[] bytesArray = new byte[bytes.size()]; // can't use toArray because conversion from Byte to byte
            for (int i = 0; i < bytes.size(); i++)
                bytesArray[i] = (byte)bytes.get(i);
            return (new String(bytesArray)).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void processInput(String s) {
        if (s == "")
            return; // TODO: process empty input error
        if (s.charAt(0) == 'X') {
            play = false;
            return;
        }
        output(sendToBot(s));
    }

    public abstract String sendToBot(String s);
    public abstract Object createBot();
} 
