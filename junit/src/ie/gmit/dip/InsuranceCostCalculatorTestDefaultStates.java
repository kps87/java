package ie.gmit.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InsuranceCostCalculatorTestDefaultStates {

	public static InsuranceCostCalculator defaultCalculator;
	public static int testCounter = 0;

	/**
	 * Some simple console output to log current test commencing
	 */
	@BeforeAll
    public static void init(){
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("-Commencing " + InsuranceCostCalculatorTestDefaultStates.class + " tests");
        System.out.println("--------------------------------------------------------------------------------------------");
    }
	
	/**
	 * Some simple console output to log current test finishing
	 */
	@AfterEach
	public void incrementTestCounter() {
		InsuranceCostCalculatorTestDefaultStates.testCounter++;
		System.out.println("\t-" + InsuranceCostCalculatorTestDefaultStates.class + " test [" + testCounter + "] complete.");
	}
	
	/**
	 * tearDown and confirm number of tests equals some number
	 */
	@AfterAll
	public static void tearDown() {
        System.out.println("-Finished " + InsuranceCostCalculatorTestDefaultStates.class + " tests");
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
	 * simple test for the default state of InsuranceCostCalculator.getBaseInsurance
	 * this number must always be positive
	 */	
	@Test
	void testGetBaseInsuranceDefaultStaticFinal() {		
		assertTrue(InsuranceCostCalculator.getBaseInsurance() > 0.0f);
	}
	
	/**
	 * simple test for the default state of Applicant.MIN_INSURABLE_AGE
	 * and Applicant.MAX_INSURABLE_AGE
	 */
	@Test
	void testGetMinAndMaxInsurableAges(){
		assertNotNull(InsuranceCostCalculator.getMinInsurableAge());
		assertNotNull(InsuranceCostCalculator.getMaxInsurableAge());
	}
	
	/**
	 * simple test for the default state of InsuranceCostCalculator.getAgeSurcharge
	 * this number must always be positive, and greater than the minimum
	 */	
	@Test
	void testGetAgeSurchageThresholdDefaultStaticFinal() {		
		assertTrue(InsuranceCostCalculator.getAgeSurchargeThreshold() > 0.0f);
	}
	
	/**
	 * simple test for the default state of InsuranceCostCalculator.getAgeSurcharge
	 * this number must always be positive
	 */	
	@Test
	void testGetAgeSurchageDefaultStaticFinal() {		
		assertTrue(InsuranceCostCalculator.getAgeSurcharge() > 0.0f);
	}
	
	/**
	 * simple test for the default state of InsuranceCostCalculator.getAgeSurcharge
	 * this number must always be positive
	 */	
	@Test
	void testGetMaxAccidentsDefaultStaticFinal() {		
		assertTrue(InsuranceCostCalculator.getMaxAccidents() > 0);
	}
	

}
