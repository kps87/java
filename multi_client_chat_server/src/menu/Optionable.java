package menu;

/**
 * Optionable is an abstract interface which declares the functional behavior
 * expected of an option which a user may wish to select or run. Behavior is
 * largely that associated with a Bean class, with the only requirements being
 * methods to define the option number, the option text, and any help text
 * associated with the option.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @see Runnable
 */
public interface Optionable {

	/**
	 * Setter method which defines the state of the option number.
	 * 
	 * @param number an {@link int}.
	 */
	public void setNumber(int number);

	/**
	 * Getter method which returns the option number.
	 * 
	 * @return int the {@link int} type assigned to this option.
	 */
	public int getNumber();

	/**
	 * Setter method which defines the text for an option.
	 * 
	 * @param text the text for this option of type {@link String}.
	 */
	public void setOptionText(String text);

	/**
	 * Getter method which returns the text for an option.
	 * 
	 * @return text the text for this option of type {@link String}.
	 */
	public String getOptionText();

	/**
	 * Setter method which defines the help text for an option.
	 * 
	 * @param help the help text for this option of type {@link String}.
	 */
	public void setHelpText(String help);

	/**
	 * Getter method which defines the help text for an option.
	 * 
	 * @return String the help text for this option of type {@link String}.
	 */
	public String getHelpText();

}
