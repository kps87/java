package ie.gmit.dip;

import java.io.File;
import java.util.*;

/**
 * OneToOneMappedThesaurusBuilder is a concrete class which is used to construct
 * a {@link OneToOneMappedThesaurus} from a pair of files, one file containing a
 * thesaurus of words, with synonyms entered as comma separated values on each
 * line, and the other file containing a list of priority words to which words
 * in the thesaurus should be mapped when said priority word is encountered in
 * the thesaurus.
 * 
 * Its primary responsibility lies in managing the state and behavior associated
 * with constructing the {@link OneToOneMappedThesaurus}. It is composed of two
 * {@link ParsableFile} objects, each of which is parsed via a
 * {@link SimpleFileParser} type.
 * 
 * Data in the priorityWordSetFile is mapped to a {@link HashSet} of
 * {@link String} types stored in the priorityWordSet variable.
 * 
 * Data in the thesaurusFile is mapped to the {@link OneToOneMappedThesaurus}
 * type, which is the central object being constructed, and which is stored in
 * the mappedThesaurus variable.
 * 
 * The priorityWordSet and the mappedThesaurus are publicly accessible via
 * getter methods.
 * 
 * The following important logic applies to this mapper:
 * 
 * words in the thesaurus are mapped to a corresponding word in the
 * priorityWordSet on a first-come-first-serve basis, once they have been mapped
 * to an equivalent word, a subsequent equivalent word will not overwrite this
 * initial mapping. This mapper is therefore deterministic based on the order of
 * appearance in the thesaurusFile.
 * 
 * A comment on Big-O notation and running times is provided in the
 * buildMappedThesaurus method below.
 * 
 * This class is coupled to and cohesive with the {@link Thesaurable} interface
 * and {@link OneToOneMappedThesaurus} which implements the former. It is also
 * coupled tightly with the {@link ParsableFile} type and has multiple
 * dependencies on the {@link SimpleFileParser} type.
 * 
 * However, the internal behavior which is dependent on {@link ParsableFile} is
 * encapsulated and fully shielded from external users of this class, as users
 * simply have to provide standard java {@link File} types as input, and
 * {@link ParsableFile} types are converted to standard java {@link File} types
 * before returning them. In this way, the user of the API can interact with the
 * {@link OneToOneMappedThesaurusBuilder} without understanding or depending on
 * the internal dependency on the {@link ParsableFile} and
 * {@link SimpleFileParser} types.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * 
 * @see OneToOneMappedThesaurus
 * @see ParsableFile
 * @see SimpleFileParser
 */
public class OneToOneMappedThesaurusBuilder {

	/**
	 * A ParsableFile type containing the location of the thesaurus input file.
	 */
	private ParsableFile thesaurusFile;
	/**
	 * A ParsableFile type containing the location of the list of top words.
	 */
	private ParsableFile priorityWordSetFile;
	/**
	 * A {@link HashSet} used to store the contents of the priorityWordSetFile.
	 */
	private HashSet<String> priorityWordSet;
	/**
	 * A {@link OneToOneMappedThesaurus} which stores the contents of the
	 * thesaurusFile after they have been mapped via the priorityWordSet.
	 */
	private OneToOneMappedThesaurus mappedThesaurus;

	/**
	 * Simple constructor which instantiates instance variables and sets the default
	 * thesaurus and priority word file inputs.
	 * 
	 * Running time is effectively O(c).
	 */
	public OneToOneMappedThesaurusBuilder() {
		super();
		this.setDefaultThesaurusFile();
		this.setDefaultPriorityWordSetFile();
		this.priorityWordSet = new HashSet<String>();
		this.mappedThesaurus = new OneToOneMappedThesaurus();
	}

	/**
	 * Overloaded constructor which allows the thesaurusFile and priorityWordSetFile
	 * locations to be defined.
	 * 
	 * Running time is effectively O(c).
	 * 
	 * @param thesaurusFile       a {@link File} type containing the location of the
	 *                            thesaurus input file.
	 * @param priorityWordSetFile a {@link File} type containing the location of the
	 *                            list of priority words.
	 */
	public OneToOneMappedThesaurusBuilder(File thesaurusFile, File priorityWordSetFile) {
		super();
		this.setThesaurusFile(new ParsableFile(thesaurusFile.getAbsolutePath()));
		this.setPriorityWordSetFile(new ParsableFile(priorityWordSetFile.getAbsolutePath()));
		this.priorityWordSet = new HashSet<String>();
		this.mappedThesaurus = new OneToOneMappedThesaurus();
	}

