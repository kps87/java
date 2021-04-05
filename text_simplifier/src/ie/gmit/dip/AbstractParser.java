package ie.gmit.dip;

/**
 * The interface AbstractParser is an abstraction of a Parser type, that is, a
 * type which can be used to parse another object.
 * 
 * Parsers ranges in complexity from quite general parsers which read an entire
 * stream, to highly specialized ones which carry out a specific series of
 * operations, perhaps in defined order, depending on the specific use-case and
 * types of different data to be retrieved.
 * 
 * This interface declares that a Parser, should have two methods at minimum,
 * one to set the input, and the other to parse the input and return the parsed
 * data.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * 
 * @param <T> a parsable of generic type T.
 * @param <U> the parsed data of generic type U.
 */
public interface AbstractParser<T, U> {

	/**
	 * Method to set the input to be parsed. Concrete types are responsible for
	 * declaring the type of parsable input.
	 * 
	 * @param parsable a parsable of generic type T.
	 * @throws Exception throw an exception if there is some issue setting the
	 *                   parsable.
	 */
	public void setInput(T parsable) throws Exception;

	/**
	 * Method to parse the input once it is set. Concrete types are responsible for
	 * declaring the type of parsable input, and for the specific implementation of
	 * the parse method, which may be quite generic, or highly specific depending on
	 * the structure of the objecting being parsed.
	 * 
	 * @throws Exception throw an exception if there is some issue during parsing.
	 * @return U the parsed data of generic type U.
	 */
	public U parse() throws Exception;

}
