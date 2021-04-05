package runner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import random.ArrayFactory;
import sorters.AbstractSorter;

/**
 * A class which stores the result of benchmarking algorithms. Can store the
 * results of more than one benchmark run and calculate various statistics
 * (mean, sigma) for reporting and analysis
 * 
 * @author ksomers
 *
 */
public class AlgorithmBenchmarker {

	private String name;
	private int arraySize;
	private int numberOfIterations = 10;
	private long meanRunTime;
	private long bestRunTime;
	private long worstRunTime;
	private long sigmaRunTime;
	private boolean printScreenUpdate = true;
	private boolean calculatedMean = false;
	private boolean calculatedSigma = false;
	private ArrayList<Long> iterationData = new ArrayList<Long>();
	private HashMap<String, Double> dataDictionary = new HashMap<String, Double>();

	/**
	 * Constructor
	 */
	public AlgorithmBenchmarker(String name) {
		this.name = name;
	}

	/**
	 * @return name a String type used to define some arbitrary name or tag
	 *         associated with the instance of AlgorithmBenchmarker
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name a String type used to define some arbitrary name or tag
	 *             associated with the instance of AlgorithmBenchmarker
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return arraySize an int type defining the size of array or the benchmark
	 */
	public int getArraySize() {
		return arraySize;
	}

	/**
	 * @return numberOfIterations an int type defining the number of iterations for
	 *         the benchmark
	 */
	public int getNumberOfIterations() {
		return numberOfIterations;
	}

