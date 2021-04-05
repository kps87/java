package ie.gmit.dip;

import java.util.*;

/**
 * Concrete class whose primary responsibility is to perform various
 * terminal/console parsing operations. Contains methods which allow for
 * retrieving single and multi-line {@link String} input, retrieving
 * {@link Integer} input, a method for choosing a {@link String} item from an
 * {@link ArrayList}, a method to pause the terminal until the user gives a
 * prompt to continue, and a method to close the composed scanner object. Relies
 * on a class level {@link Scanner} instance for terminal parsing.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 *
 */
public class TerminalInputParser {

	/**
	 * Terminal parser has a scanner for parsing input.
	 */
	private Scanner scanner = new Scanner(System.in);

	/**
	 * Constructor method to instantiate a new TerminalInputParser().
	 * 
	 * Has O(c) running time.
	 * 
	 */
	public TerminalInputParser() {
		super();
	}

	/**
	 * Method which allows the use to provide multiple line (return character
	 * separated) fields at the command line, which are compiled into a single
	 * {@link String}.
	 * 
	 * The method has O(c) running time.
	 * 
	 * @return String the {@link String} parsed from the terminal input.
	 */
	public String getSingleLineTextInput() {

		TerminalTitleWriter.writeTitle("", "-", 75, "-Enter text and press enter:");
		String text = this.scanner.nextLine().trim();
		TerminalTitleWriter.writeLine("", "-", 75);
		return text;

	}

	/**
	 * Method which allows the use to provide multiple line (return character
	 * separated) fields at the command line, which are compiled into a single
	 * {@link String}.
	 * 
	 * Ignoring the TerminalTitleWriter.writeLine() calls, this method has O(n)
	 * running time where n is the number of lines entered at the command line.
	 * 
	 * @return String the {@link String} built from the multiline terminal input.
	 * @throws Exception
	 */
	public String getMultiLineInput() {

		TerminalTitleWriter.writeTitle("", "-", 75,
				"-Provide command line input (type 'END INPUT' to terminate input):");
		StringBuilder sb = new StringBuilder();
		String str;
		while (this.scanner.hasNextLine() && !((str = this.scanner.nextLine()).equals("END INPUT"))) {
			str = str.trim();
			sb.append(str).append("\n");
		}
		sb.append("\n");

		TerminalTitleWriter.writeLine("", "-", 75);
		return sb.toString();

	}

	/**
	 * Method for parsing a single integer from the command line.
	 * 
	 * Has O(c) running time.
	 * 
	 * @return Integer the {@link int} read from the terminal input.
	 */
	public Integer getIntegerInput() {
		TerminalTitleWriter.writeTitle("", "-", 75, "-Provide a single integer option as input:");
		Integer i = null;
		try {
			i = Integer.parseInt(this.scanner.next().trim());
			return i;
		} catch (Exception exception) {
			System.out.println("\t-[warning] please provide an integer less than [" + Integer.MAX_VALUE + "] as input");
		}
		return i;
	}

	/**
	 * This method allows the user to choose an option from an {@link ArrayList} of
	 * {@link String} types.
	 * 
	 * This method has O(n) running time where n is the number of elements in the
	 * {@link ArrayList} provided as input.
	 * 
	 * @param strings an {@link ArrayList} containing a collection of {@link String}
	 *                types to print to the screen.
	 * @return String the {@link String} which the user has selected.
	 */

	public String chooseStringFromList(ArrayList<String> strings) {

		int i = 0;
		for (String string : strings) {
			i++;
			System.out.println(i + ") " + string);
		}
		String choice;
		try {
			choice = strings.get(this.getIntegerInput() - 1);
			return choice;
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}

		return null;
	}

	/**
	 * A simple method which pauses the screen until the user hits return.
	 * 
	 * This method has O(c) running time.
	 */
	public void pause() {
		TerminalTitleWriter.writeLine("", "-Press enter to return to proceed", 1);
		this.scanner.nextLine();
	}

	/**
	 * Method for closing the composed scanner.
	 * 
	 * This method has O(c) running time.
	 */
	public void closeScanner() {
		this.scanner.close();
	}

}
