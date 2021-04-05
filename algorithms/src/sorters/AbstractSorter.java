package sorters;

/**
 * An abstract base class defining the methods required of a concrete Sorter
 * class
 * 
 * @author ksomers
 *
 * @param <T> the type to be sorted
 */
public abstract class AbstractSorter<T> {

	/**
	 * @param sortMethod a String type denoting the sort method to be used
	 */
	public abstract void setSortMethod(String sortMethod);

	/**
	 * @return a String type denoting the sort method currently defined
	 */
	public abstract String getSortMethod();

	/**
	 * an abstract method whose type T is defined by a concrete class which
	 * implements the method
	 */
	public abstract long sort(T input);

}