	/**
	 * @param numberOfIterations an int type defining the number of iterations for
	 *                           the benchmark
	 */
	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}

	/**
	 * @return calculatedMean a long type defining the average runTime for the
	 *         benchmark
	 */
	public long getMeanRunTime() {
		if (this.calculatedMean == false) {
			this.calculateMeanRunTime();
			this.calculatedMean = true;
		}
		return meanRunTime;
	}

	/**
	 * @return bestRunTime a long type defining the bestRunTime for the benchmark
	 */
	public long getBestRunTime() {
		this.bestRunTime = Collections.min(this.iterationData);
		return this.bestRunTime;
	}

	/**
	 * @return worstRunTime a long type defining the worstRunTime for the benchmark
	 */
	public long getWorstRunTime() {
		this.worstRunTime = Collections.max(this.iterationData);
		return this.worstRunTime;
	}

	/**
	 * @param bool a boolean type which defines whether certain information is
	 *             printed to the screen during the benchmark
	 */
	public void printScreenUpdates(boolean bool) {
		this.printScreenUpdate = bool;
	}

	/**
	 * calculates the meanRunTime for the benchmark
	 */
	public void calculateMeanRunTime() {
		this.meanRunTime = 0;
		for (long time : iterationData) {
			this.meanRunTime += time;
		}
		this.meanRunTime = this.meanRunTime / this.numberOfIterations;
	}

	/**
	 * @return sigmaRunTime a long type defining the 1 sigma standard deviation of
	 *         the iterationData
	 */
	public long getStandardDeviation() {
		if (this.calculatedSigma == false) {
			this.calculateStandardDeviation();
		}
		return sigmaRunTime;
	}

	/**
	 * calculates the standard deviation of the run times by calls to either a
	 * sample or population standard deviation method
	 */
	public void calculateStandardDeviation() {

		if (this.calculatedMean == false) {
			this.calculateMeanRunTime();
			this.calculatedMean = true;
		}

		if (this.numberOfIterations <= 100) {
			this.sampleStandardDeviation();
		} else {
			this.populationStandardDeviation();
		}

		this.calculatedSigma = true;
	}

	/**
	 * calculates the sample standard deviation of the run times
	 */
	public void sampleStandardDeviation() {

		long sumSquared = 0;
		if (this.iterationData.size() > 1) {
			for (long time : iterationData) {
				sumSquared = sumSquared + (time - this.getMeanRunTime()) * (time - this.getMeanRunTime());
			}
			this.sigmaRunTime = (long) Math.sqrt(sumSquared / (this.numberOfIterations - 1));
		}

	}

	/**
	 * calculates the population standard deviation of the run times
	 */
	public void populationStandardDeviation() {
		long sumSquared = 0;
		for (long time : iterationData) {
			sumSquared = sumSquared + (time - this.getMeanRunTime()) * (time - this.getMeanRunTime());
		}
		this.sigmaRunTime = (long) Math.sqrt(sumSquared / (this.numberOfIterations));
	}

	/**
	 * @return iterationData an ArrayList<Long> containing the benchmark times for
	 *         each iteration
	 */
	public ArrayList<Long> getIterationData() {
		return this.iterationData;
	}

	/**
	 * sets a HashMap as a data dictionary so that various parameters can be
	 * retrieved via a keyword call
	 */
	private void setDataDictionary() {
		this.dataDictionary.put("meanRunTime", this.getMeanRunTime() / 1E+06);
		this.dataDictionary.put("sigmaRunTime", this.getStandardDeviation() / 1E+06);
		this.dataDictionary.put("bestRunTime", this.getBestRunTime() / 1E+06);
		this.dataDictionary.put("worstRunTime", this.getWorstRunTime() / 1E+06);
		this.dataDictionary.put("sigmaRunTimeAsPercentOfMean",
				100.0d * this.getStandardDeviation() / this.getMeanRunTime());
	}

	/**
	 * @param property defines a string to be used to get data from the
	 *                 dataDictionary
	 */
	public Double getProperty(String property) {
		return this.dataDictionary.get(property);
	}

	/**
	 * Run numberOfIterations benchmarks for an array of arraySize elements A new
	 * random array of elements is generated for every iteration
	 * 
	 * @param sorter             a polymorphic input -- any type which extends the
	 *                           AbstractSorter base class and which is configured
	 *                           to sort int types
	 * @param arraySize          an int type defining the size of the array to be
	 *                           generate via the ArrayFactor.generateIntegers
	 *                           method
	 * @param numberOfIterations an int type defining the number of times to run the
	 *                           benchmark
	 */
	public void runIntegerSortBenchmark(AbstractSorter<int[]> sorter, int arraySize, int numberOfIterations) {

		// set the array size and samples per array size
		this.arraySize = arraySize;
		this.numberOfIterations = numberOfIterations;

		// benchmark the sorting algorithm numberOfIterations times
		for (int i = 0; i < this.numberOfIterations; i++) {

			// generate an array of random numbers of
			// a given size before each sorting operation
			int[] array = ArrayFactory.generateIntegers(arraySize);

			// run the sort method
			long runTime = sorter.sort(array);
			this.iterationData.add(runTime);

			if (this.confirmIsSorted(array) == false) {
				System.out.println("Array was not sorted! Error in algorithm!");
				System.exit(0);
			}
		}

		// if screen printing is turned on print some data for inspection
		if (this.printScreenUpdate) {
			System.out.println("\t-mean  run time (nanoseconds)                     = " + this.getMeanRunTime());
			System.out.println("\t-best  run time (nanoseconds)                     = " + this.getBestRunTime());
			System.out.println("\t-worst run time (nanoseconds)                     = " + this.getWorstRunTime());
			System.out.println(
					"\t-variation in run time [+- 2.sigma] (nanoseconds) = " + (2 * this.getStandardDeviation()));
			System.out.println("\t-variation in run time [+- 2.sigma] (% of mean)   = "
					+ (100 * 2 * this.sigmaRunTime / this.meanRunTime) + "%");
		}

		// set the data dictionary of outputs that can be access externally
		this.setDataDictionary();

	}

	/**
	 * Takes an array as input and verifies whether it is sorted or not based on a
	 * highest watermark algorithm
	 * 
	 * @param array
	 * @return a boolean indicating whether the array is sorted or not
	 */
	public boolean confirmIsSorted(int[] array) {
		int current_min = array[0];
		for (int i = 0; i < array.length - 1; i++) {
			if (array[i] < current_min) {
				return false;
			}
			current_min = array[i];
		}
		return true;
	}

}
