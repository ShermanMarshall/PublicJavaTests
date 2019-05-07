package multithreading;

/**
 * @class:       MultiThreading
 * @description: This class shows how to properly multi-thread in Java, where
		 one wants to consistently perform a long-running task, maintaining
		 synchronized threads over time  
 * @author:      Sherman
 */
public class MultiThreading {
    public static void main(String...args) {
        final Object lock = new Object();
        final boolean[] done = new boolean[16];
        for (int x = 0; x< 16; x++) {
            final int xx = x;
            Thread t = new Thread(new Runnable(){
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep((int) (Math.random() * 7000));
                        } catch (InterruptedException ie) {
                            System.out.println(ie.getMessage());
                        }
                        done[xx] = true;
                        boolean wait = false;
                        for (boolean b : done) {
                            if (!b) {
                                wait = true;
                                break;
                            }
                        }
                        if (wait) {
                            try {                            
                                System.out.println("Thread-" + xx +" waiting");
                                synchronized(lock) {
                                    lock.wait();
                                }
                            } catch (InterruptedException ie) {
                                System.out.println(ie.getMessage());
                            }
                        } else {
                            synchronized(lock) {
                                for (int x = 0; x < 16; x++) {
                                    done[x] = false;
                                }
                                lock.notifyAll();
                            }
                        }
                        System.out.println("Thread-"+xx+" complete");
                    }
                }
            });
            t.start();
           
        }
    }
}
