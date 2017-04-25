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
 * @author  Sherman Marshall
 */
public class TraditionalComparatorVsLambdaComparator {
    static int sizeFactor = 100;
    
    public static void main(String args[]){
        
      if (args.length > 0) {
          sizeFactor = Integer.parseInt(args[0]);
      }

      //Declare arrays with expected size to avoid resizing memory allocation and gc activity
      List<String> names1 = new ArrayList<String>(sizeFactor);
      
      //Add {sizeFactor} elements to names1
      for (int x = 0; x < sizeFactor; x++) {
          names1.add(UUID.randomUUID().toString());
      }
      
      //Sort names1, and compute time to complete
      System.out.println("Sort using Java 7 syntax: ");
      long num = System.currentTimeMillis();
      sortUsingJava7(names1); 
      System.out.println(Long.toString(System.currentTimeMillis() - num));
      //System.out.println(names1);
      
      //Remove reference to unnecessary object, and begin garbage collection to free memory
      names1 = null;
      System.gc();
      
      //Sort names2, and compute time to complete
      List<String> names2 = new ArrayList<String>(sizeFactor);
      for (int x = 0; x< sizeFactor; x++) {
          names2.add(UUID.randomUUID().toString());
      }
      
      System.out.println("Sort using Java 8 syntax: ");      
      num = System.currentTimeMillis();
      sortUsingJava8(names2);
      System.out.println(Long.toString(System.currentTimeMillis() - num));
      //System.out.println(names2);
   }

   private static void sortUsingJava7(List<String> names){
      //sort using java 7
      Collections.sort(names, new Comparator<String>() {
         @Override
         public int compare(String s1, String s2) {
            return s1.compareTo(s2);
         }
      });
   }

   private static void sortUsingJava8(List<String> names){
      //sort using java 8
      Collections.sort(names, (s1, s2) ->  s1.compareTo(s2));      
   }
    
}

