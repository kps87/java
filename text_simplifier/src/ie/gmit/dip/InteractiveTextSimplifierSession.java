package ie.gmit.dip;

/**
 * InteractiveTextSimplifierSession is a simple utility class that allows the
 * user to interact with and control the functionality embedded in the
 * {@link ThesaurusMapTextSimplifier} via an in-built menu
 * ({@link RunnableCommandLineOptionMenu}) of {@link RunnableCommandLineOption}
 * options.
 * 
 * Each {@link RunnableCommandLineOption} option is defined as a
 * {@link Runnable} defined via a lambda expression which calls on a
 * pre-existing method in the {@link ThesaurusMapTextSimplifier} class. These
 * {@link Runnable} types are used to compose a
 * {@link RunnableCommandLineOptionMenu} which the user interacts with directly
 * from the terminal.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @see ThesaurusMapTextSimplifier
 * @see RunnableCommandLineOption
 * @see RunnableCommandLineOptionMenu
 */
public class InteractiveTextSimplifierSession {

	/**
	 * An class level ThesaurusMapTextSimplifier.
	 */
	private ThesaurusMapTextSimplifier textSimplifier;
	/**
	 * A fully composed RunnableCommandLineOptionMenu.
	 */
	private static final RunnableCommandLineOptionMenu menu = new RunnableCommandLineOptionMenu();

	/**
	 * A simple constructor to instantiate the session.
	 */
	public InteractiveTextSimplifierSession() {
	}

	/**
	 * A simple method to print a title to the terminal.
	 * 
	 * Has O(c) running time.
	 */
	public void printTitle() {

		System.out.println("***************************************************************************");
		System.out.println("*           GMIT - Dept. Computer Science & Applied Physics               *");
		System.out.println("*                       Text Simplifier V1.0                              *");
		System.out.println("*                 (AKA Orwellian Language Compliance)                     *");
		System.out.println("*                                                                         *");
		System.out.println("*                     Author: Kieran P. Somers                            *");
		System.out.println("*                       ID: g00221349@gmit.ie                             *");
		System.out.println("***************************************************************************");

	}

	/**
	 * A method to start the session.
	 * 
	 * Prints the title to screen, builds the
	 * ({@link RunnableCommandLineOptionMenu}) menu of
	 * {@link RunnableCommandLineOption} options and then keeps the menu open so
	 * that the user can interact with the {@linkThesaurusMapTextSimplifier} in
	 * various ways.
	 * 
	 * Running time is dependent on command line options being called by user.
	 */
	public void start() {

		this.printTitle();
		boolean keepRunning = true;
		textSimplifier = new ThesaurusMapTextSimplifier();
		this.buildMenuOptions();
		while (keepRunning) {
			System.out.println(textSimplifier.getDefaultColors().get("Default text font"));
			menu.setOptionToRunFromCommandLine();
			menu.runSetOption();

			// protect from ctrl-d operations in linux, and ctrl-z in windows
			// by stopping if menu reverts to default option of -2.
			if (menu.getOptionToRun() == -2) {
				keepRunning = false;
			}
		}
	}

