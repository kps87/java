package ie.gmit.dip;

import org.junit.runner.RunWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;


@RunWith(JUnitPlatform.class)
@SelectClasses({ 
		ApplicantTestConstructors.class, ApplicantTestExceptions.class, ApplicantTestDefaultStates.class,
		ApplicantTestParameterizedSettersGetters.class,

		InsuranceCostCalculatorTestConstructor.class, InsuranceCostCalculatorTestCalculators.class,
		InsuranceCostCalculatorTestDefaultStates.class,

		ApplicantDataFileParserTestConstructors.class, ApplicantDataFileParserTestSettersGetters.class,
		ApplicantDataFileParserTestFileParsers.class, ApplicantDataFileParserTestExceptions.class,

		ApplicantListBuilderTestConstructor.class, ApplicantListBuilderTestDefaultState.class,
		ApplicantListBuilderTestExceptions.class, ApplicantListBuilderTestListBuilder.class

})
class JUnitTestSuite {
	
//	public static void main(String[] args) {
//		System.out.println("Commencing JUnit test suite");
//	}
	

}
