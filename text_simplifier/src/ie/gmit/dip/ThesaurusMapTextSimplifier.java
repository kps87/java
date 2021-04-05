package ie.gmit.dip;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

/**
 * ThesaurusMapTextSimplifier is a concrete and highly specialized class which
 * allows a user to perform a range of operations related to simplifying text,
 * and controlling its color, based on command line input.
 * 
 * This is not a generic or highly extensible type, hence it is designated as
 * final such that it cannot form the basis of a subtype in a hierarchy - it is
 * highly specific to performing the specific task requested for this project -
 * mapping a word to its synonym and printing that word in a specific color to a
 * terminal.
 * 
 * It is composed of the following types, largely in the form of full
 * composition unless otherwise stated:
 * 
 * Two {@link ArrayList} types consisting of {@link String} types are used to
 * store the text to convert and the converted text. The user has complete
 * control over the text being simplified which can be set from the command
 * line, or, by a setter method should one wish to avoid the command line
 * entirely/run this API in an automated fashion. These {@link ArrayList} types
 * are not externally modifiable and are fully encapsulated within this class.
 * 
 * A single, fully encapsulated/composed, {@link OneToOneMappedThesaurusBuilder}
 * is used to construct and access a {@link OneToOneMappedThesaurus} from
 * files including a thesaurus file, and a list of top words (as identified by
 * Google in this case).
 * 
 * In principle it is not straight forward to reconfigure this simplifier with a
 * Thesaurable type that does not perform a one to one mapping of words - the
 * logic required for one-to-many and many-to-many thesaurus mappings is quite
 * different, and potentially complicated in terms of time and space complexity.
 * However, it is entirely possible to substitute both (a) the thesaurusFile (so
 * long as it is arranged as line and comma-separated values) and (b) the list
 * of top words to which the thesaurus should be mapped, and the user has
 * control over these latter two components from the command line.
 * 
 * A single, fully encapsulated/composed {@link TerminalInputParser} is used to
 * get user input from the command line. These colors are stored and managed via
 * a {@link HashMap} of {@link String} types entitled defaultColors which is
 * used to assign ANSI color codes to different words depending on their
 * presence or absence in the thesaurus or list of google top words.
 * 
 * A fully encapsulated {@link ConfigurableAnsiColorCodeMap} is used to set the
 * colors/fonts for printing, and the user has control over the color scheme
 * being used from the command line.
 * 
 * Aside from the above composed types, one of central features of this text
 * simplifier is its ability to convert {@link String} types. This class
 * delegates responsibility for {@link String} conversion to the
 * {@link ConvertibleString} and {@link AbstractConverter} classes, and
 * accompanying concrete types for the latter ({@link StringCaseConverter},
 * {@link StringColourConverter} and {@link StringSynonymConverter}). This
 * ThesaurusMapTextSimplifier is therefore strongly coupled to these packages
 * via dependencies in its own convertString() method.
 * 
 * Aside from a set of core methods associated with constructing a
 * {@link OneToOneMappedThesaurus}, getting command line input, converting
 * strings to their synonym and an appropriate color, there are a set of utility
 * methods which allow the user to run various operations in predefined orders,
 * and these form the basis of an interactive menu implemented in the
 * {@link InteractiveTextSimplifierSession} class. The
 * ThesaurusMapTextSimplifier is therefore coupled to and designed to be
 * cohesive with the {@link InteractiveTextSimplifierSession} class which allows
 * the user to interact with the ThesaurusMapTextSimplifier without
 * understanding the details of the code implementation.
 * 
 * As well as mapping text to a synonym found in the thesaurus, this simplifier
 * also preserves the original case of the text being simplified, as well as
 * punctuation. On the preservation of case, it is impossible to completely
 * retain the original case of a word that was mapped to a word of different
 * length, however, title case is specifically preserved.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @see AbstractConverter
 * @see OneToOneMappedThesaurus
 * @see OneToOneMappedThesaurusBuilder
 * @see TerminalInputParser
 * @see ConfigurableAnsiColorCodeMap
 * @see InteractiveTextSimplifierSession
 * @see ConvertibleString
 * @see AbstractConverter
 * @param ArrayList<String> the {@link AbstractTextSimplifier} has been
 *                          implemented with two {@link ArrayList} of
 *                          {@link String} types used to define the
 *                          textToSimplify and simplifiedText.
 */
