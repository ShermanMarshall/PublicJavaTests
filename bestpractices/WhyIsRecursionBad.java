package bestpractices;

/**
 * @class:       WhyIsRecursionBad
 * @description: A test comparing recursion to iteration. Iteration is preferable due to the potential for
		 StackOverflowErrors
 * @author:      Sherman Marshall
 */
public class WhyIsRecursionBad {
	static long value = Long.MAX_VALUE;
	static long factor = (long) Math.pow(2, 25);
	static long counter = 0L;
	
	/**
	 * Function increments counter (so we know how many iterations have occurred) and calls itself until the condition is satisfied.
	 * StackOverflowError occurs quite quickly, meaning recursion can only be used under limited circumstances.
	 */
	public static long recursiveFunction(long input, Object o) throws StackOverflowError {
		counter++;
		return input == 0 ? input : recursiveFunction(input - factor, new Object());
	}

	public static void recursionIsBad() {
		value = Long.MAX_VALUE;
		counter = 0L;
		try {
			System.out.println(recursiveFunction(value, null));
		} catch (StackOverflowError soe) {
			System.out.println(soe);
			System.out.println("Ran " + Long.toString(counter) + " iterations");
		}
	}
	
	/**
	 * Function increments counter until the condition is satisfied. Iteration essentially guarantees that the flow of the program 
	 * won't be interrupted, however, other concerns may arise, like OOM issues.
	 */
	public static void iterationIsGood() {
		value = Long.MAX_VALUE;
		counter = 0L;
		while ((value-= factor) > 0) { counter++; }
		System.out.println("Ran " + Long.toString(counter) + " iterations");
	}
	
	public static void main(String...args) {
		iterationIsGood();
		recursionIsBad();
	}
}

