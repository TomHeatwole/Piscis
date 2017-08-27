import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class IO {

    private FileInputStream in;
    private FileOutputStream out;
    private LinkedList<String> queuedInput;

    public IO (String inputFile, String outputFile) {
        queuedInput = new LinkedList<String>();
        try {
            in = new FileInputStream(inputFile);
            out = new FileOutputStream(outputFile);
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
        if (queuedInput.size() > 0) {
            System.out.println("We using that fat linkedlist");
            return queuedInput.remove();
        }
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
        }
        return "";
    }
}
