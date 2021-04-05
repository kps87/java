package ie.gmit.dip;

/**
 * The interface AbstractTextSimplifier is an abstraction of a type which
 * simplifies text. Its primary responsibility is to set some text to be
 * simplified, simplify that text, and return the simplified text. Therefore, it
 * has public getter and setter methods associated with setting and retrieving
 * the text to simplify, as well as a simplify method which runs the
 * conversion/simplification.
 * 
 * The interface is configured with generic types, such that concrete types can
 * be configured to take and return arbitrary input, ranging from simple
 * {@link String} types, to Collections, to more complex objects.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @param <T> a generic type assigned to the text which is being simplified.
 * @param <U> a generic type assigned to the text which has been simplified.
 */
public interface AbstractTextSimplifier<T, U> {

	/**
	 * Setter method which sets the textToSimplify.
	 * 
	 * @param textToSimplify the text to simplify can be of any generic type T.
	 */
	public void setTextToSimplify(T textToSimplify);

	/**
	 * Getter method which returns text to simplify.
	 * 
	 * @return T the text to simplify can be of any generic type T.
	 */
	public T getTextToSimplify();

	/**
	 * Getter method which returns the simplified text.
	 * 
	 * @return U the simplified text can be of generic type U, can be a different
	 *         type to the input text of type T.
	 */
	public U getSimplifiedText();

	/**
	 * Method to run to simplify the text. Concrete types are responsible for the
	 * specifics of text simplification.
	 */
	public void simplify();

}