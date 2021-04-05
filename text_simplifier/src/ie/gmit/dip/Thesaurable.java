package ie.gmit.dip;

/**
 * The abstract interface Thesaurable declares the basic functionality expected
 * of a thesaurus, that is a mapping of a {@link String} or multiple
 * {@link String} types, to another {@link String} or multiple {@link String}
 * types.
 * 
 * The generic definition of the variables T, U and R allows for one to map a
 * single {@link String}, or multiple {@link String} types, to any other type,
 * be it another {@link String} for a simple thesaurus, or a Collection of
 * {@link String} in the case of more complex mappings, or even, more complex
 * types.
 * 
 * Whilst {@link String} operations are largely expected, the interface is
 * written with generic types should a user wish to provide their own
 * configuration e.g. mapping Collections to other Collections.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 *
 * @param <T> a generic type definition for a word.
 * @param <U> a generic type definition for a synonym.
 * @param <R> a generic type definition for a group or Collection of words.
 */
public interface Thesaurable<T, U, R> {

	/**
	 * the addWord method allows a word and its synonym(s) to be added to the a type
	 * implementing Thesaurable.
	 * 
	 * @param word    the word to add.
	 * @param synonym the corresponding synonym to add.
	 */
	public void addWord(T word, U synonym);

	/**
	 * the removeWord method allows a word and its synonym(s) to be removed from the
	 * type implementing Thesaurable.
	 * 
	 * @param word the word to remove.
	 */
	public void removeWord(T word);

	/**
	 * the hasWord method allows one to test for the existence of a word in a type
	 * implementing Thesaurable.
	 * 
	 * @param word the word to search for.
	 */
	public boolean hasWord(T word);

	/**
	 * Getter method that returns all of the words in the type implementing
	 * Thesaurable.
	 * 
	 * @return R a generic type containing more than one word.
	 */
	public R getAllWords();

	/**
	 * Setter method to set all of the words in the type implementing Thesaurable.
	 * 
	 * @param words a generic type containing more than one word.
	 */
	public void setAllWords(R words);

	/**
	 * Getter method which returns a synonym given a word.
	 * 
	 * @return U is a generic return type containing one or more synonym(s)
	 */
	public U getSynonym(T word);

}
