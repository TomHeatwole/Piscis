import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
    
public class BotDriver {

    private static FileInputStream in;
    private static FileOutputStream out;
    private static boolean play = true;

    public static void main (String[] args) {        
        try {
            in = new FileInputStream(args[0]);
            out = new FileOutputStream(args[1]);
            while (play) {
                while (in.available() == 0){}
                String s = input();
                String[] lines = s.split("\n");
                for (int i = 0; i < lines.length; i++)
                    processInput(lines[i]);
            }
            output("Line 1 \nLine 2");
            output("LINE 3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void output(String s) {
        s = s + "\n";
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
            return (new String(bytes)).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void processInput(String s) {
        if (s == "")
            return; // TODO: process empty input error
        if (s.charAt(0) == 'X') {
            play = false;
            return;
        }
        System.out.println(s); //TODO: Change this to send to bot
    }
} 
