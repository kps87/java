---------------------------------------------------------------------------------------------------------------
Requirements
---------------------------------------------------------------------------------------------------------------
The brief for this project is as follows:
	Requirement 1: You are required to refactor this code so that can be tested using JUnit5.
	Requirement 2: You test should contain a test suite.
	Requirement 3: Your test code should contain the following annotations @BeforeAll, @BeforeEach, @Test, @ParameterizedTest, @Timeout, @AfterAll and @AfterEach.
	Requirement 4: Your code should also be able to test for two different kinds of exceptions.

These requirements have been met, as described below.
Several screenshots of the code in action are provided in the project base directory.
If there are compilation errors, please contact me as code has been successfully tested 
on many occassions

---------------------------------------------------------------------------------------------------------------
Project layout
---------------------------------------------------------------------------------------------------------------
The files are stored as an Eclipse project workspace.
/eclipse_workspace_kieran_somers/ is the workspace base path
/eclipse_workspace_kieran_somers/eclipse_junit_project/ is a java project base directory

/eclipse_workspace_kieran_somers/eclipse_junit_project/bin/ is the compilation bin
/eclipse_workspace_kieran_somers/eclipse_junit_project/src/ contains all the .java class and JUnit source files

The source code is contained in a package called ie.gmit.dip and corresponding files are therefore found in:
/eclipse_workspace_kieran_somers/eclipse_junit_project/src/ie/gmit/dip/

/eclipse_workspace_kieran_somers/eclipse_junit_project/ApplicantDataFileParserTestInput.txt
	-this is an example input file for the ApplicantDataFileParser.java class which is also used by the ExampleRunner.java class

/eclipse_workspace_kieran_somers/eclipse_junit_project/calculatedApplicantInsuranceEstimates.txt
	-this is an example output produced by the ExampleRunner.java

Four *.png images are provided in /eclipse_workspace_kieran_somers/eclipse_junit_project/
	-these illustrate the ExampleRunner and the test suite in operation.



---------------------------------------------------------------------------------------------------------------
Requirement 1: You are required to re-factor this code so that can be tested using JUnit5.
---------------------------------------------------------------------------------------------------------------
-The insurance premium calculator has been re-factored into four main java classes, which aim to comply
-with basic OOP principles including abstraction, encapsulation, composition, the single responsbility principle,
-as well as using various types of data structures ranging from primitive types, arrays, and different collections
-including Lists/ArrayLists and Maps.
	Applicant.java is a class for handling applicant personal data state and behaviour
		-This class has key methods associated with age, number of accidents, applicant name, etc.
	InsuranceCostCalculator.java is a class for calculating insurance costs when provided with relevant data from an applicant
		-This class has methods associated with insurance costs and whether applicants meet insurance requirements
	ApplicantDataFileParser.java is a class which parsers in a file containing a list of applicants.
		-This class allows for easy processing of large numbers of applicants rather than providing each applicant
		-one-by-one from the command line.
	ApplicantListBuilder.java builds an applicant list which can be used to batch operations on applicants
		-This class allows one to build a list of applicants from a String[] array with space separated fields
		-Is used in conjuction with ApplicantDataFileParser.java to generated lists of applicants, but could
		-be used in isolation from ApplicantDataFileParser.java

-An example runner is provided in ExampleRunner.java showing that the re-factored code is functional:
	-It reads in the applicant data in "ApplicantDataFileParserTestInput.txt" (located in the project base directory)
	-It builds a list of applicants from the parsed input
	-It runs insurance cost calculations for the list of applicants
	-It prints an output file containing insurance estimates to "calculatedApplicantInsuranceEstimates.txt"

-All code has been written in Eclipse version 2019-09 with java-13-oracle version:
	java version "13.0.1" 2019-10-15
	Java(TM) SE Runtime Environment (build 13.0.1+9)
	Java HotSpot(TM) 64-Bit Server VM (build 13.0.1+9, mixed mode, sharing)


