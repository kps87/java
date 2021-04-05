package ie.gmit.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test class which test the default states of variables which have not been
 * altered via setters
 */
class ApplicantTestDefaultStates {

	public static Applicant defaultApplicant;
	public static int testCounter = 0;

	/**
	 * Some simple console output to log current test commencing
	 */
	@BeforeAll
	public static void init() {
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		System.out.println("-Commencing " + ApplicantTestDefaultStates.class + " tests");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}

	/**
	 * Some simple console output to log current test finishing
	 */
	@AfterEach
	public void incrementTestCounter() {
		ApplicantTestDefaultStates.testCounter++;
		System.out.println("\t-" + ApplicantTestDefaultStates.class + " test [" + testCounter + "] complete.");
	}

	/**
	 * tearDown and confirm number of tests equals some number
	 */
	@AfterAll
	public static void tearDown() {
		System.out.println("-Finished " + ApplicantTestDefaultStates.class + " tests");
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
	}

	/**
	 * simple test for the default state of Applicant.name
	 */
	@Test
	void testGetNameDefaultState() {
		assertNull(defaultApplicant.getName());
	}

	/**
	 * simple test for the default state of Applicant.age
	 */
	@Test
	void testGetAgeDefaultState() {
		assertEquals(defaultApplicant.getAge(), 0);
	}

	/**
	 * simple test for the default state of Applicant.numberOfAccidents
	 */
	@Test
	void testGetNumberOfAccidentsDefaultState() {
		assertEquals(defaultApplicant.getNumberOfAccidents(), 0);
	}

	/**
	 * simple test for the default state of Applicant.isInsurable
	 */
	@Test
	void testIsInsurableDefaultState() {
		assertFalse(defaultApplicant.isInsurable());
	}

	/**
	 * simple test for the default state of Applicant.baseInsurance
	 */
	@Test
	void testGetBaseInsuranceDefaultState() {
		assertEquals(defaultApplicant.getBaseInsurance(), 0);
	}

	/**
	 * simple test for the default state of Applicant.ageSurcharge
	 */
	@Test
	void testGetAgeSurchargeDefaultState() {
		assertEquals(defaultApplicant.getAgeSurcharge(), 0);
	}

	/**
	 * simple test for the default state of Applicant.accidentSurcharge
	 */
	@Test
	void testGetAccidentSurchargeDefaultState() {
		assertEquals(defaultApplicant.getAccidentSurcharge(), 0);
	}

	/**
	 * simple test for the default state of Applicant.totalInsuranceCost
	 */
	@Test
	void testGetTotalInsuranceCostDefaultState() {
		assertEquals(defaultApplicant.getTotalInsuranceCost(), 0);
	}

}
