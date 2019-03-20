package strings;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class UseStringBuilder {
    public static byte[] prepareOutput(String content) {
        StringBuilder builder = new StringBuilder(Integer.toString(content.length()));
        builder.append((char) 4);
        builder.append(content);
        return builder.toString().getBytes();
    }
     public static void main(String[] args) throws IOException {
     String str = "";
     StringBuilder builder = new StringBuilder();
     StringBuffer buffer = new StringBuffer();
     long[] metrics = new long[4], //count of time taken for each scheme
           averages = new long[4]; //count of averages for each scheme
     int arraySize = 300000,  //Base size of 300000;
         numIterations = 10; //number of times to repeat for averages
     long start = 0, stop = 0;
     
     try {
        Thread.sleep(1000);
     } catch (InterruptedException ie) {
         System.out.println("Error running example. Terminating");
         return;
     }
     Socket sock = new Socket(InetAddress.getByName("localhost"), 27011);
     OutputStream os = sock.getOutputStream();
     for (int iterations = 0, metricCounter = 0; iterations < numIterations; iterations++) {
          os.write(prepareOutput("Testing time for String concatenation"));
          start = System.currentTimeMillis();
          for (int x = 0; x < arraySize; x++) { //Add characters to create the 26 character alphabet
               for (int xx = 0; xx < 26; xx++) {
                    str.concat(new String(new char[]{(char) (65+xx)}));
               }
               str = new String();
          }
          stop = System.currentTimeMillis() - start;
          metrics[metricCounter] = stop;
          averages[metricCounter++] += stop;
          os.write(prepareOutput("Testing time for String building"));
          start = System.currentTimeMillis();
          for (int x = 0; x < arraySize; x++) {  //Add characters to create the 26 character alphabet
                for (int xx = 0; xx < 26; xx++) {
                      builder.append((char) (65+xx));
                }                builder = new StringBuilder();
          }
          stop = System.currentTimeMillis() - start;
          metrics[metricCounter] = stop;
          averages[metricCounter++] += stop;
          os.write(prepareOutput("Testing time for String buffering"));
          start = System.currentTimeMillis();
          for (int x = 0; x < arraySize; x++) {  //Add characters to create the 26 character alphabet
                for (int xx = 0; xx < 26; xx++) {
                      buffer.append((char) (65+xx));
                }
                buffer = new StringBuffer();
          }
          stop = System.currentTimeMillis() - start;
          metrics[metricCounter] = stop;
          averages[metricCounter++] += stop;
          os.write(prepareOutput("Testing time for String formatting"));
          start = System.currentTimeMillis();
          for (int x = 0; x < arraySize; x++) { //Use format string to create the alphabet
               String s = String.format("%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s", "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
          }
          stop = System.currentTimeMillis() - start;
          metrics[metricCounter] = stop;
          averages[metricCounter++] += stop;
          os.write(prepareOutput(String.format("Results for iteration: %d\n\tConcatenation: %d\n\tBuilding: %d\n\tBuffering: %d\n\tFormatting: %d", iterations+1, metrics[0], metrics[1], metrics[2], metrics[3])));
          metricCounter = 0;
      }
     os.write(prepareOutput(String.format("Averages follow:\n\tConcatenation: %d\n\tBuilding: %d\n\tBuffering: %d\n\tFormatting: %d", averages[0]/numIterations, averages[1]/numIterations, averages[2]/numIterations, averages[3]/numIterations)));
     os.write(prepareOutput("end"));
     os.close();
     sock.close();
   }
}
