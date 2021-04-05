package ie.gmit.dip;

/**
 * StringSynonymConverter is a concrete class which extends the abstract base
 * class {@link AbstractStringConverter}. Its primary responsibility is to
 * associate a word with its synonym. It is effectively a Bean class with little
 * behavior beyond getting and setting the string variable and its corresponding
 * synonym variable. All operations run in O(c) time.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @see AbstractStringConverter
 *
 */
public class StringSynonymConverter extends AbstractStringConverter {

	/**
	 * {@link String} variable which defines the value of the string.
	 */
	private String string;
	/**
	 * {@link String} variable which defines the value of the synonym.
	 */
	private String synonym;

	/**
	 * Simple constructor method.
	 * 
	 * Has O(c) running time.
	 * 
	 */
	public StringSynonymConverter() {
		super();
	}

	/**
	 * Overloaded constructor method which allows the user to define a string and
	 * its synonym.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param string  the {@link String} value of the variable which has a synonym.
	 * @param synonym the {@link String} value of the synonym.
	 */
	public StringSynonymConverter(String string, String synonym) {
		super();
		this.string = string;
		this.synonym = synonym;
	}

	/**
	 * Getter method for the string variable.
	 * 
	 * Has O(c) running time.
	 * 
	 * @return String the {@link String} value of variable which has a synonym.
	 */
	public String getString() {
		return this.string;
	}

	/**
	 * Setter method for the string variable.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param string the {@link String} value of the variable which has a synonym.
	 */
	public void setString(String string) {
		this.string = string;
	}

	/**
	 * Setter method for the synonym variable.
	 * 
	 * Has O(c) running time.
	 * 
	 * @return synonym the {@link String} value of the synonym.
	 */
	public String getSynonym() {
		return this.synonym;
	}

	/**
	 * Setter method for the synonym variable.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param synonym the {@link String} value of the synonym.
	 */
	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

	/**
	 * The convert method which delegates conversion of a string to its synonym via
	 * the getSynonym method.
	 * 
	 * Has O(c) running time.
	 * 
	 * @see AbstractStringConverter
	 * @return String the value of the synonym.
	 */
	@Override
	public String convert(String string) {
		return getSynonym();
	}

}
