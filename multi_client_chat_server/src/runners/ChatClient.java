package runners;

import client.Client;
import event.*;
import menu.*;
import stream.*;
import terminalIO.*;

import java.util.*;

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
public class ChatClient {

	// instance variables list
	private TerminalInputParser terminalParser = new TerminalInputParser();
	private RunnableCommandLineOptionMenu menu = new RunnableCommandLineOptionMenu();
	private Client client;

	private String currentMessage = "default message from client";
	private ArrayList<String> otherClients;
	private ArrayList<String> newClients;
	private ArrayList<String> clientsToMessage;
	private ArrayList<String> receivedMessages = new ArrayList<String>();
	private boolean printMessages = true;
	private Thread eventMonitor;

	/**
	 * Constructor which instantiates a new chat client
	 */
	public ChatClient() {
		super();
		this.client = new Client();
	}

	/**
	 * Simple getter method that returns the client
	 * 
	 * @return
	 */
	public Client getClient() {
		return this.client;
	}

	/**
	 * A simple method to print a title to the terminal.
	 */
	public void printTitle() {

		System.out.println("***************************************************************************");
		System.out.println("*           GMIT - Dept. Computer Science & Applied Physics               *");
		System.out.println("*                       ChatApp: ChatClient                               *");
		System.out.println("*                                                                         *");
		System.out.println("*                     Author: Kieran P. Somers                            *");
		System.out.println("*                       ID: g00221349@gmit.ie                             *");
		System.out.println("***************************************************************************");

	}

	/**
	 * Method used to build the menu options, all of which are executed as runnables
	 */
	public void buildMenuOptions() {

		// add an exit option
		Runnable r0 = () -> {
			this.exit();
		};
		RunnableCommandLineOption o0 = new RunnableCommandLineOption(r0, 0, "Exit program", "Safely exit the program");
		menu.addOption(o0);

		// add option to set the server port #
		Runnable r1 = () -> {
			this.setServerSocketPort();
		};
		RunnableCommandLineOption o1 = new RunnableCommandLineOption(r1, 1, "Set the ChatServer Port To Connect To",
				"Allows the user to change the port which it will try to connect to");
		menu.addOption(o1);

		// add option to set the server host name
		Runnable r2 = () -> {
			this.setHostName();
		};
		RunnableCommandLineOption o2 = new RunnableCommandLineOption(r2, 2, "Set the ChatServer HostName (as a string)",
				"Allows the user to change the hostname which it will try to connect to");
		menu.addOption(o2);

		// add option to connect to the server
		Runnable r3 = () -> {
			this.connect();
		};
		RunnableCommandLineOption o3 = new RunnableCommandLineOption(r3, 3, "Connect to a ChatServer",
				"Tries to establish a connection to a ChatServer");
		menu.addOption(o3);

		// add option to get a message to send to the server
		Runnable r4 = () -> {
			this.getUserMessageFromCommandLine();
		};
		RunnableCommandLineOption o4 = new RunnableCommandLineOption(r4, 4, "Enter New Chat Message",
				"This option allows the user to enter text to be distributed to clients.\nEnter \\q to terminate connection to the server.");
		menu.addOption(o4);

		// add option to choose a list of clients to send message to
		Runnable r5 = () -> {
			this.chooseDestinationClients();
		};
		RunnableCommandLineOption o5 = new RunnableCommandLineOption(r5, 5, "Choose Destination Clients",
				"This option allows the user to choose which clients will receive the message.");
		menu.addOption(o5);

		// add option to send the message
		Runnable r6 = () -> {
			this.sendMessage();
		};
		RunnableCommandLineOption o6 = new RunnableCommandLineOption(r6, 6, "Send Message to the ChatServer",
				"This option allows user to send a stored message to the server");
		menu.addOption(o6);

		// add an option to print logs to screen
		Runnable r7 = () -> {
			this.printClientLogs();
		};
		RunnableCommandLineOption o7 = new RunnableCommandLineOption(r7, 7, "Print Client Logs",
				"Prints a detailed set of logs about client events");
		menu.addOption(o7);

		// add an option to refresh the screen -- effectively does nothing other than
		// refresh the screen
		Runnable r8 = () -> {
			this.refresh();
		};
		RunnableCommandLineOption o8 = new RunnableCommandLineOption(r8, 8, "Refresh",
				"Will refresh the menu and print any received messages");
		menu.addOption(o8);

		// add an option to disconnect safely
		Runnable r9 = () -> {
			this.disconnect();
		};
		RunnableCommandLineOption o9 = new RunnableCommandLineOption(r9, 9, "Disconnect from server",
				"Allows the user to request the current client disconnects from the server");
		menu.addOption(o9);

	}

