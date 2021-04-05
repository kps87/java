package ie.gmit.dip;

/**
 * The class AbstractFileParser is an abstract base type which implements the
 * {@link AbstractParser} interface and therefore must have methods associated
 * with setting some input which is to be parsed, and subsequently parsing the
 * input.
 * 
 * As an abstract class, it is intended to form the base of a hierarchy of
 * specialized FileParser types. Specifically, an AbstractFileParser is designed
 * to take a {@link ParsableFile} type as input in methods from
 * {@link AbstractParser} which it implements. Generic functionality
 * (getters/setters) are all incorporated to manage any {@link ParsableFile}
 * operations.
 * 
 * The parse method which must be implemented by anything implementing the
 * {@link AbstractParser} interface remains abstract in AbstractFileParser, and
 * must be completely specified by any specialized sub-types of
 * AbstractFileParser which inherit from it.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @see ParsableFile
 * @see AbstractParser
 * 
 * @param ParsableFile the {@link AbstractParser} is configured with this type.
 * @param <T> a generic type that defines the return type after parsing.
 * 
 */
public abstract class AbstractFileParser<T> implements AbstractParser<ParsableFile, T> {

	/**
	 * {@link ParsableFile} instance which is set before parsing takes place.
	 */
	private ParsableFile parsableFile;

	/**
	 * Simple constructor method with no fields.
	 */
	public AbstractFileParser() {
		super();
	}

	/**
	 * Constructor method which takes a {@link ParsableFile} as input.
	 * 
	 * Has O(c) running time.
	 * 
	 * @param parsableFile the {@link ParsableFile} type to be parsed.
	 */
	public AbstractFileParser(ParsableFile parsableFile) {
		super();
		this.parsableFile = parsableFile;
	}

	/**
	 * Getter which returns the {@link ParsableFile} which has been set for parsing.
	 * 
	 * Has O(c) running time.
	 * 
	 * @return ParsableFile the {@link ParsableFile} type to be parsed, or which has been
	 *         parsed.
	 */
	public ParsableFile getParsable() {
		return parsableFile;
	}

	/**
	 * Overridden setter method to set the parsableFile variable i.e. the file to be
	 * parsed.
	 * 
	 * Has O(c) running time.
	 * 
	 * @see AbstractParser
	 * @param parsableFile the {@link ParsableFile} type to be parsed.
	 */
	@Override
	public void setInput(ParsableFile parsableFile) throws NullPointerException {
		if (parsableFile == null) {
			throw new NullPointerException("\t-[error] ParsableFile cannot be null.");
		} else {
			this.parsableFile = parsableFile;
		}

	}

	/**
	 * In this case the parse method is abstract (declarative only) and must be
	 * implemented by any concrete types which inherit from {@link AbstractFileParser}.
	 * 
	 * @see AbstractParser
	 * @return T the type returned is specified by inheriting classes.
	 * @throws Exception if there is some error during parsing.
	 */
	@Override
	public abstract T parse() throws Exception;

}