package sorters;

/**
 * Concrete class which implements the AbstractSorter class for integer types
 * 
 * @author ksomers
 *
 */
public class IntegerSorter extends AbstractSorter<int[]> {

	private String sortMethod;

	/**
	 * Constructor
	 */
	public IntegerSorter() {
		super();
	}

	/**
	 * @return sortMethod a String type denoting the sort method currently assigned
	 *         when the IntegerSorter.sort() method is called
	 */
	public String getSortMethod() {
		return sortMethod;
	}

	/**
	 * @param sortMethod a String type denoting the sort method currently assigned
	 *                   when the IntegerSorter.sort() method is called
	 */
	public void setSortMethod(String sortMethod) {
		this.sortMethod = sortMethod;
	}

	/**
	 * Overrides the abstract method defined in AbstractSorter Delegates
	 * responsibility for the sort to a specific method as defined by the sortMethod
	 * variable
	 * 
	 * @return runTime the benchmark CPU time as a long
	 */
	@Override
	public long sort(int[] input) {

		sortMethod = sortMethod.toLowerCase();
		long runTime = -1;

		// project criterion 1: choose a simple comparison based sort from bubble,
		// selection or insertion sort.
		// bubble sort chosen
		if (sortMethod.equals("bubble")) {
			long startTime = System.nanoTime();
			IntegerSorter.iterativeBubbleSort(input);
			runTime = System.nanoTime() - startTime;
		}

		// project criterion 2: choose an efficient comparison based sort from merge
		// sort, quick sort, or heap sort
		// quicksort chosen
		else if (sortMethod.equals("quick")) {
			long startTime = System.nanoTime();
			IntegerSorter.quickSort(input, 0, input.length - 1);
			runTime = System.nanoTime() - startTime;
		}

		// project criterion 3: choose a non comparison based sort chosen from counting
		// sort, bucket sort or radix sort
		// counting sort chosen
		else if (sortMethod.equals("counting")) {
			long startTime = System.nanoTime();
			IntegerSorter.countingSort(input);
			runTime = System.nanoTime() - startTime;
		}

		// project criterion 4: any other sorting algorithm of my choice
		// merge sort chosen as it is an efficient comparison based sort
		else if (sortMethod.equals("merge")) {
			long startTime = System.nanoTime();
			IntegerSorter.mergeSort(input);
			runTime = System.nanoTime() - startTime;
		}

		// project criterion 5: any other sorting algorithm of my choice
		// insertion sort chosen as example of simple comparison based sorting algorithm
		else if (sortMethod.equals("insertion")) {
			long startTime = System.nanoTime();
			IntegerSorter.insertionSort(input);
			runTime = System.nanoTime() - startTime;
		}

		// extras: an implementation of selection sort - a simple comparison based sort
		else if (sortMethod.equals("selection")) {
			long startTime = System.nanoTime();
			IntegerSorter.selectionSort(input);
			runTime = System.nanoTime() - startTime;
		}

		// warn user that they have not chosen a valid sort from list of sorting methods
		// implemented
		else {
			System.out.println("\t-No valid sorting method chosen.");
			System.exit(0);
		}

		return runTime;

	}

