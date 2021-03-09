package hu.ak_akademia.concurrent.prime_generator_parallel.utility;

/**
 * <b>Note: </b>This class been developed by Lajos Czuczor.
 *  
 * @author Lajos Czuczor
 *
 */

public class PrimeMathUtility {
	

	/**
	 * Returns a reasonably good <b>high bound</b> for pi(x) function. x must be greater than or equal to 17.
	 * 
	 * @param x argument to pi()
	 * @return an upper bound for pi(x)
	 */
	public static int primeCountHigh(long x) {
		if (x >= 60_184) {
			return (int) (x / (Math.log(x) - 1.1));
		} else { // provided that n >= 17
			return (int) (1.25506 * x / Math.log(x));
		}
	}

	/**
	 * Returns a reasonably good <b>low bound</b> for pi(x) function. x must be greater than or equal to 17.
	 * 
	 * @param x argument to pi()
	 * @return a low bound for pi(x)
	 */
	public static int primeCountLow(long x) {
		if (x >= 5_393) {
			return (int) (x / (Math.log(x) - 1.0));
		} else { // provided that n >= 17
			return (int) (x / Math.log(x));
		}
	}

	/**
	 * Returns a reasonably good <b>high bound</b> for number of primes between k and n. Both arguments are meant inclusive
	 * and must be greater than or equal to 17.
	 * 
	 * @param k the 'from' number
	 * @param n the 'to' number
	 * @return an upper bound for the number of primes between k and n (both inclusive)
	 */
	public static int highBoundForPrimeCount(long k, long n) {
		return primeCountHigh(n) - primeCountLow(k - 1);
	}


}
