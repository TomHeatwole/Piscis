import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit; // For now this is only here for testing so delete if seen in repo

public class IO {

    private FileInputStream in;
    private FileOutputStream out;

    public IO (String inputFile, String outputFile) {
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
        try {
            byte[] bytes = new byte[in.available()];
            byte b = 0;
            for (int i = 0; (b = (byte)(in.read())) != -1; i++)
                 bytes[i] = b;
            return (new String(bytes)).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // Main method exists only for testing. Delete if found in repo
    public static void main (String[] args) {
        IO io = new IO("i.txt", "o.txt");
        io.output("test1"); 
        io.output("test2");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {e.printStackTrace();}
        io.output("test3");
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception e) {e.printStackTrace();}
        io.output("X");
    }

}
