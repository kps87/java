package ie.gmit.dip;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicantDataFileParserTestFileParsers {

	public static ApplicantDataFileParser defaultApplicantDataFileParser;
	public static int testCounter = 0;

	/**
	 * Some simple console output to log current test commencing
	 */
	@BeforeAll
	public static void init() {
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		System.out.println("-Commencing " + ApplicantDataFileParser.class + " tests");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}

	/**
	 * Some simple console output to log current test finishing
	 */
	@AfterEach
	public void incrementTestCounter() {
		ApplicantDataFileParserTestFileParsers.testCounter++;
		System.out.println("\t-" + ApplicantDataFileParser.class + " test [" + testCounter + "] complete.");
	}

	/**
	 * tearDown and confirm number of tests equals some number
	 */
	@AfterAll
	public static void tearDown() {
		System.out.println("-Finished " + ApplicantDataFileParser.class + " tests");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}

	/**
	 * initialize a default ApplicantDataFileParser to use for testing before every
	 * other tests are run
	 */
	@BeforeEach
	public void SetUp() {
		defaultApplicantDataFileParser = new ApplicantDataFileParser();
		assertNotNull(defaultApplicantDataFileParser);
	}

	/**
	 * this method is similar to testFileToStringArray() which tests a parser which
	 * when given a file name the ApplicantDataFileParser.parseInputFile() simply
	 * delegates most of the work to an encapsulated fileToStringArray() method the
	 * ApplicantDataFileParser.parseInputFile() method returns a boolean
	 */
	@Test
	public void testParseInputFile() {

		// this file should be located in the root project directory
		// in the same directory as the /bin and /src files
		File testFile = new File("ApplicantDataFileParserTestInput.txt");
		try {
			boolean wasParsed = defaultApplicantDataFileParser.parseInputFile(testFile);
			assertTrue(wasParsed);
		} catch (FileNotFoundException exception) {
			System.out
					.println("-[FileNotFoundException] could not parse file to String Array " + exception.getMessage());
		}

	}

	/**
	 * this method tests a parser which when given a file name attempts to parse it
	 * line by line into a String[] array the
	 * defaultApplicantDataFileParser.fileToStringArray() method throws a
	 * FileNotFoundException if the input file cannot be found on file system
	 */
	@Test
	public void testFileToStringArray() {

		// this file should be located in the root project directory
		// in the same directory as the /bin and /src files
		File testFile = new File("ApplicantDataFileParserTestInput.txt");
		try {
			String[] stringArray = defaultApplicantDataFileParser.fileToStringArray(testFile);
			assertNotNull(stringArray);
		} catch (FileNotFoundException exception) {
			System.out
					.println("-[FileNotFoundException] could not parse file to String Array " + exception.getMessage());
		}

	}

}
