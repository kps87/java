package ie.gmit.dip;

/**
 * AbstractConverter is a simple abstract interface which defines the basis of a
 * hierarchy of classes and functional interfaces of Converter types.
 * 
 * These Converter types take a type T as input and return a type U as output,
 * with only a single method, convert(), required to carry out the conversion.
 * 
 * In this sense it can be seen as similar to other functional interfaces such
 * as Comparable, Runnable and Comparator, which implement only a single method.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @param <T> a generic input type.
 * @param <U> a generic output type.
 */
public interface AbstractConverter<T, U> {

	/**
	 * The convert method takes a type T as input, and returns a type U.
	 * 
	 * @param input defines the type to be converted.
	 * @return U a generic type for the converted variable.
	 */
	public U convert(T input);

}
