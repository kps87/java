package ie.gmit.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InsuranceCostCalculatorTestConstructor {

	public static int testCounter = 0;

	/**
	 * Some simple console output to log current test commencing
	 */
	@BeforeAll
    public static void init(){
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("-Commencing " + InsuranceCostCalculatorTestConstructor.class + " tests");
        System.out.println("--------------------------------------------------------------------------------------------");
    }
	
	/**
	 * Some simple console output to log current test finishing
	 */
	@AfterEach
	public void incrementTestCounter() {
		InsuranceCostCalculatorTestConstructor.testCounter++;
		System.out.println("\t-" + InsuranceCostCalculatorTestConstructor.class + " test [" + testCounter + "] complete.");
	}
	
	/**
	 * tearDown and confirm number of tests equals some number
	 */
	@AfterAll
	public static void tearDown() {
        System.out.println("-Finished " + InsuranceCostCalculatorTestConstructor.class + " tests");
        System.out.println("--------------------------------------------------------------------------------------------");
	}

	/**
	 * method to test the InsuranceCostCalculator constructor with no parameters
	 * provided as input
	 */
	@Test
	public void testConstructorWithNoParameters() {
		InsuranceCostCalculator icc = new InsuranceCostCalculator();
		assertNotNull(icc);
	}
	
	
}