	/**
	 * A method which builds the list of {@link RunnableCommandLineOption} menu
	 * options which can be run from the current session.
	 * 
	 * Each option is defined as a {@link Runnable} defined via a lambda expression
	 * which calls on a pre-existing method in the
	 * {@link ThesaurusMapTextSimplifier} class.
	 * 
	 * These {@link Runnable} types are used to compose a
	 * {@link RunnableCommandLineOptionMenu} which the user interacts with directly
	 * from the terminal.
	 * 
	 * Effectively has O(c) running time.
	 * 
	 */
	public void buildMenuOptions() {

		// add an exit option
		Runnable r0 = () -> {
			System.exit(0);
		};
		RunnableCommandLineOption o0 = new RunnableCommandLineOption(r0, 0, "Exit program", "Safely exit the program");
		menu.addOption(o0);

		// add a global text simplification method
		// which asks for command line input, runs the conversion, and prints the
		// results to screen.
		Runnable r1 = () -> {
			textSimplifier.getTextRunConverterAndPrintToScreen();
		};
		RunnableCommandLineOption o1 = new RunnableCommandLineOption(r1, 1,
				"Enter text, run converter and print old and new text",
				"This option effectively runs all steps of text simplication in series.\n"
						+ "The exception is construction of the thesaurus which is only carried out\n"
						+ "when the thesaurus input files are updated (see options)");
		menu.addOption(o1);

		// add the thesaurus builder method
		// should one want to re-build the thesaurus map.
		Runnable r2 = () -> {
			textSimplifier.buildThesaurusMap();
		};
		RunnableCommandLineOption o2 = new RunnableCommandLineOption(r2, 2, "Re-build the thesaurus",
				"This option builds the thesaurus map.\n"
						+ "This option is useful if you wish to change the thesuarus input files");
		menu.addOption(o2);

		// add an option for the user just to enter
		// text at the command line
		Runnable r3 = () -> {
			textSimplifier.getTextFromCommandLine();
		};
		RunnableCommandLineOption o3 = new RunnableCommandLineOption(r3, 3, "Enter new text for simplification",
				"This option allows the user to enter text for conversion (without actually running the conversion)");
		menu.addOption(o3);

		// add an option for the user to view the text to convert
		Runnable r4 = () -> {
			textSimplifier.printTextToConvertToScreen();
		};
		RunnableCommandLineOption o4 = new RunnableCommandLineOption(r4, 4, "Print user input text to screen",
				"This option allows user to inspect the text they entered");
		menu.addOption(o4);

		// add an option for the user to view the converted text.
		Runnable r5 = () -> {
			textSimplifier.printConvertedTextToScreen();
		};
		RunnableCommandLineOption o5 = new RunnableCommandLineOption(r5, 5, "Print converted text to screen",
				"This option allows user to inspect the converted text");
		menu.addOption(o5);

		// add an option to run the converter and print the output to screen.
		Runnable r6 = () -> {
			textSimplifier.runConverterAndPrintToScreen();
		};
		RunnableCommandLineOption o6 = new RunnableCommandLineOption(r6, 6, "Run the converter",
				"This option allows user to run the convert on text that has already been entered");
		menu.addOption(o6);

		// add an option so the user can update the thesaurus file based on command line
		// input
		Runnable r7 = () -> {
			textSimplifier.setThesaurusFileFromCommandLine();
		};
		RunnableCommandLineOption o7 = new RunnableCommandLineOption(r7, 7, "Provide path to new thesaurus file",
				"This option allows user to change one of the key inputs of this software. Thesuarus maps are automatically rebuilt when specified");
		menu.addOption(o7);

		// add an option so the user can update the google words file based on command
		// line input
		Runnable r8 = () -> {
			textSimplifier.setPriorityWordSetFileFromCommandLine();
		};
		RunnableCommandLineOption o8 = new RunnableCommandLineOption(r8, 8,
				"Provide path to new file containing most popular words",
				"This option allows user to change one of the key inputs of this software. Thesuarus maps are automatically rebuilt when specified");
		menu.addOption(o8);

		// add an option so the user can update the colors at the command line
		Runnable r9 = () -> {
			textSimplifier.updateColorFromCommandLine();
		};
		RunnableCommandLineOption o9 = new RunnableCommandLineOption(r9, 9,
				"Change default color of printed words (this includes menu font)",
				"Allows the user to specify some colors for printing to the screen");
		menu.addOption(o9);

		// add an option so the the user can query words/phrases in the thesaurus map
		// from the command line
		Runnable r10 = () -> {
			textSimplifier.queryThesaurusMap();
		};
		RunnableCommandLineOption o10 = new RunnableCommandLineOption(r10, 10,
				"Check if a word or phrase is in the thesaurus (case insensitive matching)",
				"Allows the user to query the thesaurus directly, conversion operations are also run");
		menu.addOption(o10);

	}

}
