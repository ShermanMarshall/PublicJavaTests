package bestpractices.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ArrayList;

/**
 * @class:       NIOForFiles
 * @description: Performs a simple test to compare the efficiency of NIO against the older Buffered IO implementation.
 *               My tests suggest NIO outperforms BufferedReading by a significant margin; however, BufferedWriting 
 *               appears to have the advantage over NIO. Somewhat odd results; however, the below code should consistently
 *               suggest the same. These results may be effected by the size of the allocated ByteBuffer. OOM errors occurring
 *               from large ByteBuffers required reduced size relative to the BufferedWriter--though, that may be more reason
 *               to favor the old way.
 * @author:      Sherman
 */
public class NIOForFiles {
    public static void createGBFile() {
	try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("oneGbOfCaps.txt")))) {
	    int num = 0, val = 0;
	    int max = (int) Math.pow(2, 30);

	    System.out.println("Starting");

	    StringBuilder output = new StringBuilder();
	    while (num < max) {
		num++;
		val = (int) (Math.random() * 28);
		if (val == 0 || val == 27) {
		    output.append("\n");
		} else {
		    output.append((char) (val + 64));
		}   
		if ((num % (int) (Math.pow(2, 27))) == 0) {
		    bw.write(output.toString());
		    bw.flush();
		    output.delete(0, output.length());
		}   
	    }   

	    System.out.println("Done");    
	} catch (IOException ioe) {
		System.out.println(ioe);
	}   
    }

    public static void createGBFileWithNIO() {
	try (FileChannel channel = (FileChannel) Files.newByteChannel( Paths.get("oneGbOfCapsNIO.txt"),
						StandardOpenOption.WRITE, StandardOpenOption.CREATE ) ) {
		ByteBuffer buffer = ByteBuffer.allocate((int) Math.pow(2, 26));
		int max = (int) Math.pow(2, 30);
		int total = 0, val = 0;
		StringBuilder output = new StringBuilder();
		while (total < max) {
			total++;
			val = (int) (Math.random() * 28);
			if (val == 0 || val == 27) {
				output.append("\n");
			} else {
				output.append((char) (val + 64));
			}
			if ((total % (int) (Math.pow(2, 26))) == 0) {
				buffer.put(output.toString().getBytes());
				buffer.flip();
				channel.write(buffer);
				buffer.clear();
				output.delete(0, output.length());
			}
		}
	} catch (InvalidPathException e) {
		System.out.println(e);
	} catch (IOException ioe) {
		System.out.println(ioe);
	}
    }
    
    public static String readFile(String filename) {
        String output = null;
        Path path = null;
        
        try (SeekableByteChannel inputChannel = Files.newByteChannel(Paths.get(filename))) {
            ByteBuffer buf = ByteBuffer.allocate(1024);
            int bytesRead = 0;
            
            while ((bytesRead = inputChannel.read(buf)) > -1) {
		buf.rewind();
                if (bytesRead < buf.limit()) {
                    byte[] remaining = new byte[bytesRead];
                    buf.get(remaining);
		    new String(remaining);
                } else {
		    new String(buf.array());
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (InvalidPathException ipe) {
            System.out.println(ipe);
        }
        
        return output;
    }
    
    public static String bufferedRead(String filename) {
        
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
            String input;
            while ((input = br.readLine()) != null) {
		//Empty string to simulate activity. JVM will collect it at some point
                //new String(input.getBytes());
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        
        return "";
    }

    public static void testReading(String...args) {
	String filename = "oneGbOfCaps.txt";

	if (!new File(filename).isFile()) {
	    createGBFile();
		return;
 	}

        ArrayList<Double> nioReadMetrics = new ArrayList();
	ArrayList<Double> bufferedReadMetrics = new ArrayList();

	double difference = 0.0;
        for (int x = 0; x < 10; x++) {
            System.out.println("Reading file with NIO");

            long time = System.currentTimeMillis();
 	    String output = readFile(filename);

            difference = (((double)System.currentTimeMillis() - time) / 1000.0);
	    System.out.println("time: " + difference);
	    nioReadMetrics.add(difference);

            System.out.println("Reading file with Buffered Reader");
            time = System.currentTimeMillis();

            bufferedRead(filename);
            difference = (((double)System.currentTimeMillis() - time) / 1000.0);
	    System.out.println("time: " + difference);
	    bufferedReadMetrics.add(difference);
        }

	double average = 0.0;
	for (Double d : nioReadMetrics) {
	    average += d;
	}
	average /= nioReadMetrics.size();

	System.out.println("Average for NIO reading: " + average);

	average = 0;
	for (Double d : bufferedReadMetrics) {
	    average += d;
	}

	average /= bufferedReadMetrics.size();
	System.out.println("Average for buffered reading: " + average);
    }

    public static void testWriting(String...args) {
	ArrayList<Double> nioWriteMetrics = new ArrayList();
	ArrayList<Double> bufferedWriteMetrics = new ArrayList();

	double difference = 0.0;
	for (int x = 0; x < 10; x++) {
	    System.out.println("Writing file with NIO");

	    long time = System.currentTimeMillis();
	    createGBFileWithNIO();

	    difference = (((double)System.currentTimeMillis() - time) / 1000.0);
	    System.out.println("time: " + difference);
	    nioWriteMetrics.add(difference);

	    System.out.println("Writing file with Buffered Writer");
	    time = System.currentTimeMillis();

	    createGBFile();
	    difference = (((double)System.currentTimeMillis() - time) / 1000.0);
	    System.out.println("time: " + difference);
	    bufferedWriteMetrics.add(difference);
	}

	double average = 0.0;
	for (Double d : nioWriteMetrics) {
	    average += d;
	}
	average /= nioWriteMetrics.size();

	System.out.println("Average for NIO writing: " + average);

	average = 0;
	for (Double d : bufferedWriteMetrics) {
	    average += d;
	}

	average /= bufferedWriteMetrics.size();
	System.out.println("Average for buffered writing: " + average);
    }	

    public static void main (String...args) {
	//testReading();
	testWriting();
    }
}
