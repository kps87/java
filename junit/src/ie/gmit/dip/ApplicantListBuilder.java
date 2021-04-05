package ie.gmit.dip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicantListBuilder {

	// class for building a list of Applicant objects from
	// from a String[] array (provided directly as literals, or parsed from a file)
	// with space-separated fields
	private List<Applicant> applicants = new ArrayList<Applicant>();

	public ApplicantListBuilder() {

	}

	public List<Applicant> getApplicants() {
		return this.applicants;
	}

	public boolean buildApplicantList(String[] applicantData) throws Exception {

		boolean haveValidApplicants = false;
		Map<Integer, Exception> exceptions = new HashMap<Integer, Exception>(); // a map of exception with the key
																				// indicating the line on which the
																				// exception occurred

		// process each line
		for (int i = 1; i < applicantData.length; i++) {

			// create a new applicant, and split the line
			Applicant applicant = new Applicant();
			String[] splitLine = applicantData[i].split("\\s+");
			boolean isValidApplicant = true;

			// ensure that there are 4 fields
			if (splitLine.length < 4) {
				ArrayIndexOutOfBoundsException exception = new ArrayIndexOutOfBoundsException(
						"Error in applicant definition. Four fields required for each applicant.");
				exceptions.put(i, exception);
				isValidApplicant = false;
			}

			// try to set the applicant name
			if (isValidApplicant) {
				try {
					applicant.setName(splitLine[0] + " " + splitLine[1]);
				} catch (IllegalArgumentException exception) {
					isValidApplicant = false;
					exceptions.put(i, exception);
				}
			}

			// try to set the applicant age, and number of accidents
			if (isValidApplicant) {
				try {
					applicant.setAge(Integer.parseInt(splitLine[2]));
				} catch (IllegalArgumentException exception) {
					isValidApplicant = false;
					exceptions.put(i, exception);
				}
			}

			// try to set the applicant age, and number of accidents
			if (isValidApplicant) {
				try {
					applicant.setNumberOfAccidents(Integer.parseInt(splitLine[3]));
				} catch (IllegalArgumentException exception) {
					isValidApplicant = false;
					exceptions.put(i, exception);
				}
			}

			// only add this applicant if input is valid
			// update return value - there is at least one good Applicant
			if (isValidApplicant) {
				this.applicants.add(applicant);
				haveValidApplicants = true;
			}
		}

		// build a string of all the exceptions and throw
		if (!exceptions.isEmpty()) {

			StringBuilder sb = new StringBuilder();

			for (int i : exceptions.keySet()) {
				Exception e = exceptions.get(i);
				String error = "\t-" + e.getClass().getCanonicalName() + " in ApplicantListBuilder input file on line "
						+ i + ". " + e.getMessage();
				sb.append(error).append("\n");
			}

			throw new Exception(sb.toString());
		}

		// return whether list is empty or not
		return haveValidApplicants;
	}

}
