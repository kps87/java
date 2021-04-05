package ie.gmit.dip;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ApplicantDataFileParserTestSettersGetters {

	public static ApplicantDataFileParser defaultApplicantDataFileParser;
	public static int testCounter = 0;

	/**
	 * Some simple console output to log current test commencing
	 */
	@BeforeAll
	public static void init() {
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		System.out.println("-Commencing " + ApplicantDataFileParserTestSettersGetters.class + " tests");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}

	/**
	 * Some simple console output to log current test finishing
	 */
	@AfterEach
	public void incrementTestCounter() {
		ApplicantDataFileParserTestSettersGetters.testCounter++;
		System.out.println(
				"\t-" + ApplicantDataFileParserTestSettersGetters.class + " test [" + testCounter + "] complete.");
	}

	/**
	 * tearDown and confirm number of tests equals some number
	 */
	@AfterAll
	public static void tearDown() {
		System.out.println("-Finished " + ApplicantDataFileParserTestSettersGetters.class + " tests");
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
	 * simple test for the default state of InsuranceCostCalculator.getBaseInsurance
	 * this number must always be positive
	 */
	@Test
	void testGetApplicantDataFileDefaultState() {
		assertNull(defaultApplicantDataFileParser.getApplicantDataFile());
	}

	/**
	 * simple test for the default state of InsuranceCostCalculator.getBaseInsurance
	 * this number must always be positive
	 */
	@Test
	void testGetParsedApplicantFileDefaultState() {
		assertNull(defaultApplicantDataFileParser.getParsedApplicantFile());
	}

	/**
	 * parameterized test method to test the
	 * defaultApplicantDataFileParser.setApplicantDataFile() and
	 * defaultApplicantDataFileParser.getApplicantDataFile() methods in combination,
	 * with multiple parameters provided. A timeout of 1 second/1000 milliseconds is
	 * enforced. Whilst writing tests for setters and getters seems redundant tests
	 * are being written to ensure 100% code coverage.
	 */
	@ParameterizedTest
	@ValueSource(strings = { "File name 1.txt", "This is another file", "A third file.dat", "maybe_with_underscores",
			"another-string.out", "fileForSomeone.dat", "YellowSubmarine.txt" })
	@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
	void testSetAndGetApplicantDataFile(String string) {
		File file = new File(string);
		defaultApplicantDataFileParser.setApplicantDataFile(file);
		assertNotNull(defaultApplicantDataFileParser.getApplicantDataFile());
		assertEquals(defaultApplicantDataFileParser.getApplicantDataFile().getName(), file.getName());
		assertEquals(defaultApplicantDataFileParser.getApplicantDataFile().getName(), string);
	}

	/**
	 * this test defines a simple space-separated String[] array and tests the
	 * defaultApplicantDataFileParser.setParsedApplicantFile() setter and getter
	 */
	@Test
	void testSetAndGetParsedApplicantFile() {

		// firstName, lastName, age, number of accidents
		// the first line of this string is a 'header' line
		String[] parsedApplicantData = new String[] { "firstName surName Age numberOfAccidents", "Amman Patton 42 7",
				"Kezia Gaines 64 0", "Layla-Rose Coles 18 4", "Nayan Barclay 28 3", "Giulia Downes 69 3",
				"Isobel Sierra 66 7" };

		defaultApplicantDataFileParser.setParsedApplicantFile(parsedApplicantData);
		assertNotNull(defaultApplicantDataFileParser.getParsedApplicantFile());

		// test that each string in the parsedApplicantData String[] array
		// equals the return value of the getter
		for (int i = 0; i < parsedApplicantData.length; i++) {
			String s = defaultApplicantDataFileParser.getParsedApplicantFile()[i];
			assertTrue(s.equals(parsedApplicantData[i]));
		}
	}

}