	/**
	 * Getter method which returns the thesaurusFile variable.
	 * 
	 * Running time is effectively O(c).
	 * 
	 * @return thesaurusFile a {@link File} type containing the location of the
	 *         thesaurus input file.
	 */
	public File getThesaurusFile() {
		return new File(thesaurusFile.getAbsolutePath());
	}

	/**
	 * Setter method which sets the thesaurusFile variable.
	 * 
	 * Running time is effectively O(c).
	 * 
	 * @param thesaurusFile a {@link File} type containing the location of the
	 *                      thesaurus input file which is converted to a
	 *                      {@link ParsableFile} for internal use
	 */
	public void setThesaurusFile(File thesaurusFile) {
		this.thesaurusFile = new ParsableFile(thesaurusFile.getAbsolutePath());
	}

	/**
	 * Getter method which returns the priorityWordSetFile as a new File.
	 * 
	 * Running time is effectively O(c).
	 * 
	 * @return priorityWordSetFile a {@link File} type containing the location of
	 *         the priorityWordSetFile input file.
	 */
	public File getPriorityWordSetFile() {
		return new File(priorityWordSetFile.getAbsolutePath());
	}

	/**
	 * Setter method which sets the priorityWordSetFile.
	 * 
	 * Running time is effectively O(c).
	 * 
	 * @param priorityWordSetFile a {@link File} type containing the location of the
	 *                            important priorityWordSetFile input file which is
	 *                            further converted to a {@link ParsableFile} for
	 *                            internal use.
	 */
	public void setPriorityWordSetFile(File priorityWordSetFile) {
		this.priorityWordSetFile = new ParsableFile(priorityWordSetFile.getAbsolutePath());
	}

	/**
	 * Private setter method which sets a default name for the thesaurusFile.
	 * 
	 * Running time is O(c).
	 */
	private void setDefaultThesaurusFile() {
		this.setThesaurusFile(this.getDefaultThesaurusFile());
	}

	/**
	 * Getter method which returns the default name for the thesaurusFile.
	 * 
	 * Running time is O(c).
	 */
	private ParsableFile getDefaultThesaurusFile() {
		return new ParsableFile("MobyThesaurus2.txt");
	}

	/**
	 * Private default setter method which sets a default name for the
	 * priorityWordSetFile.
	 * 
	 * Running time is O(c).
	 */
	private void setDefaultPriorityWordSetFile() {
		this.setPriorityWordSetFile(getDefaultPriorityWordSetFile());
	}

	/**
	 * Default getter method which returns the default name for the
	 * priorityWordSetFile.
	 * 
	 * Running time is O(c).
	 */
	private ParsableFile getDefaultPriorityWordSetFile() {
		return new ParsableFile("google-1000.txt");
	}

	/**
	 * Getter method which returns the constructed {@link OneToOneMappedThesaurus}
	 * type.
	 * 
	 * Running time is O(c).
	 * 
	 * @return mappedThesaurus A {@link OneToOneMappedThesaurus} which stores the
	 *         contents of the thesaurusFile after they have been mapped via the
	 *         priorityWordSet
	 */
	public OneToOneMappedThesaurus getMappedThesaurus() {
		return this.mappedThesaurus;
	}

	/**
	 * Parser method which reads the contents of the thesaurusFile. Responsibility
	 * for reading is delegated to a {@link SimpleFileParser} with contents stored
	 * in the thesaurusFile which is a {@link ParsableFile}.
	 * 
	 * Running time is O(c) locally but is O(n) when the {@link SimpleFileParser}
	 * running time is considered.
	 * 
	 * @throws Exception if the {@link File} or {@link ParsableFile} cannot be read
	 *                   or parsed.
	 */
	public void readThesaurusFile() throws Exception {
		SimpleFileParser simpleFileParser = new SimpleFileParser();
		try {
			simpleFileParser.setInput(new ParsableFile(this.thesaurusFile.getAbsolutePath()));
			this.thesaurusFile.setParsedData(simpleFileParser.parse());
		} catch (Exception exception) {
			System.out.println("\t-[error] in ThesaurusMapBuilder().readThesaurusFile()\n" + exception.getMessage());
		}

	}

