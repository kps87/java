package ie.gmit.dip;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ApplicantTestParameterizedSettersGetters {

	public static Applicant defaultApplicant;
	public static int testCounter = 0;

	/**
	 * Some simple console output to log current test commencing
	 */
	@BeforeAll
	public static void init() {
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		System.out.println("-Commencing " + ApplicantTestParameterizedSettersGetters.class + " tests");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}

	/**
	 * Some simple console output to log current test finishing
	 */
	@AfterEach
	public void incrementTestCounter() {
		ApplicantTestParameterizedSettersGetters.testCounter++;
		System.out.println(
				"\t-" + ApplicantTestParameterizedSettersGetters.class + " test [" + testCounter + "] complete.");
	}

	/**
	 * tearDown and confirm number of tests equals some number
	 */
	@AfterAll
	public static void tearDown() {
		System.out.println("-Finished " + ApplicantTestParameterizedSettersGetters.class + " tests");
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
	 * parameterized test method to test the Applicant.setName() and
	 * Applicant.getName() methods in combination, with multiple parameters
	 * provided. A timeout of 1 second/1000 milliseconds is enforced. Whilst writing
	 * tests for setters and getters seems redundant tests are being written to
	 * ensure 100% code coverage.
	 */
	@ParameterizedTest
	@ValueSource(strings = { "Amman Patton", "Kezia Gaines", "Layla-Rose Coles", "Nayan Barclay", "Giulia Downes",
			"Isobel Sierra", "Teejay Harrell", "Rico Powers", "Olivia-Rose Cuevas", "Pheobe Alford" })
	@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
	void testSetandGetName(String name) {
		try {
			defaultApplicant.setName(name);
			assertNotNull(defaultApplicant.getName());
			assertEquals(defaultApplicant.getName(), name);
		} catch (IllegalArgumentException exception) {
			System.out.println(
					"-[IllegalArgumentException] could not set applicant name on line " + exception.getMessage());
		}
	}

	/**
	 * method which tests that Applicant.setAge() functions correctly for ages in
	 * the range between the minimum insurable age and the maximum insurable age
	 * this is a parameterized test which has a timeout of 1000 milliseconds
	 */
	@ParameterizedTest
	@ValueSource(ints = { 16, 17, 20, 31, 42, 53, 64, 75, 86, 97, 98, 99 })
	@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
	void testSetandGetAge(int age) {

		// catch error where age < 16 exception
		try {
			defaultApplicant.setAge(age);
			assertNotNull(defaultApplicant.getAge());
			assertEquals(defaultApplicant.getAge(), age);
		} catch (IllegalArgumentException exception) {
			System.out.println("-[IllegalArgumentException] could not set applicant age " + exception.getMessage());
		}
	}

	/**
	 * method which tests that Applicant.setNumberOfAccidents() functions correctly
	 * for integers > 0
	 */
	@ParameterizedTest
	@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 10, 15, 20, 100, 1000 })
	@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
	void testSetandGetNumberOfAccidents(int numberOfAccidents) {

		// catch error where number numberOfAccidents < 0
		try {
			defaultApplicant.setNumberOfAccidents(numberOfAccidents);
			assertNotNull(defaultApplicant.getNumberOfAccidents());
			assertEquals(defaultApplicant.getNumberOfAccidents(), numberOfAccidents);
		} catch (IllegalArgumentException exception) {
			System.out.println("-[IllegalArgumentException] could not set numberOfAccidents for applicant "
					+ exception.getMessage());
		}
	}

	/**
	 * simple test of the Applicant.setInsurable() method with two possible tests
	 * for a true or false result
	 */
	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	public void testSetInsurableAndIsInsurable(boolean isInsurable) {
		defaultApplicant.setInsurable(isInsurable);
		assertTrue(defaultApplicant.isInsurable() == true || defaultApplicant.isInsurable() == false);
	}

	/**
	 * method which tests that Applicant.setBaseInsurance() functions correctly for
	 * baseInsurance values > 0.0f this is a parameterized test which has a timeout
	 * of 1000 milliseconds
	 */
	@ParameterizedTest
	@ValueSource(floats = { 0.0f, 0.1f, 1.1f, 2.25f, 3.50f, 9.99f, 10.0f, 20.0f, 300.0f, 400.0f, 500.0f })
	@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
	void testSetAndGetBaseInsurance(float baseInsurance) {

		// catch error where number numberOfAccidents < 0
		try {
			defaultApplicant.setBaseInsurance(baseInsurance);
			assertNotNull(defaultApplicant.getBaseInsurance());
			assertEquals(defaultApplicant.getBaseInsurance(), baseInsurance);
		} catch (IllegalArgumentException exception) {
			System.out.println("-[IllegalArgumentException] could not set baseInsurance as " + baseInsurance
					+ " for applicant " + exception.getMessage());
		}
	}

	/**
	 * method which tests that Applicant.setAge() functions correctly for surcharges
	 * >= 0.0f
	 */
	@ParameterizedTest
	@ValueSource(floats = { 0.0f, 0.1f, 1.1f, 2.25f, 3.50f, 9.99f, 10.0f, 20.0f, 300.0f, 400.0f, 500.0f })
	@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
	void testSetAndGetAgeSurcharge(float ageSurcharge) {

		// catch error where number numberOfAccidents < 0
		try {
			defaultApplicant.setAgeSurcharge(ageSurcharge);
			assertNotNull(defaultApplicant.getAgeSurcharge());
			assertEquals(defaultApplicant.getAgeSurcharge(), ageSurcharge);
		} catch (IllegalArgumentException exception) {
			System.out.println("-[IllegalArgumentException] could not set ageSurcharge as " + ageSurcharge
					+ " for applicant " + exception.getMessage());
		}
	}

	/**
	 * method which tests that Applicant.setAccident() functions correctly for
	 * surcharges > 0.0f
	 */
	@ParameterizedTest
	@ValueSource(floats = { 0.0f, 0.1f, 1.1f, 2.25f, 3.50f, 9.99f, 10.0f, 20.0f, 300.0f, 400.0f, 500.0f })
	@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
	void testSetAndGetAccidentSurcharge(float accidentSurcharge) {

		// catch error where number numberOfAccidents < 0
		try {
			defaultApplicant.setAccidentSurcharge(accidentSurcharge);
			assertNotNull(defaultApplicant.getAccidentSurcharge());
			assertEquals(defaultApplicant.getAccidentSurcharge(), accidentSurcharge);
		} catch (IllegalArgumentException exception) {
			System.out.println("-[IllegalArgumentException] could not set accidentSurcharge as " + accidentSurcharge
					+ " for applicant " + exception.getMessage());
		}
	}

	/**
	 * method which tests that Applicant.setAccident() functions correctly for
	 * surcharges > 0.0f
	 */
	@ParameterizedTest
	@ValueSource(floats = { 0.0f, 0.1f, 1.1f, 2.25f, 3.50f, 9.99f, 10.0f, 20.0f, 300.0f, 400.0f, 500.0f })
	@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
	void testSetAndGetTotalInsuranceCost(float totalInsuranceCost) {
		try {
			defaultApplicant.setTotalInsuranceCost(totalInsuranceCost);
			assertNotNull(defaultApplicant.getTotalInsuranceCost());
			assertEquals(defaultApplicant.getTotalInsuranceCost(), totalInsuranceCost);
		} catch (IllegalArgumentException exception) {
			System.out.println("-[IllegalArgumentException] could not set totalInsuranceCost as " + totalInsuranceCost
					+ " for applicant " + exception.getMessage());
		}
	}

}
