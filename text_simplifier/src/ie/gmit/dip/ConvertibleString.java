package ie.gmit.dip;

import java.util.ArrayList;

/**
 * ConvertibleString is a concrete class which implements the
 * AbstractConvertible interface. ConvertibleString is configured to take a
 * {@link String} as input, return a {@link String} as output, with the
 * {@link String}-to-{@link String} conversions carried out via the provision of
 * an {@link ArrayList} of {@link AbstractStringConverter} types. The
 * {@link ArrayList}, being an ordered Collection, is therefore executed in the
 * order of insertion of the {@link AbstractStringConverter} types.
 * 
 * This type relies on polymorphism and the Liskov Substitutability Principle,
 * with responsibility for conversion delegated to the specific
 * {@link AbstractStringConverter} which has been externally configured.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @see AbstractStringConverter
 * @see StringCaseConverter
 * @see StringColourConverter
 * @see StringSynonymConverter
 *
 * @param String                               the {@link AbstractConvertible}
 *                                             is configured with a
 *                                             {@link String} as input, and
 *                                             returns a {@link String} as
 *                                             output.
 * @param <ArrayList<AbstractStringConverter>> an {@link ArrayList} of
 *                                             {@link AbstractStringConverter}
 *                                             types.
 */
public class ConvertibleString implements AbstractConvertible<String, String, ArrayList<AbstractStringConverter>> {

	/**
	 * {@link String} variable which stores the variable to be converted.
	 */
	private String convertible;
	/**
	 * {@link String} variable which stores the converted variable.
	 */
	private String converted;
	/**
	 * The {@link ArrayList} of {@link AbstractStringConverter} types used to carry
	 * out the conversion.
	 */
	private ArrayList<AbstractStringConverter> converters;

	/**
	 * Simple constructor method.
	 */
	public ConvertibleString() {
		super();
	}

	/**
	 * Overloaded constructor method which allows for the definition of the
	 * convertible variable.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param convertible the {@link String} to be converted.
	 */
	public ConvertibleString(String convertible) {
		super();
		this.setConvertible(convertible);
	}

	/**
	 * Setter method which allows for the definition of the convertible variable.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param convertible the {@link String} to be converted.
	 */
	@Override
	public void setConvertible(String convertible) {
		this.convertible = convertible;
	}

	/**
	 * Getter method which returns the convertible variable.
	 * 
	 * Has O(c) running time.
	 * 
	 * @return String the {@link String} to be converted.
	 */
	@Override
	public String getConvertible() {
		return this.convertible;
	}

	/**
	 * Getter method which returns the converted string.
	 * 
	 * Has O(c) running time.
	 * 
	 * @return String the converted {@link String}.
	 */
	@Override
	public String getConverted() {
		return this.converted;
	}

	/**
	 * Setter method which allows one to set the list of converters which are
	 * executed via the convert() method.
	 * 
	 * Has O(c) running time locally.
	 * 
	 * @param converters an {@link ArrayList} of {@link AbstractStringConverter}
	 *                   types used to carry out the conversion
	 */
	@Override
	public void setConverters(ArrayList<AbstractStringConverter> converters) {
		this.converters = converters;
	}

	/**
	 * This is the central and important method which converts the instance variable
	 * this.convertible, and sets the converted variable this.converted.
	 * 
	 * Conversion is carried out by a series of {@link AbstractStringConverter}
	 * types which are iteratively called. The user should therefore ensure that the
	 * {@link ArrayList} of {@link AbstractStringConverter} types is configured in
	 * the correct order.
	 * 
	 * The running time is O(n) with respect to the number of
	 * {@link AbstractStringConverter} types being run, however, the running time of
	 * each converter is not known a priori, and depends on the running time of the
	 * specific {@link AbstractStringConverter} types being run.
	 * 
	 */
	@Override
	public void convert() {

		this.converted = this.convertible;
		if (this.converters.size() > 0) {
			for (AbstractStringConverter converter : this.converters) {
				this.converted = converter.convert(this.converted);
			}
		}

	}

}
