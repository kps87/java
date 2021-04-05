package ie.gmit.dip;

/**
 * The interface AbstractConvertible is an abstract base type which defines the
 * basic functionality expected in a more specialized hierarchy of convertible
 * types, that is, something of type T is convertible to a type U, by a
 * converter of type R.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * 
 * @param <T> a generic type for the convertible object.
 * @param <U> a generic type for the converted object.
 * @param <R> a generic type for the converters used to carry out conversion.
 * 
 */
public interface AbstractConvertible<T, U, R> {

	/**
	 * Setter method which sets the convertible input of type T which is to be
	 * converted.
	 * 
	 * @param convertible the type to be converted.
	 */
	public void setConvertible(T convertible);

	/**
	 * Getter method which returns the instance of the type T to be converted.
	 * 
	 * @return T the type to be converted.
	 */
	public T getConvertible();

	/**
	 * Getter method which returns the converted object, of type U.
	 * 
	 * @return U the type which has undergone conversion.
	 */
	public U getConverted();

	/**
	 * Method which allows converters of type R to be defined.
	 * 
	 * This allows for a convertible to undergo multiple conversions if a Collection
	 * of converters are provided. The Collection can be ordered or unordered, the
	 * user should be specific depending on the use case.
	 * 
	 * @param converters a single converter, or multiple converters, used to carry
	 *                   out conversion.
	 */
	public void setConverters(R converters);

	/**
	 * The key method for a convertible is the convert method. The details of
	 * conversion are handled by any specific concrete type that implements this
	 * interface.
	 */
	public void convert();

}
