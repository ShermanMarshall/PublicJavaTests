package java8versus7;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

/**
 * The following class is a test of the functionality of Lambdas, versus their traditional 
 * anonymous function counterparts. The test uses a sizeFactor to create opposing sets of Strings
 * for use in comparison. Once the sets are populated, the code then performs a sort using the 
 * traditional comparator declared anonymously, outputting the time to complete following completion.
 * The same approach is done using both a Comparator implementing the the traditional anonymous
 * Comparator object as input, and with a Comparator declared using Lambda syntax. 
 * 
 * I tried several scale factors, and the trend is that the larger the scale factor, the better
 * the Lambda version performed; however, in no cases was the Lambda-based Comparator superior.
 * One can recognize this because the size of the array is a constant between the two algorithm
 * implementations, where the compile time of their respective instructions is not.
 * 
 * The following table summarizes my results (in ms):
 * 
 * Scale Factor   |     Anonymous Comparator | Lambda Comparator
 *      1000000             1310                    1542
 *      10000               31                      127
 *      100                 7                       123
 * 
 * Scott Oaks in "Java Performance: The Definitive Guide" suggests that testing Java applications
 * is not very easy, because the same system which makes Java a powerful programming language (the JVM)
 * also is somewhat arcane to developers, and they do not consistently write code, pass necessary flags,
 * or isolate conditions (GC activity, OS kernel calls)  which are necessary steps in the sequence of 
 * execution in the methods they are invoking, and the lack of these considerations skews results.
 * 
 * That statement does not absolve me of fault for committing what I understand to be a potential error in
 * testing Java code; however, I believe the results that I have taken in the following tests are accurate,
 * and I can promise that they will be revisited upon completing the book.
 * 
 * [edit]
 * I still haven't finished the book (life is hectic), though, I wanted to revisit this and provide a more
 * iterative test, without the System.gc() call. Many people remarked that of all things, that call
 * could obfuscate results, given the unpredictability of the JVM garbage collection activity. The results
 * still stand however, somewhat unsurprisingly, from my perspective. 
 * 
 * [edit-2]
 * After running the iterative test (multiple times) on some occasions, the Lambda implementation wins!
 * The circumstances under which it wins escape me--and likely fall under the arcana of the JVM mentioned above;
 * however, when using a large sizeFactor, it seems that the initial time to run the code is very time-consuming
 * due to JIT compilation (for a Oracle HotSpot JVM)--yet, subsequent runs are very quick. It appears that the results
 * are skewed as a result. The initial run for Lambda comparison is very slow, due to its compilation; however, subsequent 
 * runs are very fast
 * 
 * @author  Sherman Marshall
 */
public class TraditionalComparatorVsLambdaComparator {
    static int sizeFactor = 1000000;
    static Comparator comp = new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
           return s1.compareTo(s2);
        }
    };
    
    public static void main(String args[]) {
      if (args.length > 0) {
          sizeFactor = Integer.parseInt(args[0]);
      }
      
      long tradTime = 0, lambdaTime = 0, holder = 0;
      
      //Declare arrays with expected size to avoid resizing memory allocation and gc activity
      List<String> traditional = new ArrayList<String>(sizeFactor);

      //Add {sizeFactor} elements to traditional
      for (int x = 0; x < sizeFactor; x++) {
          traditional.add(UUID.randomUUID().toString());
      }
      
      //Sort lambda, and compute time to complete
      List<String> lambda = new ArrayList<String>(sizeFactor);
      for (int x = 0; x< sizeFactor; x++) {
          lambda.add(UUID.randomUUID().toString());
      }
      
      for (int xx = 0; xx < 100; xx++ ) {
        //Sort traditional, and compute time to complete
        System.out.println("Sort using Java 7 syntax: ");
        long num = System.currentTimeMillis();
        sortUsingJava7(traditional); 
        holder = System.currentTimeMillis() - num;
        System.out.println(Long.toString(holder));
        //System.out.println(traditional);
        
        tradTime += holder;

        System.out.println("Sort using Java 8 syntax: ");      
        num = System.currentTimeMillis();
        sortUsingJava8(lambda);
        holder = System.currentTimeMillis() - num;
        System.out.println(Long.toString(holder));
        //System.out.println(lambda);
        
        lambdaTime += holder;
      }
      
      System.out.println("Avg time for traditional: " + Double.toString((double) tradTime / (double) 100));
      System.out.println("Avg time for lambda: " + Double.toString((double) lambdaTime / (double) 100));
   }

   private static void sortUsingJava7(List<String> names){
      //sort using java 7
      Collections.sort(names, comp);
   }

   private static void sortUsingJava8(List<String> names) {
      //sort using java 8
      Collections.sort(names, (s1, s2) ->  s1.compareTo(s2));      
   }
    
}

