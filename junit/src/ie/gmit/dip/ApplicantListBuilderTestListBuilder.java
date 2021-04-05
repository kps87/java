package ie.gmit.dip;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicantListBuilderTestListBuilder {

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
		ApplicantListBuilderTestListBuilder.testCounter++;
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

	@Test
	public void testBuildApplicantList() {

		// firstName, lastName, age, number of accidents
		// the first line of this string is a 'header' line
		String[] applicantData = new String[] { "firstName surName Age numberOfAccidents", "Amman Patton 42 7",
				"Kezia Gaines 64 0", "Layla-Rose Coles 18 4", "Nayan Barclay 28 3", "Giulia Downes 69 3",
				"Isobel Sierra 66 7" };

		// test the boolean return value
		try {
			boolean listNotEmpty = defaultApplicantListBuilder.buildApplicantList(applicantData);
			assertTrue(listNotEmpty);
		} catch (Exception exception) {
			System.out.println("-[Exception] could not build Applicant list " + exception.getMessage());
		}

		// test the getter and that the size of the list above is correct
		// the -1 offset on applicantData.length is due the listBuilder
		// skipping the first line assuming it is a 'header' line
		List<Applicant> applicants = defaultApplicantListBuilder.getApplicants();
		assertNotNull(applicants);
		assertEquals(applicants.size(), applicantData.length - 1);
	}

}
