import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class IO {

    FileInputStream in;
    FileOutputStream out;

    public IO (String inputFile, String outputFile) {
        try {
            in = new FileInputStream(inputFile);
            out = new FileOutputStream(outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void output(String s) {
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
            return (new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
