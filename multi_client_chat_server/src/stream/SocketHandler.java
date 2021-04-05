package stream;

import java.net.*;

/**
 * a trivial class that returns string based information when provided with a
 * Socket e.g. addresses for printing to screens.
 * 
 * @author ksomers
 *
 */
public class SocketHandler {

	/**
	 * Converts a socket to a standard string format for printing to screen
	 * typically
	 */
	public static String getSocketInfo(Socket socket) {
		return socket.getLocalAddress() + ":" + socket.getLocalPort() + " | " + socket.getInetAddress() + ":"
				+ socket.getPort();
	}

}
