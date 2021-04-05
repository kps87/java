package ie.gmit.dip;

import java.util.*;

/**
 * OneToOneMappedThesaurus is a concrete class which implements the
 * {@link Thesaurable} interface. In broad terms, it simply maps one
 * {@link String} to another {@link String} via a {@link HashMap}, and therefore
 * provides a simple one-to-one mapping of words to their synonym.
 * 
 * This class is responsible for state and behavior related to adding,
 * searching, and removing the words and synonyms in a thesaurus. Since
 * virtually all operations are delegated to a {@link HashMap}, the running time
 * complexity of a {@link HashMap} are expected in all cases with O(c) running
 * time typical for most methods.
 * 
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @param String  the {@link Thesaurable} is configured with a {@link String}
 *                input type for words.
 * @param String  the {@link Thesaurable} is configured with a {@link String}
 *                return type for synonyms.
 * @param HashMap the {@link Thesaurable} is configured with a
 *                {@link HashMap<String, String>} for mapping of words to their
 *                synonyms.
 */
public class OneToOneMappedThesaurus implements Thesaurable<String, String, HashMap<String, String>> {

	/**
	 * thesaurusWords is a one-to-one {@link String}-to-{@link String}
	 * {@link HashMap}.
	 */
	private HashMap<String, String> words;

	/**
	 * Simple constructor method.
	 */
	public OneToOneMappedThesaurus() {
		super();
		words = new HashMap<String, String>();
	}

	/**
	 * Constructor method which allows one to configure the {@link HashMap} words.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param words a {@link HashMap} of words mapped on a one-to-one basis
	 * 
	 */
	public OneToOneMappedThesaurus(HashMap<String, String> words) {
		super();
		this.setAllWords(words);
	}

	/**
	 * Getter method which sets the {@link HashMap} of mapped words.
	 * 
	 * Has O(c) running time.
	 * 
	 * @return words a {@link HashMap} of words mapped on a one-to-one basis.
	 */
	public HashMap<String, String> getAllWords() {
		return this.words;
	}

	/**
	 * Setter method which sets the words HashMap.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param words a {@link HashMap} of words mapped on a one-to-one basis.
	 */
	@Override
	public void setAllWords(HashMap<String, String> words) {
		this.words = new HashMap<String, String>(words);
	}

	/**
	 * Method to add a word and its synonym to the {@link HashMap}.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param word    the word to be added as a key to the {@link HashMap}.
	 * @param synonym the synonym to be added as the corresponding value to the
	 *                {@link HashMap}.
	 */
	@Override
	public void addWord(String word, String synonym) {
		this.words.put(word, synonym);
	}

	/**
	 * Method to verify if the {@link HashMap} contains a given key/word.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param word the word to be tested as a key in the {@link HashMap}.
	 */
	@Override
	public boolean hasWord(String word) {
		return this.words.containsKey(word);
	}

	/**
	 * Method to remove a key-value pair from the {@link HashMap}.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param word the word to be removed as a key from the {@link HashMap}.
	 */
	@Override
	public void removeWord(String word) {
		this.words.remove(word);
	}

	/**
	 * Method to get the synonym of a given word from the {@link HashMap}.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param word the word for which the corresponding synonym is requested.
	 */
	@Override
	public String getSynonym(String word) {
		if (this.words.containsKey(word)) {
			return this.words.get(word);
		}
		return null;
	}

}
