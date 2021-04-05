package stream;

import java.io.*;
import java.net.*;

import event.*;

/**
 * the basic functionality to write outgoing messages is abstracted into this
 * class. StreamWriter instances are provided with a Socket to write the message
 * to, a message to send, and an optional source and destination address (all
 * communication is through the Socket, but to facilitate client-to-client
 * communication the source and destination address may be varied from those
 * associated with the Socket). Also contains a method formatOutgoingMessage to
 * format messages in a standard format (src=[*] dest=[*] message=[*]).
 * 
 * @author ksomers
 *
 */
public class StreamWriter extends Thread {

	// instance variables
	private Socket socket;
	private EventLog eventLog;
	private PrintStream outputStream;
	private String message;
	private String source;
	private String destination;

	/**
	 * Constructor which takes as input a socket, an eventLog and a message to send.
	 * The source and destination addresses are assigned from the socket
	 */
	public StreamWriter(Socket socket, EventLog eventLog, String message) {
		this.socket = socket;
		this.eventLog = eventLog;
		this.message = message;
		this.source = this.socket.getLocalSocketAddress().toString();
		this.destination = this.socket.getRemoteSocketAddress().toString();
	}

	/**
	 * Constructor which takes as input a socket, an eventLog and a message to send,
	 * and a specific destination address. This allows the server to send
	 * communications to a specific client.
	 */
	public StreamWriter(Socket socket, EventLog eventLog, String message, String destination) {
		this.socket = socket;
		this.eventLog = eventLog;
		this.source = this.socket.getLocalSocketAddress().toString();
		this.destination = destination;
		this.message = message;
	}

	/**
	 * Constructor which takes as input a socket, an eventLog and a message to send,
	 * a source address, and a specific destination address. This allows the server
	 * to send client-to-client messages where each client is aware of the specific
	 * source of the message.
	 */
	public StreamWriter(Socket socket, EventLog eventLog, String message, String source, String destination) {
		this.socket = socket;
		this.eventLog = eventLog;
		this.source = source;
		this.destination = destination;
		this.message = message;
	}

	/**
	 * Overrides the default run method of a Thread. Creates an output stream,
	 * formats outgoing messages and updates the event log.
	 */
	public void run() {

		// try to open a write stream
		try {

			// add attempt message to log
			this.eventLog.addEvent(new Event("connection", "establishing output stream", true));

			// try to create new stream
			this.outputStream = new PrintStream(this.socket.getOutputStream());

			// add success message to log
			this.eventLog.addEvent(new Event("connection", "output stream established", true));

			this.outputStream.println(this.formatOutgoingMessage());
			this.eventLog.addEvent(new Event("outgoing message", "sent message -> " + message + "", true));
		}
		// catch any io exceptions from not being able to establish a stream
		catch (IOException e) {
			this.eventLog.addEvent(new Event("outgoing message exception",
					"ioexception: " + e.getMessage() + " -> could not establish stream", true));
		}
	}

	/**
	 * Converts a message to a standard format which includes a source address, a
	 * destination address and the message. This format can be parsed by a
	 * StreamReader at a later stage.
	 */
	public String formatOutgoingMessage() {
		return "src=[" + this.source + "]" + " dest=[" + this.destination + "] message=[" + this.message + "]";
	}

}
