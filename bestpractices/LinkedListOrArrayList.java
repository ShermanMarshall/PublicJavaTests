package bestpractices;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * This is a simple test to determine (under general circumstances) which data structure
 * is appropriate for use. LinkedLists are commonly used for storing data, and benefit from
 * efficient memory usage given the node-for-object relationship they have between Collection
 * and data.
 * 
 * ArrayLists are also commonly used for storing data. Given their internal representation as
 * Arrays, they are faster for performing get operations (I'm not an assembly wiz, but I think
 * the reason is because of the pointer arithmetic arrays permit--foreknowledge of the array 
 * offset means less instructions to determine the location of the memory to be accessed/operated on).
 * They sometimes suffer, however, from underutilization of memory, due to the way the array is resized.
 * As values are added, the size of the array grows, and if it grows beyond its current limits, the data
 * must be copied into a new, larger, contiguous space. This relationship between Collection and data
 * is greater than node-for-object, as is the case with LinkedLists.
 *
 * This test shows LinkedLists perform worse under general circumstances than ArrayLists. The benefits
 * of more efficient memory usage without array resizing are lost in accessing data. Basically, if you
 * can afford the memory--which you can, because this is 2017--you should be using an ArrayList.
 * 
 * One thing that I can't account for is that the time to add values into the ArrayList has not been
 * effected by setting the sizeFactor... Very odd. ArrayList resizing operations have been touted as
 * costly, because the resizing algorithm regularly discards the memory currently in use, copying 
 * old values into a new contiguous memory space. Remarkably, however, this does not appear to effect
 * the time it takes the ArrayList to complete. I suspect something is going on within the JVM to 
 * optimize the efficacy of the ArrayList here, because this completely dismisses understanding of how
 * ArrayLists operate.
 * 
 * @author Sherman Marshall
 */
public class LinkedListOrArrayList {
    static int sizeFactor = 10000;
    
    public static void main(String...args) {
        long llAddTime = 0, alAddTime = 0, llGetTime = 0, alGetTime = 0, holder;
        
        if (args.length > 0) { 
		try {
		   sizeFactor = Integer.parseInt(args[0]);
		} catch (NumberFormatException nfe) {
		   System.out.println("You didn't supply an integer dumdum");
		}
	}

        for (int xx = 0; xx < 100; xx++) {
            List<String> ll = new LinkedList();

            long time = System.currentTimeMillis();
            for (int x = 0; x < sizeFactor; x++) {
                ll.add(UUID.randomUUID().toString());
            }
            holder = System.currentTimeMillis() - time;
            llAddTime += holder;
            //System.out.println("LL add time: " + holder);

            List<String> al = new ArrayList(sizeFactor);
            time = System.currentTimeMillis();
            for (int x = 0; x < sizeFactor; x++) {
                al.add(UUID.randomUUID().toString());
            }
            holder = System.currentTimeMillis() - time;
            alAddTime += holder;
            //System.out.println("AL add time: " + holder);

            time = System.currentTimeMillis();
            for (int x = 0; x < sizeFactor; x++) {
                String s = ll.get(x);
                char c = s.charAt(x % s.length());
            }
            holder = System.currentTimeMillis() - time;
            llGetTime += holder;
            //System.out.println("LL get time: " + holder);

            time = System.currentTimeMillis();
            for (int x = 0; x < sizeFactor; x++) {
                String s = al.get(x);
                char c = s.charAt(x % s.length());
            }
            holder = System.currentTimeMillis() - time;
            alGetTime += holder;
            //System.out.println("AL get time: " + holder);
        }
        
        System.out.println("LL average addTime: " + Double.toString((double)llAddTime / (double) 100));
        System.out.println("AL average addTime: " + Double.toString((double)alAddTime / (double) 100));
        
        System.out.println("LL average getTime: " + Double.toString((double)llGetTime / (double) 100));
        System.out.println("AL average getTime: " + Double.toString((double)alGetTime / (double) 100));
    }
}
