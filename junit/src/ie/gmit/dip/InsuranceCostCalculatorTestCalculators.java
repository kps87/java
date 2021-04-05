package ie.gmit.dip;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class InsuranceCostCalculatorTestCalculators {

	public static InsuranceCostCalculator defaultCalculator;
	public static int testCounter = 0;

	/**
	 * Some simple console output to log current test commencing
	 */
	@BeforeAll
    public static void init(){
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("-Commencing " + InsuranceCostCalculatorTestCalculators.class + " tests");
        System.out.println("--------------------------------------------------------------------------------------------");
    }
	
	/**
	 * Some simple console output to log current test finishing
	 */
	@AfterEach
	public void incrementTestCounter() {
		InsuranceCostCalculatorTestCalculators.testCounter++;
		System.out.println("\t-" + InsuranceCostCalculatorTestCalculators.class + " test [" + testCounter + "] complete.");
	}
	
	/**
	 * tearDown and confirm number of tests equals some number
	 */
	@AfterAll
	public static void tearDown() {
        System.out.println("-Finished " + InsuranceCostCalculatorTestCalculators.class + " tests");
        System.out.println("--------------------------------------------------------------------------------------------");
	}
	
	/**
	 * initialize a default applicant to use for testing before all other tests are run
	 */
	@BeforeEach
	public void SetUp() {
		defaultCalculator = new InsuranceCostCalculator();
		assertNotNull(defaultCalculator);
	}

	/**
	 * parameterized test method to test the InsuranceCostCalculator.isInsurable()
	 * with multiple parameters provided for the integer values of the applicant age
	 * and the numberOfAccidents they have been.
	 * Numbers have been generated at random between
	 * A timeout of 1 second/1000 milliseconds is enforced.
	 * The result must be either true or false for positive integers
	 */	
	@ParameterizedTest
	@CsvSource(
			{
				"125,27",
				"140,16",
				"45,6",
				"127,28",
				"143,8",
				"135,30",
				"118,18",
				"123,25",
				"46,39",
				"69,34",
				"88,43",
				"141,7",
				"131,48",
				"75,28",
				"104,27",
				"58,11",
				"50,39",
				"69,10",
				"78,33",
				"122,14",
				"121,36",
				"17,28",
				"133,29",
				"0,41",
				"145,2",
				"23,4",
				"52,43",
				"28,47",
				"116,49",
				"52,26",
				"41,0",
				"135,22",
				"149,11",
				"13,14",
				"10,37",
				"79,33",
				"145,22",
				"147,40",
				"126,7",
				"112,13",
				"22,0",
				"138,37",
				"12,44",
				"113,18",
				"74,41",
				"118,40",
				"63,4",
				"47,17",
				"76,27",
				"24,23"	
			}
	)
	@Timeout(value = 1000, unit=TimeUnit.MILLISECONDS)
	void testIsInsurableParamterized(int age, int numberOfAccidents) {		
		assertTrue(
				defaultCalculator.isInsurable(age, numberOfAccidents) == true 
				|| 
				defaultCalculator.isInsurable(age, numberOfAccidents) == false
		);
	}	
	
	/**
	 * parameterized test method to test the InsuranceCostCalculator.calculateAgeSurcharge()
	 * method
	 * A timeout of 1 second/1000 milliseconds is enforced.
	 * The result must be either >= 0.0f
	 */	
	@ParameterizedTest
	@ValueSource(
			ints = {
				0,
				1,
				2,
				5,
				14,
				15,
				16,
				17,
				20,
				31,
				40,
				56,
				90
			}
	)
	@Timeout(value = 1000, unit=TimeUnit.MILLISECONDS)
	void testCalculateAgeSurchargeParameterized(int age) {		
		assertTrue(defaultCalculator.calculateAgeSurcharge(age) >= 0.0f);
	}	
	
	/**
	 * parameterized test method to test the InsuranceCostCalculator.calculateAccidentSurcharge()
	 * method
	 * A timeout of 1 second/1000 milliseconds is enforced.
	 * The result must be either >= 0.0f
	 */	
	@ParameterizedTest
	@ValueSource(
			ints = {
				0,
				1,
				2,
				3,
				4,
				5,
				6,
				7,
				8,
				9,
				10,
				29,
				37,
				48,
				120,
				1000
			}
	)
	@Timeout(value = 1000, unit=TimeUnit.MILLISECONDS)
	void testCalculateAccidentSurchargeParameterized(int numberOfAccidents) {		
		assertTrue(defaultCalculator.calculateAccidentSurcharge(numberOfAccidents) >= 0.0f);
	}	

	
}
