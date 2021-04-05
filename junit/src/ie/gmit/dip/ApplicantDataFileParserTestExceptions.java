package ie.gmit.dip;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicantDataFileParserTestExceptions {

	public static ApplicantDataFileParser defaultApplicantDataFileParser;
	public static int testCounter = 0;

	/**
	 * Some simple console output to log current test commencing
	 */
	@BeforeAll
	public static void init() {
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		System.out.println("-Commencing " + ApplicantDataFileParserTestExceptions.class + " tests");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}

	/**
	 * Some simple console output to log current test finishing
	 */
	@AfterEach
	public void incrementTestCounter() {
		ApplicantDataFileParserTestExceptions.testCounter++;
		System.out
				.println("\t-" + ApplicantDataFileParserTestExceptions.class + " test [" + testCounter + "] complete.");
	}

	/**
	 * tearDown and confirm number of tests equals some number
	 */
	@AfterAll
	public static void tearDown() {
		System.out.println("-Finished " + ApplicantDataFileParserTestExceptions.class + " tests");
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
	 * tests the ApplicantDataFileParser.parseInputFile() method throws a
	 * FileNotFoundException if initialized with a filename that does not exist
	 */
	@Test
	void testParseInputFileExceptions() {

		File file = new File("dummy file name will not exist.txt");

		// catch a file not found error
		FileNotFoundException exception = assertThrows(FileNotFoundException.class,
				() -> defaultApplicantDataFileParser.parseInputFile(file));
		assertNotNull(exception.getMessage());
		assertEquals(exception.getMessage(),
				"\t-[error] ApplicantDataFileParser.parseInputFile() could not parse file: [" + file + "]");
		System.out.println("\t-" + exception.getClass().getCanonicalName() + " " + exception.getMessage());
	}

	/**
	 * tests the ApplicantDataFileParser.parseInputFile() method throws a
	 * FileNotFoundException if initialized with a filename that does not exist
	 */
	@Test
	void testFileToStringArray() {

		File file = new File("dummy file name will not exist.txt");

		// catch a file not found error
		FileNotFoundException exception = assertThrows(FileNotFoundException.class,
				() -> defaultApplicantDataFileParser.fileToStringArray(file));
		assertNotNull(exception.getMessage());
		assertEquals(exception.getMessage(),
				"\t-[error] ApplicantDataFileParser.fileToStringArray() could not parse file: [" + file + "]");
		System.out.println("\t-" + exception.getClass().getCanonicalName() + " " + exception.getMessage());
	}

}
