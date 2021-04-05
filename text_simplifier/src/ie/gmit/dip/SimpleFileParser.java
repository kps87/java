package ie.gmit.dip;

import java.io.*;

/**
 * The class SimpleFileParser is an concrete type which extends the abstract
 * base type {@link AbstractFileParser} by fully implementing a parse
 * functionality. The key functionality is that it allows one to parse any
 * {@link ParsableFile} to a string, with appropriate exception handling
 * implemented.
 * 
 * This class, and the cohesive {@link ParsableFile} class are potentially of
 * use as generic parsers in a wide variety of projects and APIs as a hierarchy
 * of specialized parsers.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @see ParsableFile
 * @see AbstractParser
 * @see AbstractFileParser
 */
public class SimpleFileParser extends AbstractFileParser<String> {

	/**
	 * Constructor method with no fields.
	 */
	public SimpleFileParser() {
		super();
	}

	/**
	 * Overloaded constructor method which takes a {@link ParsableFile} as input.
	 * 
	 * @param parsableFile the file of type {@link ParsableFile} to be parsed.
	 */
	public SimpleFileParser(ParsableFile parsableFile) {
		super(parsableFile);
	}

	/**
	 * Method which parses a file line by line and returns a {@link String} with
	 * appropriate error-catching and exception throwing.
	 * 
	 * The running time of the parse method scales as O(n) where n is the number of
	 * lines in the file to be parsed.
	 * 
	 * @return String returns the newly constructed {@link String} containing the
	 *         file contents.
	 * @throws Exception if the file does not exist or cannot be read.
	 * 
	 */
	@Override
	public String parse() throws Exception {

		StringBuilder sb = new StringBuilder();

		if (this.getParsable().exists()) {

			if (this.getParsable().canRead()) {

				try {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(new FileInputStream(this.getParsable())));
					String line = null;
					while ((line = br.readLine()) != null) {
						sb.append(line + "\n");
					}
					br.close();

				} catch (Exception exception) {
					System.out.println(exception.getCause());
					throw new Exception("\t-[error] while parsing file [" + this.getParsable().getName() + "]");
				}

			} else {
				throw new Exception("\t-[error] cannot read [" + this.getParsable().getName() + "]");
			}
		} else {
			throw new Exception("\t-[error] file [" + this.getParsable().getName() + "] does not exist");
		}
		return sb.toString();
	}

}
