package runner;

import java.util.ArrayList;
import java.util.HashMap;

import printers.BenchmarkDataPrinter;
import printers.TerminalTitleWriter;
import sorters.IntegerSorter;

/**
 * Class used to couple other packages together so that useful integer sorting
 * benchmark computations can be run
 * 
 * @author ksomers
 *
 */
public class Runner {

	/**
	 * Returns a list of strings with tags for each algorithm
	 * 
	 * @return algorithms an array of strings defining algorithm names
	 */
	private static String[] getAlgorithmsToRun() {

		// write a title to track progress
		TerminalTitleWriter.writeTitle("", "-", 50, "-Runner.getAlgorithmsToRun()");

		// define the algorithms to use
		String[] algorithms = { "bubble", "insertion", "selection", "quick", "counting", "merge" };

		// print some info to screen before running
		System.out.println("-Will run the following algorithms:");
		for (String algorithm : algorithms) {
			System.out.println("\t-" + algorithm);
		}
		TerminalTitleWriter.writeLine("", "-", 50);

		// return the array of algorithm names
		return algorithms;

	}

	/**
	 * @return algorithmMaxArraySizes a HashMap of algorithm names (keys) and the
	 *         maximum size of the array for which a benchmark will be run for that
	 *         algorithm (values)
	 */
	private static HashMap<String, Integer> getAlgorithmSpecificMaxArraySizes() {

		// write a title to track progress
		TerminalTitleWriter.writeTitle("", "-", 50, "-Runner.getAlgorithmsMaxArraySizes()");

		// define the algorithms to use
		HashMap<String, Integer> algorithmMaxArraySizes = new HashMap<String, Integer>();
		algorithmMaxArraySizes.put("bubble", 60000);
		algorithmMaxArraySizes.put("insertion", 120000);
		algorithmMaxArraySizes.put("selection", 120000);
		algorithmMaxArraySizes.put("merge", 52428801);
		algorithmMaxArraySizes.put("quick", 52428801);
		algorithmMaxArraySizes.put("counting", 104857602);

		// return the array of algorithm names
		return algorithmMaxArraySizes;

	}

	/**
	 * Generates an array containing a list of numbers corresponding to the sizes of
	 * different arrays to be tested by the sorting algorithms
	 * 
	 * @param minArraySize       the minimum number of elements in an array to be
	 *                           sorted
	 * @param numberOfArraySizes the total number of array sizes to be generated
	 * @return arraySizes a list of integers increasing in multiples of 2 from
	 *         minArraySize
	 */
	private static int[] generateArraySizes(int minArraySize, int numberOfArraySizes) {

		// write a title to track progress
		TerminalTitleWriter.writeTitle("", "-", 50, "-Runner.generateArraySizes()");

		// generate arrays increasing in size in factors of two
		int[] arraySizes = new int[numberOfArraySizes];
		for (int i = 0; i < numberOfArraySizes; i++) {
			arraySizes[i] = minArraySize;
			minArraySize *= 2; // double the number of elements each time
		}

		// tell the user the number of arrays and their max and min sizes
		System.out.println("-Run each algorithm for " + arraySizes.length + " different array sizes");
		System.out.println("\t-Smallest array size = " + arraySizes[0] + " elements");
		System.out.println("\t-Largest array size = " + arraySizes[arraySizes.length - 1] + " elements");
		TerminalTitleWriter.writeLine("", "-", 50);

		// return the int array of array sizes
		return arraySizes;
	}

