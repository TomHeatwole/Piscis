import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
    
public class BotDriver {

    private static FileInputStream in;
    private static FileOutputStream out;

    public static void main (String[] args) {        
        try {
            in = new FileInputStream(args[0]);
            out = new FileOutputStream(args[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void output(String s) {
        byte[] bytes = s.getBytes();
        try {
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String input() {
        try {
            byte[] bytes = new byte[in.available()];
            byte b = 0;
            for (int i = 0; (b = (byte)(in.read())) != -1; i++)
                 bytes[i] = b;
            return (new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