	/**
	 * Parser method which reads the contents of the priorityWordSetFile.
	 * Responsibility for reading is delegated to a {@link SimpleFileParser} with
	 * contents stored in the priorityWordSetFile which is a {@link ParsableFile}.
	 * 
	 * Running time is O(c) locally but is O(n) when the {@link SimpleFileParser}
	 * running time is considered.
	 * 
	 * @throws Exception if the file cannot be read or parsed.
	 */
	public void readPriorityWordSetFile() throws Exception {
		SimpleFileParser simpleFileParser = new SimpleFileParser();
		try {
			simpleFileParser.setInput(new ParsableFile(this.priorityWordSetFile.getAbsolutePath()));
			this.priorityWordSetFile.setParsedData(simpleFileParser.parse());
		} catch (Exception exception) {
			System.out.println("\t-[error] in ThesaurusMapBuilder().readWordsToMapToFile()\n" + exception.getMessage());
		}
	}

	/**
	 * Public method which can be called to construct a new
	 * {@link OneToOneMappedThesaurus}.
	 * 
	 * Input files are first read in, then the priorityWordSet and
	 * {@link OneToOneMappedThesaurus} are built.
	 * 
	 * Running time is O(c) locally but is the sum of the running times of the four
	 * individual methods being called. The first three method calls are O(n), with
	 * the buildMappedThesaurus() method exhibiting cubic O(n^3) scaling in the
	 * worst case scenario.
	 * 
	 * @throws Exception if the file cannot be read or parsed.
	 */
	public void build() throws Exception {

		TerminalTitleWriter.writeTitle("", "-", 75, "-Building a thesaurus map");
		System.out.println("\t-thesaurus file:" + this.getThesaurusFile().getCanonicalPath());
		System.out.println("\t-priority words file:" + this.getPriorityWordSetFile().getCanonicalPath());
		readPriorityWordSetFile();
		readThesaurusFile();
		setPriorityWords();
		buildMappedThesaurus();
	}

	/**
	 * Getter method which returns the priorityWordSet as a {@link HashSet} of
	 * {@link String} types.
	 * 
	 * Running time is O(c).
	 * 
	 * @return priorityWordSet a {@link HashSet} used to store the contents of the
	 *         priorityWordSetFile
	 */
	public HashSet<String> getPriorityWordSet() {
		return this.priorityWordSet;
	}

	/**
	 * Setter method which builds the priorityWordSet as a {@link HashSet} of
	 * {@link String} types.
	 * 
	 * Running time is O(n) where n is the number of lines in the
	 * priorityWordSetFile to be added to the {@link HashSet}.
	 * 
	 */
	public void setPriorityWords() {
		this.priorityWordSet = new HashSet<String>();

		for (String str : this.priorityWordSetFile.getParsedData().split("\n")) {
			str = str.trim();
			str = str.toLowerCase();
			priorityWordSet.add(str);
		}
	}

