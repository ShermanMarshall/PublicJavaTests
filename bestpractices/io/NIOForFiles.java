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
		 NIO outperforms, by a significant margin, per my tests. These results may be effected further by the 
		 size of the allocated ByteBuffer. I will likely revisit this in the future to test writing as well
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
		    output = new StringBuilder();
		}   
	    }   

	    System.out.println("Done");    
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
                if (bytesRead < buf.limit()) {
                    buf.rewind();
                    byte[] remaining = new byte[bytesRead];
                    buf.get(remaining);
		    new String(remaining);
                } else {
		    new String(buf.array());
                }
                buf.rewind();
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (InvalidPathException ipe) {
            System.out.println(ipe);
        }
        
        return output;
    }
    
    public static boolean writeFile(String content, String filepath) {
        Path path = null;
        
        try {
            path = Paths.get(filepath);
        } catch (InvalidPathException ipe) {
            System.out.println(ipe);
            return false;
        }
        
        try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(
                                                        Paths.get(filepath), 
                                                        StandardOpenOption.WRITE, 
                                                        StandardOpenOption.CREATE)) {
            
            ByteBuffer buffer = ByteBuffer.allocate(content.length());
            buffer.put(content.getBytes());
            buffer.rewind();
            
            fileChannel.write(buffer);
            
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (InvalidPathException ipe) {
            System.out.println(ipe);
        }
        
        return true;
    }
    
    public static String bufferedRead(String filename) {
        
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
            String input;
            while ((input = br.readLine()) != null) {
		//Empty string to save memory. JVM will collect it at some point
                new String(input.getBytes());
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        
        return "";
    }

    public static void bufferedWrite(String filename) {

	try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filename)))) {

	} catch (IOException ioe) {

	}
    }
    
    public static void main(String...args) {
	String filename = "oneGbOfCaps.txt";

	if (!new File(filename).isFile()) {
	    createGBFile();
 	}

        ArrayList<Double> nioReadMetrics = new ArrayList();
	ArrayList<Double> bufferedReadMetrics = new ArrayList();
	//ArrayList<Double> nioWriteMetrics = new ArrayList();
	//ArrayList<Double> bufferedWriteMetrics = new ArrayList();

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

	/*
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
	*/
    }
}

