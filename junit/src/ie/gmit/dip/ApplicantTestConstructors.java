package ie.gmit.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicantTestConstructors {

	public static Applicant defaultApplicant;
	public static int testCounter = 0;

	/**
	 * Some simple console output to log current test commencing
	 */
	@BeforeAll
	public static void init() {
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		System.out.println("-Commencing " + ApplicantTestConstructors.class + " tests");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}

	/**
	 * Some simple console output to log current test finishing
	 */
	@AfterEach
	public void incrementTestCounter() {
		ApplicantTestConstructors.testCounter++;
		System.out.println("\t-" + ApplicantTestConstructors.class + " test [" + testCounter + "] complete.");
	}

	/**
	 * tearDown and confirm number of tests equals some number
	 */
	@AfterAll
	public static void tearDown() {
		System.out.println("-Finished " + ApplicantTestConstructors.class + " tests");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}

	/**
	 * initialize a default applicant to use for testing before all other tests are
	 * run
	 */
	@BeforeEach
	public void SetUp() {
		defaultApplicant = new Applicant();
		assertNotNull(defaultApplicant);
		assertNull(defaultApplicant.getName());
		assertEquals(defaultApplicant.getAge(), 0);
		assertEquals(defaultApplicant.getNumberOfAccidents(), 0);
	}

	/**
	 * method to test the Applicant constructor with no parameters provided as
	 * input, default values of getters associated with constructor(s) are also
	 * tested
	 */
	@Test
	public void testConstructorWithNoParameters() {
		defaultApplicant = new Applicant();
		assertNotNull(defaultApplicant);
		assertNull(defaultApplicant.getName());
		assertEquals(defaultApplicant.getAge(), 0);
		assertEquals(defaultApplicant.getNumberOfAccidents(), 0);
	}

	/**
	 * method to test an Applicant constructor with all parameters provided as input
	 * to an overloaded constructor, initialized values of variables are also tested
	 * via the corresponding getters
	 */
	@Test
	public void testConstructorWithAllParameters() {
		String name = "A.N. Applicant";
		int age = 20;
		int numberOfAccidents = 6;
		defaultApplicant = new Applicant(name, age, numberOfAccidents);
		assertNotNull(defaultApplicant);
		assertEquals(defaultApplicant.getName(), "A.N. Applicant");
		assertEquals(defaultApplicant.getAge(), 20);
		assertEquals(defaultApplicant.getNumberOfAccidents(), 6);
	}

}
