package hu.ak_akademia.concurrent.prime_generator_parallel.main;

import java.util.ArrayList;
import java.util.List;

import hu.ak_akademia.concurrent.prime_generator_parallel.utility.PrimeMathUtility;

/**
 * <p>
 * The SimplePrimeFinder class is a supporting class which looks for primes
 * under a certain limit (set by the user). It uses naive way of checking if
 * numbers are primes or not.
 * </p>
 * 
 * @author Izabella Marton
 *
 */

public class SimplePrimeFinder {

	private double maxValue;

	public SimplePrimeFinder(double maxValue) {
		this.maxValue = maxValue;
	}

	public List<Integer> getPrimesUnderLimit() {
		List<Integer> primes = new ArrayList<>(PrimeMathUtility.highBoundForPrimeCount(0L, Math.round(maxValue)));
		primes.addAll(List.of(Integer.valueOf(2), Integer.valueOf(3)));
		for (int num = 5; num < maxValue; num += 2) {
			if (checkPrimes(num))
				primes.add(num);
		}
		return primes;
	}

	private boolean checkPrimes(int number) {
		for (int i = 3; i < number; i += 2) {
			if (number % i == 0)
				return false;
		}
		return true;
	}

}
