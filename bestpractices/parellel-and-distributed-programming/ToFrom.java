import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Console;

/**
 *
 * @author owner
 */
public class ToFrom {
    
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

        do {
	    if (is.available() > 0) {
            size = is.read(data);

	    //Skip operations if no data to be read,
  	    //and output data
	    if (size == -1) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException ie) {
			System.out.println(ie.getMessage());
		}
		//bos.write("No data read\n".getBytes());
            } else {
            	s = new String(data);
            	content.append(num).append(": ").append(s).append("\n");
            	System.out.println(content.toString());
		bos.write(content.toString().getBytes());
		bos.flush();
		content = new StringBuilder();

		if (s.equals("exit")) {
		   bos.write("Exiting\n".getBytes());
		}
	    }
	    } else {
		//System.out.println("data");
	    }

        } while (!s.equals("exit"));

        bos.flush();
	bos.close();
    }
    
}
