import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Console;
import java.util.Scanner;

/**
 *
 * @author owner
 */
public class ScanFrom {
    
    public static void main(String...args) throws IOException {
        InputStream is = System.in;
        
        OutputStream out = System.out;
        
	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("output")));
        Console console = System.console();
	
	bos.write(console == null ? "null".getBytes() : console.toString().getBytes());
	bos.write(is == null ? "null".getBytes() : is.toString().getBytes());
 	bos.write(out == null ? "null".getBytes() : out.toString().getBytes());
	bos.flush();
 
        byte[] data = new byte[1024];
        int size = 0, num = 1;
        String s = "";
        StringBuilder content = new StringBuilder();

	Scanner scan = new Scanner(System.in);
	bos.write("\nblocking".getBytes());
        bos.flush();
	do {
		s = scan.nextLine();
		bos.write(s.getBytes());
		bos.flush();
		System.out.println(s);
        } while (!s.equals("exit"));

        bos.flush();
	bos.close();
    }
    
}
