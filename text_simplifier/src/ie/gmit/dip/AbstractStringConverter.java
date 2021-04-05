package ie.gmit.dip;

/**
 * AbstractStringConverter is an abstract base class which implements the
 * {@link AbstractConverter} interface. It is configured to take a
 * {@link String} as input, and return a {@link String} as output. The specifics
 * of the convert method are implemented by specialized concrete base types
 * which inherit from this abstract class.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @see AbstractConverter
 *
 * @param String the input type, and the return type from conversion are both {@link String} types.
 *
 */
public abstract class AbstractStringConverter implements AbstractConverter<String, String> {

	/**
	 * The convert method takes a {@link String} as input and returns a converted
	 * {@link String} as output.
	 * 
	 * @param string is the string to be converted.
	 * @return String is the converted string.
	 */
	public abstract String convert(String string);

}
