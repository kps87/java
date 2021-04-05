package server;

import java.io.*;
import java.net.*;
import java.util.*;

import event.*;
import stream.*;

/**
 * When a Client connects to a MultiClientServer, the Client is assigned its own
 * MultiClientServerThread to facilitate ongoing communication. This class
 * contains a range of getter/setter methods for retrieving/updating lists of
 * connected/disconnected MultiClientServerThread objects. The
 * startMultiClientThreadMonitor method is threaded and it processes the
 * EventLogs of all MultiClientServerThread objects in order to monitor their
 * connection status and to facilitate client-server and client-to-client
 * communication.
 * 
 * @author ksomers
 *
 */
public class MultiClientServer extends Thread {

	// variables list
	private int port;
	private String status;
	private EventLog eventLog = new EventLog();
	private volatile ArrayList<MultiClientServerThread> multiServerThreads = new ArrayList<MultiClientServerThread>();
	private ArrayList<MultiClientServerThread> activeThreads = new ArrayList<MultiClientServerThread>();
	private ArrayList<MultiClientServerThread> inactiveThreads = new ArrayList<MultiClientServerThread>();
	private Thread clientThreadMonitor;

	/**
	 * Constructor, is initialized with a default port
	 */
	public MultiClientServer() {
		super();
		this.port = this.getDefaultPort();
	}

	/**
	 * Simple method to get a default port value
	 */
	private int getDefaultPort() {
		return 16000;
	}

	/**
	 * Simple method to get the current server port
	 */
	public int getPort() {
		return this.port;
	}

	/**
	 * Simple method to set the current server port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Simple method to get the current event of the server
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Simple method to get a list of all the threads which have been spawned from
	 * this server
	 */
	public synchronized ArrayList<MultiClientServerThread> getMultiServerThreads() {
		return this.multiServerThreads;
	}

	/**
	 * Simple method to set a list of the threads which have been spawned from this
	 * server
	 */
	public synchronized void setMultiClientServerThreads(ArrayList<MultiClientServerThread> threads) {
		this.multiServerThreads = threads;
	}

	/**
	 * Simple method to get a list of active threads running on this server. Only
	 * active threads are distributed to clients for client-to-client communication
	 */
	public synchronized ArrayList<MultiClientServerThread> getActiveThreads() {
		return this.activeThreads;
	}

	/**
	 * Simple method to set a list of active threads running on this server. Only
	 * active threads are distributed to clients for client-to-client communication
	 */
	public synchronized void setActiveThreads(ArrayList<MultiClientServerThread> threads) {
		this.activeThreads = threads;
		for (MultiClientServerThread thread : this.activeThreads) {
			thread.getEventLog().addEvent(new Event("status", "moved to active thread list", true));
		}
	}

	/**
	 * Simple method to get a list of inactive threads no longer listening on this
	 * server.
	 */
	public synchronized ArrayList<MultiClientServerThread> getInactiveThreads() {
		return this.inactiveThreads;
	}

	/**
	 * Simple method to set a list of inactive threads no longer listening on this server
	 */
	public synchronized void setInactiveThreads(ArrayList<MultiClientServerThread> threads) {
		this.inactiveThreads = threads;
		for (MultiClientServerThread thread : this.inactiveThreads) {
			thread.getEventLog().addEvent(new Event("status", "moved to inactive thread list", true));
		}
	}

	/**
	 * Returns the number of active threads
	 */
	public int getTotalNumberOfThreads() {
		return this.getMultiServerThreads().size();
	}

	/**
	 * Returns the number of active threads
	 */
	public int getNumberOfActiveThreads() {
		return this.getActiveThreads().size();
	}

	/**
	 * Returns the number of inactive threads
	 */
	public int getNumberOfInactiveThreads() {
		return this.getInactiveThreads().size();
	}

	/**
	 * Simple method which returns the event log
	 */
	public EventLog getEventLog() {
		return this.eventLog;
	}

	/**
	 * Method which starts the server
	 */
	public void run() {

		// boolean used to control the while loop
		boolean listening = true;

		// update the status log
		this.eventLog.addEvent(new Event("status", "accepting connections on port = " + this.port, true));

		// start the thread monitor
		this.startThreadMonitor(this);

		try (ServerSocket serverSocket = new ServerSocket(this.port)) {
			while (listening) {

				// set the status
				this.status = "available for clients";

				// await a connection
				Socket socket = serverSocket.accept();

				// create a new MultiServerThread if connection accepted and
				// add it to a list that can be retrieved
				MultiClientServerThread mst = new MultiClientServerThread(socket);
				this.multiServerThreads.add(mst);

				// start the MultiServerThread
				mst.start();

				this.eventLog.addEvent(new Event("connection", "accepted client on port = " + this.port
						+ " -> [socket info: host|client] -> " + SocketHandler.getSocketInfo(socket), true));

			}
		} catch (IOException e) {
			this.eventLog.addEvent(new Event("exception", "IOException: " + e.getMessage(), true));
		}

	}

	/**
	 * Monitors the status of each thread and keeps two lists of threads -- the
	 * active and inactive ones, active threads are forwarded for client to client
	 * communication inactive ones are kept so their logs can be viewed
	 */
	private void startThreadMonitor(MultiClientServer mcs) {

		// creates a new thread
		this.clientThreadMonitor = new Thread() {

			// run method
			public void run() {

				// add an event to the event log
				mcs.getEventLog().addEvent(new Event("status", "started the client thread monitor", true));

				while (true) {

					// partition threads into active and inactive
					if (mcs.getMultiServerThreads().size() > 0) {
						mcs.updateThreadLists();
					}

					// sleep momentarily so thread is not always checking for updates
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						mcs.getEventLog().addEvent((new Event("interrupted exception",
								"InterruptedException in client event monitor -> not fatal will start again upon user-requested connection"
										+ e.getMessage(),
								true)));
					}

				}
			}
		};

		// start the event monitor
		this.clientThreadMonitor.start();
	}

	/**
	 * Monitors which treads are active and inactive and sorts lists accordingly.
	 * The ChatServer will then be able to forward lists of known addresses to
	 * clients any time an update has occurred.
	 */
	private void updateThreadLists() {

		// initialize lists of inactive and active threads
		ArrayList<MultiClientServerThread> inactive = new ArrayList<MultiClientServerThread>();
		ArrayList<MultiClientServerThread> active = new ArrayList<MultiClientServerThread>();

		// get the active and inactive threads
		for (MultiClientServerThread clientThread : this.getMultiServerThreads()) {

			if (clientThread.getSocket() == null) {
				inactive.add(clientThread);
			} else {
				active.add(clientThread);
			}
		}

		// now update the instance level variables
		boolean threadsWereUpdated = false;
		if (!active.equals(this.getActiveThreads())) {
			this.setActiveThreads(active);
			threadsWereUpdated = true;
		}
		if (!inactive.equals(this.getInactiveThreads())) {
			this.setInactiveThreads(inactive);
			threadsWereUpdated = true;
		}

		// if the threads were updated, add an event to the eventLog which can
		// be parsed by the ChatServer which will forward updates lists of clients to
		// all clients
		if (threadsWereUpdated) {
			this.eventLog.addEvent(new Event("status", "updated active or inactive client threads list", false));
		}

	}

}