	/**
	 * A method to start an interactive session.
	 */
	public void startInteractiveSession() {

		// print a title
		this.printTitle();

		// build the menu options which will be presented to the user
		this.buildMenuOptions();

		while (true) {

			// print any received messages is only set to false after certain methods are
			// run so as to not print too much to the screen
			if (this.printMessages) {
				this.printReceivedMessages();
			}
			this.printMessages = true;

			// get an option from the user
			menu.setOptionToRunFromCommandLine();

			// run the option
			menu.runSetOption();

		}
	}

	/**
	 * Simple method which allows the user to change the port to which the client
	 * will connect
	 */
	public void setServerSocketPort() {
		this.client.setPort(this.terminalParser.getIntegerInput());
	}

	/**
	 * Simple method which allows the user to change the host to which the client
	 * will connect
	 */
	public void setHostName() {
		this.client.setHostname(this.terminalParser.getSingleLineTextInput());
	}

	/**
	 * Get a message from the command line which can be sent to the server
	 */
	public void getUserMessageFromCommandLine() {

		// get the message from the command line
		this.currentMessage = this.terminalParser.getSingleLineTextInput();

		// if message = \q then disconnect
		if (this.currentMessage.equals("\\q") && this.client.isConnected()) {
			this.disconnect();
		}

	}

	/**
	 * Sends messages to all chosen clients
	 */
	public void sendMessage() {

		// if message = \q then disconnect
		if (this.currentMessage.equals("\\q") && this.client.isConnected()) {
			this.disconnect();
		}
		// else send the message to all clients the user has requested
		else if (this.client.isConnected()) {
			// iterate over clients
			for (String client : this.clientsToMessage) {
				this.client.sendMessage(this.currentMessage, client);
			}
		}

	}

	/**
	 * Method which tries to establish a connection to a server. Delegates to a
	 * method in the client.
	 */
	public void connect() {

		// if the client is not already connected
		if (!this.client.isConnected()) {

			// try to connect
			boolean connectedSuccessfully = this.client.connect();

			// if the connection was successful start the event monitor
			if (connectedSuccessfully) {

				// start the event monitor thread
				this.startMonitor(this);

				// add the server to the list of "clients" which this client can message
				this.otherClients = new ArrayList<String>();
				this.otherClients.add(this.client.getSocket().getRemoteSocketAddress().toString());

				// set the server as the default client to message
				// this can be overridden by user if other clients join etc.
				this.clientsToMessage = this.otherClients;

			}
		}

	}

	/**
	 * Refreshes the screen
	 */
	public void refresh() {

	}

	/**
	 * Method which disconnects a client from the server
	 */
	public void disconnect() {

		// if the client has been initiated
		if (this.client != null) {

			// interrupt the event monitor
			if (this.eventMonitor != null) {
				this.eventMonitor.interrupt();
			}

			// disconnect the client
			this.client.disconnect();

			// set some variables to default/null values
			this.otherClients = null;
			this.clientsToMessage = null;
			this.currentMessage = "Default message from ChatClient";

		} else {
			System.out.println("\t-[disconnecting] cannot disconnect until connected to server.");
		}

	}