	/**
	 * runs integer benchmark sorting algorithms for: x different arraySizes y
	 * different samplesPerArraySize and z different algorithms
	 * 
	 * @param arraySizes          an integer array containing the sizes of each
	 *                            array of random numbers to be sorted
	 * @param samplesPerArraySize an int defining the number of statistical samples
	 *                            to run for each array size
	 * @param algorithms          a String array defining the names of each
	 *                            algorithm -- these must correspond to names
	 *                            defined in sorters.IntegerSorter()
	 * @return algorithmBenchmarkers an ArrayList of AlgorithmBenchmarker types each
	 *         of which contains data from each benchmark run
	 */
	private static ArrayList<AlgorithmBenchmarker> runIntegerSortBenchmarks(int[] arraySizes, int samplesPerArraySize,
			String[] algorithms) {

		// write a title to the screen to keep user informed
		TerminalTitleWriter.writeTitle("", "-", 75, "-Runner.runIntegerSortBenchmarks()");
		System.out.println("\t-For each array size, and each algorithm run " + samplesPerArraySize);
		System.out.println("\t-samples to generate statistics on performance");
		TerminalTitleWriter.writeLine("", "-", 75);

		// get the max size for a given algorithm
		HashMap<String, Integer> algorithmMaxArraySizes = Runner.getAlgorithmSpecificMaxArraySizes();

		// create an integerSorter instance and an ArrayList of AlgorithmBenchmarkers
		// the integerSorter.sort() method will be called to run sorts by each
		// AlgorithmBenchmarker
		IntegerSorter integerSorter = new IntegerSorter();
		ArrayList<AlgorithmBenchmarker> algorithmBenchmarkers = new ArrayList<AlgorithmBenchmarker>();

		// for each algorithm in list of algorithms
		// and each array size, run n iterations
		// each AlgorithmBenchmarker be
		for (String algorithm : algorithms) {

			// set the sort method
			integerSorter.setSortMethod(algorithm);

			for (int arraySize : arraySizes) {

				if (arraySize > algorithmMaxArraySizes.get(algorithm)) {
					// write a title to screen to inform user
					TerminalTitleWriter.writeTitle("\t", "-", 67,
							"\t-Will not run [" + algorithm + "] sort for array size of [" + arraySize + "] integers");
				} else {
					// write a title to screen to inform user
					TerminalTitleWriter.writeTitle("\t", "-", 67,
							"\t-Running [" + algorithm + "] sort for array size of [" + arraySize + "] integers");

					// create a new AlgorithmBenchmarker instance
					// which will run the sorts via calls to the IntegerSorter.sort() method
					// and add it to the algorithmBenchmarkers ArrayList to get data
					// later on
					AlgorithmBenchmarker benchmarker = new AlgorithmBenchmarker(algorithm);
					algorithmBenchmarkers.add(benchmarker);

					// set the number of iterations for this run
					// and run the benchmark
					benchmarker.runIntegerSortBenchmark(integerSorter, arraySize, samplesPerArraySize);

				}
			}
		}

		// return the array list of algorithm benchmarkers
		return algorithmBenchmarkers;

	}

	/**
	 * delegates printing responsibilities to the BenchmarkDataPrinter class and its
	 * methods.
	 * 
	 * @param arraySizes                      an integer array containing the sizes
	 *                                        of each array of random numbers to be
	 *                                        sorted
	 * @param samplesPerArraySize             an int defining the number of
	 *                                        statistical samples run for each array
	 *                                        size
	 * @param algorithms                      a String array defining the names of
	 *                                        each algorithm -- these must
	 *                                        correspond to names defined in
	 *                                        sorters.IntegerSorter()
	 * @param ArrayList<AlgorithmBenchmarker> an ArrayList of AlgorithmBenchmarker
	 *                                        types each of which contains data from
	 *                                        each benchmark run
	 */
	private static void printOutputData(String[] algorithms, int[] arraySizes, int samplesPerArraySize,
			ArrayList<AlgorithmBenchmarker> algorithmBenchmarkers) {

		// print data to output files
		String[] printProperties = { "bestRunTime", "meanRunTime", "worstRunTime", "sigmaRunTime",
				"sigmaRunTimeAsPercentOfMean" };
		for (String property : printProperties) {
			BenchmarkDataPrinter.printBenchmarkPropertyToScreen(property, algorithms, arraySizes,
					algorithmBenchmarkers);
		}
		for (String property : printProperties) {
			BenchmarkDataPrinter.printBenchmarkPropertyToFile(property, algorithms, arraySizes, algorithmBenchmarkers);
		}
		// print the iteration data results to a file
		BenchmarkDataPrinter.printIterationDateToFiles(samplesPerArraySize, algorithmBenchmarkers);
	}

	/**
	 * 
	 * main method called to run integer sort benchmarks
	 * 
	 * @param args array of command line arguments -- not used for any
	 *             logic/operation
	 */
	public static void main(String[] args) {

		// timer to count TOTAL execution time
		// not times for individual runs
		long startTime = System.nanoTime();

		// write a simple title to screen
		TerminalTitleWriter.writeTitle("", "-", 50, "-Runner.main()");

		// get the list of algorithms to run
		String[] algorithms = Runner.getAlgorithmsToRun();
		
		// define the number of samples to be run for each 
		// algorithm/array size
		int samplesPerArraySize = 10;

		// generate a list of sample sizes, n, which determine the number of elements
		// in an array to be sorted
		int[] arraySizes = Runner.generateArraySizes(100, 20);
		
		// run a batch of integer sort benchmarks
		ArrayList<AlgorithmBenchmarker> algorithmBenchmarkers;
		algorithmBenchmarkers = Runner.runIntegerSortBenchmarks(arraySizes, samplesPerArraySize, algorithms);

		// print the output data
		Runner.printOutputData(algorithms, arraySizes, samplesPerArraySize, algorithmBenchmarkers);

		// print the total run time to screen
		long runTime = System.nanoTime() - startTime;
		System.out.println("-total execution time = " + String.format("%-1.3f", runTime / 1E+09) + " seconds");

	}

}
