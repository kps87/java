package event;

import java.util.*;
import server.*;
import terminalIO.*;

/**
 * contains basic code to print the Events in EventLogs stored in client and
 * server classes to the screen in a readable format – it is simple logic but
 * the methods are used in both the client and server side applications.
 * 
 * @author ksomers
 *
 */
public class EventLogPrinter {

	/**
	 * Simple method which prints lists of events according to the event type
	 */
	public static synchronized void printLogsByType(EventLog log) {

		HashMap<String, ArrayList<String>> eventsByType = log.getEventsByType();
		for (String type : eventsByType.keySet()) {
			System.out.println("[" + type + " event logs]");
			for (String s : eventsByType.get(type)) {
				System.out.println("\t-" + s);
			}
			System.out.println();
		}

	}

	/**
	 * Simple method which prints lists of events in the order they were added to
	 * the event log
	 */
	public static synchronized void printLogsByTime(EventLog log) {

		ArrayList<String> events = log.getOrderedEvents();

		for (String s : events) {
			System.out.println("\t-" + s);
		}
		System.out.println();

	}

	/**
	 * Method which takes a list of MultiClientServer objects and prints all their
	 * logs. Delegates to the printAllServerLogs method.
	 */
	public static void printServerLogs(ArrayList<MultiClientServer> servers) {

		// if there are servers then print a log
		if (servers.size() > 0) {
			EventLogPrinter.printAllServerLogs(servers);
		} else {
			System.out.println("\t-There are no active server logs.");
		}
		System.out.println();
	}

	/**
	 * Method which takes a list of MultiClientServer objects and prints detailed
	 * logs for each server
	 */
	public static void printAllServerLogs(ArrayList<MultiClientServer> servers) {

		// create a counter variable
		int serverIndex = 0;

		// for each server in the list of servers print a event update
		for (MultiClientServer server : servers) {

			// increment the counter
			serverIndex += 1;

			// print general overview
			TerminalTitleWriter.writeTitle("", "-", 75, "Server # = " + serverIndex);
			System.out.println("-[port] = " + server.getPort());
			System.out.println("-[status] = " + server.getStatus());
			System.out.println("-[# active/open clients] = " + server.getNumberOfActiveThreads());
			System.out.println("-[# inactive/closed clients] = " + server.getNumberOfInactiveThreads());
			System.out.println();

			// print logs according to the order they were added to the event log
			EventLogPrinter.printLogsByTime(server.getEventLog());

			// print the logs for each thead in this server
			EventLogPrinter.printServerThreadLogs(server);

		}

	}

	/**
	 * Prints the logs foreach each thread for a single server
	 */
	public static void printServerThreadLogs(MultiClientServer server) {

		// if a thread has been started (it can be active or inactive)
		if (server.getMultiServerThreads().size() > 0) {

			// print a title
			TerminalTitleWriter.writeLine("", "-", 50);
			System.out.println("[MultiServerClientThread event logs]");
			TerminalTitleWriter.writeLine("", "-", 50);
			System.out.println();

			// start a counter
			int threadIndex = 0;

			// foreach thread print a summary
			for (MultiClientServerThread thread : server.getMultiServerThreads()) {

				// increment the counter
				threadIndex += 1;

				// get the thread status -- is the connection open or closed?
				String status;
				if (thread.getSocket() != null) {
					status = "connected";
				} else {
					status = "disconnected";
				}

				// print a title
				TerminalTitleWriter.writeLine("", "-", 35);
				System.out.println("[MultiServerClientThread # = " + threadIndex + "] [status = " + status + "]");
				TerminalTitleWriter.writeLine("", "-", 35);
				System.out.println();
				System.out.println(thread.socketToString());
				System.out.println();
				System.out.println();

				// print the logs in the order they were added to the event log
				EventLogPrinter.printLogsByTime(thread.getEventLog());

			}
		}
	}

}
