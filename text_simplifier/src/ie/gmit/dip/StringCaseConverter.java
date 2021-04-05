package ie.gmit.dip;

/**
 * StringCaseConverter is an concrete class which extends the abstract base
 * class {@link AbstractStringConverter}. Its primary responsibility concerns
 * converting the case of a string, and is currently configured to handle
 * transformations between upper, lower, and title case, with responsibility for
 * these operations delegated to the {@link String} and {@link Character}
 * classes.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @see AbstractStringConverter
 *
 */
public class StringCaseConverter extends AbstractStringConverter {

	/**
	 * {@link String} variable which defines the modification which should be
	 * carried out to the string.
	 */
	private String caseModifier;

	/**
	 * Constructor method
	 */
	public StringCaseConverter() {
		super();
	}

	/**
	 * Overloaded Constructor method which allows the caseModifier variable to be
	 * set.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param caseModifier is a {@link String} which defines the modification to be
	 *                     carried out to the {@link String} provided to the convert
	 *                     method.
	 */
	public StringCaseConverter(String caseModifier) {
		super();
		this.caseModifier = caseModifier;
	}

	/**
	 * Getter method which returns the {@link String} which defines the modification
	 * to be carried out the string variable provided to the convert method.
	 * 
	 * @return String caseModifier which is a {@link String} which defines the
	 *         modification to be carried out to the {@link String} provided to the
	 *         convert method.
	 */
	public String getCaseModifier() {
		return caseModifier;
	}

	/**
	 * Setter method to define the caseModifier variable/
	 * 
	 * O(c) run-time.
	 * 
	 * @param caseModifier a {@link String} which defines the modification to be
	 *                     carried out to the {@link String} provided to the convert
	 *                     method.
	 */
	public void setCaseModifier(String caseModifier) {
		this.caseModifier = caseModifier.toLowerCase();
	}

	/**
	 * Convert method which takes a {@link String} as input and converts it to title
	 * case, lower case, or upper case depending on the definition of the
	 * caseModifier variable.
	 * 
	 * Worst-case run time is O(n) where n = the number of characters in the string
	 * input variable.
	 * 
	 * @see AbstractStringConverter
	 * @return String the converted {@link String}.
	 * 
	 */
	@Override
	public String convert(String string) {

		if (caseModifier.equals("title")) {
			StringBuilder sb = new StringBuilder();
			char[] charArray = string.toCharArray();
			sb.append(Character.toTitleCase(string.charAt(0)));
			for (int i = 1; i < string.length(); i++) {
				sb.append(charArray[i]);
			}
			return sb.toString();
		} else if (caseModifier.equals("lower")) {
			return string.toLowerCase();
		} else if (caseModifier.equals("upper")) {
			return string.toUpperCase();
		}
		return string;
	}

}