	/**
	 * Method which starts a new thread which monitors and parses any unserviced
	 * events and which tests the connection.
	 */
	public void startMonitor(ChatClient cc) {

		// if the client is connected and the event monitor has not been started
		if (this.client.isConnected() && this.eventMonitor == null) {

			// creates a new thread
			this.eventMonitor = new Thread() {

				// run method
				public void run() {

					// add an event to the event log
					cc.client.getEventLog().addEvent(new Event("status", "started the client event monitor", true));

					// always running ...
					while (true) {

						// monitor the event log
						cc.monitorEventLog();

						// sleep momentarily so thread is not always checking for updates
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							cc.client.getEventLog().addEvent((new Event("interrupted exception",
									"InterruptedException in client event monitor -> not fatal will start again upon user-requested connection -> "
											+ e.getMessage(),
									true)));
						}

					}
				}
			};

			// start the event monitor
			this.eventMonitor.start();
		}

	}

	/**
	 * Method which monitors the client event log and processes any unserviced
	 * events.
	 */
	public synchronized void monitorEventLog() {

		// some local variables -- new clients are other clients which the server has
		// forward to the client.
		this.newClients = new ArrayList<String>();
		boolean disconnect = false;
		
		// process any unserviced events
		for (Event event : this.client.getEventLog().getEvents()) {

			// send to the incoming message parser if the incoming message event was not yet
			// dealt with
			if (event.getType() == "incoming message" && !event.wasServiced()) {
				this.parseIncomingMessage(event);
				event.changeServiceStatus();
			}
			// process a connection event
			else if (event.getType() == "connection" && !event.wasServiced()) {

				if (event.getText() == "connection stopped by client or server") {
					disconnect = true;
				} else if (event.getText() == "connection stopped, cause unknown") {
					disconnect = true;
				} else if (event.getText().contains("ioexception")) {
					disconnect = true;
				}
				event.changeServiceStatus();

			}

		}

		// update the list of other clients -- this list of new clients was edited in
		// the parseIncomingMessage() method
		if (this.newClients.size() > 0) {

			// update the event log
			this.client.getEventLog().addEvent(new Event("status", "updated clients list", true));

			// update the list of other known clients
			this.otherClients = new ArrayList<String>();

			// always add the server first
			this.otherClients.add(this.client.getSocket().getRemoteSocketAddress().toString());

			// now add the other clients
			for (String client : this.newClients) {
				if (client != null) {
					this.otherClients.add(client);
				}
			}

		}

		// disconnect
		if (disconnect) {
			this.disconnect();
		}

	}

	/**
	 * Parses a single message and responds according to the message text
	 */
	public void parseIncomingMessage(Event event) {
		
		ArrayList<String> parsedMessage = StreamReader.parseMessage(event.getText());

		// if the message was parsed successfully there will be source, destination
		// and message text fields -- process the text.
		if (parsedMessage != null) {

			String source = parsedMessage.get(0);
			String destination = parsedMessage.get(1);
			String messageText = parsedMessage.get(2);

			// if the server has forwarded a known client, add the client string to the list
			// of new clients
			if (messageText.contains("forwarding known client:")) {
				this.newClients.add(messageText.replace("forwarding known client: ", ""));
			}
			// else if this client is the only client just add a null client to the client
			// list
			else if (messageText.contains("only one client on network")) {
				this.newClients.add(null);
			}

			// add the received message to the list of received messages.
			String formattedMessage = "From: " + source + "\nTo: " + destination + "\nReceived: " + event.getTime()
					+ "\nMessage: " + messageText;
			this.addNewReceivedMessage(formattedMessage);

		}
		// else print a warning to the screen that a null message was received
		else {
			System.out.println("\t-Received null message from server");
		}

	}

	/**
	 * Method which allows a received message to be added to the arraylist. Must be
	 * accessed in a synchronized manner as new messages are potentially been added
	 * while the list is being accessed.
	 */
	public synchronized void addNewReceivedMessage(String message) {
		this.receivedMessages.add(message);
	}

	/**
	 * Method which allows the list of received messages to be retrieved i.e. for
	 * printing. Must be accessed in a synchronized manner as new messages are
	 * potentially been added while the list is being accessed.
	 */
	public synchronized ArrayList<String> getReceivedMessages() {
		return this.receivedMessages;
	}

	/**
	 * Allows the user to choose a list of clients that will receive any future
	 * messages.
	 */
	public void chooseDestinationClients() {

		// if there are other clients ...
		if (this.otherClients != null) {
			

			// print a tip to the screen so user knowns how to enter data
			System.out.println();
			TerminalTitleWriter.writeTitle("", "-", 75,
					"-ChatClient.chooseDestinationClients()" + "\nchoose 1 or more destinations for message."
							+ "\nEnter options as comma/hyphen separated values."
							+ "\nTo choose clients 1,2,4,5,6,7,9 and 15 enter 1,2,4-7,9,15"
							+ "\nEnter 0 to retain current or default settings.");

			// get the user selected terminal input
			int[] userChoice = this.terminalParser.chooseMultipleStringsFromList(this.otherClients);

			// now convert the user choices to an array
			this.clientsToMessage = new ArrayList<String>();

			if(userChoice != null) {
				// if there is only one user input and it is equal to 0
				// no changes are made to the clients list, the default client with index
				// 0 i.e. the ChatServer will be the destination for messages
				if (userChoice.length == 1 && userChoice[0] == 0) {
					this.clientsToMessage.add(this.otherClients.get(0));
				}
				// else update the clients to message arraylist
				else {
					for (int i = 0; i < userChoice.length; i++) {
						if (userChoice[i] >= 1 && userChoice[i] <= this.otherClients.size()) {
							this.clientsToMessage.add(this.otherClients.get(userChoice[i] - 1));
						}
					}
				}
			}
			else {
				System.out.println("\t-error parsing user input, message will be sent to server.");
				this.clientsToMessage.add(this.otherClients.get(0));
			}

			// print update the screen
			for (String client : this.clientsToMessage) {
				System.out.println("\t-will send all future messages to: " + client);
			}

		}
		// else let the user know that there are no possible clients to message
		else {
			System.out.println("-no clients exist, are you connected to the server?");
		}

	}

	/**
	 * Prints any stored logs to screen, delegates to EventLogPrinter
	 */
	public synchronized void printClientLogs() {

		// print a title
		System.out.println();
		TerminalTitleWriter.writeTitle("", "-", 75, "-writing detailed client logs");
		System.out.println();
		System.out.println(this.client.socketToString());
		System.out.println();
		EventLogPrinter.printLogsByTime(this.client.getEventLog());
		this.printMessages = false;

	}

	/**
	 * Prints a list of received messages to screen
	 */
	public void printReceivedMessages() {

		// if there are messages, print them
		if (this.getReceivedMessages().size() > 0) {

			System.out.println();
			TerminalTitleWriter.writeTitle("", "-", 75, "-writing received messages");
			System.out.println();

			int index = 0;
			for (String string : this.getReceivedMessages()) {
				index++;
				TerminalTitleWriter.writeTitle("", "-", 75, "Message # " + index);
				System.out.println(string);
				TerminalTitleWriter.writeLine("", "-", 75);
				System.out.println();
			}
		}
	}

	/**
	 * Method which safely closes all connections before safely closing entire
	 * application
	 */
	public void exit() {

		if (this.client.isConnected()) {
			this.disconnect();
		}
		System.out.println("User requested exit - slan leat!");
		System.exit(0);

	}

	/**
	 * Main method -- called when class is executed at command line. No command line
	 * arguments are parsed as all actions can be implemented using the menu that
	 * has been configured
	 */
	public static void main(String[] args) {

		ChatClient client = new ChatClient();
		client.startInteractiveSession();

	}

}