---------------------------------------------------------------------------------------------------------------
-Requirement 2: You test should contain a test suite.
---------------------------------------------------------------------------------------------------------------
-A comprehensive suite of tests have been written for the following core classes:
	-Applicant.java
	-InsuranceCostCalculator.java
	-ApplicantDataFileParser.java
	-ApplicantListBuilder.java
-Each one of the above *.java classes has its own corresponding JUnit test files 
-which test every method in these classes:
	-Test files are named as [ClassName][Test*].java where * is a description of the type of test being run
	-Common tests include constructor tests, default state tests, getter and setter tests, exception tests
	-Each test contains at least the @BeforeAll, @BeforeEach, @Test, @AfterAll and @AfterEach methods
	-Other specific types of test are included as needed/required including @ParameterizedTest and @Timeout

-The above tests are called from a JUnit test suite contained in JUnitTestSuite.java which has been constructed
-in line with the course materials, and which aims to perform ~230 tests on the core classes in the above files. 

-However, it appears there are some small bugs/compatibility issues with JUnit 4 and 5 and 
-there may be an issue if one tries to run the 'JUnitTestSuite.java' class where it appears that only 2 tests have been run.
-The author has tested all individual test classes, and can confirm each one functions properly.
-If the above error is encountered, a test suite can be run easily in Eclipse by right clicking on the ie.gmit.dip package
-in the package explorer,-and clicking 'Run as'->'JUnit Test'. 
-Alternatively, one can right click on 'JUnitTestSuite.java'->'Run As'->'Run Configurations'. 
-In the 'Test' tab, click the 'Run all tests in the selected project, package or source folder' button.
-For the 'Test runner' field, select 'JUnit 5', and then click the 'Run' button in the bottom corner.
-This should run 233 test cases on the core classes. 

---------------------------------------------------------------------------------------------------------------
Requirement 3: Your test code should contain the following annotations:
@BeforeAll, @BeforeEach, @Test, @AfterAll and @AfterEach
@ParameterizedTest, @Timeout
---------------------------------------------------------------------------------------------------------------
-Every test class described above has at least @BeforeAll, @BeforeEach, @Test, @AfterAll 
-and @AfterEach methods. 

-The @BeforeAll and @BeforeEach methods are typically used to instantiate new default class objects before 
-@Test and @ParameterizedTest calls are run.
-@AfterAll and @AfterEach methods are implemented in every test case also, typically to print information
-to the console after every test case, and every every test class is run.

-The other specific types of test are included as needed (parameterized tests with timeouts are only sensible in some cases)
-Tests classes which included @ParameterizedTest and @Timeout tests include:
	-ApplicantDataFileParserTestSettersGetters.java
	-ApplicantTestParameterizedSettersGetters.java
	-ApplicantTestExceptions.java
	-InsuranceCostCalculatorTestCalculators.java

---------------------------------------------------------------------------------------------------------------
Requirement 4: Your code should also be able to test for two different kinds of exceptions.
---------------------------------------------------------------------------------------------------------------
At least two different types of Exceptions have been incorporated into the various JUnit test files
	
	-The ApplicantDataFileParserTestExceptions.java tests for "FileNotFoundException" exceptions
	-for cases where input files are not properly defined/contained on the file system
	
	-The ApplicantTestExceptions.java tests for "IllegalArgumentException" exceptions
	-as constraints are placed on legal inputs for the 
	-Applicant.age, Applicant.numberOfAccidents, Applicant.name etc.

	-Further to the above two Exceptions:
		-The ApplicantListBuilderTestExceptions.java contains a generic "Exception" tests
		-Multiple different types of possible exceptions can be returned from the ApplicantListBuilder
		-depending on the input error encountered, and an all-encompassing generic 
		-java.lang.Exception type is used to handle these.
		-A complete list of different types of errors are caught, and printed to screen by these test file
		-including:
			java.lang.Exception
			java.lang.ArrayIndexOutOfBoundsException
			java.lang.NumberFormatException
			java.lang.IllegalArgumentException


	