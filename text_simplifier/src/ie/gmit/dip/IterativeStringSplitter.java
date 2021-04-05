package ie.gmit.dip;

import java.util.*;

/**
 * The class IterativeStringSplitter is a concrete type that implements the
 * {@link AbstractParser} interface. {@link String} splitting is a frequent task
 * when parsing files and other streams. Having multiple utility classes which
 * allow for various splitting operations is useful for anyone carrying out a
 * lot of file parsing operations.
 * 
 * The IterativeStringSplitter is configured with a set of ordered delimiters
 * (i.e. {@link String} types, perhaps in the form of regular expressions) which
 * are used to iteratively split a string into an ordered {@link ArrayList} of
 * {@link String} types, which preserves the original order of elements in the
 * {@link String} type provided as input.
 * 
 * Note that an important feature of this parser is that it aims to preserve, as
 * closely as possible, the original {@link String} type which is being split.
 * Therefore, the delimiters which are used as the input for the String.split()
 * method, are added to the final {@link ArrayList} as elements in the order
 * they appeared/splitting occurred.
 * 
 * In order to achieve this, the {@link ArrayList} of delimiters is accompanied
 * by a {@link HashMap} of delimiters, which is used to map a given regular
 * expression/delimiter to a {@link String} which is substituted for that
 * regular expression after splitting occurs i.e. the regular expression used
 * for splitting is mapped to an equivalent {@link String} type which is used to
 * reconstruct the original {@link String} with its original delimiters
 * preserved.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @see AbstractParser
 * 
 * @param String    the {@link AbstractParser} interface is implemented to take
 *                  a {@link String} as input.
 * @param ArrayList the {@link AbstractParser} interface is implemented to
 *                  return an {@link ArrayList} of {@link String} types as
 *                  output.
 * 
 */
public class IterativeStringSplitter implements AbstractParser<String, ArrayList<String>> {

	private String parsable;
	private HashMap<String, String> delimiterMap;
	private ArrayList<String> delimiterOrder;

	/**
	 * Constructor which also configures the default delimiter order and delimiter
	 * map.
	 * 
	 * Has O(c) running time.
	 * 
	 */
	public IterativeStringSplitter() {
		super();
		this.setDefaultDelimiterOrder();
		this.setDefaultDelimiterMap();
	}

	/**
	 * Setter method which is used to assign a {@link String} to parse
	 * 
	 * @param parsable the {@link String} to be parsed
	 * @throws Exception if there is an issue setting the input.
	 * @see {@link AbstractParser}
	 */
	@Override
	public void setInput(String parsable) throws Exception {
		if (parsable != null) {
			this.parsable = parsable;
		} else {
			throw new Exception("\t-[error] null input provided by user");
		}
	}

	/**
	 * Getter method which returns the {@link ArrayList} of ordered delimiters used
	 * to split the input {@link String}.
	 * 
	 * Has O(c) running time.
	 * 
	 * @return ArrayList<String> an {@link ArrayList} of delimiters of type
	 *         {@link String} use to split the input.
	 */
	public ArrayList<String> getDelimiterOrder() {
		return this.delimiterOrder;
	}

	/**
	 * Setter method to define the {@link ArrayList} of ordered delimiters used to
	 * split the {@link String}.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param delimiterOrder an {@link ArrayList} of delimiters of type
	 *                       {@link String} use to split the input.
	 */
	public void setDelimiterOrder(ArrayList<String> delimiterOrder) {
		this.delimiterOrder = delimiterOrder;
	}

	/**
	 * Getter method which returns the {@link HashMap} of {@link String} type
	 * delimiters (keys) and the alternative {@link String} types (values) which
	 * replace the delimiters upon splitting.
	 * 
	 * Has O(c) running time.
	 * 
	 * @return {@link HashMap} - the {@link HashMap} of {@link String} type
	 *         delimiters (keys) and the alternative {@link String} types (values)
	 *         which replace the delimiters upon splitting.
	 */
	public HashMap<String, String> getDelimiterMap() {
		return this.delimiterMap;
	}

	/**
	 * Setter method to define the {@link HashMap} of {@link String} type delimiters
	 * (keys) and the alternative {@link String} types (values) which replace the
	 * delimiters upon splitting.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param delimiterMap is a {@link HashMap} of {@link String} type delimiters
	 *                     (keys) and the alternative {@link String} types (values)
	 *                     which replace the delimiters upon splitting.
	 * 
	 */
	public void setDelimiterMap(HashMap<String, String> delimiterMap) {
		this.delimiterMap = new HashMap<String, String>(delimiterMap);
	}

