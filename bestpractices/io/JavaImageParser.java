import java.io.*;

public class JavaImageParser {

	public static void main(String...args) {
	
		HttpURLConnection hurlc = (HttpURLConnection) new URL(args[0]).openConnection();

		InputStream is = hurlc.getInputStream();
		
	
	}

}
