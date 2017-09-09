package bestpractices;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 * @class:       SocketsToExceedBacklog
 * @description: Ok... This is interesting
 * 
 *               A couple of weeks ago, I made an argument for a single-request response
 *               interaction for a system component, on the basis of the backlog being
 *               a vulnerability of our system--too many requests, backlog gets exceeded, 
 *               essential data as a part of our service goes missing, inexplicably.
 *               So, I made a test case. And...I was right, mostly, anyways. I go into 
 *               a confused rant of sorts at the end of this file. I will leave it there
 *               so I can remember the issues later... 
 * 
 *               Anyways, to replicate:
 *                  Pick your backlog size in BacklogExceeded.java
 *                  Pick your Thread pool size and #of iterations in 
 *                     SocketsToExceedBacklog.java
 *                  Run BacklogExceeded.java
 *                  Run SocketsToExceedBacklog.java
 *                      wait...
 *                  ls BacklogTest | wc -l
 *                  ls RequestData | wc -l
 * 
 *                  Suggest that I am on to something...
 * 
 *               So, the test results:
 *                                     
 *               Test#:     Thread Pool  Backlog  Iterations  #Requests #Files
 *               1          50           2        20          942       936
 *               2          500          2        10          3874      3695
 * 
 *               I am fairly confident the difference between the number of requests and files is
 *               because of network failure. That is the expectation. Memory issues, and such
 *               will cause delays between processes (GC pauses idling Server code), but in the end,
 *               that helps my case: reducing the number of network requests to perform an operation
 *               to only that which will complete the operation, or fail entirely, is necessary, because
 *               network unpredictability can be expected to produce situations where some requests
 *               are handled, and others are not.
 * 
 * @author:      Sherman Marshall
 */
public class SocketsToExceedBacklog  {
    public static int THREAD_POOL_SIZE = 500;
    public static int NUMBER_OF_TESTS = 10;
    public static int SHORT_WAIT = 500;
    public static int LONG_WAIT = 2000;
    
    public static void main (String...args) throws IOException {
        Thread[] threadPool = new Thread[THREAD_POOL_SIZE];
        ArrayList<String> expectedFileNames = new ArrayList(THREAD_POOL_SIZE * NUMBER_OF_TESTS);
        
        for (int x = 0; x < NUMBER_OF_TESTS; x++) {
            for (Thread thread : threadPool) {

                thread = new Thread(new Runnable() {

                    public void run() {
                        try {
                            Socket s = new Socket(InetAddress.getByName("localhost"), 27011);

                            OutputStream os = s.getOutputStream();
                            
                            String uuid = UUID.randomUUID().toString();
                            expectedFileNames.add(uuid);
                            
                            os.write(uuid.getBytes());
                            os.close();
                            
                            try {
                                Thread.sleep(SHORT_WAIT);
                            } catch (InterruptedException ie) {
                                
                            }
                            
                            s.close();
                        } catch (IOException ioe) {
                            System.out.println(ioe.getMessage());
                        }
                    }

                });
                thread.start();
            }
            try {
                Thread.sleep(LONG_WAIT);
            } catch (InterruptedException ie) {
                
            }
        }
        
        for (String s : expectedFileNames) {
            new File("RequestData/" + s).createNewFile();
        }
        
        try {
            
            Socket terminate = new Socket(InetAddress.getByName("localhost"), 27011);
            terminate.getOutputStream().write("EXIT".getBytes());
            
            terminate.close();
        } catch (IOException ioe) {
            
        }
        
    }
}
/**
 *               But, the reason for it escapes me a bit.
 *               If you run the provided code you will see a couple of interesting exceptions
 *               reported: Connection reset, and Broken Pipe. These exceptions correspond
 *               to RST messages being sent from TCP connections, and I think IO failures;
 *               neither of which are in the programmer's capability of control. The RST
 *               messages make me wonder whether the issue here isn't in the design of 
 *               the client server communications, but with the TCP protocol itself. If
 *               the TCP protocol's fault tolerance, and connection guarantee means these 
 *               RST messages will keep getting resent, the problem is that the server gets
 *               killed early... but, that also raises the issue of how does application
 *               logic operate when the socket has not been sent...
 *               The amount of "just works" hardware and software we rely on is incredible
 */
