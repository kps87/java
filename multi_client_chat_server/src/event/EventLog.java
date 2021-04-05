package event;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * an EventLog is simply a list of Events. Many of the classes described above
 * rely on having their EventLog monitored in order to facilitate interactive
 * user sessions. Since EventLogs are passed between classes and may be
 * concurrently monitored/altered by different threads, the they are typically
 * accessed from synchronized methods throughout the application.
 * 
 * @author ksomers
 *
 */
public class EventLog {

	// instance variables
	private CopyOnWriteArrayList<Event> log = new CopyOnWriteArrayList<Event>();

	/**
	 * Synchronized adder method to add a new event to the log
	 */
	public synchronized void addEvent(Event event) {
		this.log.add(event);
	}

	/**
	 * Synchronized getter method that returns the log
	 */
	public synchronized CopyOnWriteArrayList<Event> getEvents() {
		return log;
	}

	/**
	 * Synchronized method that returns a hashmap of events where the key is the
	 * event type and the values are an arraylist of strings with the time and text
	 * associated with the event
	 */
	public synchronized HashMap<String, ArrayList<String>> getEventsByType() {

		HashMap<String, ArrayList<String>> eventsByType = new HashMap<String, ArrayList<String>>();
		for (Event e : this.getEvents()) {

			// get the event data
			String type = e.getType();
			String time = e.getTime();
			String text = e.getText();
			boolean serviced = e.wasServiced();

			// if key exists get old array list and append
			if (eventsByType.containsKey(type)) {
				if (serviced) {
					eventsByType.get(type).add("[" + time + "] [" + text + "]");
				} else {
					eventsByType.get(type)
							.add("[" + time + "] [" + text + "] [service warning, event may not have been handled]");
				}
			}
			// else build new array list
			else {
				ArrayList<String> events = new ArrayList<String>();
				events.add("[" + time + "] [" + text + "] [serviced] = " + serviced);
				eventsByType.put(type, events);
			}
		}
		return eventsByType;

	}

	/**
	 * Synchronized method that returns an ArrayList of strings containing the event
	 * time, type and text fields in the order they were added to the event log.
	 */
	public synchronized ArrayList<String> getOrderedEvents() {

		ArrayList<String> events = new ArrayList<String>();
		for (Event e : this.getEvents()) {

			// get the event data
			String type = e.getType();
			String time = e.getTime();
			String text = e.getText();
			boolean serviced = e.wasServiced();

			if (serviced) {
				events.add("[" + time + "] [" + type + "] [" + text + "]");
			} else {
				events.add("[" + time + "] [" + type + "] [" + text
						+ "[service warning, event may not have been handled]");
			}

		}
		return events;

	}
}
