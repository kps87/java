package ie.gmit.dip;

import java.io.*;

public class ApplicantDataFileParser {

	// this class is associated with parsing in a list of applicants
	// from a space-separated text file

	private File applicantDataFile;
	private String[] parsedApplicantFile;

	public ApplicantDataFileParser() {
	}

	public ApplicantDataFileParser(File applicantDataFile) {
		this.applicantDataFile = applicantDataFile;
	}

	public File getApplicantDataFile() {
		return this.applicantDataFile;
	}

	public void setApplicantDataFile(File applicantDataFile) {
		this.applicantDataFile = applicantDataFile;
	}

	public String[] getParsedApplicantFile() {
		return this.parsedApplicantFile;
	}

	public void setParsedApplicantFile(String[] parsedApplicantFile) {
		this.parsedApplicantFile = parsedApplicantFile;
	}

	public boolean parseInputFile(File file) throws FileNotFoundException {
		System.out.println("\t-ApplicantDataFileParser.parseInputFile(): Input = [" + file + "]");
		boolean wasParsed = false;
		try {
			this.setParsedApplicantFile(fileToStringArray(file));
			wasParsed = true;
		} catch (FileNotFoundException exception) {
			throw new FileNotFoundException(
					"\t-[error] ApplicantDataFileParser.parseInputFile() could not parse file: [" + file + "]");
		}

		return wasParsed;
	}

	public String[] fileToStringArray(File file) throws FileNotFoundException {

		String[] stringArray = new String[0];

		// try to parse the file and append each file to a StringBuilder
		try {

			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;

			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}

			br.close(); // close the reader
			stringArray = sb.toString().split("\\n");

		}
		// else catch the exception, warn the user
		catch (Exception e) {
			throw new FileNotFoundException(
					"\t-[error] ApplicantDataFileParser.fileToStringArray() could not parse file: [" + file + "]");
		}

		return stringArray;

	}

}
