package ie.gmit.dip;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ConfigurableAnsiColorCodeMap is a simple class which allows the user to
 * define, control, and extend the colors used for printing text to a
 * terminal/console window. It is coupled to and highly cohesive with the
 * abstract functional interface defined by {@link DefaultAnsiColorCodeMap}, and
 * relies heavily on, and delegates most functionality to the {@link HashMap}
 * class of the java Collections framework.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @see DefaultAnsiColorCodeMap
 */
public class ConfigurableAnsiColorCodeMap {

	/**
	 * A {@link HashMap} of colors where keys are simple {link String} types and
	 * values are corresponding ANSI codes.
	 */
	private HashMap<String, String> colorMap;
	/**
	 * An {@link ArrayList} of the keys in the colorMap.
	 */
	private ArrayList<String> colorKeys;

	/**
	 * Constructor method which initializes the color map with default values via
	 * the {@link DefaultAnsiColorCodeMap}.
	 * 
	 * Method has O(c) running time locally.
	 * 
	 * @see DefaultAnsiColorCodeMap
	 */
	public ConfigurableAnsiColorCodeMap() {
		super();
		this.setColorMap(DefaultAnsiColorCodeMap.getDefaultColorMap());
	}

	/**
	 * Getter method which returns the color map as a {@link HashMap} of
	 * {@link String} types.
	 * 
	 * Method has O(n) running time due to manual deep copy of the {@link HashMap}.
	 * 
	 * @return colorMap a copy of the the {@link HashMap} of colors where keys are
	 *         {@link String} types and values are corresponding ANSI codes defined
	 *         as {@link String} types.
	 */
	public HashMap<String, String> getColorMap() {
		HashMap<String, String> copy = new HashMap<String, String>();
		for (String key : this.colorMap.keySet()) {
			copy.put(new String(key), new String(this.colorMap.get(key)));
		}
		return copy;
	}

	/**
	 * Setter method to set the color map in its entirety.
	 * 
	 * O(n) running time where n is the number of keys in the input colorMap.
	 * 
	 * @param colorMap a {@link HashMap} of color names and corresponding ANSI
	 *                 codes.
	 */
	public void setColorMap(HashMap<String, String> colorMap) {
		this.colorMap = new HashMap<String, String>();
		for (String key : colorMap.keySet()) {
			this.colorMap.put(new String(key), new String(colorMap.get(key)));
		}
	}

	/**
	 * Getter method which allows the user to get a {@link ArrayList} of color keys
	 * in a sorted format. The colors are sorted via the Collections framework's
	 * in-built sort method using the default compareTo method of {@link String}.
	 * 
	 * This method has O(n*log(n)) running time as is the case for the
	 * Collections.sort() method.
	 * 
	 * @return a sorted {@link ArrayList} of {@link String} types defining
	 *         various colors by name.
	 */
	public ArrayList<String> getColors() {
		ArrayList<String> colors = new ArrayList<String>(this.colorMap.keySet());
		Collections.sort(colors);
		return colors;
	}

	/**
	 * Method to test whether the map already contains a color.
	 * 
	 * Running time is O(1) or O(c) as is the case for the containsKey() method of
	 * {@link HashMap}.
	 * 
	 * @param color a {@link String} type defining the name of a color.
	 * @return boolean true or false depending on whether color is in
	 *         {@link HashMap}.
	 */
	public boolean containsColor(String color) {
		if (this.colorMap.containsKey(color)) {
			return true;
		}
		return false;
	}

	/**
	 * Method to get the ansi code of a given color
	 * 
	 * Running time is O(1) or O(c) as is the case for the containsKey() and get()
	 * methods of {@link HashMap}.
	 * 
	 * @param color a {@link String} type defining the color whose ANSI code is
	 *              required.
	 * @return String a {@link String} type with the ANSI code, or a null
	 *         {@link String} if absent.
	 */
	public String getAnsiCode(String color) {
		if (this.containsColor(color)) {
			return this.colorMap.get(color);
		} else {
			return null;
		}
	}

	/**
	 * Method which allows the user to add a color and its ANSI code to the map.
	 * 
	 * The contains and add methods of {@link HashMap} are all O(1) or O(c) and by
	 * extension this method has the same performance.
	 * 
	 * @param color a {@link String} type defining the color to be added.
	 * @param ansi  a {@link String} type defining the ANSI color code of the color
	 *              to be added.
	 * @return boolean true if added, false if not added.
	 */
	public boolean addColorCode(String color, String ansi) {
		if (!containsColor(color)) {
			if (!this.colorKeys.contains(color)) {
				this.colorKeys.add(new String(color));
			}
			this.colorMap.put(new String(color), new String(ansi));
			return true;
		}
		return false;
	}

}
