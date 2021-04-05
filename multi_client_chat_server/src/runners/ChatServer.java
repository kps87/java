package runners;

import terminalIO.*;

import java.net.*;
import java.util.*;

import event.*;
import menu.*;
import server.*;
import stream.StreamReader;
import stream.StreamWriter;

/**
 * the ChatServer has TerminalInputParser and RunnableCommandLineOptionMenu
 * objects to facilitate interactive user sessions, an EventLog (for storing
 * acting upon message/socket events and storing general status), and a list of
 * MultiClientServer objects. Each MultiClientServer object in the ChatServer
 * contains much of the functionality of the actual server and the ChatServer
 * wraps that functionality in an interactive way. Contains functionality to set
 * the port of the server, to launch one or more servers on different ports, to
 * enter a chat message and forward it to specific clients or all connected
 * clients, to distribute a list of all connected clients to all other clients
 * to facilitate client-to-client communication, and to safely close all client
 * connections. The startClientMonitor and startMessageRelay methods are
 * threaded and they monitor the MultiClientServer and MultiClientServerThread
 * EventLogs in order to distribute lists of connected clients across the
 * network, and to relay messages between clients.
 * 
 * @author ksomers
 *
 */
public class ChatServer {

	// variables list
	private int port = 16000;
	private RunnableCommandLineOptionMenu menu = new RunnableCommandLineOptionMenu();
	private TerminalInputParser terminalParser = new TerminalInputParser();
	private String currentMessage;
	private ArrayList<MultiClientServer> servers = new ArrayList<MultiClientServer>();
	private EventLog eventLog = new EventLog();
	private ArrayList<String> receivedMessages = new ArrayList<String>();
	private boolean printMessages = true;

	/**
	 * Constructor
	 */
	public ChatServer() {
		super();
	}

	/**
	 * Simple getter method to get a list of servers
	 */
	public ArrayList<MultiClientServer> getServers() {
		return servers;
	}

	/**
	 * Get a message from the command line which can be sent to clients
	 */
	public void getUserMessageFromCommandLine() {

		// get the message
		this.currentMessage = this.terminalParser.getSingleLineTextInput();

		// update the event log
		this.eventLog.addEvent(
				new Event("status", "got user defined message from command line " + this.currentMessage, true));

		// if message = \q then disconnect
		if (this.currentMessage.equals("\\q") && this.getNumberOfClients() > 0) {
			this.disonnectAllClients();
		}

	}

	/**
	 * Gets the number of clients currently connected to the server(s)
	 * 
	 * @return
	 */
	public int getNumberOfClients() {

		// counter variable
		int numberClients = 0;

		// for each server get the number of active client threads
		if (this.servers.size() > 0) {
			for (MultiClientServer server : this.servers) {
				numberClients += server.getNumberOfActiveThreads();
			}
		}

		// return the counter
		return numberClients;
	}

	/**
	 * Returns a list of known remote socket addresses e.g. client address+port
	 */
	public ArrayList<String> getActiveClientAddresses() {

		// get a list of remote socket addresses
		ArrayList<String> remoteAddresses = new ArrayList<String>();
		for (MultiClientServer server : this.servers) {
			ArrayList<MultiClientServerThread> threads = server.getActiveThreads();
			if (threads != null) {
				for (MultiClientServerThread thread : threads) {
					if (thread.getSocket() != null) {
						remoteAddresses.add(thread.getSocket().getRemoteSocketAddress().toString());
					}
				}
			}
		}
		return remoteAddresses;
	}

