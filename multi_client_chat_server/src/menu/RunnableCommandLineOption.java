package menu;

/**
 * RunnableCommandLineOption is a concrete class that implements the
 * {@link Optionable}, Comparable and Runnable interfaces. This class has a
 * single responsibility for the definition of state and behavior associated
 * with the aggregated Runnable type.
 * 
 * It also has responsibility for the ancillary information associated with the
 * option, including the option number, the option text and help text associated
 * with the option.
 * 
 * Whilst the RunnableCommandLineOption is a Runnable by definition, and
 * contains the requisite run method, in effect the RunnableCommandLineOption is
 * executed by delegation to the run method of the Runnable of which it is
 * composed.
 * 
 * RunnableCommandLine option implements the Comparable interface such that a
 * Collection of RunnableCommandLineOption types can be sorted if required - in
 * this case, the compareTo method compares RunnableCommandLineOption types
 * based on the integer number variable, with the comparison delegated to the
 * default Integer.compareTo method. The equality and comparability of
 * RunnableCommandLine types is therefore dependent on the integer number value
 * assigned to them.
 * 
 * In terms of coupling and cohesion, this class is therefore coupled to the
 * Runnable and Comparable functional interfaces, and is coupled but highly
 * cohesive with the user defined {@link Optionable} interface which it also
 * implements. It is designed to be highly cohesive with the {@link Menuable}
 * interface and {@link RunnableCommandLineOptionMenu} concrete type.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @see Optionable
 * @see Comparable
 * @see Runnable
 * @see Menuable
 * @see RunnableCommandLineOptionMenu
 */
public class RunnableCommandLineOption implements Optionable, Comparable<Object>, Runnable {

	/**
	 * The Runnable type which is ultimately executed.
	 */
	private Runnable runnable;
	/**
	 * Number variable used to compare RunnableCommandLineOption types and which can
	 * be used to assign a number in a menu.
	 */
	private int number;
	/**
	 * The text associated with the option - i.e. what does does option do?
	 */
	private String optionText;
	/**
	 * Help text associated with the option - i.e. more detailed information on what
	 * the option does.
	 */
	private String help;

	/**
	 * Simple constructor method
	 */
	public RunnableCommandLineOption() {

	}

	/**
	 * Overloaded constructor method which allows for all state to be defined.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param runnable   the Runnable type which is ultimately executed.
	 * @param number     {@link int} variable used to compare
	 *                   RunnableCommandLineOption types and which can be used to
	 *                   assign a number in a menu.
	 * @param optionText the text associated with the option defined as a
	 *                   {@link String} - i.e. what does does option do?
	 * @param help       text associated with the option defined as a {@link String}
	 *                   - i.e. more detailed information on what the option does.
	 */
	public RunnableCommandLineOption(Runnable runnable, int number, String optionText, String help) {
		super();
		this.runnable = runnable;
		this.number = number;
		this.optionText = optionText;
		this.help = help;
	}

	/**
	 * Getter method which returns the Runnable.
	 * 
	 * Has O(c) running time.
	 * 
	 * @return Runnable the Runnable type which is intended to be executed.
	 */
	public Runnable getRunnable() {
		return runnable;
	}

	/**
	 * Setter method to define the state of the Runnable.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param runnable the Runnable type which is intended to be executed.
	 */
	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}

	/**
	 * Getter method which returns the option number.
	 * 
	 * Has O(c) running time.
	 * 
	 * @return int an {@link int} variable used to compare RunnableCommandLineOption
	 *         types and which can be used to assign a number in a menu.
	 */
	@Override
	public int getNumber() {
		return this.number;
	}

	/**
	 * Setter method which defines the state of the the option number.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param number an {@link int} variable used to compare
	 *               RunnableCommandLineOption types and which can be used to assign
	 *               a number in a menu.
	 */
	@Override
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Getter method which returns the text for this option.
	 * 
	 * Has O(c) running time.
	 * 
	 * @return optionText the text associated with the option defined as a
	 *         {@link String} - i.e. what does does option do?
	 */
	@Override
	public String getOptionText() {
		return this.optionText;
	}

	/**
	 * Setter method which defines the text for this option.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param text the text associated with the option defined as a {@link String} -
	 *             i.e. what does does option do?
	 */
	@Override
	public void setOptionText(String text) {
		this.optionText = text;
	}

	/**
	 * Getter method which returns the text for this option.
	 * 
	 * Has O(c) running time.
	 * 
	 * @return help the help text associated with the option defined as a
	 *         {@link String} - i.e. more detailed information on what the option
	 *         does.
	 */
	@Override
	public String getHelpText() {
		return help;
	}

	/**
	 * Setter method which defines the text for this option.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param help the help text associated with the option defined as a
	 *             {@link String} - i.e. more detailed information on what the
	 *             option does.
	 */
	@Override
	public void setHelpText(String help) {
		this.help = help;
	}

	/**
	 * Overridden compareTo method required of a Comparable type.
	 * RunnableCommandLineOption types are compared based on the value of their
	 * Integer number variable.
	 * 
	 * Locally can assign O(c) running time with true running time that of the
	 * Java.lang.Integer.compareTo() and Java.lang.Integer.compare() methods which
	 * are called.
	 * 
	 * @return int an {@link int} which defines the ranking of the
	 *         RunnableCommandLineOption instances.
	 */
	@Override
	public int compareTo(Object other) {

		Integer int1 = ((Integer) this.number);
		Integer int2 = ((Integer) ((RunnableCommandLineOption) other).getNumber());

		return int1.compareTo(int2);
	}

	/**
	 * The run method is a prerequisite of any type which implements the Runnable
	 * interface.
	 * 
	 * In this case running the option is delegated to the actual Runnable of which
	 * a RunnableCommandLineOption is composed.
	 * 
	 * Running time depends on the specifics of the Runnable being called.
	 * 
	 */
	@Override
	public void run() {
		this.runnable.run();
	}

}
