package bestpractices;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * @class:       BacklogExceeded
 * @description: Server code for this Backlog test
 * @author:      Sherman Marshall
 */
public class BacklogExceeded {
    public static int PORT = 27011;
    public static int BACKLOG = 200;
    public static String FILE_ROOT = "BacklogTest/";
    public static String OTHER_FILE_ROOT = "RequestData/";
    public static String TERMINATE_MSG = "EXIT";
    
    public static void createDirs() throws IOException {
        File backlogTests = new File(FILE_ROOT);
        if (!backlogTests.exists()) {
            backlogTests.mkdir();
        }
        File requestData = new File(OTHER_FILE_ROOT);
        if (!requestData.exists()) {
            requestData.mkdir();
        }
    }

    public static void main (String...args) throws IOException {
        final ServerSocket ss = new ServerSocket(PORT, BACKLOG, InetAddress.getByName("localhost"));
        
        try {
            createDirs();
            
            while (true) {
                final Socket socket = ss.accept();

                Thread thread = new Thread(new Runnable() {

                    public void run() {
                        StringBuilder sb = new StringBuilder(FILE_ROOT);

                        int num;
                        byte one;
                        try {
                            InputStream is = socket.getInputStream();

                            while (( num = is.read()) != -1) {
                                sb.append((char)(byte) num);
                            }

                            if (sb.toString().equals(TERMINATE_MSG)) {
                                System.out.println("exiting");
                                ss.close();
                            } else {
                                System.out.println(sb.toString());
                                File f = new File(sb.toString());
                                f.createNewFile();
                            }
                        } catch (IOException ioe) {
                            System.out.println(sb.toString());
                            System.out.println(ioe.getMessage());
                        }
                    }

                });

                thread.start();

            }
        } catch (SocketException se) {
            System.out.println(se.getMessage());
        }
    }
}
