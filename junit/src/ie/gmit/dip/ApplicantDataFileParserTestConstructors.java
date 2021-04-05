package ie.gmit.dip;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicantDataFileParserTestConstructors {

	public static ApplicantDataFileParser defaultApplicantDataFileParser;
	public static int testCounter = 0;

	/**
	 * Some simple console output to log current test commencing
	 */
	@BeforeAll
    public static void init(){
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("-Commencing " + ApplicantDataFileParserTestConstructors.class + " tests");
        System.out.println("--------------------------------------------------------------------------------------------");
    }
	
	/**
	 * Some simple console output to log current test finishing
	 */
	@AfterEach
	public void incrementTestCounter() {
		ApplicantDataFileParserTestConstructors.testCounter++;
		System.out.println("\t-" + ApplicantDataFileParserTestConstructors.class + " test [" + testCounter + "] complete.");
	}
	
	/**
	 * tearDown and confirm number of tests equals some number
	 */
	@AfterAll
	public static void tearDown() {
        System.out.println("-Finished " + ApplicantDataFileParserTestConstructors.class + " tests");
        System.out.println("--------------------------------------------------------------------------------------------");
	}
	
	/**
	 * initialize a default ApplicantDataFileParser to use for testing 
	 * before every other tests are run
	 */
	@BeforeEach
	public void SetUp() {
		defaultApplicantDataFileParser = new ApplicantDataFileParser();
		assertNotNull(defaultApplicantDataFileParser);
	}

	/**
	 * method to test the ApplicantDataFileParser constructor with no parameters
	 * provided as input
	 */
	@Test
	public void testConstructorWithNoParameters() {
		defaultApplicantDataFileParser = new ApplicantDataFileParser();
		assertNotNull(defaultApplicantDataFileParser);
	}
	
	/**
	 * method to test an ApplicantDataFileParser constructor with all parameters 
	 * provided as input to an overloaded constructor, 
	 * initialized values of variables are also tested
	 * via the corresponding getters
	 */	
	@Test
	public void testConstructorWithAllParameters() {
		String name = "dummyFile.txt";
		File file = new File(name);
		defaultApplicantDataFileParser = new ApplicantDataFileParser(file);
		assertNotNull(defaultApplicantDataFileParser);
		assertEquals(defaultApplicantDataFileParser.getApplicantDataFile().getName(), file.getName());
		assertEquals(defaultApplicantDataFileParser.getApplicantDataFile().getName(), name);
	}


}
