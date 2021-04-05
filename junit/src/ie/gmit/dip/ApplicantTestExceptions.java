package ie.gmit.dip;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ApplicantTestExceptions {

	public static Applicant defaultApplicant;
	public static int testCounter = 0;

	/**
	 * Some simple console output to log current test commencing
	 */
	@BeforeAll
	public static void init() {
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		System.out.println("-Commencing " + ApplicantTestExceptions.class + " tests");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}

	/**
	 * Some simple console output to log current test finishing
	 */
	@AfterEach
	public void incrementTestCounter() {
		ApplicantTestExceptions.testCounter++;
		System.out.println("\t-" + ApplicantTestExceptions.class + " test [" + testCounter + "] complete.");
	}

	/**
	 * tearDown and confirm number of tests equals some number
	 */
	@AfterAll
	public static void tearDown() {
		System.out.println("-Finished " + ApplicantTestExceptions.class + " tests");
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
	 * tests the Applicant.setName() method throws an IllegalArgumentException if
	 * initialized with a null string or with a string of length < 0
	 */
	@Test
	void testSetNameExceptions() {

		// catch a null String exception
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> defaultApplicant.setName(null));
		assertNotNull(exception.getMessage());
		assertEquals(exception.getMessage(), "Applicant.name cannot be null");

		// catch an empty string exception
		exception = assertThrows(IllegalArgumentException.class, () -> defaultApplicant.setName(""));
		assertNotNull(exception.getMessage());
		assertEquals(exception.getMessage(), "Applicant.name must have length > 0");
		System.out.println("\t-" + exception.getClass().getCanonicalName() + " " + exception.getMessage());

	}

	/**
	 * method which tests that Applicant.setAge() throws an exception if initialized
	 * with a number that is outside the range between the minimum insurable age and
	 * the maximum insurable age this is a parameterized test which has a timeout of
	 * 100 milliseconds
	 */
	@ParameterizedTest
	@ValueSource(ints = { -1000, -128, -63, -50, -1, })
	@Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
	void testSetAgeExceptions(int age) {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> defaultApplicant.setAge(age));
		assertNotNull(exception.getMessage());
		assertEquals(exception.getMessage(), "Applicant.age must be >= 0");
		System.out.println("\t-" + exception.getClass().getCanonicalName() + " " + exception.getMessage());

	}

	/**
	 * method which tests that Applicant.setNumberOfAccidents() throws an exception
	 * if initialized with a number that is less than 0
	 */
	@ParameterizedTest
	@ValueSource(ints = { -128, -63, -28, -1 })
	@Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
	void testSetNumberOfAccidentExceptions(int numberOfAccidents) {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> defaultApplicant.setNumberOfAccidents(numberOfAccidents));
		assertNotNull(exception.getMessage());
		assertEquals(exception.getMessage(), "Applicant.numberOfAccidents must be >= 0");
		System.out.println("\t-" + exception.getClass().getCanonicalName() + " " + exception.getMessage());
	}

	/**
	 * method which tests that Applicant.setBaseInsurance() throws an exception if
	 * initialized with a number that is less than 0.0f this is a parameterized test
	 * which has a timeout of 100 milliseconds
	 */
	@ParameterizedTest
	@ValueSource(floats = { -10000000.0f, -128.0f, -65.1f, -15.2f, -2.1f, -0.1f, -0.0000000001f, })
	@Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
	void testSetBaseInsuranceExceptions(float baseInsurance) {

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> defaultApplicant.setBaseInsurance(baseInsurance));

		assertNotNull(exception.getMessage());
		assertEquals(exception.getMessage(), "Applicant.baseInsurance must be >= 0.0");
		System.out.println("\t-" + exception.getClass().getCanonicalName() + " " + exception.getMessage());

	}

	/**
	 * method which tests that Applicant.setAgeSurcharge() throws an exception if
	 * initialized with a number that is less than 0.0f this is a parameterized test
	 * which has a timeout of 100 milliseconds
	 */
	@ParameterizedTest
	@ValueSource(floats = { -10000000.0f, -128.0f, -65.1f, -15.2f, -2.1f, -0.1f, -0.0000000001f, })
	@Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
	void testSetAgeSurchargeExceptions(float ageSurcharge) {

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> defaultApplicant.setAgeSurcharge(ageSurcharge));

		assertNotNull(exception.getMessage());
		assertEquals(exception.getMessage(), "Applicant.ageSurcharge must be >= 0.0");
		System.out.println("\t-" + exception.getClass().getCanonicalName() + " " + exception.getMessage());

	}

	/**
	 * method which tests that Applicant.setAccidentSurcharge() throws an exception
	 * if initialized with a number that is less than 0.0f this is a parameterized
	 * test which has a timeout of 100 milliseconds
	 */
	@ParameterizedTest
	@ValueSource(floats = { -10000000.0f, -128.0f, -65.1f, -15.2f, -2.1f, -0.1f, -0.0000000001f, })
	@Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
	void testSetAccidentSurchargeExceptions(float accidentSurcharge) {

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> defaultApplicant.setAccidentSurcharge(accidentSurcharge));

		assertNotNull(exception.getMessage());
		assertEquals(exception.getMessage(), "Applicant.accidentSurcharge must be >= 0.0");
		System.out.println("\t-" + exception.getClass().getCanonicalName() + " " + exception.getMessage());
	}

	/**
	 * method which tests that Applicant.setTotalInsuranceCost() throws an exception
	 * if initialized with a number that is less than 0.0f this is a parameterized
	 * test which has a timeout of 100 milliseconds
	 */
	@ParameterizedTest
	@ValueSource(floats = { -10000000.0f, -128.0f, -65.1f, -15.2f, -2.1f, -0.1f, -0.0000000001f, })
	@Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
	void testSetTotalInsuranceCostExceptions(float totalInsuranceCost) {

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> defaultApplicant.setTotalInsuranceCost(totalInsuranceCost));

		assertNotNull(exception.getMessage());
		assertEquals(exception.getMessage(), "Applicant.totalInsuranceCost must be >= 0.0");
		System.out.println("\t-" + exception.getClass().getCanonicalName() + " " + exception.getMessage());

	}

}
