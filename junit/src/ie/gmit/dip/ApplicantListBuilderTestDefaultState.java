package ie.gmit.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicantListBuilderTestDefaultState {

	public static ApplicantListBuilder defaultApplicantListBuilder;
	public static int testCounter = 0;

	/**
	 * Some simple console output to log current test commencing
	 */
	@BeforeAll
	public static void init() {
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		System.out.println("-Commencing " + ApplicantListBuilderTestListBuilder.class + " tests");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}

	/**
	 * Some simple console output to log current test finishing
	 */
	@AfterEach
	public void incrementTestCounter() {
		ApplicantListBuilderTestDefaultState.testCounter++;
		System.out.println("\t-" + ApplicantListBuilderTestListBuilder.class + " test [" + testCounter + "] complete.");
	}

	/**
	 * tearDown and confirm number of tests equals some number
	 */
	@AfterAll
	public static void tearDown() {
		System.out.println("-Finished " + ApplicantListBuilderTestListBuilder.class + " tests");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}

	/**
	 * initialize a default ApplicantListBuilder to use for testing before every
	 * other tests are run
	 */
	@BeforeEach
	public void SetUp() {
		defaultApplicantListBuilder = new ApplicantListBuilder();
		assertNotNull(defaultApplicantListBuilder);
	}

	/**
	 * method to test the ApplicantListBuilder.getApplicants() method which returns
	 * a List<Applicant> objects
	 */
	@Test
	public void testApplicantsListDefaultState() {
		defaultApplicantListBuilder = new ApplicantListBuilder();
		assertNotNull(defaultApplicantListBuilder.getApplicants());
	}

}
