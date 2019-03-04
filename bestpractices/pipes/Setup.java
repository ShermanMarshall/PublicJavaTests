import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class Setup {
/*

System.out.println("Free memory: " + rt.freeMemory());
                                System.out.println("Max memory: " + rt.maxMemory());
                                System.out.println("Total memory: " + rt.totalMemory());

*/
    public static void main(String[] args) throws IOException {
        InputStream in = System.in;
        OutputStream out = System.out;
						///Users/shermanmarshall/Desktop/Workspace/Javabox/RunArea
						///Users/admin/Workspace/JavaBox/RunArea/toFromScript");
        ProcessBuilder pb = new ProcessBuilder("/Users/shermanmarshall/Desktop/Workspace/Javabox/RunArea");
        Process p = pb.start();
        
        InputStream from = p.getInputStream();
        OutputStream to = p.getOutputStream();
        
        Scanner s = new Scanner(in);
        String data = "";
        byte[] back = new byte[1024];
	int size = 0;
	Scanner fromScan = new Scanner(from);

        for (int x = 0; (x < 10); x++) {
	    	System.out.print("Input: ");
            data = s.nextLine();
//            to.write(data.getBytes());

	    System.out.println("You entered: " + data);

	    try {
		Thread.sleep(10);
	    } catch (InterruptedException ie) {
		System.out.println(ie.getMessage());
	    }

	    //System.out.println("blocking");
	    //if (from.available() > 0) {
            //size = from.read(back);

		data = fromScan.nextLine();

		System.out.println(data);
	    //No data read
	    if (size == -1) {
		try {
			Thread.sleep(1000);
			System.out.println("sleeped to catch output");

			size = from.read(back);
			System.out.println(size);
		} catch (InterruptedException ie) {
			System.out.println(ie.getMessage());
		}
	    } //else {
            	System.out.println(x + ": " + new String(back));
	    /*}
            } else {
   		System.out.println(" none available ");
	    }*/
	    if (data.equals("exit")) {
		break;
	    }
        }
    }
}
