/*
	A class to measure time elapsed
*/

public class Stopwatch {
	private long start;
	private long stop;
	
	public static final double NANOS_PER_SEC = 1000000000.0;
	
	/*
		Start the stop watch
	*/
	public void start () {
		System.gc();
		start = System.nanoTime();
	}
	
	/*
		Stop the stop watch
	*/
	public void stop () {
		stop = System.nanoTime();
	}
	
	/*
		Elapsed time in seconds
	*/
	public double time () {
		return (stop - start) / NANOS_PER_SEC;
	}
	
	/*
		Elapsed time in nanoseconds
	*/
	public long timeInNanoseconds () {
		return (stop - start);
	}
	
	public String toString () {
		return "Elapsed time: " + time() + " seconds";
	}
}