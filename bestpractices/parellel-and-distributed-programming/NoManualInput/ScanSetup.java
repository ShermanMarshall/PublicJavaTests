import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.*;
import java.util.Scanner;

public class ScanSetup {
    public static void main(String[] args) throws IOException {
        InputStream in = System.in;
        OutputStream out = System.out;
	//System.out.println(System.out.toString());
        
        ProcessBuilder pb = new ProcessBuilder(
	 "/Users/admin/Workspace/JavaBox/RunArea/NoManualInput/toFromScript");
	pb.redirectInput(ProcessBuilder.Redirect.PIPE);
	pb.redirectOutput(ProcessBuilder.Redirect.PIPE);

        Process p = pb.start();
        BufferedInputStream from = (BufferedInputStream) p.getInputStream();
        FileOutputStream to = (FileOutputStream) p.getOutputStream();

	File f = new File("descriptor");
	PrintWriter pw = new PrintWriter(f);
	System.out.println(to.getFD());
	//pw.write(to.getFD(
        
        Scanner s = new Scanner(in);
        String data = "";
        byte[] back = new byte[1024];
	int size = 0;

	//Scanner fromScan = new Scanner(from);
        for (int x = 0; (x < 10); x++) {
	    System.out.print("Input: ");
            data = s.nextLine();
            to.write(data.getBytes());
	    to.flush();
	    System.out.println("You entered: " + data);

	    try {
		Thread.sleep(10);
	    } catch (InterruptedException ie) {
		System.out.println(ie.getMessage());
	    }
	    //System.out.println(Integer.toString(x) + "\n");
	    //to.write(((String) (Integer.toString(x) + "\n")).getBytes());

	    //System.out.println("blocking");
	    if (from.available() > 0) {
            size = from.read(back);

		System.out.println("woot");
		to.write("wootwoot".getBytes());

	    //data = fromScan.nextLine();
	    System.out.println(new String(back));

	    //No data read
	    if (size == -1) {
		try {
			Thread.sleep(100);
			System.out.println("sleeped to catch output");

			size = from.read(back);
			System.out.println(size);
		} catch (InterruptedException ie) {
			System.out.println(ie.getMessage());
		}
	    } //else {
            	System.out.println(x + ": " + new String(back));
	    //}
            } else {
   		System.out.println(" none available ");
	    }
	    if (data.equals("exit")) {
		break;
	    }
        }
    }
}
