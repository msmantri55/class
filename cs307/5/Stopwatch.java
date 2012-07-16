/*
 A class to measure time elapsed
 */
public class Stopwatch {
	private long startTime;
	private long stopTime;
	
	public static final double NANOS_PER_SEC = 1000000000.0;
	
	/*
	 Start the stopwatch
	 */
	public void start () {
		System.gc();
		startTime = System.nanoTime();
	}
	
	/*
	 Stop the stopwatch
	 */
	public void stop () {
		stopTime = System.nanoTime();
	}
	
	/*
	 Elapsed time in seconds
	 */
	public double time () {
		return (stopTime - startTime) / NANOS_PER_SEC;
	}
	
	/*
	 Elapsed time in nanoseconds
	 */
	public long timeInNanoseconds () {
		return stopTime - startTime;
	}
	
	public String toString () {
		return "Elapsed time: " + time() + " seconds.";
	}
}