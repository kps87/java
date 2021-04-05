package ie.gmit.dip;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class ExampleRunner {

	public ExampleRunner() {
	}

	public void runFromFile(File input, File output) {

		// setup the file parser and create a list of applicants
		// for which insurance costs should be estimated
		ApplicantDataFileParser applicantDataParser = new ApplicantDataFileParser();
		try {
			applicantDataParser.parseInputFile(input);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			System.exit(0);
		}

		// build a list of applicant objects
		ApplicantListBuilder applicantListBuilder = new ApplicantListBuilder();
		try {
			applicantListBuilder.buildApplicantList(applicantDataParser.getParsedApplicantFile());
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			System.exit(0);
		}

		// calculate the insurance costs for the list of applicants
		this.calculateApplicantsInsuranceCosts(applicantListBuilder.getApplicants(), new InsuranceCostCalculator());

		// printing the outputs
		try {
			this.printApplicantsInsuranceSummaries(applicantListBuilder.getApplicants(), output);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}

	}

	public void calculateApplicantsInsuranceCosts(List<Applicant> applicants, InsuranceCostCalculator calculator) {

		System.out.println(
				"\t-ExampleRunner.calculateApplicantsInsuranceCosts() for " + applicants.size() + " applicants");

		System.out.println("\t-Calculating insurance costs for " + applicants.size() + " applicants");
		List<Applicant> insurables = new ArrayList<Applicant>();
		List<Applicant> uninsurables = new ArrayList<Applicant>();

		for (Applicant applicant : applicants) {

			if (calculator.isInsurable(applicant.getAge(), applicant.getNumberOfAccidents())) {

				applicant.setInsurable(true);
				insurables.add(applicant);

				float base = InsuranceCostCalculator.getBaseInsurance();
				float accidentSurcharge = calculator.calculateAccidentSurcharge(applicant.getNumberOfAccidents());
				float ageSurcharge = calculator.calculateAgeSurcharge(applicant.getAge());
				float total = base + accidentSurcharge + ageSurcharge;

				applicant.setBaseInsurance(base);
				applicant.setAccidentSurcharge(accidentSurcharge);
				applicant.setAgeSurcharge(ageSurcharge);
				applicant.setTotalInsuranceCost(total);

			} else {
				applicant.setInsurable(false);
				uninsurables.add(applicant);
			}
		}

		// print a summary
		System.out.println("\t\t-There are " + insurables.size() + " insurable applicants");
		System.out.println("\t\t-There were " + uninsurables.size() + " insurable applicants");

	}

	public void printApplicantsInsuranceSummaries(List<Applicant> applicants, File outputFile) throws Exception {

		System.out.println("\t-ExampleRunner.printApplicantsInsuranceSummaries(): Output = [" + outputFile + "]");

		try {

			FileWriter fw = new FileWriter(outputFile);

			// write the headers to the file
			String[] fileHeaders = { "firstName", "lastName", "age", "numberOfAccidents", "isInsurable",
					"baseInsurance", "ageSurcharge", "accidentSurcharge", "totalInsuranceCost" };

			for (String h : fileHeaders) {
				fw.write(h + " ");
			}
			fw.write("\n");

			// write the applicant data to a file
			for (Applicant applicant : applicants) {
				fw.write(applicant.getName() + " ");
				fw.write(applicant.getAge() + " ");
				fw.write(applicant.getNumberOfAccidents() + " ");
				fw.write(applicant.isInsurable() + " ");
				if (applicant.isInsurable()) {
					fw.write(applicant.getBaseInsurance() + " ");
					fw.write(applicant.getAgeSurcharge() + " ");
					fw.write(applicant.getAccidentSurcharge() + " ");
					fw.write(applicant.getTotalInsuranceCost() + " ");
				} else {
					fw.write("NaN NaN NaN NaN");
				}
				fw.write("\n");
			}

			fw.flush();
			fw.close();

		}
		// if an exception occurs let the user know
		// and print a stack trace
		catch (Exception exception) {
			throw new Exception("[error] could not create output file [" + outputFile + "]");
		}
	}

	public static void main(String[] args) {

		System.out.println("-Running insurance cost calculations");
		ExampleRunner runner = new ExampleRunner();
		File input = new File("ApplicantDataFileParserTestInput.txt");
		File output = new File("calculatedApplicantInsuranceEstimates.txt");
		runner.runFromFile(input, output);

	}

}