	/**
	 * Method which builds the {@link OneToOneMappedThesaurus}. This method contains
	 * specialized/specific logic pertaining to the current project. Specifically,
	 * the following logic is enforced:
	 * 
	 * 1) For each line in the thesaurus file, split by commas.
	 * 
	 * 2) If a line contains a word found in the priorityWordSet, set the
	 * equivalentWord variable to this word.
	 * 
	 * 3) For each word on the line, remove whitespace and convert to lowercase,
	 * then enforce the following logic.
	 * 
	 * 3a) if this word is in the priorityWordSet but not the mappedThesaurus then
	 * map it to itself in the mappedThesaurus.
	 * 
	 * 3b) If this word is not in the priorityWordSet but an equivalentWord that is
	 * in the priorityWordSet is available, then map the word to the equivalent word
	 * if and only if the word has not already been mapped to an equivalent word, or
	 * if it has been mapped to itself.
	 * 
	 * 3c) If no equivalent word is available, then map the word to itself, but this
	 * self-mapping can be overridden if an equivalent word in the priorityWordSet
	 * becomes available on a subsequent line.
	 * 
	 * A comment on Big-O notation is relevant.
	 * 
	 * The outer loop which iterates over the contents of the file has O(n) running
	 * time where n is the number of lines in the file. The first inner loop which
	 * tests the line for the presence of a word in the "Google 1000" file (i.e.
	 * priorityWordSet) has worst-case O(n) running time, as all elements of the
	 * line may have to be tested before an equivalent word is found.
	 * 
	 * The second inner loop (not tested with first loop) also runs in O(n) time,
	 * but this is compounded by the series of logical if and else if statements,
	 * which rely on {@link HashSet} and {@link HashMap} searches via the contains,
	 * hasWord, addWord and getSynonym methods. These latter methods are all
	 * dictionary key based operations which have O(1) running time, and are
	 * therefore quick.
	 * 
	 * The String.equals() method call which is use to compare equality of two
	 * {@link String} types has worst-case O(n) running time where n is the length
	 * of the {@link String}.
	 * 
	 * The running time of this method can be approximate by O(l*n*c) where l is the
	 * average number of lines in a file, n is the average number of elements
	 * (words) on a line, and c is the average number of characters in an element
	 * (word). These might imply an O(n^3) running time in the worst case scenario,
	 * however, the number of elements on a line (n) and number of characters in an
	 * element (c) are likely to be much less than the number of lines.
	 * 
	 * The use of multiple cheap logic statements to test the thesaurus contents
	 * without invoking the expensive equals method, and the use of key based method
	 * as frequently as possible should ensure running times are rarely as severe as
	 * O(n^3). The actual running time cannot be guaranteed as it depends on the
	 * complexity of the thesaurus file provided, and the complexity and order of
	 * its contents. In practice, the thesaurus files provided for this project,
	 * consisting of 30000 lines, and many more words, can be mapped in less than a
	 * second or so.
	 */
	public void buildMappedThesaurus() {

		this.mappedThesaurus = new OneToOneMappedThesaurus();

		for (String line : this.thesaurusFile.getParsedData().split("\n")) {

			// split the line by comma separated values
			String equivalentWord = null;
			String[] csvSplit = line.split(",");

			// test if their is an equivalent word in the
			for (String csv : csvSplit) {
				csv = csv.trim();
				csv = csv.toLowerCase();

				// if a word on this line is found in
				// the most popular word list, make note of it
				// and break the loop
				if (priorityWordSet.contains(csv)) {
					equivalentWord = csv;
					break;
				}
			}

			// now map the contents of the thesaurusFile to a OneToOneMappedThesaurus
			for (String csv : csvSplit) {
				csv = csv.trim();
				csv = csv.toLowerCase();

				// if this word is in the top 1000, but not in the thesaurus
				// then map it to itself in the thesaurus map, ensures google words are always
				// mapped to themselves in thesuarus map
				if (priorityWordSet.contains(csv) && !mappedThesaurus.hasWord(csv)) {
					mappedThesaurus.addWord(csv, csv);
				}
				// if the equivalent word is not null, and this key
				// is not defined in the topWords then map to an equivalent
				// with further conditions/caveats below
				else if (!priorityWordSet.contains(csv) && equivalentWord != null) {

					// if the temporary map does not contain the key
					// map it to the equivalent word
					if (!mappedThesaurus.hasWord(csv)) {
						mappedThesaurus.addWord(csv, equivalentWord);
					}
					// if the temporary map contains this word, but it has been
					// mapped to itself, map it to the equivalent but not identical word
					else if (mappedThesaurus.hasWord(csv) && mappedThesaurus.getSynonym(csv).equals(csv)) {
						mappedThesaurus.addWord(csv, equivalentWord);
					}

				}
				// else if the popular equivalent is null, and the word is not in the top words
				// list and the temporary map does not already contain this word, then map it to
				// itself: this self-mapping is over-riden if a synonym is encountered above
				else if (priorityWordSet.contains(csv) && !mappedThesaurus.hasWord(csv)) {
					mappedThesaurus.addWord(csv, csv);
				}
			}
		}

	}

}
