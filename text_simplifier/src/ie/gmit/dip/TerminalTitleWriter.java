package ie.gmit.dip;

/**
 * This is a simple static utility class for writing titles and borders to a
 * terminal window in a controllable and consistent manner. Provides
 * functionality for controlling the level of print indentation (via tabs,
 * whitespace), the {@link String} to be printed, and the number of times a
 * {@link String} should be printed.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 *
 */
public class TerminalTitleWriter {

	/**
	 * Simple method which allows the user to provide a level of print indentation
	 * via the indent variable, along with a {@link String} defined by the border
	 * variable which is printed n times as defined by the int n. This effectively
	 * allows the user to print borders/section titles to the screen. The
	 * {@link String} title contains the informative text.
	 * 
	 * This method has O(n) running time where n is the number of border symbols to
	 * print.
	 * 
	 * @param indent the print indentation to use e.g. x number of tabs, y number of
	 *               spaces.
	 * @param border the symbol to use for printing the title border e.g. an equals
	 *               sign (=), or dash (-).
	 * @param n      the number of times to print the border symbol.
	 * @param title  the text to print to screen.
	 */
	public static void writeTitle(String indent, String border, int n, String title) {
		writeLine(indent, border, n);
		writeLine("", title, 1);
		writeLine(indent, border, n);
	}

	/**
	 * Simple method which allows the user to provide a level of print indentation
	 * via the indent variable, along with a {@link String} defined by the string
	 * variable which is printed int n times.
	 * 
	 * This method has O(n) running time where n is the number of border symbols to
	 * print.
	 * 
	 * @param indent the print indentation to use e.g. x number of tabs, y number of
	 *               spaces.
	 * @param string a {@link String} to be printed n times.
	 * @param n      the number of times to print the {@link String}.
	 */
	public static void writeLine(String indent, String string, int n) {
		System.out.print(indent);
		for (int i = 0; i < n; i++) {
			System.out.print(string);
		}
		System.out.println("");
	}

}
