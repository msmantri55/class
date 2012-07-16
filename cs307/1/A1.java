/*
	Student information for assignment

	On my honor, Taylor Fausak, this programming assignment is my own work.
	EID: tdf268
	E-mail address: tfausak@gmail.com
	TA name: Vishvas Vasuki
	Programming hours: 2
	Lines of code: 275

	Slip days info

	Slip days used on this assignment: 0
	Slip days I think I have used for the term thus far: 0
*/

// Imports
import java.util.ArrayList;

/*
	A class with two static methods for Assignment 1
*/

public class A1
{
	/*
		Main method contains some tests for methods matches and
		findPrimes.
		@param args not used
	*/

	public static void main (String[] args)
	{
		ArrayList<Integer> result;
		ArrayList<Integer> expected = new ArrayList<Integer>();

		// Test 1: matches
		result = matches("aaaa", "aaa");
		expected.add(0);
		expected.add(1);
		printTestResults(result, expected, 1);
		expected.clear();

		// Test 2: matches
		result = matches("aaa", "aaaa");
		printTestResults(result, expected, 2);

		// Test 3: matches
		result = matches("aabbaabbaa", "abb");
		expected.add(1);
		expected.add(5);
		printTestResults(result, expected, 3);
		expected.clear();

		// Test 4: matches
		result = matches("babcabc", "abc");
		expected.add(2);
		expected.add(5);
		printTestResults(result, expected, 4);
		expected.clear();


		// Test 5: matches
		result = matches("aaaabaaaa", "aaa");
		expected.add(0);
		expected.add(1);
		expected.add(5);
		expected.add(6);
		printTestResults(result, expected, 5);
		expected.clear();


		// Test 6: matches
		result = matches("aaabaaabaaabaa", "aab");
		expected.add(1);
		expected.add(5);
		expected.add(9);
		printTestResults(result, expected, 6);
		expected.clear();

		// Test 7: findPrimes
		result = findPrimes(2, 10);
		expected.add(2);
		expected.add(3);
		expected.add(5);
		expected.add(7);
		printTestResults(result, expected, 7);
		expected.clear();

		// Test 8: findPrimes
		result = findPrimes(900, 1010);
		int[] temp = {907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009};
		for(int i = 0; i < temp.length; i++)
		{
			expected.add( temp[i] );
		}
		printTestResults(result, expected, 8);
		expected.clear();

		// Test 9: findPrimes
		result = findPrimes(151, 193);
		temp = new int[] {151, 157, 163, 167, 173, 179, 187, 191, 193};
		for(int i = 0; i < temp.length; i++)
		{
			expected.add( temp[i] );
		}
		printTestResults(result, expected, 9);
		expected.clear();

		// Test 10: matches
		// My test looks for "ana" in a concatenated version of the
		// palindrome "A man, a plan, a canal, Panama!"
		result = matches("amanaplanacanalpanama", "ana");
		expected.add(2);
		expected.add(7);
		expected.add(11);
		expected.add(16);
		printTestResults(result, expected, 10);
		expected.clear();

		// Test 11: matches
		// My test looks for "me" in the word "awesome". Reference
		// to http://www.bustedtees.com/shirt/aweso/.
		result = matches("awesome", "me");
		expected.add(5);
		printTestResults(result, expected, 11);
		expected.clear();

		// Test 12: findPrimes
		// My test finds all of the primes from 2 to 100
		result = findPrimes(2, 100);
		int[] tmp = new int[] {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
		for (int i = 0; i < tmp.length; i++)
		{
			expected.add(tmp[i]);
		}
		printTestResults(result, expected, 12);
		expected.clear();

		// Test 13: findPrimes
		// My test finds all the prime years between my birth and
		// today.
		result = findPrimes(1989, 2008);
		expected.add(1993);
		expected.add(1997);
		expected.add(1999);
		expected.add(2003);
		printTestResults(result, expected, 13);
		expected.clear();
	}

	/**
	 * Find all indices in <tt>source</tt> that are the start of a complete
	 * match of <tt>target</tt>.
	 * @param  source  != null, source.length() > 0
	 * @param  target  != null, target.length() > 0
	 * @return  an ArrayList that contains the indices in source that are the
	 * start of a complete match of target. The indices are stored in
	 * ascending order in the ArrayList.
	 */

	public static ArrayList<Integer> matches (String source, String target)
	{
		// Check preconditions
		assert (source != null) && (source.length() > 0) : "matches: Violation of precondition";

		ArrayList<Integer> result = new ArrayList<Integer>();

		// This avoids having to calculate the length every time
		int sourceLength = source.length();
		int targetLength = target.length();

		for (int i = 0; i < sourceLength; i++)
		{
			boolean match = true;
			for (int c = 0; c < targetLength; c++)
			{
			// Checks for overflow first to avoid error
				if (i + c >= sourceLength || source.charAt(i + c) != target.charAt(c))
				{
					match = false;
				}
			}
			if (match)
			{
				result.add(i);
			}
		}

		return result;
	}

	/**
	 * Find all prime numbers between <tt>from</tt> and <tt>to</tt>, inclusive.
	 * @param  from   from > 1
	 * @param  to   to > 1, to >= from
	 * @return  an ArrayList that contains all of the prime numbers that occur
	 * between <tt>from</tt> and <tt>to</tt> inclusive. The primes numbers
	 * are stored in ascending order in the ArrayList. If there are no
	 * prime numbers that between <tt>from</tt> and <tt>to</tt> returns
	 * an ArrayList of size 0.
	 */

	public static ArrayList<Integer> findPrimes (int from, int to)
	{
		// Check preconditions
		assert (from > 1) && (to > 1) && (to >= from) : "findPrimes: Violation of precondition";

		ArrayList<Integer> results = new ArrayList<Integer>();

		// Make an array of all the numbers from 0 to to assuming
		// all numbers are prime to begin with.
		boolean[] primes = new boolean[to + 1];
		for (int i = 2; i <= to; i++)
		{
			primes[i] = true;
		}

		// Using the Sieve of Eratosthenes from 2 to sqrt(to)
		for (int i = 2; i * i <= to; i++)
		{
			// If i is prime, mark multiples as non-prime
			if (primes[i])
			{
				for (int j = i; i * j <= to; j++)
				{
					primes[i*j] = false;
				}
			}
		}

		// Add the results of the sieve to the ArrayList
		for (int i = from; i <= to; i++)
		{
			if (primes[i])
			{
				results.add(i);
			}
		}

		return results;
	}

	/*
		Helper method for showing results of tests
		@param result result != null
		@param expected expected != null
	*/

	private static void printTestResults (ArrayList<Integer> result, ArrayList<Integer> expected, int testNum)
	{
		System.out.println("Expected results: " + expected);
		System.out.println("Actual results: " + result);

		if (result.equals(expected))
		{
			System.out.println("Passed test " + testNum + ".");
		}
		else
		{
			System.out.println("Failed test " + testNum + ".");
		}

		System.out.println();
	}
}

/*
Short Code Examples
Number 1
Expected: 5
Actual: 2

Number 2
Expected: 2
Actual: 2

Number 3
Expected: 5.4
Actual: 5.4

Number 4
Expected: 5.4
Actual: 5.4

Number 5
Expected: 5.0
Actual: 5.0

Number 6
Expected: 5.4
Actual: 5.4

Number 7
Expected: 2.7
Actual: 2.0
The typecasting from int to double happens after division

Number 8
Expected: 0.666 (repeating)
Actual: 0.666 (repeating)

Number 9
Expected: 2.0
Actual: 2.0

Number 10
Expected: 0
Actual: -2147483648
I misread this one

Number 11
Expected: NOT 0.91, but I'm not sure what it really is.
It doesn't give the specific answer because floating-
point arithmetic is not precise. Java converts base-10
numbers to binary, which doesn't always work.
Actual: 0.9100000000000001

Number 12
Expected: NOT 999999999.99999999 
10
1000000010
Actual: 1.0E9
10.00000012522831
1.0E9
I didn't predict how off the math would be with doubles

Number 13
Expected: 16
Actual: 16

Number 14
Expected: 24
Actual: 24

Number 15
Expected: 7
Actual: 7

Number 16
Expected: 5
6
13 3 4
++ has preference with associativity to the right
Actual: 5
6
6 4 3
I thought it was 'x16 = x16_1 + ++x16_2'

Number 17
Expected: 6
6
7
Actual: 6
6
7

Number 18
Expected: compile error
Actual: compile error

Number 19
Expected: false
true
true
box191 and box 192 are pointers.
Actual: false
true
true

Number 20
Expected: compile error
Actual: compile error

Number 21
Expected: 12.0
Actual: 12.0

Number 22
Expected: -2
flooring rounds down, even for negative values.
Actual: -1
Different floor behavior than expected

Number 23
Expected: 2.0
3.0
You don't have to import java.lang.Math because the
java.lang package is imported by default.
Actual: 2.0
2.0
Misunderstood Math.rint()

Number 24
Expected: -49
runtime error
Actual: -49
ArrayIndexOutOfBoundsException

Number 25
Expected: 4

Number 26
Expected: compile error
Actual: compile error

Number 27
Expected: s261: We are a "work for the ni
s262: "work 
Actual: s261: ght is coming." culture.
s262: work
Misunderstood the substring method and miscounted

Number 28
Expected: 5 -2

Number 29
Expected: 1 39
Actual: 6 39
Miscalculated modulus

Number 30
Expected: 5 2
5 15
2 6
2 2
5 -3
2 2
2 4 5
Actual: 5 2
5 15
2 6
2 2
5 -3
2 2
2 4 5
*/
