import java.net.*;
import java.util.*;

/* Java class for listing network interfaces. Useful for finding network interface configuration information
as well as for hardware/vendor information */
public class GetIPs {
	public static void main (String[] args) throws UnknownHostException, SocketException {
		Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
		for (int x = 1; nis.hasMoreElements(); x++) {
			NetworkInterface ni = nis.nextElement();
			
			/* Comment out the following conditional to find the appropriate interface for your connection */
			if (!ni.getDisplayName().equals("The name of your preferred interface"))
				continue;

			System.out.println(ni.getDisplayName());			
			Enumeration<InetAddress> addresses = ni.getInetAddresses();
			for ( ; addresses.hasMoreElements(); ) {
				InetAddress address = addresses.nextElement();
				System.out.println(Integer.toString(x) + ": " + address.getHostAddress());
			}
		}
	}
}