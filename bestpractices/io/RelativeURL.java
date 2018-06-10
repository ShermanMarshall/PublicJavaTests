package bestpractices.io;

import java.net.URL;
import java.net.MalformedURLException;

import java.io.*;

/**
 * @class: 	 RelativeURL
 * @description: Demonstrates the way to create a URL to a file on the FS,
 */
public class RelativeURL {
	public static void main(String...args) throws MalformedURLException {
		URL url = new URL("file://" + new File("test.txt").getAbsolutePath());
		System.out.println(url);

		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
			System.out.println(br.readLine());
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}
}
