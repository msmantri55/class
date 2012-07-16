/*
	A class to measure time elapsed.
*/
public class Stopwatch {
	private long _start;
	private long _stop;
	private static final double NANOS_PER_SEC = 1000000000.0;

	public void start () {
		System.gc();
		_start = System.nanoTime();
	}

	public void stop () {
		_stop = System.nanoTime();
	}

	public double time () {
		return (_stop - _start) / NANOS_PER_SEC;
	}

	public String toString () {
		return "Elapsed time: " + time() + " seconds.";
	}

	public long timeInNanoseconds () {
		return (_stop - _start);
	}
}
