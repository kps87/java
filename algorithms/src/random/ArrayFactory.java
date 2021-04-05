package random;

/**
 * A simple class to generate arrays of random integers via static calls
 * @author ksomers
 *
 */
public class ArrayFactory {

	private static final int maxInt = 100000;

	/**
	 * Takes as input an integer, n, defining the total number of random integers to
	 * generate, and an int max, defining the upper limit of the random integers
	 * being generated. Generation of the initial random number is delegated to the
	 * Math.random() method
	 * Adapted from project specification as provided by Dominic Carr
	 * 
	 * @param size defines total number of integers to be generated
	 */
	public static int[] generateIntegers(int size) {
		int[] random = new int[size];
		for (int i = 0; i < size; i++) {
			random[i] = (int) (Math.random() * maxInt);
		}
		return random;
	}

}
