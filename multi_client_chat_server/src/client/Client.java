package client;

import java.io.*;
import java.net.*;

import event.*;
import stream.*;

/**
 * composed of TerminalInputParser and a RunnableCommandLineOptionMenu objects
 * (which facilitate interactive user sessions where the user chooses tasks from
 * a pre-built menu of options), and a Client object (which manages state and
 * behaviour related to connecting to the server). Contains functionality which
 * allows the user to set the port and hostname to which the client will
 * connect, to connect to said server, to enter a user defined message, to
 * choose a list of clients (or the ChatServer) to send the message to, to
 * forward the message to the chosen clients, print received messages and
 * detailed event logs to the screen, and to disconnect from the server and
 * subsequently reconnect with all previous status/events/messages for the
 * session still available. The startMonitor and monitorEventLog methods are
 * threaded and they monitor the Client EventLog in order to parse incoming
 * messages (including lists of other Clients which are forwarded by the
 * server), and to monitor the Socket connection status (i.e. disconnection as a
 * result of user action or network errors).
 * 
 * @author ksomers
 *
 */
public class Client {

	// variables list
	private int port;
	private String hostname;
	private Socket socket;
	private EventLog eventLog = new EventLog();
	private StreamReader streamReader;
	private StreamWriter streamWriter;
	private boolean haveConnection = false;

	/**
	 * Constructor, the client is initialized with default hostnames and port
	 * numbers
	 */
	public Client() {
		super();
		this.hostname = this.getDefaultHost();
		this.port = this.getDefaultPort();
	}

	/**
	 * Simple method to get a default host
	 */
	private String getDefaultHost() {
		return "localhost";
	}

	/**
	 * Simple method to get a default port number
	 */
	private int getDefaultPort() {
		return 16000;
	}

	/**
	 * Simple method to get the current port number
	 */
	public int getPort() {
		return this.port;
	}

	/**
	 * Simple method to set the current port value. If the client is currently
	 * connected, the port number will not be changed.
	 */
	public void setPort(int port) {
		if (this.isConnected()) {
			this.eventLog.addEvent(new Event("status",
					"cannot set port to " + port + " as client is already connected to server", true));
		} else {
			this.eventLog.addEvent(new Event("status", "set port to " + port, true));
			this.port = port;
		}
	}

	/**
	 * Simple method to get the current hostname
	 */
	public String getHostname() {
		return this.hostname;
	}

	/**
	 * Simple method to set the current hostname. If the client is currently
	 * connected, the hostname will not be changed.
	 */
	public void setHostname(String hostname) {

		if (this.isConnected()) {
			this.eventLog.addEvent(new Event("status",
					"cannot set hostname to " + hostname + " as client is already connected to server", true));
		} else {
			this.eventLog.addEvent(new Event("status", "set hostname to " + hostname, true));
			this.hostname = hostname;
		}

	}

	/**
	 * Returns the client Socket instance
	 */
	public Socket getSocket() {
		return this.socket;
	}

	/**
	 * Simple method which returns the Client EventLog
	 */
	public synchronized EventLog getEventLog() {
		return this.eventLog;
	}

	/**
	 * Converts the client hostname and port number to a string for printing
	 */
	public String hostPortAsString() {
		return "hostname:port = " + this.hostname + ":" + this.port;
	}

	/**
	 * Converts the clients Socket to a string which can be printed to the screen
	 * 
	 * @return
	 */
	public String socketToString() {
		if (this.haveConnection) {
			return "[socket info: client|host " + SocketHandler.getSocketInfo(this.socket) + "]";
		}
		return "[socket info: not connected to server]";
	}

	/**
	 * Simple method used to determine whether the client is connected to a Socket
	 */
	public boolean isConnected() {
		return this.haveConnection;
	}

	/**
	 * Connects the client to the server. Automatically opens up a threaded
	 * streamReader which listens for incoming messages.
	 */
	public boolean connect() {

		// add a note to the event log
		this.eventLog.addEvent(new Event("connection", "connecting to " + this.hostPortAsString(), true));

		// try to open a socket to the hostname:port
		try {

			// if successful assign the socket to the client variable
			this.socket = new Socket(this.hostname, this.port);

			// add successful messages to the log
			this.eventLog.addEvent(new Event("connection", "established connection " + this.hostPortAsString(), true));
			this.eventLog.addEvent(new Event("connection",
					"socket info: client|host " + SocketHandler.getSocketInfo(this.socket), true));
			this.haveConnection = true;

			// setup the streamReader -- give it access to the client event log to
			// add any messages/events to
			this.streamReader = new StreamReader(this.socket, this.eventLog);
			this.streamReader.start();

			// return true if connection was successful
			return true;

		}
		// catch an unknown host exception and add a message to the event log
		// and return false
		catch (UnknownHostException e) {
			this.eventLog.addEvent(new Event("exception",
					"[UnknownHostException: " + e.getMessage() + "] -> [host is unavailable]", true));
			return false;
		}
		// catch an io exception and add a message to the error log
		// and return false
		catch (IOException e) {
			this.eventLog.addEvent(new Event("exception",
					"[IOException: " + e.getMessage() + "] -> [host is known, but no server is listening on this port]",
					true));
			return false;
		}

	}

	/**
	 * Method which forwards a message from the client to the destination. The
	 * destination address is a string of the format /127.0.0.1:port. It does not
	 * have to correspond to the remote address of the socket, the destination
	 * address can be that of another client or the server at hostname:port.
	 */
	public void sendMessage(String message, String destination) {

		// if the client has a connection send the message
		if (this.haveConnection) {

			// add some items to the event log
			this.eventLog.addEvent(new Event("status", "sending message to " + this.hostPortAsString(), true));
			this.eventLog.addEvent(new Event("status", "message -> " + message, true));

			// send the message via a stream writer
			this.streamWriter = new StreamWriter(socket, eventLog, message, destination);
			this.streamWriter.start();

		}
		// else add a warning to the event log
		else {
			this.eventLog
					.addEvent(new Event("connection", "client is not connected to server, cannot send message", true));

		}

	}

	/**
	 * Disconnect the current client from the host
	 */
	public void disconnect() {

		// if the socket has be initialized and is not == null
		if (socket != null) {

			// chose the stream reader
			this.streamReader.close();

			// try to gracefully tear down the connection
			try {
				this.socket.close();
				this.eventLog.addEvent(new Event("connection", "socket has been closed", true));
			} catch (IOException e) {
				this.eventLog.addEvent(new Event("connection", "ioexception: " + e.getMessage()
						+ " -> error closing socket, some communications may be incomplete, restart server/client",
						true));
			}

			// set the socket equal to null and the haveConnection variable to false
			this.socket = null;
			this.haveConnection = false;
		}

	}

}
