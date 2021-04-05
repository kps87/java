package ie.gmit.dip;

/**
 * 
 * The interface AbstractParsable is a universal and minimal abstraction of a
 * generic "Parsable" type, that is, a type that can be parsed, such as a file,
 * a stream, or a command line input.
 * 
 * Each Parsable should be assigned a name such that Parsable types can be
 * stored, indexed, and queried using an assigned String. Methods, in the form
 * of getters and setters, should exist for assigning and retrieving parsed data
 * associated with the Parsable type.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @param <T> a generic type assigned to the parsed data.
 */
public abstract interface AbstractParsable<T> {

	/**
	 * Gets the name of the Parsable type.
	 * 
	 * @return String the name of the Parsable type.
	 */
	public String getName();

	/**
	 * Sets the name of the Parsable object.
	 * 
	 * @param name the name to be assigned to the parsable.
	 */
	public void setName(String name);

	/**
	 * Get the parsed data associated with this object.
	 * 
	 * @return T the return type should be defined by concrete classes that
	 *         implement this interface.
	 */
	public T getParsedData();

	/**
	 * Set the parsed data associated with this object.
	 * 
	 * @param parsedData data of generic type T which has been parsed.
	 */
	public void setParsedData(T parsedData);

}
