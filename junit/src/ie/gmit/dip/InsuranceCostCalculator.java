package ie.gmit.dip;

import java.util.*;

public class InsuranceCostCalculator {

	// this is a class associated with estimating the cost of insurance

	private static final int MIN_INSURABLE_AGE = 16;
	private static final int MAX_INSURABLE_AGE = 100;
	private static final float BASE_INSURANCE = 500.0f;
	private static final float AGE_SURCHARGE = 100.0f;
	private static final int MAX_ACCIDENTS = 6;
	private static final int AGE_SURCHARGE_THRESHOLD = 25;

	public InsuranceCostCalculator() {

	}

	public static float getBaseInsurance() {
		return BASE_INSURANCE;
	}

	public static float getAgeSurcharge() {
		return AGE_SURCHARGE;
	}

	public static float getAgeSurchargeThreshold() {
		return AGE_SURCHARGE_THRESHOLD;
	}

	public static float getMaxAccidents() {
		return MAX_ACCIDENTS;
	}

	public static int getMinInsurableAge() {
		return MIN_INSURABLE_AGE;
	}

	public static int getMaxInsurableAge() {
		return MAX_INSURABLE_AGE;
	}

	public boolean isInsurable(int age, int numberOfAccidents) {

		if (age < MIN_INSURABLE_AGE || age > MAX_INSURABLE_AGE || numberOfAccidents >= MAX_ACCIDENTS) {
			return false;
		} else {
			return true;
		}
	}

	public float calculateAgeSurcharge(int age) {
		if (age >= AGE_SURCHARGE_THRESHOLD) {
			return getAgeSurcharge();
		} else {
			return 0.0f;
		}
	}

	public float calculateAccidentSurcharge(int numberOfAccidents) {

		// a map is used here for simplicity - a continuous function
		// where one can plug in a number of accidents and return
		// a positive number would be a more appropriate way to do this
		// surcharge = constant*exp(numberOfAccidents) would do
		// but this is still testable and robust in terms of floor/ceiling values
		Map<Integer, Float> accidentSurchargeMap = new HashMap<Integer, Float>();
		accidentSurchargeMap.put(0, 0.0f);
		accidentSurchargeMap.put(1, 50.0f);
		accidentSurchargeMap.put(2, 125.0f);
		accidentSurchargeMap.put(3, 225.0f);
		accidentSurchargeMap.put(4, 375.0f);
		accidentSurchargeMap.put(5, 575.0f);
		int maxKey = 5;

		if (accidentSurchargeMap.containsKey(numberOfAccidents)) {
			return accidentSurchargeMap.get(numberOfAccidents);
		} else {

			if (numberOfAccidents > maxKey) {
				return accidentSurchargeMap.get(maxKey);
			} else {
				return 0.0f;
			}
		}
	}

}
