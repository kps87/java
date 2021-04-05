package ie.gmit.dip;

/**
 * StringColourConverter is a concrete class which extends the abstract base
 * class {@link AbstractStringConverter}. Its primary responsibility is to
 * modify the color of a {@link String} when printing it to a terminal window.
 * It does so by prepending and appending the appropriate ANSI codes, which are
 * provided/set as {@link String} types.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @see AbstractStringConverter
 */
public class StringColourConverter extends AbstractStringConverter {

	/**
	 * {@link String} variable which defines the ANSI format string which sets the
	 * color at the beginning of the string to be converted,
	 */
	private String startColor;
	/**
	 * {@link String} variable which defines the ANSI format string which sets the
	 * color at the end of the string to be converted.
	 */
	private String endColor;

	/**
	 * Simple constructor method.
	 * 
	 * Has O(c) running time.
	 */
	public StringColourConverter() {
		super();
	}

	/**
	 * Overloaded constructor method which allow the startColor and endColor to be
	 * defined.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param startColor defines the ANSI format {@link String} which sets the color
	 *                   at the beginning of the {@link String} to be converted.
	 * @param endColor   defines the ANSI format {@link String} which sets the color
	 *                   at the end of the {@link String} to be converted.
	 */
	public StringColourConverter(String startColor, String endColor) {
		super();
		this.startColor = startColor;
		this.endColor = endColor;
	}

	/**
	 * Getter method for the startColor variable.
	 * 
	 * Has O(c) running time.
	 * 
	 * @return startColor defines the ANSI format string which sets the color at the
	 *         beginning of the {@link String} to be converted.
	 */
	public String getStartColor() {
		return this.startColor;
	}

	/**
	 * Setter method for the startColor variable.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param startColor defines the ANSI format {@link String} which sets the color
	 *                   at the beginning of the {@link String} to be converted.
	 */
	public void setStartColor(String startColor) {
		this.startColor = startColor;
	}

	/**
	 * Getter method for the endColor variable.
	 * 
	 * Has O(c) running time.
	 * 
	 * @return endColor defines the ANSI format {@link String} which sets the color
	 *         at the end of the {@link String} to be converted.
	 */
	public String getEndColor() {
		return this.endColor;
	}

	/**
	 * Setter method for the endColor variable.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param endColor defines the ANSI format {@link String} which sets the color
	 *                 at the end of the {@link String} to be converted
	 */
	public void setEndColor(String endColor) {
		this.endColor = endColor;
	}

	/**
	 * Overridden convert method which modifies the color at the beginning and end
	 * of the {@link String}.
	 * 
	 * Has O(c) running time.
	 * 
	 * @see AbstractStringConverter
	 * @return String the converted {@link String} which is modified with a starting
	 *         and ending color.
	 */
	@Override
	public String convert(String string) {
		return this.startColor + string + this.endColor;
	}

}
