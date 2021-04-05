package event;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * events are used as the basis of maintaining connection status and relaying
 * messages within and between clients and servers. Each Event has type, time,
 * text and serviced fields. The type varies depending on the type of event that
 * occurred and is used to distinguish between general status update events, and
 * more specific connection, incoming message, and outgoing message events. The
 * time field dictates when an event occurred, and the text field is some
 * messages associated with the event (a user message, or an internally
 * generated one). The serviced field is boolean – if serviced is true, the
 * threaded method which monitor event logs throughout the application do not
 * need to act or have already acted on the event and set serviced to true. If
 * serviced is false it means that a monitor/thread somewhere in the application
 * must service the event or carry out some action which facilitates
 * client-to-client or client-server communication.
 * 
 * @author ksomers
 *
 */
public class Event {

	// variables list
	private String type;
	private String time;
	private boolean serviced;
	private String text;

	/**
	 * Constructor
	 */
	public Event() {
	}

	/**
	 * Constructor where the event type, text and serviced status are explicitly
	 * provided
	 * 
	 */
	public Event(String type, String text, boolean serviced) {
		super();
		this.type = type;
		this.time = Event.getDateTime();
		this.text = text;
		this.serviced = serviced;
	}

	/**
	 * Simple getter method that returns the event type
	 */
	public synchronized String getType() {
		return type;
	}

	/**
	 * Simple setter method to set the event type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Simple getter method that returns the event time
	 */
	public synchronized String getTime() {
		return time;
	}

	/**
	 * Simple method which gets the current time in a suitable format
	 */
	public static String getDateTime() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
	}

	/**
	 * Simple getter method that returns the message text
	 */
	public synchronized String getText() {
		return text;
	}

	/**
	 * Simple setter method that sets the message text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Simple method to assess whether the event was acted upon/serviced
	 */
	public synchronized boolean wasServiced() {
		return serviced;
	}

	/**
	 * Simple method that changes the event service status to true
	 * 
	 * @return
	 */
	public synchronized void changeServiceStatus() {
		this.serviced = true;
	}

}
