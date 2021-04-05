package ie.gmit.dip;

import java.util.HashMap;

/**
 * DefaultAnsiColorCodeMap is a functional interface with a single method,
 * getDefaultColorMap, which returns a {@link HashMap} where the keys are a
 * {@link String} which define, in legible human terms, a unique color, with the
 * corresponding value being the ANSI code in the form of a {@link String}.
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 *
 */
public abstract interface DefaultAnsiColorCodeMap {

	/**
	 * getDefaultColorMap is the sole method of this functional interface, which
	 * returns a {@link HashMap} of colors and their ANSI codes as {@link String}
	 * types.
	 * 
	 * With respect to the {@link HashMap} construction, the method has O(n) running
	 * time where n = sum(c) where c is a constant for each individual put() call in
	 * {@link HashMap}.
	 * 
	 * A copy of the {@link HashMap} is ultimately constructed with O(n) running
	 * time, and this is returned.
	 * 
	 * @return colorMap a copy of a {@link HashMap} where the keys are a
	 *         {@link String} which define the name of a unique color, and the value
	 *         is the corresponding ANSI code, also defined as a {@link String}.
	 */
	public static HashMap<String, String> getDefaultColorMap() {
		HashMap<String, String> colorMap = new HashMap<String, String>();
		colorMap.put("reset", "\033[0m");
		colorMap.put("black", "\033[0;30m");
		colorMap.put("red", "\033[0;31m");
		colorMap.put("green", "\033[0;32m");
		colorMap.put("yellow", "\033[0;33m");
		colorMap.put("blue", "\033[0;34m");
		colorMap.put("purple", "\033[0;35m");
		colorMap.put("cyan", "\033[0;36m");
		colorMap.put("white", "\033[0;37m");
		colorMap.put("black_bold", "\033[1;30m");
		colorMap.put("red_bold", "\033[1;31m");
		colorMap.put("green_bold", "\033[1;32m");
		colorMap.put("yellow_bold", "\033[1;33m");
		colorMap.put("blue_bold", "\033[1;34m");
		colorMap.put("purple_bold", "\033[1;35m");
		colorMap.put("cyan_bold", "\033[1;36m");
		colorMap.put("white_bold", "\033[1;37m");
		colorMap.put("black_underlined", "\033[4;30m");
		colorMap.put("red_underlined", "\033[4;31m");
		colorMap.put("green_underlined", "\033[4;32m");
		colorMap.put("yellow_underlined", "\033[4;33m");
		colorMap.put("blue_underlined", "\033[4;34m");
		colorMap.put("purple_underlined", "\033[4;35m");
		colorMap.put("cyan_underlined", "\033[4;36m");
		colorMap.put("white_underlined", "\033[4;37m");
		colorMap.put("black_background", "\033[40m");
		colorMap.put("red_background", "\033[41m");
		colorMap.put("green_background", "\033[42m");
		colorMap.put("yellow_background", "\033[43m");
		colorMap.put("blue_background", "\033[44m");
		colorMap.put("purple_background", "\033[45m");
		colorMap.put("cyan_background", "\033[46m");
		colorMap.put("white_background", "\033[47m");
		colorMap.put("black_bright", "\033[0;90m");
		colorMap.put("red_bright", "\033[0;91m");
		colorMap.put("green_bright", "\033[0;92m");
		colorMap.put("yellow_bright", "\033[0;93m");
		colorMap.put("blue_bright", "\033[0;94m");
		colorMap.put("purple_bright", "\033[0;95m");
		colorMap.put("cyan_bright", "\033[0;96m");
		colorMap.put("white_bright", "\033[0;97m");
		colorMap.put("black_bold_bright", "\033[1;90m");
		colorMap.put("red_bold_bright", "\033[1;91m");
		colorMap.put("green_bold_bright", "\033[1;92m");
		colorMap.put("yellow_bold_bright", "\033[1;93m");
		colorMap.put("blue_bold_bright", "\033[1;94m");
		colorMap.put("purple_bold_bright", "\033[1;95m");
		colorMap.put("cyan_bold_bright", "\033[1;96m");
		colorMap.put("white_bold_bright", "\033[1;97m");
		colorMap.put("black_background_bright", "\033[0;100m");
		colorMap.put("red_background_bright", "\033[0;101m");
		colorMap.put("green_background_bright", "\033[0;102m");
		colorMap.put("yellow_background_bright", "\033[0;103m");
		colorMap.put("blue_background_bright", "\033[0;104m");
		colorMap.put("purple_background_bright", "\033[0;105m");
		colorMap.put("cyan_background_bright", "\033[0;106m");
		colorMap.put("white_background_bright", "\033[0;107m");

		HashMap<String, String> copy = new HashMap<String, String>();
		for (String key : colorMap.keySet()) {
			copy.put(new String(key), new String(colorMap.get(key)));
		}
		return copy;
	}

}