	/**
	 * Setter method to define a default {@link HashMap} of key value pairs for the
	 * delimiterMap variable.
	 * 
	 * Has O(c) running time.
	 */
	public void setDefaultDelimiterMap() {
		this.setDelimiterMap(this.getDefaultDelimiterMap());
	}

	/**
	 * Getter method which defines a default {@link HashMap} of key value pairs for
	 * the delimiterMap variable.
	 * 
	 * Running time is effectively is the sum of the O(c) put operations.
	 * 
	 * @return {@link HashMap} the {@link HashMap} of {@link String} type delimiters
	 *         (keys) and the alternative {@link String} types (values) which
	 *         replace the delimiters upon splitting.
	 */
	public HashMap<String, String> getDefaultDelimiterMap() {

		HashMap<String, String> delimiterMap = new HashMap<String, String>();
		delimiterMap.put("\n", "\n");
		delimiterMap.put("\\s+", " ");
		delimiterMap.put("\\.", ".");
		delimiterMap.put(",", ",");
		delimiterMap.put(";", ";");
		return delimiterMap;

	}

	/**
	 * Getter method which defines a default ArrayList of delimiters use to split
	 * the String. These delimiters have a natural order, that is, the string is
	 * parsed by new line characters first, then by spaces (words), then by periods
	 * (i.e. sentences), commas (,) and semicolons (;).
	 * 
	 * Any order of delimiters can of course be used.
	 * 
	 * Running time is effectively is the sum of the O(c) add operations.
	 * 
	 * @return delimiterOrder an {@link ArrayList} of delimiters of {@link String}
	 *         types in the order they are used to split the {@link String}.
	 */
	public ArrayList<String> getDefaultDelimiterOrder() {

		ArrayList<String> delimiterOrder = new ArrayList<String>();
		delimiterOrder.add("\n");
		delimiterOrder.add("\\s+");
		delimiterOrder.add("\\.");
		delimiterOrder.add(",");
		delimiterOrder.add(";");
		return delimiterOrder;

	}

	/**
	 * Setter method to define a default {@link ArrayList} of {@link String} types
	 * to set the the delimiterOrder variable.
	 * 
	 * Running time is effectively O(c).
	 */
	public void setDefaultDelimiterOrder() {
		this.setDelimiterOrder(this.getDefaultDelimiterOrder());
	}

	/**
	 * The parse() method which is used to iteratively split the string. The order
	 * of operations is as follows.
	 * 
	 * Convert the original string to an {@link ArrayList} with a single element.
	 * 
	 * For each delimiter in the this.delimiterOrder {@link ArrayList}, loop over
	 * all of the strings in the input.
	 * 
	 * If the original string contains the mapped delimiter character, then split
	 * the {@link String} into individual elements based on that delimiter, and add
	 * the split elements to a temporary array, along with the correct number of
	 * delimiters characters.
	 * 
	 * If the original {@link String} does not contain the delimiter character, then
	 * no split is necessary, and the original {@link String} is simply added to the
	 * temporary array.
	 * 
	 * The original {@link ArrayList} (of one element) is replaced with the new
	 * array of potentially multiple elements, and this list of {@link String} types
	 * is now iteratively split by the next delimiter.
	 * 
	 * A comment on running time and Big-O notation. The three nested for loops over
	 * the delimiters, the {@link ArrayList} of strings and finally the split
	 * strings imply an O(n*m*l) cubic running time where n is the number of
	 * delimiters, m is the number of {@link String} types to split, and m is the
	 * number of {@link String} types returned from splitting the {@link String} m.
	 * The actual running time will vary depending on the number of delimiters to
	 * split on, and the characters in the initial {@link String} being split.
	 * 
	 * @return ArrayList<String> an {@link ArrayList} of split {@link String}
	 *         types.
	 * @throws Exception if there is an error during splitting.
	 * 
	 */
	@Override
	public ArrayList<String> parse() throws Exception {

		ArrayList<String> toSplit = new ArrayList<String>();
		toSplit.add(this.parsable);

		for (String delimiter : this.delimiterOrder) {

			ArrayList<String> temp = new ArrayList<String>();
			String mapped = delimiterMap.get(delimiter);
			for (String string : toSplit) {

				if (string.contains(mapped)) {
					String[] split = string.split(delimiter);
					int i = 0;
					int max = 0;
					if (split.length == 1) {
						max = 1;
					} else {
						max = split.length - 1;
					}
					for (String spl : split) {
						temp.add(spl);
						if (i < max) {
							temp.add(mapped);
						}
						i += 1;
					}
				} else {
					temp.add(string);
				}
			}
			toSplit = temp;
		}

		return new ArrayList<String>(toSplit);
	}

}
