package ie.gmit.dip;

public class Applicant {

	// this is a class that handles state and behaviour associated with insurance
	// applicants

	private static final int MIN_INSURABLE_AGE = 16;
	private static final int MAX_INSURABLE_AGE = 100;
	private String name;
	private int age;
	private int numberOfAccidents;
	private boolean isInsurable;
	private float baseInsurance;
	private float ageSurcharge;
	private float accidentSurcharge;
	private float totalInsuranceCost;

	public Applicant() {

	}

	public Applicant(String name, int age, int numberOfAccidents) {
		this.name = name;
		this.age = age;
		this.numberOfAccidents = numberOfAccidents;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalArgumentException {

		if (name == null) {
			throw new IllegalArgumentException("Applicant.name cannot be null");
		} else if (name.length() == 0) {
			throw new IllegalArgumentException("Applicant.name must have length > 0");
		} else {
			this.name = name;
		}

	}

	public int getAge() {
		return age;
	}

	public int getMinInsurableAge() {
		return MIN_INSURABLE_AGE;
	}

	public int getMaxInsurableAge() {
		return MAX_INSURABLE_AGE;
	}

	public void setAge(int age) throws IllegalArgumentException {
		if (age > 0) {
			this.age = age;
		} else {
			throw new IllegalArgumentException("Applicant.age must be >= 0");
		}
	}

	public int getNumberOfAccidents() {
		return this.numberOfAccidents;
	}

	public void setNumberOfAccidents(int numberOfAccidents) throws IllegalArgumentException {
		this.numberOfAccidents = numberOfAccidents;
		if (numberOfAccidents >= 0) {
			this.numberOfAccidents = numberOfAccidents;
		} else {
			throw new IllegalArgumentException("Applicant.numberOfAccidents must be >= 0");
		}
	}

	public boolean isInsurable() {
		return isInsurable;
	}

	public void setInsurable(boolean isInsurable) {
		this.isInsurable = isInsurable;
	}

	public float getBaseInsurance() {
		return baseInsurance;
	}

	public void setBaseInsurance(float baseInsurance) throws IllegalArgumentException {
		if (baseInsurance >= 0.0f) {
			this.baseInsurance = baseInsurance;
		} else {
			throw new IllegalArgumentException("Applicant.baseInsurance must be >= 0.0");
		}
	}

	public float getAgeSurcharge() {
		return ageSurcharge;
	}

	public void setAgeSurcharge(float ageSurcharge) throws IllegalArgumentException {
		if (ageSurcharge >= 0.0f) {
			this.ageSurcharge = ageSurcharge;
		} else {
			throw new IllegalArgumentException("Applicant.ageSurcharge must be >= 0.0");
		}
	}

	public float getAccidentSurcharge() {
		return accidentSurcharge;
	}

	public void setAccidentSurcharge(float accidentSurcharge) throws IllegalArgumentException {
		if (accidentSurcharge >= 0.0f) {
			this.accidentSurcharge = accidentSurcharge;
		} else {
			throw new IllegalArgumentException("Applicant.accidentSurcharge must be >= 0.0");
		}
	}

	public float getTotalInsuranceCost() {
		return totalInsuranceCost;
	}

	public void setTotalInsuranceCost(float totalInsuranceCost) throws IllegalArgumentException {

		if (totalInsuranceCost >= 0.0f) {
			this.totalInsuranceCost = totalInsuranceCost;
		} else {
			throw new IllegalArgumentException("Applicant.totalInsuranceCost must be >= 0.0");
		}

	}

}
