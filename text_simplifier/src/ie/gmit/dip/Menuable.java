package ie.gmit.dip;

import java.util.Collection;


/**
 * Menuable is a simple abstract interface which defines the functionality
 * expected of a menu, including setting and configuring options, returning a
 * list of options, and showing a list of options to a user. The menu itself is
 * configured with generic types E, and T where E is an element of a Collection
 * of options.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @param <E> the type with which the Collection is defined.
 */
public interface Menuable<E> {

	/**
	 * Setter method to define the Collection of options of type E.
	 * 
	 * @param options a Collection of options of type E
	 */
	public void setOptions(Collection<E> options);

	/**
	 * Getter method to retrieve the Collection of options of type E.
	 * 
	 * @return Collection<E> of options of type E.
	 */
	public Collection<E> getOptions();

	/**
	 * A method to add an option of type E to the collection of options.
	 * 
	 * @param option a variable of type E.
	 */
	public void addOption(E option);

	/**
	 * A method to add a collection of options of type E to an existing Collection
	 * of options of type E.
	 * 
	 * @param options a Collection of options of type E.
	 */
	public void addOptions(Collection<E> options);

	/**
	 * A method to show the list of options to the user. Typically done via printing
	 * to the terminal, but other approaches can of course be implemented by a
	 * concrete type that implements this interface.
	 * 
	 */
	public void showOptions();

}
