package server;

import java.io.IOException;
import java.net.*;
import java.util.*;

import event.*;
import stream.*;

/**
 * is composed of a Socket, StreamReader and two EventLog objects (one for
 * general messages, another for messages to forward to other clients). The
 * startEventMonitor method is threaded and it monitors the
 * MultiClientServerThread EventLog to keep track of active/inactive
 * connections, and to parse messages from clients which can subsequently be
 * dealt with hierarchically by the MultiClientServer and the ChatServer.
 * 
 * @author ksomers
 *
 */
public class MultiClientServerThread extends Thread {

	// variables
	private Socket socket;
	private EventLog eventLog = new EventLog();
	private StreamReader streamReader;
	private EventLog messagesToRelay = new EventLog();

	/**
	 * Basic constructor
	 */
	public MultiClientServerThread() {
		super();
	}

	/**
	 * Constructor initialized directly with a Socket
	 */
	public MultiClientServerThread(Socket socket) {
		super();
		this.socket = socket;
	}

	/**
	 * Returns the thread socket
	 */
	public Socket getSocket() {
		return this.socket;
	}

	/**
	 * Converts the socket object to a string for printing etc.
	 */
	public String socketToString() {
		if (this.socket != null) {
			return "[socket info: host|client " + SocketHandler.getSocketInfo(this.socket) + "]";
		}
		return "[socket info: not connected to server]";
	}

	/**
	 * Simple method which returns the event log
	 */
	public synchronized EventLog getEventLog() {
		return this.eventLog;
	}

	/**
	 * Simple getter that returns a list of messages which must be relayed to other
	 * clients
	 */
	public synchronized EventLog getMessagesToRelay() {
		return messagesToRelay;
	}

	/**
	 * Simple setter method that allows the list of messages to relay to be set
	 * externally
	 */
	public synchronized void setMessagesToRelay(EventLog messagesToRelay) {
		this.messagesToRelay = messagesToRelay;
	}

	/**
	 * Run method which overrides usual method from Threads library. Automatically
	 * opens up a threaded streamReader which listens for incoming messages.
	 */
	public void run() {

		// add some items to the event log
		this.eventLog.addEvent(new Event("status", "started new client thread", true));
		this.eventLog.addEvent(
				new Event("status", "socket info: host|client -> " + SocketHandler.getSocketInfo(this.socket), true));

		// setup the streamReader
		this.streamReader = new StreamReader(this.socket, this.eventLog);
		this.streamReader.start();
		this.startEventMonitor(this);

	}

	/**
	 * Method which starts a new thread which monitors and parses any unserviced
	 * events i.e. connection status and incoming messages
	 */
	public void startEventMonitor(MultiClientServerThread mst) {

		// create a new thread
		Thread t = new Thread() {

			// run method
			public void run() {

				// update the event log
				mst.getEventLog().addEvent(new Event("status", "started the incoming message monitor", true));

				// always running ...
				while (true) {

					// monitor the event log
					mst.monitorEventLog();

					// get some status variables
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						mst.getEventLog().addEvent((new Event("interrupted exception",
								"InterruptedException in client event monitor " + e.getMessage(), true)));
					}

				}
			}
		};
		t.start();

	}

	/**
	 * Method which mointors the thread event log to parse incoming messages, add
	 * these messages to lists of messages which must be relayed to other clients,
	 * and which deals with connection problems.
	 */
	public void monitorEventLog() {

		// boolean used to decide whether the client has disconnected
		boolean disconnect = false;

		// process each event
		EventLog eventLog = this.getEventLog();
		for (Event event : eventLog.getEvents()) {

			// if there is an incoming message that has not been serviced
			// then parse it, and
			if (event.getType() == "incoming message" && !event.wasServiced()) {

				// parse the message and change the status
				String result = this.parseIncomingMessage(event.getText());

				// else add a message to the messagesToRelay EventLog which is independently
				// monitored by the server
				if(result != null) {
					if (result.equals("forward to client")) {
						this.getMessagesToRelay().addEvent(new Event("forward to client", event.getText(), false));
					} else {
						this.getMessagesToRelay().addEvent(new Event("message for server", event.getText(), false));
					}
				}

				// has been serviced
				event.changeServiceStatus();

			}
			// if there are connection events i.e. the stream reader being stopped
			// then set a disconnect switch and safely tear down the thread later
			else if (event.getType() == "connection" && !event.wasServiced()) {

				// service various disconnection warnings
				if (event.getText() == "connection stopped by client or server") {
					disconnect = true;
				} else if (event.getText() == "connection stopped, cause unknown") {
					disconnect = true;
				} else if (event.getText().contains("ioexception")) {
					disconnect = true;
				}

				// has been serviced
				event.changeServiceStatus();
			}

		}

		if (disconnect) {
			this.disconnect();
		}

	}

	/**
	 * Parses a single message and responds according to the message text
	 */
	public String parseIncomingMessage(String message) {

		ArrayList<String> parsedMessage = StreamReader.parseMessage(message);

		// if the parsed message is not null
		if (parsedMessage != null) {

			// get the destination address
			String destinationAddress = parsedMessage.get(1);

			// if the destination contains "local" then substitute out that part of the
			// string
			// to retain only the ip and port part of the string.
			if (destinationAddress.contains("localhost")) {
				destinationAddress = destinationAddress.replace("localhost", "");
			}

			// get the server address and test whether it is a message for the server or
			// another client
			String serverAddress = this.socket.getLocalAddress() + ":" + this.socket.getLocalPort();

			if (destinationAddress.equals(serverAddress)) {
				return "message for server";
			} else {
				return "forward to client";
			}

		}

		return null;
	}

	/**
	 * Disconnect the current client from the host
	 */
	public void disconnect() {

		// close the stream reader
		this.streamReader.close();
		
		// if the socket is not null then try to close it
		if (socket != null) {
			try {
				this.socket.close();
				this.eventLog.addEvent(new Event("connection", "socket has been closed", true));
			} catch (IOException e) {
				this.eventLog.addEvent(new Event("connection", "ioexception: " + e.getMessage()
						+ " -> error closing socket, some communications may be incomplete, restart server/client",
						true));
			}
			this.socket = null;
		}
	}

}