public final class ThesaurusMapTextSimplifier implements AbstractTextSimplifier<ArrayList<String>, ArrayList<String>> {

	/**
	 * An {@link ArrayList} of the text which will be simplified/converted.
	 */
	private ArrayList<String> textToSimplify;
	/**
	 * An {@link ArrayList} of the simplified/converted text generated from
	 * textToSimplify.
	 */
	private ArrayList<String> simplifiedText;
	/**
	 * An instance of a {@link OneToOneMappedThesaurusBuilder} used to simplify
	 * text.
	 */
	private OneToOneMappedThesaurusBuilder thesaurusMapBuilder;
	/**
	 * An instance of a {@link TerminalInputParser} used to get user input for the
	 * text simplification process.
	 */
	private TerminalInputParser inputParser;
	/**
	 * An instance of a {@link ConfigurableAnsiColorCodeMap} used to control colors
	 * to use when printing to the console.
	 */
	private ConfigurableAnsiColorCodeMap colorMap;
	/**
	 * A {@link HashMap} of default colors to use when printing to the console,
	 * works in tandem with the {@link ConfigurableAnsiColorCodeMap}.
	 */
	private HashMap<String, String> defaultColors;

	/**
	 * Constructor method for a {@link ThesaurusMapTextSimplifier}. Initializes a
	 * new set of composed objects/default variables for use during the
	 * simplification process.
	 * 
	 * Running time is effectively given by the buildThesaurusMap() method which has
	 * complex time dependency.
	 * 
	 * @see OneToOneMappedThesaurusBuilder
	 */
	public ThesaurusMapTextSimplifier() {
		super();

		// initialize the composed objects
		this.textToSimplify = new ArrayList<String>();
		this.inputParser = new TerminalInputParser();
		this.thesaurusMapBuilder = new OneToOneMappedThesaurusBuilder();
		this.simplifiedText = new ArrayList<String>();
		this.defaultColors = new HashMap<String, String>();
		this.colorMap = new ConfigurableAnsiColorCodeMap();

		// initialize the basic important options required to perform
		// text simplification operation
		try {
			this.initializeDefaultColors();
			this.buildThesaurusMap();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Setter method which sets the textToSimplify {@link ArrayList}.
	 * 
	 * Has O(n) running time due to deep copying of the input {@link ArrayList}.
	 * 
	 * @param textToSimplify an {@link ArrayList} of the text which will be
	 *                       simplified/converted.
	 */
	@Override
	public void setTextToSimplify(ArrayList<String> textToSimplify) {
		this.textToSimplify.clear();
		ArrayList<String> copy = new ArrayList<String>();
		for (String string : textToSimplify) {
			copy.add(new String(string));
		}
		this.textToSimplify = new ArrayList<String>(copy);
	}

	/**
	 * Getter method which returns an {@link ArrayList} of the textToSimplify
	 * 
	 * Has O(n) running time due to deep copying of the {@link ArrayList}.
	 * 
	 * @return textToSimplify a copy of the {@link ArrayList} of the text which will
	 *         be simplified/converted.
	 */
	@Override
	public ArrayList<String> getTextToSimplify() {
		ArrayList<String> copy = new ArrayList<String>();
		for (String string : this.textToSimplify) {
			copy.add(new String(string));
		}
		return copy;
	}

	/**
	 * Getter method which returns an ArrayList of the simplified text
	 * 
	 * Has O(n) running time due to deep copying of the {@link ArrayList}.
	 * 
	 * @return simplifiedText a copy of the {@link ArrayList} of the simplified text
	 */
	@Override
	public ArrayList<String> getSimplifiedText() {
		ArrayList<String> copy = new ArrayList<String>();
		for (String string : this.simplifiedText) {
			copy.add(new String(string));
		}
		return copy;
	}

	/**
	 * Getter method which returns a {@link HashMap} of the default colors which
	 * will be used when printing to the screen
	 * 
	 * Has O(n) running time due to deep copying of the {@link HashMap}.
	 * 
	 * @return defaultColors a copy of the {@link HashMap} of String-Color pairs
	 */
	public HashMap<String, String> getDefaultColors() {
		HashMap<String, String> copy = new HashMap<String, String>();
		for (String key : this.defaultColors.keySet()) {
			copy.put(new String(key), new String(this.defaultColors.get(key)));
		}
		return copy;
	}

	/**
	 * Getter method which returns a HashMap of the default colors which will be
	 * used when printing to the screen.
	 * 
	 * Each put operation has O(c) running time, and running time for method is
	 * effectively constant.
	 * 
	 */
	private void initializeDefaultColors() {
		this.defaultColors.put("Default text font", this.colorMap.getAnsiCode("white_bright"));
		this.defaultColors.put("Text in google 1000 and in thesaurus", this.colorMap.getAnsiCode("blue_bold_bright"));
		this.defaultColors.put("Text in google 1000 but not in thesaurus",
				this.colorMap.getAnsiCode("green_bold_bright"));
		this.defaultColors.put("Text in thesaurus that has been mapped to a google 1000 word",
				this.colorMap.getAnsiCode("purple_bold_bright"));
		this.defaultColors.put("Text in thesaurus that has been mapped to itself",
				this.colorMap.getAnsiCode("yellow_bold_bright"));
	}

	/**
	 * Method which instantiates/builds the {@link OneToOneMappedThesaurus}, with
	 * responsibility delegated to the {@link OneToOneMappedThesaurusBuilder}.
	 */
	public void buildThesaurusMap() {
		try {
			thesaurusMapBuilder.build();
		} catch (Exception exception) {
			System.out.println(
					"\t-[error] building the thesaurus map.\n\t-Ensure that the input files are configured correctly.");
		}
	}

	/**
	 * The convert method is an overridden method which is prerequisite of a type
	 * which implements the {@link AbstractTextSimplifier} interface. This method is
	 * called in order to convert the textToSimplify to the simplifiedText.
	 * 
	 * Responsibility is delegated to the convertString() method, which takes as
	 * input the {@link OneToOneMappedThesaurus}, the priorityWordSet (e.g. google
	 * 1000 words), and the string to be converged.
	 * 
	 * The general running time is O(n) where n is the number of {@link String}
	 * types to convert. The actual running time is further dependent on the
	 * convertString() method, which has more complex behavior.
	 * 
	 * @see convertString
	 */
	@Override
	public void simplify() {

		TerminalTitleWriter.writeTitle("", "-", 75, "Running text simplification and font conversion");
		if (!this.textToSimplify.isEmpty()) {
			this.simplifiedText.clear();
			for (String string : this.textToSimplify) {
				this.simplifiedText.add(this.convertString(string));
			}
		} else {
			System.out.println("\t-please provide some text to simplify.");
		}

	}

	/**
	 * This method is used to configure a {@link ConvertibleString} type with a
	 * series of appropriate StringConverter types which extend the
	 * {@link AbstractStringConverter} abstract base class. The primary aim of this
	 * method is to correctly configure the case, color, and the synonym that has
	 * been mapped, for a given word.
	 * 
	 * The behavior of this method is highly specific to the current application.
	 * Depending on the {@link String} being converted, a
	 * {@link StringSynonymConverter}, {@link StringCaseConverter}, and/or
	 * {@link StringColourConverter} can and will be configured, before the
	 * {@link String} is ultimately simplified/converted.
	 * 
	 * In terms of running times and Big-O notation, most of the important
	 * operations are O(1) or O(c) as all Collection based operations, including
	 * hasWord, add, getSynonym, contains, are constant time O(1) operations.
	 * 
	 * The exception is the use of the String.equals() method which is O(n) and is
	 * sometimes employed to verify if a word has been mapped to itself. The worst
	 * case running time of the main block of if-else statements is therefore O(n).
	 * 
	 * The cstring.convert() method invocation has an initial running time of O(n)
	 * where n is the number of {@link AbstractStringConverter} operations to carry
	 * out. Of these {@link AbstractStringConverter} types, the worst case
	 * performance is O(n) for the {@link StringCaseConverter}, which has O(n)
	 * running time for conversion of a string to a title case character.
	 * 
	 * The worst case running time for this method can be approximated as the sum of
	 * two separate (serial, not tested) O(n) operations where n is the number of
	 * characters in the {@link String} being converted - one O(n) operation for the
	 * String.equals method (if it is invoked), and the other for the
	 * StringCaseConverter.convert() method (if it is invoked).
	 * 
	 * @param string is the {@link String} variable to be converted.
	 * @see ConvertibleString
	 * @see AbstractStringConverter
	 * @see StringSynonymConverter
	 * @see StringCaseConverter
	 * @see StringColourConverter
	 * @return String the converted {@link String}.
	 */
	public String convertString(String string) {

		ConvertibleString cstring = new ConvertibleString(string);
		String lcstring = new String(string.toLowerCase());
		ArrayList<AbstractStringConverter> converters = new ArrayList<AbstractStringConverter>();
		OneToOneMappedThesaurus thesaurusMap = this.thesaurusMapBuilder.getMappedThesaurus();
		HashSet<String> priorityWordSet = thesaurusMapBuilder.getPriorityWordSet();

		// get the synonym if it is in the thesaurus map
		if (thesaurusMap.hasWord(lcstring)) {
			converters.add(new StringSynonymConverter(lcstring, thesaurusMap.getSynonym(lcstring)));
		}

		// if the word is in the thesaurus map or the priority word list
		// carry out one of the following operations which sets the color
		if (thesaurusMap.hasWord(lcstring) || priorityWordSet.contains(lcstring)) {

			// case conversions
			if (Character.isUpperCase(string.charAt(0))) {
				converters.add(new StringCaseConverter("title"));
			}

			// set colors for a word that is in the google 1000 and the thesaurus
			if (priorityWordSet.contains(lcstring) && thesaurusMap.hasWord(lcstring)) {
				converters.add(new StringColourConverter(this.defaultColors.get("Text in google 1000 and in thesaurus"),
						this.defaultColors.get("Default text font")));
			}
			// set colors for a word that is in the google 1000 but not in the thesaurus
			else if (priorityWordSet.contains(lcstring) && !thesaurusMap.hasWord(lcstring)) {
				converters.add(
						new StringColourConverter(this.defaultColors.get("Text in google 1000 but not in thesaurus"),
								this.defaultColors.get("Default text font")));
			}
			// set color for word converted to google 1000 word
			else if (thesaurusMap.hasWord(lcstring) && !thesaurusMap.getSynonym(lcstring).equals(lcstring)) {
				converters.add(new StringColourConverter(
						this.defaultColors.get("Text in thesaurus that has been mapped to a google 1000 word"),
						this.defaultColors.get("Default text font")));
			}
			// set color for word converted to google 1000 word
			else if (thesaurusMap.hasWord(lcstring) && thesaurusMap.getSynonym(lcstring).equals(lcstring)) {
				converters.add(new StringColourConverter(
						this.defaultColors.get("Text in thesaurus that has been mapped to itself"),
						this.defaultColors.get("Default text font")));
			}
		}
		// set color for word mapped to itself as it is not in any dictionary
		else {
			converters.add(new StringColourConverter(this.defaultColors.get("Default text font"),
					this.defaultColors.get("Default text font")));
		}

		// run the conversions and return a copy of the converted word
		cstring.setConverters(converters);
		cstring.convert();
		String converted = new String(cstring.getConverted());
		return converted;

	}

	/**
	 * Setter method which allows the user to set the thesaurus input file
	 * externally. The buildThesaurusMap() method is invoked if this file is
	 * changed.
	 * 
	 * Running time is dependent on the buildThesaurusMap() method, which has
	 * further called to the buildMappedThesaurus() of the
	 * {@link OneToOneMappedThesaurusBuilder} class.
	 * 
	 * @param fileName the name of the thesaurusFile to use in the
	 *                 {@link OneToOneMappedThesaurusBuilder}.
	 * @see OneToOneMappedThesaurusBuilder
	 */
	public void setThesaurusFile(String fileName) {
		this.thesaurusMapBuilder.setThesaurusFile(new File(fileName));
		this.buildThesaurusMap();
	}

	/**
	 * Setter method which allows the user to set the priorityWordSet input file
	 * externally. The buildThesaurusMap() method is invoked if this file is
	 * changed.
	 * 
	 * Running time is dependent on the buildThesaurusMap() method, which has
	 * further called to the buildMappedThesaurus() of the
	 * {@link OneToOneMappedThesaurusBuilder} class.
	 * 
	 * @param fileName the name of the priorityWordSetFile to use in the
	 *                 {@link OneToOneMappedThesaurusBuilder}.
	 */
	public void setPriorityWordSetFile(String fileName) {
		this.thesaurusMapBuilder.setPriorityWordSetFile(new File(fileName));
		this.buildThesaurusMap();
	}

	/**
	 * Setter method which allows the user to set the thesaurus input file
	 * externally by provision of information at the command line. If the file is
	 * valid, the this.setThesaurusFile() method is invoked, which also invokes a
	 * re-build of the thesaurus map.
	 * 
	 * Running time is dependent on the buildThesaurusMap() method, which has
	 * further called to the buildMappedThesaurus() of the
	 * {@link OneToOneMappedThesaurusBuilder} class.
	 * 
	 * @see OneToOneMappedThesaurusBuilder
	 */
	public void setThesaurusFileFromCommandLine() {

		try {
			String fileName = inputParser.getSingleLineTextInput();
			File file = new File(fileName);
			if (!file.canRead() || !file.exists()) {
				System.out.println("\t-[error] updating thesuaurus file: [" + fileName + "] is not a readable file");
				System.out.println(
						"\t-please put file in the following path: " + Paths.get("").toAbsolutePath().toString());
				System.out.println("\t-or include full path to file in input");
			} else {
				this.setThesaurusFile(fileName);
			}

		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			exception.printStackTrace();
		}

	}

	/**
	 * Setter method which allows the user to set the thesaurus input file
	 * externally by provision of information at the command line. If the file is
	 * valid, the this.setThesaurusFile() method is invoked, which also invokes a
	 * re-build of the thesaurus map.
	 * 
	 * Running time is dependent on the buildThesaurusMap() method, which has
	 * further called to the buildMappedThesaurus() of the
	 * {@link OneToOneMappedThesaurusBuilder} class.
	 * 
	 * @see OneToOneMappedThesaurusBuilder
	 */
	public void setPriorityWordSetFileFromCommandLine() {
		try {
			String fileName = inputParser.getSingleLineTextInput();
			File file = new File(fileName);
			if (!file.canRead() || !file.exists()) {
				System.out.println(
						"\t-[error] updating list of top words from file: [" + fileName + "] is not a readable file");
				System.out.println(
						"\t-please put file in the following path: " + Paths.get("").toAbsolutePath().toString());
				System.out.println("\t-or include full path to file in input");
			} else {
				this.setPriorityWordSetFile(fileName);
			}

		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Utility method which allows the user to provide text at the command line
	 * which can then be simplified/converted.
	 * 
	 * Running time is dependent on the parse() method of the
	 * {@link IterativeStringSplitter} instance, which has complex time dependency.
	 * 
	 * @see IterativeStringSplitter
	 */
	public void getTextFromCommandLine() {
		try {
			String text = this.inputParser.getMultiLineInput();
			IterativeStringSplitter iss = new IterativeStringSplitter();
			iss.setInput(text);
			this.textToSimplify = iss.parse();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Utility method which allows gets text from the command line, runs the
	 * converter, and prints the input text and converted output text to screen.
	 * 
	 * Has complex running time, which is dependent on other methods.
	 * 
	 */
	public void getTextRunConverterAndPrintToScreen() {
		this.getTextFromCommandLine();
		this.runConverterAndPrintToScreen();
	}

	/**
	 * Utility method which runs the converter, and prints the input text and
	 * converted output text to screen.
	 * 
	 * Has complex running time, which is dependent on other methods.
	 */
	public void runConverterAndPrintToScreen() {
		this.simplify();
		this.printTextToConvertToScreen();
		this.printConvertedTextToScreen();
	}

	/**
	 * Utility method which prints the input text to screen
	 * 
	 * Running time is O(n) where n is the number of {@link String} types in the
	 * textToSimplify {@link ArrayList}.
	 */
	public void printTextToConvertToScreen() {

		if (!this.textToSimplify.isEmpty()) {
			TerminalTitleWriter.writeTitle("", "-", 75, "User input text");
			for (String string : this.textToSimplify) {
				System.out.print(string);
			}
			System.out.println("");
			TerminalTitleWriter.writeLine("", "-", 75);
		} else {
			System.out.println("\t-please provide some text to simplify");
		}
	}

	/**
	 * Utility method which prints a description of, and the color of, each type of
	 * word to the screen. This legend is used so that the user can interpret the
	 * results of {@link String} conversion.
	 * 
	 * Each {@link HashMap} get call has O(c) running time, and so does this method
	 * by extension.
	 * 
	 */
	public void printDefaultColorsToScreen() {

		System.out.println(this.defaultColors.get("Default text font")
				+ "[Text in this color [ARE NOT IN THE GOOGLE 1000 LIST OR THE THESAURUS] and are therefore [UNCHANGED]]");

		System.out.println(this.defaultColors.get("Text in google 1000 and in thesaurus")
				+ "[Text in this color [ARE IN THE GOOGLE 1000 LIST AND THE THESAURUS], and are therefore [UNCHANGED]]");

		System.out.println(this.defaultColors.get("Text in google 1000 but not in thesaurus")
				+ "[Text in this color [ARE IN THE GOOGLE 1000 LIST BUT NOT IN THE THESAURUS], and are therefore [UNCHANGED]]");

		System.out.println(this.defaultColors.get("Text in thesaurus that has been mapped to a google 1000 word")
				+ "[Text in this color [WERE IN THE THESAURUS] but have now been [MAPPED TO A GOOGLE 1000 WORD]");

		System.out.println(this.defaultColors.get("Text in thesaurus that has been mapped to itself")
				+ "[Text in this color [ARE IN THE THESAURUS BUT CANNOT BE MAPPED TO A GOOGLE 1000 WORD], and are therefore [UNCHANGED]]");

		System.out.println(this.defaultColors.get("Default text font"));

	}

	/**
	 * Utility method which prints the converted text to screen.
	 * 
	 * Has O(n) running time where n is the number of {@link String} types in the
	 * simplifiedText {@link ArrayList}.
	 */
	public void printConvertedTextToScreen() {

		TerminalTitleWriter.writeTitle("", "-", 75, "Converted text");
		this.printDefaultColorsToScreen();
		if (!this.textToSimplify.isEmpty()) {
			for (String string : this.simplifiedText) {
				System.out.print(string);
			}
			System.out.println("");
		} else {
			System.out.println("\t-please provide some text to simplify");
		}

		TerminalTitleWriter.writeLine("", "-", 75);
	}

	/**
	 * Utility method which allows the user to choose new colors for each type of
	 * text from a list of colors in the colorMap.
	 * 
	 * Running time is O(n) where n is the number of colors in the {@link ArrayList}
	 * of defaultColors and colorMap.
	 * 
	 */
	public void updateColorFromCommandLine() {

		// choose the default font you would like to change
		TerminalTitleWriter.writeTitle("", "-", 75, "What font type would you like to change?");
		String colorToChange = inputParser.chooseStringFromList(new ArrayList<String>(this.defaultColors.keySet()));

		TerminalTitleWriter.writeTitle("", "-", 75, "What color would you like to change it to?");
		String newColor = inputParser.chooseStringFromList(this.colorMap.getColors());

		if (this.defaultColors.containsKey(colorToChange) && this.colorMap.containsColor(newColor)) {
			System.out.println("\t-Changing font color for [" + colorToChange + "] to [" + newColor + "]");
			this.defaultColors.put(colorToChange, this.colorMap.getAnsiCode(newColor));
		} else {
			System.out.println("\t-[warning] Will not change font color [" + colorToChange + "] to [" + newColor + "]");
			if (!this.defaultColors.containsKey(colorToChange)) {
				System.out.println("\t\t-[warning] not a valid color->" + colorToChange);
			} else if (this.colorMap.containsColor(newColor)) {
				System.out.println("\t\t-[warning] not a valid color->" + newColor);
			}
		}

	}

	/**
	 * Utility method which allows the user to verify if a word or phrase is in the
	 * thesaurusMap {@link OneToOneMappedThesaurus}, and to then visualise its
	 * color, and the word to which it is mapped.
	 * 
	 * Running time is dependent on the convertString method as described elsewhere.
	 */
	public void queryThesaurusMap() {

		try {
			String testText = inputParser.getSingleLineTextInput();
			String converted = this.convertString(testText);
			this.printDefaultColorsToScreen();
			System.out.println("[" + testText + "] is mapped to [" + converted + "]");
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			exception.printStackTrace();
		}

	}

}