	/**
	 * 
	 * Implements a simple iterative bubble sort algorithm for integer arrays has
	 * been adopted from the lecture notes of Patrick Mannion with modifications for
	 * best-case scenario performance where there zero inversions on the first
	 * traversal of the array.
	 * 
	 * @param array an unsorted array of integer types
	 */
	public static void iterativeBubbleSort(int[] array) {

		// include a counter
		// for number of inversions
		int numberInversion = 0;

		// outer array starts at last index of array
		// and decrements while it is greater than 0
		for (int i = array.length - 1; i > 0; i--) {

			// inner loop starts at first index of array
			// and increases while it is less than i
			// it will terminate therefore when i = 1, and j = 0
			// if the value of array[j] > than next element in array
			// push the j value into a temp array, then swap element j+1
			// which is smaller than element j, with element j, which is greater
			for (int j = 0; j < i; j++) {
				if (array[j] > array[j + 1]) {
					numberInversion++;
					int temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}

			// if the number of inversions
			// is zero the array is already sorted
			// and so the outer loop should break
			// this will give best case O(n)
			// running time for a pre-sorted array
			if (numberInversion == 0) {
				break;
			}
		}
	}

	/**
	 * Implements insertion sort Adapted from
	 * 
	 * @param array an unsorted array of integer types
	 */
	public static void insertionSort(int[] array) {

		// start looping at second element of the array
		// and increment index until we get to the last element
		for (int i = 1; i < array.length; i++) {

			// assign the key as the current element
			// and the j counter as the previous element
			int key = array[i];
			int j = i - 1;

			// while the counter j is greater than or equal to 0,
			// or while the element at j is greater than the key
			// decrement the counter j, and swap element by moving it to the right
			// if they are greater than the key
			while (j >= 0 && array[j] > key) {
				array[j + 1] = array[j];
				j = j - 1;
			}

			// insert the key at its new index
			array[j + 1] = key;
		}

	}

	/**
	 * Implements selection sort Adapted from
	 * https://www.javatpoint.com/selection-sort-in-java
	 * 
	 * @param array an unsorted array of integer types
	 */
	public static void selectionSort(int[] array) {

		// start looping at beginning of array,
		// and increment index until we get to the last element
		for (int i = 0; i < array.length; i++) {
			int min_index = i;

			// start looping at the element after the outer loop
			// and end the loop at the last element
			// find the minimum value in the array
			for (int j = i + 1; j < array.length; j++) {
				if (array[j] < array[min_index]) {
					min_index = j;
				}
			}

			// swap the element i, with the minimum element
			int temp = array[i];
			array[i] = array[min_index];
			array[min_index] = temp;
		}
	}

	/**
	 * Implements counting sort Adapted from
	 * https://www.growingwiththeweb.com/2014/05/counting-sort.html and
	 * https://www.javainuse.com/java/countingsort
	 * 
	 * @param array the array of integers to be sorted
	 */
	public static void countingSort(int[] array) {

		// find the min and max values in the array
		int min = array[0], max = array[0];
		for (int i = 0; i < array.length; i++) {
			if (array[i] < min) {
				min = array[i];
			}
			if (array[i] > max) {
				max = array[i];
			}
		}

		// set the range and create a
		// new integer array with n = range elements
		int range = max - min + 1;
		int[] count = new int[range];

		// count the number of occurrences of each
		// key, correcting the index for the min
		// key so indices start at 0
		for (int i = 0; i < array.length; i++) {
			int key = array[i];
			count[key - min]++;
		}

		// count the cumulative sum of elements
		// preceding this element/key - defines
		// the starting index for each value
		for (int i = 1; i < range; i++) {
			count[i] += count[i - 1];
		}

		// populate the original array with each number accounting for its frequency
		int j = 0;
		for (int i = 0; i < range; i++) {
			while (j < count[i]) {
				array[j++] = i + min;
			}
		}
	}

	/**
	 * Implements merge sort, Adapted from
	 * https://www.educative.io/edpresso/how-to-implement-a-merge-sort-in-java
	 * 
	 * @param array the array of integers to be sorted
	 */
	public static void mergeSort(int[] array) {

		// base case: arrays of length 0 or 1 are sorted
		if (array.length < 2) {
			return;
		}

		// calculate the median index
		// and populate two new sub arrays
		int mid = array.length / 2;
		int[] left = new int[mid];
		int[] right = new int[array.length - mid];
		for (int i = 0; i < array.length; i++) {
			if (i < mid) {
				left[i] = array[i];
			} else {
				right[i - mid] = array[i];
			}
		}

		// recursively sort the sub arrays
		IntegerSorter.mergeSort(left);
		IntegerSorter.mergeSort(right);

		// indexes for iterated over left, right and merged array
		int i = 0;
		int l = 0;
		int r = 0;

		// while the l and r indices are less
		// than their respective array lengths
		// find the smaller element of the two and insert it into
		// the final array in the correct position
		while (l < left.length && r < right.length) {
			if (left[l] < right[r]) {
				array[i] = left[l];
				i = i + 1;
				l = l + 1;
			} else {
				array[i] = right[r];
				i = i + 1;
				r = r + 1;
			}
		}

		// now add the remaining left elements
		while (l < left.length) {
			array[i] = left[l];
			i = i + 1;
			l = l + 1;
		}

		// now add the remaining right elements
		while (r < right.length) {
			array[i] = right[r];
			i = i + 1;
			r = r + 1;
		}
	}

	/**
	 * Implements quick sort, adapted from:
	 * https://www.vogella.com/tutorials/JavaAlgorithmsQuicksort/article.html
	 * https://www.java2novice.com/java-sorting-algorithms/quick-sort/
	 * http://www.algolist.net/Algorithms/Sorting/Quicksort
	 * 
	 * @param array       the array of numbers which is being recursively sorted
	 * @param lower_index an integer defining the left-most starting index
	 * @param upper_index an integer defining the right-most ending index
	 */
	public static void quickSort(int[] array, int lower_index, int upper_index) {

		// base case: lower index >= higher index
		// i.e. search range < 2
		if (lower_index >= upper_index) {
			return;
		}

		// get the start and end indices and set the pivot value
		// based on the median element in the array
		int i = lower_index;
		int j = upper_index;
		int pivot = array[(lower_index + upper_index) / 2];

		// iterate while the index i <= index j
		while (i <= j) {

			// find an element in left side
			// whose value is greater than or equal to the pivot
			// if no larger values will stop when
			// pivot reached
			while (array[i] < pivot) {
				i++;
			}

			// find an element in right hand side
			// whose value is less than or equal to the pivot
			// if no smaller values will stop when
			// pivot reached
			while (array[j] > pivot) {
				j--;
			}

			// if the index i
			// is less than or equal to
			// index j swap the two elements
			// and increment i and decrement j
			if (i <= j) {
				int temp = array[i];
				array[i] = array[j];
				array[j] = temp;
				i++;
				j--;
			}
		}

		// recursively call quick sort on
		// array[lower_index...j]
		// and array[i...upper_index]
		// sort the smaller of the two sublists first
		IntegerSorter.quickSort(array, lower_index, j);
		IntegerSorter.quickSort(array, i, upper_index);

	}

}
