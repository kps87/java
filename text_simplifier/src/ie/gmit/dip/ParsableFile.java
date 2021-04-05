package ie.gmit.dip;

import java.io.File;

/**
 * The class ParsableFile is a concrete Bean class that extends the java.io.File
 * base class by implementing the {@link AbstractParsable} interface.
 * 
 * The primary responsibility of this class is to extend the standard
 * java.io.File class such that information embedded in a file can be externally
 * associated with it so that it can be retrieved and operated on.
 * 
 * A ParsableFile is a specialized java.io.File type, and therefore inherits all
 * of the java.io.File default state and behavior, but it is augmented by
 * implementing the methods required of an {@link AbstractParsable} object, that
 * is, methods for retrieving and setting the parsed data associated with a
 * {@link File}.
 * 
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @see AbstractParsable
 * @see java.io.File
 * 
 * @param String the {@link AbstractParsable} interface is implemented with the
 *               parsedData stored as a simple {@link String} type.
 * 
 */
public class ParsableFile extends File implements AbstractParsable<String> {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * private instance variable used to store any parsed data as a a string.
	 */
	private String parsedData;

	/**
	 * Constructor which takes the filename as input, which is delegated to the
	 * java.io.File constructor.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param name the name of the input file as a {@link String}.
	 */
	public ParsableFile(String name) {
		super(name);
	}

	/**
	 * Method used to set the name of the ParsableFile as required by the
	 * {@link AbstractParsable} interface. Responsibility is delegated to the
	 * java.io.file.renameTo() method so that the name of the ParsableFile is bound
	 * to the java.io.File API
	 * 
	 * Effectively has O(c) running time locally, but is dependent on running time
	 * of File.renameTo().
	 * 
	 * @see java.io.File
	 * @param name the name of the file as a {@link String}.
	 */
	@Override
	public void setName(String name) {
		this.renameTo(new File(name));
	}

	/**
	 * Method used to get the name of the ParsableFile as required by the
	 * {@link AbstractParsable} interface. Responsibility is delegated to the
	 * java.io.file.getAbsolutePath().toString() method so that the name of the
	 * ParsableFile is bound to the java.io.File API.
	 * 
	 * Has O(c) running time.
	 * 
	 * @see AbstractParsable
	 * @see java.io.File
	 * @return String the absolute path name of the file as a {@link String}.
	 */
	@Override
	public String getName() {
		return this.getAbsolutePath().toString();
	}

	/**
	 * Method used to set the parsedData variable, as required by the {@link
	 * AbstractParsable} interface.
	 * 
	 * Has O(c) running time.
	 * 
	 * @see AbstractParsable
	 * @param parsedData a {@link String} consisting of the parsed data.
	 */
	@Override
	public void setParsedData(String parsedData) {
		this.parsedData = parsedData;
	}

	/**
	 * Method used to retrieve any parsed data, as required by the
	 * {@link AbstractParsable} interface.
	 * 
	 * Has O(c) running time.
	 * 
	 * @see AbstractParsable
	 * @return parsedData a {@link String} consisting of the parsed data.
	 */
	@Override
	public String getParsedData() {
		return this.parsedData;
	}

}
