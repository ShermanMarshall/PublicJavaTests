package processes;

import java.io.*;

public class IntCount {
	public static void main(String...args) throws IOException {
		StringBuilder sb = new StringBuilder();
		int x = 0;

		ProcessBuilder pb = new ProcessBuilder(new String[]{ "java", "processes.ReadInt" });
		pb.redirectInput(ProcessBuilder.Redirect.PIPE);
		pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);

		Process p = pb.start();

		OutputStream os = p.getOutputStream();
		InputStream is = p.getInputStream();
		
		byte[] data = new byte[4096];
		int bytesRead = 0;
		
		StringBuilder output = new StringBuilder();
		boolean isBlockingForInput = true;

		do {
			sb.append(x++);
			if (x % 1024 == 0) {
				os.write(sb.toString().getBytes());
				os.write("\0\3\4".getBytes());
				os.flush();
				
				sb.delete(0, sb.length());
				try {
					Thread.sleep(50);
				} catch (InterruptedException ie) {
					System.out.println(ie);
				}

				while (isBlockingForInput && ((bytesRead = is.read(data)) != -1)) {
					if (bytesRead == data.length) {
						output.append(new String(data));
					} else {
						byte[] subset = new byte[bytesRead];
						System.arraycopy(data, 0, subset, 0, bytesRead);
						output.append(new String(subset));
					}
				}
				
				System.out.println(output.toString());
				output.delete(0, output.length());
				System.out.println("input complete");
			}
		} while(x < (1024 * 16));

		os.write("exit".getBytes());
		os.close();
	}
}
