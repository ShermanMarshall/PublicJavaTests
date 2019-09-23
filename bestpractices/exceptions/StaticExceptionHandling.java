

public class StaticExceptionHandling extends Exception {

	private static StaticExceptionHandling exception;

	public StaticExceptionHandling(String msg) {
		super(msg);
	}
	
	public static void main (String...args) {
		long NUMBER_ITERATIONS = (long) Math.pow(2, 30);
		
		long allocatedTime = 0L;
		long start = 0L;
		
		for (int x = 0; x < NUMBER_ITERATIONS; x++) {
			start = System.currentTimeMillis();
			try {
				throw new StaticExceptionHandling("Fuck you Iheanyi");
			} catch (Exception e) { }
			allocatedTime += System.currentTimeMillis() - start;
		}

		System.out.println(allocatedTime / NUMBER_ITERATIONS);
		allocatedTime = 0L;

		for (int x = 0; x < NUMBER_ITERATIONS; x++) {
			start = System.currentTimeMillis();
			try {
				Exception eexception = exception == null ? exception = new StaticExceptionHandling("Fuck you Iheanyi") : exception;
				throw eexception;
			} catch (Exception e) { }
			allocatedTime += System.currentTimeMillis() - start;
		}
		
		System.out.println(allocatedTime / NUMBER_ITERATIONS);
	}
}