	/**
	 * Simple method which allows the user to change the port which the server is
	 * listening to/awaiting connections from
	 */
	public void setServerSocketPort() {

		// get the port from the command line
		this.port = this.terminalParser.getIntegerInput();

		// update the event log
		this.eventLog.addEvent(new Event("status", "next server will be started on port " + this.port, true));

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
	 * A simple method to print a title to the terminal.
	 */
	public void printTitle() {

		System.out.println("***************************************************************************");
		System.out.println("*           GMIT - Dept. Computer Science & Applied Physics               *");
		System.out.println("*                       ChatApp: ChatServer                               *");
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

		// add an option to start a new server
		Runnable r1 = () -> {
			this.startNewServer();
		};
		RunnableCommandLineOption o1 = new RunnableCommandLineOption(r1, 1, "Start a New Multi-Client Chat-Server",
				"Starts a new multi-client server which awaits incoming connection requests from clients.\nPort numbers are autoincremented after each server is created.");
		menu.addOption(o1);

		// add an option to set the server socket port
		Runnable r2 = () -> {
			this.setServerSocketPort();
		};
		RunnableCommandLineOption o2 = new RunnableCommandLineOption(r2, 2, "Set the Server port",
				"Allows the user to change the active port for any new incoming clients connections");
		menu.addOption(o2);

		// add an option to print the server logs
		Runnable r3 = () -> {
			this.printServerLogs();
		};
		RunnableCommandLineOption o3 = new RunnableCommandLineOption(r3, 3, "Print Server Logs",
				"Print a detailed summary of various events such as status updates, connection events, and messages sent/received.");
		menu.addOption(o3);

		// add an option to get user message from the command line
		Runnable r4 = () -> {
			this.getUserMessageFromCommandLine();
		};
		RunnableCommandLineOption o4 = new RunnableCommandLineOption(r4, 4, "Enter New Chat Message",
				"This option allows the user to enter text to be distributed to clients.\nEnter \\q to terminate connection to ALL clients.");
		menu.addOption(o4);

		// add an option to send the message to a list of specific clients
		Runnable r5 = () -> {
			this.sendMessageToSpecificClients();
		};
		RunnableCommandLineOption o5 = new RunnableCommandLineOption(r5, 5, "Send Message to Specific Client",
				"Sends a message to a specific group of client(s) that are connected to any server. Sends a default message if none have been entered.");
		menu.addOption(o5);

		// add an option to distribute the message to all clients
		Runnable r6 = () -> {
			this.sendMessageToAllClients();
		};
		RunnableCommandLineOption o6 = new RunnableCommandLineOption(r6, 6, "Send Message to All Clients",
				"Sends a message to all clients with a currently opened connection. Sends a default message if none have been entered.");
		menu.addOption(o6);

		// add an option to refresh the screen
		Runnable r7 = () -> {
			this.refresh();
		};
		RunnableCommandLineOption o7 = new RunnableCommandLineOption(r7, 7, "Refresh",
				"Will refresh the menu and print any received messages");
		menu.addOption(o7);

		// add an option to disconnect all clients safely
		Runnable r8 = () -> {
			this.disonnectAllClients();
			;
		};
		RunnableCommandLineOption o8 = new RunnableCommandLineOption(r8, 8, "Close all client connections",
				"Will close all connections to the server");
		menu.addOption(o8);

	}

	/**
	 * A method to start the session.
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
	 * Method which safely closes all connections before safely closing entire
	 * application
	 */
	public void exit() {

		if (this.getActiveClientAddresses().size() > 0) {
			this.disonnectAllClients();
		}
		System.out.println("User requested exit - slan leat!");
		System.exit(0);

	}

	/**
	 * Method creates a new multi client server and adds it to a list of servers
	 */
	public void startNewServer() {

		// create a new server instance, set the port and start the server
		this.eventLog.addEvent(new Event("status", "starting new server on port " + this.port, true));
		MultiClientServer server = new MultiClientServer();
		server.setPort(this.port);
		this.servers.add(server);
		server.start();

		// update the event logs
		this.eventLog.addEvent(new Event("status", "total number of active servers = " + this.servers.size(), true));
		this.port += 1;
		this.eventLog.addEvent(new Event("status", "next server will be started on port " + this.port, true));

		// start the server monitors
		if (this.servers.size() == 1) {
			this.startClientMonitor(this);
			this.startMessageRelay(this);
		}

	}

	/**
	 * Refreshes the screen
	 */
	public void refresh() {

	}

	/**
	 * Thread which monitors any updates to the client list and forwards host
	 * addresses to all clients any time a new client connects or a connected client
	 * disconnects
	 */
	public void startClientMonitor(ChatServer cs) {

		// create a new thread
		Thread t = new Thread() {

			// create a run method
			public void run() {

				// update the chat server event log
				cs.eventLog.addEvent(new Event("status", "started the client monitor", true));

				// always running ...
				while (true) {

					// check if there are status events indicating the active/inactive clients were
					// updated
					boolean forwardHostAddresses = false;
					for (MultiClientServer server : cs.getServers()) {
						for (Event event : server.getEventLog().getEvents()) {
							if (!event.wasServiced()) {

								if (event.getText().equals("updated active or inactive client threads list")) {
									forwardHostAddresses = true;
								}

								event.changeServiceStatus();
							}
						}
					}

					// now forward the host addresses
					if (forwardHostAddresses) {
						cs.forwardHostAddresses();
					}

					// try to sleep so thread is not hogging cpu
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						cs.eventLog.addEvent((new Event("interrupted exception",
								"InterruptedException in client monitor " + e.getMessage(), true)));
					}

				}
			}
		};

		// start the thread
		t.start();

	}

