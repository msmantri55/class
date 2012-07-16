// -----------------------------
// projects/collatz/Collatz.java
// Copyright (C) 2010
// Glenn P. Downing
// -----------------------------

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

final class Collatz
{
	/**
	 * Reads two integers into an array.
	 *
	 * @param scanner reads input
	 * @param array stores input
	 * @return true on success, false otherwise
	 */
	public static boolean read (Scanner scanner, int[] array)
	{
		if (!scanner.hasNextInt())
		{
			return false;
		}

		array[0] = scanner.nextInt();
		array[1] = scanner.nextInt();

		return true;
	}

	/**
	 * Prints the lower bound, upper bound, and maximum cycle length.
	 *
	 * @param writer writes output
	 * @param bounds upper and lower bounds
	 * @param max maximum cycle length within the bounds
	 */
	public static void print (Writer writer, int[] bounds, int max) throws IOException
	{
		writer.write(bounds[0] + " ");
		writer.write(bounds[1] + " ");
		writer.write(max + "\n");

		writer.flush();
	}

	/**
	 * Computes the maximum cycle length in a range.
	 *
	 * @param bounds upper and lower bounds
	 * @return maximum cycle length within the bounds, zero on failure
	 */
	public static int eval (int[] bounds)
	{
		Map<Integer, Integer> cache;
		int lower_bound, upper_bound, cycle_length, max;

		if (bounds[0] < 1 || bounds[1] < 1)
		{
			return 0;
		}

		lower_bound = bounds[0];
		upper_bound = bounds[1];
		max = 0;
		cache = new HashMap<Integer, Integer>();

		if (bounds[1] < bounds[0])
		{
			lower_bound = bounds[1];
			upper_bound = bounds[0];
		}

		assert (lower_bound > 0) && (upper_bound > 0);
		assert lower_bound <= upper_bound;

		for (int n = 0; n <= 20; n++)
		{
			cache.put(1 << n, n + 1);
		}

		for (int n = lower_bound; n <= upper_bound; n++)
		{
			cycle_length = get_cycle_length(n, cache);

			assert cycle_length > 0;

			if (cycle_length > max)
			{
				max = cycle_length;
			}
		}

		assert max > 0;

		return max;
	}

	/**
	 * Computes the cycle length.
	 *
	 * @param n number to compute the cycle length of
	 * @param cache map of pre-computed values
	 * @return cycle length
	 */
	public static int get_cycle_length (int n, Map<Integer, Integer> cache)
	{
		int cycle_length;

		assert n > 0;

		cycle_length = 1;

		// This loop uses bitwise operators for better performance
		// m is a long because it overflows an int for some n > 419839
		for (long m = n; m != 1; cycle_length++)
		{
			if ((m & 1) == 1)
			{
				// Avoids a multiply: (3m+1)/2 = m + m/2 + 1
				m += (m >> 1) + 1;
				cycle_length++;
			}
			else
			{
				m >>= 1;
			}

			if (cache.containsKey(m))
			{
				cycle_length += cache.get(m);
				break;
			}
		}

		assert cycle_length > 0;

		cache.put(n, cycle_length);

		return cycle_length;
	}
}
