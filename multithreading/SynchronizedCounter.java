paackage multithreading;

import java.io.*;

/* Class for testing method synchronization */
public class SynchronizedCounter {
	private int x = 0;
	public synchronized void increment() { 	x++; 	}
	public synchronized void decrement() {	x--;	}
	public synchronized int getValue() { 	return x;	}

	public static void main (String[] args) {
		Thread[] threads = new Thread[10];
		final SynchronizedCounter sc = new SynchronizedCounter();
		System.out.println("Out");
		for (int x = 0; x < 10; x++) {
			final int z = x;
			threads[x] = new Thread(new Runnable() {
				public void run() {
					for (int y = 0; y < 1000; y++) {
						System.out.println((z+1) + ": " + sc.getValue());
						switch ((int) (Math.random() * 2)) {
							case 0:
								sc.increment();
							break;
							case 1:
								sc.decrement();
							break;
							default:
								System.out.println("Error");
							break;
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {	
							System.out.println(e.getMessage());	
						}
					}
				}
			});
			threads[x].start();
		}
	}

}
