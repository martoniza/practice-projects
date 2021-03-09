package hu.ak_akademia.concurrent.prime_generator_parallel.main;

/**
 * <p>The Constants class stores constants which are necessary for configuration:
 * </p>
 * <ul>
 * <li>minimum and maximum values for the interval in which the program searches
 * for primes</li>
 * <li>min size of intervals</li>
 * <li>min and max values for the number of smaller intervals the whole interval
 * is devided into</li>
 * <li>min and max values for the number of threads</li>
 * </ul>
 * 
 * @author Izabella Marton
 *
 */

class Constants {

	static final long SEARCH_MIN_LIMIT = 100_000L;
	static final long SEARCH_MAX_LIMIT = 5_000_000_000L;
	static final int REQUIRED_INTERVAL_SIZE = 10_000;
	static final int MIN_INTERVAL_COUNT = 1;
	static final int MAX_INTERVAL_COUNT = 10_000;
	static final int MIN_THREAD_COUNT = 1;
	static final int MAX_THREAD_COUNT = 100;

	private Constants() {
	}

}
