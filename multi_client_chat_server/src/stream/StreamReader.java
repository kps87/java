package stream;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import event.*;

/**
 * the basic functionality to retrieve incoming messages is abstracted into this
 * class, and the client and server classes described elsewhere are typically
 * composed with their own StreamReader instance which awaits input from the
 * Socket and adds the received messages to an EventLog. It also contains a
 * parseMessage method which is used by other classes to parse received messages
 * from clients/servers. This class extends the Thread class – when the
 * StreamReader is started, it awaits messages, when it receives one it adds it
 * to an event log, and whatever object is composed with that StreamReader then
 * processes the events via its event monitor methods.
 * 
 * @author ksomers
 *
 */
public class StreamReader extends Thread {

	// variables
	private Socket socket;
	private EventLog eventLog;
	private Scanner inputStream;
	private boolean stop = false;

	/**
	 * Constructor which take a Socket and an event log (for writing event updates
	 * to) as input.
	 */
	public StreamReader(Socket socket, EventLog eventLog) {
		this.socket = socket;
		this.eventLog = eventLog;
	}

	/**
	 * Overrides the default run method of a Thread. Starts the streamreader which
	 * listens for incoming messages.
	 */
	public void run() {

		// try to establish an input stream
		try {

			// try to get the input stream
			this.getInputStream();

			// while the stream has another line
			while (!stop) {
				while (this.inputStream.hasNextLine()) {

					this.eventLog.addEvent(new Event("incoming message", this.inputStream.nextLine(), false));

				}
				this.stop = true;
			}

		}
		// catch an io exceptions from not being able to establish stream
		catch (IOException e) {
			this.eventLog.addEvent(new Event("connection",
					"ioexception: " + e.getMessage() + " -> could not establish stream", false));
			this.stop = true;
		}
		// finally
		finally {
			if (this.stop) {
				this.eventLog.addEvent(new Event("connection", "connection stopped by client or server", false));
			} else {
				this.eventLog.addEvent(new Event("connection", "connection stopped, cause unknown", false));
			}
			this.stop = true;
		}

	}

	/**
	 * Establish an input stream using a scanner. Throws an IOException which can be
	 * caught elsewhere.
	 */
	private void getInputStream() throws IOException {

		// write attempt message to log
		this.eventLog.addEvent(new Event("connection", "establishing input stream", true));

		// try to open stream
		this.inputStream = new Scanner(this.socket.getInputStream());

		// write success message to log
		this.eventLog.addEvent(new Event("connection", "input stream established", true));

	}

	/**
	 * Method which stops/closes the stream which is indefinitely running in a while
	 * loop elsewhere.
	 */
	public void close() {
		this.stop = true;
	}

	/**
	 * Method which takes a String based message as input and parses it into
	 * sourceAddress, destinationAddress and text-message fields. ChatClients and
	 * ChatServers can later carry out specific actions depending on the message
	 * text.
	 */
	public static ArrayList<String> parseMessage(String message) {

		// Create a Pattern object
		Pattern pattern = Pattern.compile("src=\\[(.*)\\] dest=\\[(.*)\\] message=\\[(.*)\\]");

		// Now create matcher object.
		Matcher matcher = pattern.matcher(message);

		// if there is a match unpack the message
		if (matcher.find()) {

			// get the source address, destination address and message text
			String sourceAddress, destinationAddress, messageText;
			sourceAddress = matcher.group(1);
			destinationAddress = matcher.group(2);
			messageText = matcher.group(3);

			ArrayList<String> parsedData = new ArrayList<String>();
			parsedData.add(sourceAddress);
			parsedData.add(destinationAddress);
			parsedData.add(messageText);

			return parsedData;

		} else {

			// if the message has some text just add default values for src, dest and
			// message
			if (message.length() > 0) {
				ArrayList<String> parsedData = new ArrayList<String>();
				parsedData.add("src");
				parsedData.add("dest");
				parsedData.add(message);
				return parsedData;
			}
			// if the message has no contents return null
			else {
				return null;
			}
		}

	}

}
