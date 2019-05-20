package processes;

import java.io.*;

public class ReadInt { 
	public static boolean eot (String str) {
		return str.charAt(str.length() - 3) == '\0' &&
			str.charAt(str.length() - 2) == '\3' &&
			str.charAt(str.length() - 1) == '\4';
	}

	public static void main(String...args) {
		InputStream is = System.in;

		byte[] data = new byte[4096];
		int bytesRead = 0;

		String content = "";
		
		try (PrintWriter pw = new PrintWriter(new FileWriter(new File("write_ints.txt")))) {
			StringBuilder sb = new StringBuilder();
			int count = 1;

			while (!content.contains("exit")) {
				while (((bytesRead = is.read(data)) != -1)) {
					if (bytesRead == data.length) {
						content = new String(data);
					} else {
						byte[] subset = new byte[bytesRead];
						System.arraycopy(data, 0, subset, 0, bytesRead);
						content = new String(subset);
					}
					if (content.length() > 3) {
						sb.append(content.substring(0, content.length() - 3));
						if (eot(content)) {
							pw.println(sb.toString());
							sb.delete(0, sb.length());
							pw.flush();
							System.out.write(new StringBuilder("Wrote to file: " + count++ + " times").toString().getBytes());
						}
					}
				}
			}
			
			pw.close();
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}
}
