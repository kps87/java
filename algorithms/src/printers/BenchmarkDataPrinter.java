package printers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import runner.AlgorithmBenchmarker;

/**
 * Utility class for printing the results of CPU benchmark calculations to a
 * terminal and to files
 * 
 * @author ksomers
 */
public class BenchmarkDataPrinter {

	/**
	 * Constructor
	 */
	public BenchmarkDataPrinter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * given a list of AlgorithmBenchmarker objects returns the AlgorithmBenchmarker
	 * with a given name and arraySize
	 * 
	 * @param name                  the AlgorithmBenchmarker name
	 * @param arraySize             the AlgorithmBenchmarker arraySize to run for
	 *                              each array size
	 * @param algorithmBenchmarkers an ArrayList of AlgorithmBenchmarker instances
	 *                              with a given name and array size
	 * @return AlgorithmBenchmarker a single algorithm benchmarker instance
	 */
	public static AlgorithmBenchmarker getAlgorithmBenchmarker(String name, int arraySize,
			ArrayList<AlgorithmBenchmarker> algorithmBenchmarkers) {
		for (AlgorithmBenchmarker ab : algorithmBenchmarkers) {
			if (ab.getArraySize() == arraySize && ab.getName().equals(name)) {
				return ab;
			}
		}
		return new AlgorithmBenchmarker("null");
	}

	/**
	 * given a property to print, an order of printing for algorithms and array
	 * sizes, and an ArrayList of AlgorithmBenchmarker objects will print a table to
	 * the console
	 * 
	 * @param property                        the property to print, can be chosen
	 *                                        from: meanRunTime, sigmaRunTime,
	 *                                        bestRunTime, worstRunTime,
	 *                                        sigmaRunTimeAsPercentOfMean
	 * @param algorithmPrintOrder             a String array of algorithm names to
	 *                                        print
	 * @param arraySizePrintOrder             an int array of integers corresponding
	 *                                        to list sizes
	 * @param algorithmBenchmarkers an ArrayList of AlgorithmBenchmarker instances
	 *                              with a given name and array size
	 */
	public static void printBenchmarkPropertyToScreen(String property, String[] algorithmPrintOrder,
			int[] arraySizePrintOrder, ArrayList<AlgorithmBenchmarker> algorithmBenchmarkers) {

		// write a title to the screen to keep user informed
		TerminalTitleWriter.writeTitle("", "-", 129, "-Runner.printBenchmarkProperty()->" + property);

		// print the algorithmPrintOrder in order
		String line = String.format("%-20s", "arraySize");
		for (int i = 0; i < algorithmPrintOrder.length; i++) {
			line = line + String.format("%-20s", algorithmPrintOrder[i]);
		}
		System.out.println(line);

		// for each algorithm and array size get the right benchmarker and print the
		// data
		for (int arraySize : arraySizePrintOrder) {
			line = "";
			line = line + String.format("%-20s", arraySize);
			for (int i = 0; i < algorithmPrintOrder.length; i++) {
				AlgorithmBenchmarker ab = BenchmarkDataPrinter.getAlgorithmBenchmarker(algorithmPrintOrder[i],
						arraySize, algorithmBenchmarkers);
				if (ab.getProperty(property) != null) {
					line = line + String.format("%-+20.4f", ab.getProperty(property));
				} else {
					line = line + String.format("%-20s", "NaN");
				}

			}
			System.out.println(line);
		}
	}

