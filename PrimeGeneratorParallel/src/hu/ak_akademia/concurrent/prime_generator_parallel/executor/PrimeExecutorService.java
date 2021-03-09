package hu.ak_akademia.concurrent.prime_generator_parallel.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import hu.ak_akademia.concurrent.prime_generator_parallel.main.Input;
import hu.ak_akademia.concurrent.prime_generator_parallel.main.SimplePrimeFinder;
import hu.ak_akademia.concurrent.prime_generator_parallel.utility.PrimeMathUtility;

/**
 * <p>
 * The PrimeExecutorService class is the brain or heart of this mutli-threaded
 * application.
 * </p>
 * <p>
 * After creating a Fixed Thread Pool (the number of threads is set in the Input
 * class) it creates a list of futures (using FindPrimesTask class) and then
 * collects the primes generated by futures into a list.
 * </p>
 * <p>
 * It's important that in order to optimize time complexity of the application,
 * each future is diminished after completing its task and providing primes
 * for the program.
 * </p>
 * 
 * @author Izabella Marton
 *
 */
public class PrimeExecutorService {

	private double sqRoot;
	private long maxLimit;
	private int intervalumCount;
	private int threadCount;
	private List<Integer> primesUnderSqRoot;

	public PrimeExecutorService(Input input) {
		this.sqRoot = input.getSqRoot();
		this.maxLimit = input.getMaxLimit();
		this.intervalumCount = input.getIntervalumCount();
		this.threadCount = input.getThreadCount();
		this.primesUnderSqRoot = new SimplePrimeFinder(sqRoot).getPrimesUnderLimit();
	}

	public List<Long> run() throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		List<Future<List<Long>>> futures = doSubmission(executor);
		executor.shutdown();
		List<Long> primes = process(futures);
		primes.addAll(primesUnderSqRoot.stream().mapToLong(Integer::longValue).boxed().collect(Collectors.toList()));
		Collections.sort(primes);
		return primes;
	}

	private List<Future<List<Long>>> doSubmission(ExecutorService executor) {
		List<Future<List<Long>>> futures = new ArrayList<>(intervalumCount);
		double intervalumSize = (maxLimit - sqRoot) / intervalumCount;
		double sqRootCeil = Math.ceil(sqRoot);
		for (int i = 0; i < intervalumCount; i++) {
			FindPrimesTask findPrimesTask = new FindPrimesTask(Math.round(sqRootCeil + i * intervalumSize),
					Math.round(sqRootCeil + (i + 1) * intervalumSize), primesUnderSqRoot);
			Future<List<Long>> future = executor.submit(findPrimesTask);
			futures.add(future);
		}
		return futures;
	}

	private List<Long> process(List<Future<List<Long>>> futures) throws InterruptedException, ExecutionException {
		List<Long> totalPrimes = new ArrayList<>(PrimeMathUtility.highBoundForPrimeCount(0L, maxLimit));
		while (!futures.isEmpty()) {
			for (Iterator<Future<List<Long>>> itr = futures.iterator(); itr.hasNext();) {
				Future<List<Long>> future = itr.next();
				if (future.isDone()) {
					totalPrimes.addAll(future.get());
					itr.remove();
				}
			}
		}
		return totalPrimes;
	}

}
