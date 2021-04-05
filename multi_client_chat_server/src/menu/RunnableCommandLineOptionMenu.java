package menu;

import java.util.*;
import terminalIO.*;

/**
 * RunnableCommandLineOptionMenu is a concrete class which implements the
 * abstract {@link Menuable} interface. This class has a single responsibility
 * for the definition of state and behavior associated with building a list of
 * menu options, providing said list of options to a user to choose from, and
 * then running/executing the desired option.
 * 
 * A runnable menu is composed of, by aggregation, a Collection (a
 * {@link SortedSet}, specifically, a {@link TreeSet}) of
 * {@link RunnableCommandLineOption} types and it is therefore coupled yet
 * cohesive with the {@link Optionable} and {@link RunnableCommandLineOption}
 * interfaces/classes.
 * 
 * This class is also fully composed with a {@link TerminalInputParser} type,
 * and is therefore coupled with this type, although it relies only on a single
 * method therein.
 * 
 * The RunnableCommandLineOptionMenu also has a dependency on a
 * {@link TerminalTitleWriter} type, although this coupling is looser than with
 * the {@link TerminalInputParser}. Dependencies on the
 * {@link TerminalInputParser} and {@link TerminalTitleWriter} could readily be
 * removed without influencing external behavior of the
 * RunnableCommandLineOptionMenu, although since such classes exist,
 * responsibility is delegated to them where appropriate.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * 
 * @param RunnableCommandLineOption the {@link Menuable} interface is
 *                                  implemented with
 *                                  {@link RunnableCommandLineOption} types
 * @see Menuable
 * @see Optionable
 * @see RunnableCommandLineOption
 * @see Runnable
 * @see TerminalInputParser
 * @see TerminalTitleWriter
 */
public class RunnableCommandLineOptionMenu implements Menuable<RunnableCommandLineOption> {

	/**
	 * Variable options is a sorted set of {@link RunnableCommandLineOption} types.
	 * 
	 * @see RunnableCommandLineOption
	 */
	private SortedSet<RunnableCommandLineOption> options;
	/**
	 * optionToRun is an integer used to control which menu option will be run.
	 */
	private int optionToRun = -1;
	/**
	 * inputParser is a {@link TerminalInputParser} type, which is used to parse the
	 * user-supplied option to run from the command line.
	 */
	private TerminalInputParser inputParser;

	/**
	 * Constructor method which instantiates a new {@link TreeSet} of
	 * {@link RunnableCommandLineOption} types and which instantiates a new
	 * {@link TerminalInputParser}.
	 * 
	 * Has O(c) running time locally.
	 */
	public RunnableCommandLineOptionMenu() {
		super();
		this.options = new TreeSet<RunnableCommandLineOption>();
		inputParser = new TerminalInputParser();
	}

	/**
	 * Getter method which returns the current state of the optionToRun variable
	 * 
	 * Has O(c) running time.
	 * 
	 * @return optionToRun an integer used to control which menu option will be run.
	 */
	public int getOptionToRun() {
		return optionToRun;
	}

	/**
	 * Setter method which sets the state of the optionToRun variable.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param i an integer used to set the state of the optionToRun variable.
	 */
	public void setOptionToRun(int i) {
		this.optionToRun = i;
	}

	/**
	 * Setter method which assigns a Collection of {@link RunnableCommandLineOption}
	 * types to the options variable, with the generic Collection (expected of a
	 * {@link Menuable}) in the method signature down-cast to a {@link TreeSet} in
	 * the current class.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param options is a Collection of {@link RunnableCommandLineOption}
	 */
	@Override
	public void setOptions(Collection<RunnableCommandLineOption> options) {
		this.options = (TreeSet<RunnableCommandLineOption>) options;
	}

	/**
	 * Getter method which returns the the Collection of options as a
	 * {@link TreeSet}.
	 * 
	 * Effectively has O(c) running time.
	 * 
	 * @return options is a Collection of {@link RunnableCommandLineOption}
	 */
	@Override
	public Collection<RunnableCommandLineOption> getOptions() {
		return new TreeSet<RunnableCommandLineOption>(this.options);
	}

	/**
	 * Method allows the user to add a single instance of a
	 * {@link RunnableCommandLineOption} type to an existing {@link SortedSet} of
	 * {@link RunnableCommandLineOption} types.
	 * 
	 * Procedures are built in to ensure that an option cannot have a value less
	 * than 0.
	 * 
	 * Has O(log(n)) running time.
	 * 
	 * @param option is a single instance of a {@link RunnableCommandLineOption}.
	 */
	@Override
	public void addOption(RunnableCommandLineOption option) {

		if (option.getNumber() >= 0) {
			this.options.add(option);
		} else {
			System.out.println("\t-[warning] cannot assign an option with number less than 0");
		}
	}

	/**
	 * Method allows the user to add multiple instances of
	 * {@link RunnableCommandLineOption} types to an existing {@link SortedSet} of
	 * {@link RunnableCommandLineOption} types.
	 * 
	 * Has O(log(n)) running time.
	 * 
	 * @param options a Collection of {@link RunnableCommandLineOption}.
	 */
	@Override
	public void addOptions(Collection<RunnableCommandLineOption> options) {
		for (RunnableCommandLineOption option : options) {
			this.addOption(option);
		}

	}

	/**
	 * The showOptions method is a pre-requisite of a {@link Menuable} type, and in
	 * this case the overridden method simply prints the options to the terminal and
	 * requests that a user select an option via delegation to the composed
	 * {@link TerminalInputParser} type.
	 * 
	 * This method has O(n) running time where n is the number of options.
	 * 
	 */
	@Override
	public void showOptions() {

		TerminalTitleWriter.writeTitle("", "-", 75, "Please select an option");
		for (RunnableCommandLineOption option : options) {
			System.out.println(option.getNumber() + ") " + option.getOptionText());
		}

	}

	/**
	 * Method which allows the user to choose an option to run from the command
	 * line. Logic is built in to ensure numbers are greater than 0.
	 * 
	 * If there is an issue getting a number from the command line, an int of -2 is
	 * assigned. This is a weak fail safe attempt, intended to combat the user
	 * performing a ctrl+d operation at the command line in linux.
	 * 
	 * This method has O(n) running time where n is the number of options to be
	 * printed in the showOptions() method.
	 */
	public void setOptionToRunFromCommandLine() {

		this.showOptions();
		Integer i = this.inputParser.getIntegerInput();
		if (i != null && i >= 0) {
			this.setOptionToRun(i.intValue());
		} else if (i != null && i < 0) {
			System.out.println("\t-[warning] please provide an integer >= 0 as input.");
		} else {
			this.setOptionToRun(-2);
		}
	}

	/**
	 * The runSetOption method is used to run the {@link RunnableCommandLineOption}
	 * which was chosen from the {@link SortedSet} of
	 * {@link RunnableCommandLineOption} types.
	 * 
	 * This method has O(n) running time where n is the number of options to be
	 * tested for equality.
	 * 
	 * The option to run is reset to -1, which is a disallowed option in the
	 * addOption method.
	 */
	public void runSetOption() {
		for (RunnableCommandLineOption option : options) {
			if (option.getNumber() == this.optionToRun) {
				TerminalTitleWriter.writeLine("", "-", 75);
				TerminalTitleWriter.writeLine("", "running: " + option.getNumber() + ") " + option.getOptionText(), 1);
				TerminalTitleWriter.writeLine("", "[help/description]\n" + option.getHelpText(), 1);
				TerminalTitleWriter.writeLine("", "-", 75);
				option.run();
				this.setOptionToRun(-1);
				break;
			}
		}
	}

}