	/**
	 * given a property to print, an order of printing for algorithms and array
	 * sizes, and an ArrayList of AlgorithmBenchmarker objects will print a table to
	 * the console
	 * 
	 * @param property                        the property to print, can be chosen
	 *                                        from: meanRunTime, sigmaRunTime,
	 *                                        bestRunTime, worstRunTime,
	 *                                        sigmaRunTimeAsPercentOfMean
	 * @param algorithmPrintOrder             a String array of algorithm names to
	 *                                        print
	 * @param arraySizePrintOrder             an int array of integers corresponding
	 *                                        to list sizes
	 * @param algorithmBenchmarkers an ArrayList of AlgorithmBenchmarker instances
	 *                              with a given name and array size
	 */
	public static void printBenchmarkPropertyToFile(String property, String[] algorithmPrintOrder,
			int[] arraySizePrintOrder, ArrayList<AlgorithmBenchmarker> algorithmBenchmarkers) {

		// write a title to the screen to keep user informed
		TerminalTitleWriter.writeTitle("", "-", 129, "-Runner.printBenchmarkPropertyToFile()->" + property);

		// get the current directory and set the output directory
		File currentDirectory = new File(new File("").getAbsolutePath());
		File outputDir = new File(currentDirectory + "/summary_data");
		System.out.println("-will print output to dir: " + outputDir);
		outputDir.mkdir();

		// create the output file name
		String fileName = String.format("%-1s/%-1ss.dat", outputDir.toString(), property);
		File outputFile = new File(fileName);
		System.out.println("\t-printing " + property + " data to " + outputFile);

		// print the algorithmPrintOrder in order
		String output = String.format("%-20s", "arraySize");
		for (int i = 0; i < algorithmPrintOrder.length; i++) {
			output = output + String.format("%-20s", algorithmPrintOrder[i]);
		}
		output = output + "\n";

		// for each algorithm and array size get the right benchmarker and print the
		// data
		for (int arraySize : arraySizePrintOrder) {
			output = output + String.format("%-20s", arraySize);
			for (int i = 0; i < algorithmPrintOrder.length; i++) {
				AlgorithmBenchmarker ab = BenchmarkDataPrinter.getAlgorithmBenchmarker(algorithmPrintOrder[i],
						arraySize, algorithmBenchmarkers);
				if (ab.getProperty(property) != null) {
					output = output + String.format("%-+20.4f", ab.getProperty(property));
				} else {
					output = output + String.format("%-20s", "NaN");
				}

			}
			output = output + "\n";
		}

		// write the output
		try {
			FileWriter fw = new FileWriter(outputFile);
			fw.write(output);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void printIterationDateToFiles(int samplesPerArraySize,
			ArrayList<AlgorithmBenchmarker> algorithmBenchmarkers) {

		TerminalTitleWriter.writeTitle("", "-", 50, "-Runner.printIterationDateToFiles()");

		// get the current directory and set the output directory
		File currentDirectory = new File(new File("").getAbsolutePath());
		File outputDir = new File(currentDirectory + "/iteration_data");
		System.out.println("-will print output to dir: " + outputDir);
		outputDir.mkdir();

		// get the list of algorithm names
		TreeSet<String> algorithms = new TreeSet<String>();
		for (AlgorithmBenchmarker ab : algorithmBenchmarkers) {
			algorithms.add(ab.getName());
		}

		// foreach algorithm get the iteration data,
		// then add it to a string and print to a file
		for (String algorithm : algorithms) {

			// for each arraysize get the iteration data and put it in a hashmap for later
			// printing
			HashMap<Integer, ArrayList<Long>> algorithmTimes = new HashMap<Integer, ArrayList<Long>>();
			SortedSet<Integer> arraySizes = new TreeSet<Integer>();
			for (AlgorithmBenchmarker ab : algorithmBenchmarkers) {
				if (ab.getName().equals(algorithm)) {
					algorithmTimes.put(ab.getArraySize(), ab.getIterationData());
					arraySizes.add(ab.getArraySize());
				}
			}

			// create the output file name
			String fileName = String.format("%-1s/%-1s_iteration_times.dat", outputDir.toString(), algorithm);
			File outputFile = new File(fileName);
			System.out.println("\t-printing " + algorithm + " sort iteration data times to: " + outputFile);

			// create a string to build the output
			String output = String.format("%-30s", "iteration[v]/size[->]");
			for (Integer arraySize : arraySizes) {
				output = output + String.format("size%-20s", arraySize);
			}
			output = output + "\n";

			// loop over columns then rows to get the output data
			for (int sampleIndex = 0; sampleIndex < samplesPerArraySize; sampleIndex++) {
				int counter = 0;
				output = output + String.format("%-30s", sampleIndex + 1);
				for (Integer arraySize : arraySizes) {
					output = output + String.format("%-24s", algorithmTimes.get(arraySize).get(sampleIndex));
					counter++;
					if (counter == arraySizes.size()) {
						output = output + "\n";
					}

				}
			}

			// write the output
			try {
				FileWriter fw = new FileWriter(outputFile);
				fw.write(output);
				fw.flush();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