	/**
	 * Starts a thread which monitors the incoming messages lists of all
	 * servers/client threads in order to facilitate client to client communication.
	 */
	public void startMessageRelay(ChatServer cs) {

		// create a new thread
		Thread t = new Thread() {

			// create a run method
			public void run() {

				// update the server event log
				cs.eventLog.addEvent(new Event("status", "started the message relay thread", true));
				while (true) {

					// get any messages to relay
					for (MultiClientServer server : cs.getServers()) {
						for (MultiClientServerThread mst : server.getMultiServerThreads()) {

							for (Event event : mst.getMessagesToRelay().getEvents()) {
								if (!event.wasServiced()) {
									cs.parseIncomingMessage(event);
									event.changeServiceStatus();
								}
							}
						}
					}

					// try to sleep so thread is not hogging cpu
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						cs.eventLog.addEvent((new Event("interrupted exception",
								"InterruptedException in client monitor " + e.getMessage(), true)));
					}

				}
			}
		};

		// start the thread
		t.start();

	}

	/**
	 * Parses a single message and responds according to the message text.
	 */
	public void parseIncomingMessage(Event event) {

		// parse the incoming message event text
		ArrayList<String> parsedMessage = StreamReader.parseMessage(event.getText());

		// if the message is not null, either forward it to a client
		// or add it to the messages specifically intended for the server
		if (parsedMessage != null) {

			String source = parsedMessage.get(0);
			String destination = parsedMessage.get(1);
			String messageText = parsedMessage.get(2);

			ArrayList<String> addresses = this.getActiveClientAddresses();
			

			if (addresses != null) {
				if (this.getActiveClientAddresses().contains(destination)) {
					this.forwardMessageToClient(source, destination, messageText);
				} else {
					String formattedMessage = "From: " + source + "\nTo: " + destination + "\nReceived: "
							+ event.getTime() + "\nMessage: " + messageText;
					this.addNewReceivedMessage(formattedMessage);
				}
			}
		}

	}

	/**
	 * When given a source address, a destination address and a message, finds the
	 * server and multiclient server thread that owns the destination address and
	 * forwards the message to them along with the source address of the client
	 * which sent the message.
	 */
	public void forwardMessageToClient(String sourceAddress, String destinationAddress, String message) {
		
		// for each server
		for (MultiClientServer server : this.servers) {

			// for each thread listening to clients on that server
			for (MultiClientServerThread thread : server.getActiveThreads()) {

				// get the socket
				Socket socket = thread.getSocket();

				// if the socket remote address matches the destination address send the message
				// to the client
				if (socket.getRemoteSocketAddress().toString().equals(destinationAddress)) {
					

					// add some items to the event log
					thread.getEventLog().addEvent(new Event("outgoing message",
							"forwarding client-to-client message from " + sourceAddress + " to " + destinationAddress,
							true));

					// open a stream writer and start the thread that writes the data to the output
					// stream
					StreamWriter streamWriter = new StreamWriter(socket, thread.getEventLog(), message, sourceAddress,
							destinationAddress);
					streamWriter.start();

				}
			}
		}

	}

	/**
	 * Method which allows the user to send a message to a specific list of
	 * destination clients
	 */
	public void sendMessageToSpecificClients() {

		// get the list of destination clients
		ArrayList<String> destinationClients = this.chooseDestinationClients();

		// if the list is not null ...
		if (destinationClients != null) {

			// check if the currentMessage to be sent is null, if so it will be overwritten
			// with a default message
			boolean messageUndefined = true;
			if (this.currentMessage != null) {
				messageUndefined = false;
			}
			int numberOfMessages = 0;

			// for each server, and each client thread on that server, forward the message
			for (MultiClientServer server : this.servers) {
				for (MultiClientServerThread thread : server.getActiveThreads()) {

					// get the thread socket
					Socket socket = thread.getSocket();

					// for each of the destination clients
					for (String destinationAddress : destinationClients) {

						// if the socket address matches the chosen destination address forward the
						// message
						if (socket.getRemoteSocketAddress().toString().equals(destinationAddress)) {

							// increment the counter
							numberOfMessages++;

							// if message is not defined send a default message
							if (messageUndefined) {
								this.currentMessage = "default message from server -> Hello, host:port "
										+ socket.getRemoteSocketAddress().toString() + "!";
							}

							// add some items to the event log
							thread.getEventLog().addEvent(new Event("status",
									"sending message to " + socket.getRemoteSocketAddress().toString(), true));
							thread.getEventLog()
									.addEvent(new Event("status", "message -> " + this.currentMessage, true));

							// open a stream writer and start the thread that writes the data to the output
							// stream
							StreamWriter streamWriter = new StreamWriter(socket, thread.getEventLog(),
									this.currentMessage);
							streamWriter.start();

						}
					}
				}
			}

			// reset the current message to null
			if (messageUndefined) {
				this.currentMessage = null;
			}

			// add an event to the event log
			this.eventLog.addEvent(new Event("status", "sent messages to " + numberOfMessages + " clients", true));

		}
		// else warn the user that there are no clients to whom they can send the
		// message
		else {
			this.eventLog.addEvent(
					new Event("status", "no active clients connected to server -> cannot forward message", true));
		}

	}

	/**
	 * Sends whatever message is stored in the currentMessage variable to all
	 * clients.
	 */
	public void sendMessageToAllClients() {

		// add an event status
		this.eventLog.addEvent(new Event("status", "sending message to all clients", true));

		// if the message is undefined a default one will be set ...
		boolean messageUndefined = true;
		if (this.currentMessage != null) {
			messageUndefined = false;
		}

		// get the number of clients
		int numberClients = this.getNumberOfClients();

		// if there are no clients connected
		if (numberClients == 0) {
			this.eventLog.addEvent(new Event("status", "no clients to send message to", true));
		}
		// else if there are clients connected
		else {

			// add an event to the eventLog
			this.eventLog.addEvent(new Event("status", "sending messages to " + numberClients + " clients", true));

			// a counter
			int numberOfMessages = 0;

			// for each server and client thread on that server send them the message
			for (MultiClientServer server : this.servers) {
				for (MultiClientServerThread thread : server.getActiveThreads()) {

					// increment the counter
					numberOfMessages++;

					// get the thread socket
					Socket socket = thread.getSocket();

					// if message is not defined send a default message
					if (messageUndefined) {
						this.currentMessage = "default message from server -> Hello, host:port "
								+ socket.getRemoteSocketAddress().toString() + "!";
					}

					// add some items to the event log
					thread.getEventLog().addEvent(new Event("status",
							"sending message to " + socket.getRemoteSocketAddress().toString(), true));
					thread.getEventLog().addEvent(new Event("status", "message -> " + this.currentMessage, true));

					// open a stream writer and start the thread that writes the data to the output
					// stream
					StreamWriter streamWriter = new StreamWriter(socket, thread.getEventLog(), this.currentMessage);
					streamWriter.start();
				}
			}

			// reset the current message to null
			if (messageUndefined) {
				this.currentMessage = null;
			}

			// add an event to the event log
			this.eventLog.addEvent(new Event("status", "sent messages to " + numberOfMessages + " clients", true));

		}
	}

	/**
	 * Forwards a list of known host addresses to all clients. Clients are not sent
	 * their own addresses
	 */
	public void forwardHostAddresses() {

		// get a list of all remote addresses
		ArrayList<String> remoteAddresses = this.getActiveClientAddresses();

		// for each server->clientThread update their list of remote hosts
		for (MultiClientServer server : this.servers) {

			// loop over multiclientserver threads
			ArrayList<MultiClientServerThread> threads = server.getActiveThreads();
			if (threads != null) {
				for (MultiClientServerThread thread : threads) {

					// get the thread socket
					Socket socket = thread.getSocket();

					if (socket != null) {
						// send each host address to each client - don't send a clients address to
						// itself
						if (remoteAddresses.size() > 1) {
							for (String remoteAddress : remoteAddresses) {
								if (!socket.getRemoteSocketAddress().toString().equals(remoteAddress)) {

									// send the message
									String message = "forwarding known client: " + remoteAddress;

									// add some items to the event log
									thread.getEventLog()
											.addEvent(new Event("outgoing message",
													"forwarding known client address "
															+ socket.getRemoteSocketAddress().toString() + " to "
															+ remoteAddress,
													true));

									// open a stream writer and start the thread that writes the data to the output
									// stream
									StreamWriter streamWriter = new StreamWriter(socket, thread.getEventLog(), message);
									streamWriter.start();
								}
							}
						}
						// if there is only one remote address, send a message
						// informing that client they are the only client on the network
						else {

							// send the message
							String message = "only one client on network: " + socket.getRemoteSocketAddress();

							// add some items to the event log
							thread.getEventLog().addEvent(
									new Event("status", "informing client " + socket.getRemoteSocketAddress().toString()
											+ " there are no other clients connected.", true));

							// open a stream writer and start the thread that writes the data to the output
							// stream
							StreamWriter streamWriter = new StreamWriter(socket, thread.getEventLog(), message);
							streamWriter.start();
						}
					}
				}
			}
		}

	}

	/**
	 * Allows the user to choose a list of clients that will receive any future
	 * message
	 */
	public ArrayList<String> chooseDestinationClients() {

		// if there are clients ...
		if (this.getNumberOfClients() > 0) {

			// create an array list to store results in
			ArrayList<String> activeClients = this.getActiveClientAddresses();

			// print a tip to the screen so user knowns how to enter data
			System.out.println();
			TerminalTitleWriter.writeTitle("", "-", 75,
					"-ChatServer.chooseDestinationClients()" + "\nchoose 1 or more destinations for message."
							+ "\nEnter options as comma/hyphen separated values."
							+ "\nTo choose clients 1,2,4,5,6,7,9 and 15 enter 1,2,4-7,9,15"
							+ "\nEnter 0 to retain current or default settings.");

			// get the user selected terminal input
			int[] userChoice = this.terminalParser.chooseMultipleStringsFromList(activeClients);

			// now convert the user choices to an array
			ArrayList<String> clientsToMessage = new ArrayList<String>();

			if(userChoice != null) {
				// if there is only one user input and it is equal to 0
				// no changes are made to the clients list, the default client with index
				// 0 i.e. the ChatServer will be the destination for messages
				if (userChoice.length == 1 && userChoice[0] == 0) {
	
				}
				// else update the clients to message arraylist
				else {
					for (int i = 0; i < userChoice.length; i++) {
						if (i >= 0 && i <= userChoice.length) {
							clientsToMessage.add(activeClients.get(userChoice[i] - 1));
						}
					}
				}
				// return the list
				return clientsToMessage;
			}
			else {
				System.out.println("\t-error parsing user input, please try again.");
				return null;
			}



		}
		// else warn the user that there are no clients on the server(s)
		else {
			System.out.println("-no active clients connected to server(s).");
			return null;
		}

	}

	/**
	 * Method which safely tears down connections with all clients
	 */
	public void disonnectAllClients() {

		// if there are clients connected ...
		if (this.getNumberOfClients() > 0) {

			// for each server->clientThread update their list of remote hosts
			for (MultiClientServer server : this.servers) {

				// loop over multiclientserver thread
				for (MultiClientServerThread thread : server.getActiveThreads()) {
					thread.disconnect();
				}
			}
		} else {
			System.out.println("-no active clients connected to server(s) - try logging in.");
		}

	}

	/**
	 * Prints a detailed report about the server
	 */
	public void printServerLogs() {

		TerminalTitleWriter.writeTitle("", "-", 75, "-writing detailed server logs");
		EventLogPrinter.printLogsByTime(this.eventLog);
		EventLogPrinter.printServerLogs(this.servers);
		this.printMessages = false;

	}

	/**
	 * Prints a list of received messages to screen
	 */
	public void printReceivedMessages() {

		// if there are some received messages print them
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
	 * Main method -- called when class is executed at command line. No command line
	 * arguments are parsed as all actions can be implemented using the menu that
	 * has been configured
	 */
	public static void main(String[] args) {

		// create a new chat server instance and start an interactive command line
		// instance
		ChatServer chatServer = new ChatServer();
		chatServer.startInteractiveSession();

	}

}
