package java8versus7;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Consumer;

/**
 * @class: 	 MultiUseStreamsOrTraditionalLoop
 * @description: This class is a simple test of old stle conditional logic versus the new functional style.
 *		 After several iterations, it seems that the Stream-based approach is not faster, though the
 * 		 syntax permits for aggregation of conditions, and may be preferable to some for various reasons.
 *		 I don't like Streams, as a SINGLE-USE data-structure is mind-bogglingly frustrating. This is 
 *		 even more evidence for me not to use them.
 */
public class MultiUseStreamsOrTraditionalLoop {
	static final int ITERATIONS = 10;
	
	public static class MyConsumer implements Consumer<Integer> {
		public MyConsumer() { }

		private static int sum = 0;
		
		public void accept(Integer i) {
		    sum += i;
		}
		
		public static int getSum() {
		    return sum;
		}

		public static void resetSum() {
			sum = 0;
		}
	}

	public static void main (String...args) {
		List<Integer> numbers = new ArrayList();
		
		for (int x = 0; x < (int) (Math.pow(2, 20)); x++) {
			numbers.add((int) (Math.random() * (Math.pow(2, 10))));
		}

		List<Long> loopCount = new ArrayList(ITERATIONS);
		List<Long> streamCount = new ArrayList(ITERATIONS);

		long sum, time, tmp;
		for (int x = 0; x < ITERATIONS; x++) {
			sum = 0;
			System.out.println("Calculating sum using a loop");
			time = System.currentTimeMillis();
			for (Integer i : numbers) {
				//Some arbitrary operations
				if (i > ((int) (Math.pow(2, 9)))) {
					sum += i;
				}
			}
			tmp = System.currentTimeMillis() - time;
			System.out.println("time: " + tmp + "; sum: " + sum);
			loopCount.add(tmp);
			
			sum = 0;
			System.out.println("Calculating sum using stream");
			time = System.currentTimeMillis();
			Stream<Integer> dumbStream = numbers.stream();
			List<Integer> arbitraryValues = dumbStream.filter(i -> i > ((int) (Math.pow(2, 9))))
				  				  .collect(Collectors.toList());
			MyConsumer.resetSum();
			arbitraryValues.forEach(new MyConsumer());
			tmp = System.currentTimeMillis() - time;
			System.out.println("time: " + tmp + "; sum: " + MyConsumer.getSum());
			streamCount.add(tmp);
		}

		double loopAverage = 0, streamAverage = 0;
		for (int x = 0; x < ITERATIONS; x++) {
			loopAverage += loopCount.get(x);
			streamAverage += streamCount.get(x);
		}
		loopAverage /= loopCount.size() * 1000.0;
		streamAverage /= streamCount.size() * 1000.0;

		System.out.println("Average time for loops: " + loopAverage);
		System.out.println("Average time for streams: " + streamAverage);
	}
}
