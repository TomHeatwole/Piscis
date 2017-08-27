import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class IO {

    private FileInputStream in;
    private FileOutputStream out;
    private LinkedList<String> queuedInput;

    public IO (int botNumber) {
        queuedInput = new LinkedList<String>();
        try {
            in = new FileInputStream("Bot" + botNumber + "ToMatch.txt");
            out = new FileOutputStream("MatchToBot" + botNumber + ".txt");
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
        if (queuedInput.size() > 0) 
            return queuedInput.remove();
        try {
            while (in.available() == 0) {
                //TODO: Start a counter before this, check if the counter reaches n seconds
                // if it does reach n seconds, return sometihng saying that the AI took too long
            }
            LinkedList<Byte> bytes = new LinkedList<Byte>(); // This needs to be a list not an Array of size 
                                                             // in.available because a bot could theoretically
                                                             // print output while we are reading output
            for (byte b = 0; (b = (byte)(in.read())) != -1; )
                bytes.add(b);
            byte[] bytesArray = new byte[bytes.size()]; // can't use toArray because conversion from Byte to byte
            for (int i = 0; i < bytes.size(); i++)
                bytesArray[i] = (byte)bytes.get(i);
            String s = (new String(bytesArray)).trim();
            String[] lines = s.split("\n");
            for (int i = 1; i < lines.length; i++)
                queuedInput.add(lines[i]);
            return lines[0];
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println((in == null) ? "LOL" : "Then what's the problem");
        }
        return "";
    }
}
