// setenv CLASSPATH .:/lusr/share/lib/java/junit/junit-4.5.jar
// javac -Xlint TestPrimes.java
// java -ea TestPrimes

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Scanner;
import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class TestPrimes extends TestCase {
	public static void main (String[] args) {
		TestRunner.run(new TestSuite(TestPrimes.class));
	}

	// testRead ------------------------------------------------------------

	// Can it deal with typical input?
	public void testRead1 () {
		Scanner scanner = new Scanner("8");
		int[] value = new int[1];
		boolean result = Primes.read(scanner, value);

		Assert.assertTrue(result == true);
		Assert.assertTrue(value[0] == 8);
	}

	// Can it deal with zeros?
	public void testRead2 () {
		Scanner scanner = new Scanner("0");
		int[] value = new int[1];
		boolean result = Primes.read(scanner, value);

		Assert.assertTrue(result == true);
		Assert.assertTrue(value[0] == 0);
	}

	// Can it deal with huge numbers?
	public void testRead3 () {
		Scanner scanner = new Scanner("" + Integer.MAX_VALUE);
		int[] value = new int[1];
		boolean result = Primes.read(scanner, value);

		Assert.assertTrue(result == true);
		Assert.assertTrue(value[0] == Integer.MAX_VALUE);
	}

	// Can it deal with negative numbers?
	public void testRead4 () {
		Scanner scanner = new Scanner("-1");
		int[] value = new int[1];
		boolean result = Primes.read(scanner, value);

		Assert.assertTrue(result == true);
		Assert.assertTrue(value[0] == -1);
	}

	// Can it deal with white space?
	public void testRead5 () {
		Scanner scanner = new Scanner(" \n\t1 \n\t");
		int[] value = new int[1];
		boolean result = Primes.read(scanner, value);

		Assert.assertTrue(result == true);
		Assert.assertTrue(value[0] == 1);
	}

	// testPrint -----------------------------------------------------------

	// Can it deal with typical output?
	public void testPrint1 () throws IOException {
		Writer writer = new StringWriter();
		int[] values = new int[] {2, 3, 5, 7};
		Primes.print(writer, values);
		String result = writer.toString();

		Assert.assertTrue(result.equals("2 3 5 7\n"));
	}

	// Can it deal with huge numbers?
	public void testPrint2 () throws IOException {
		Writer writer = new StringWriter();
		int[] values = new int[] {Integer.MAX_VALUE, 3, 5, 7};
		Primes.print(writer, values);
		String result = writer.toString();

		Assert.assertTrue(result.equals(Integer.MAX_VALUE + " 3 5 7\n"));
	}

	// Can it deal with impossible output?
	public void testPrint3 () throws IOException {
		Writer writer = new StringWriter();
		int[] values = new int[] {};
		Primes.print(writer, values);
		String result = writer.toString();

		Assert.assertTrue(result.equals("Impossible.\n"));
	}

	// testEval ------------------------------------------------------------
	
	// Can it deal with typical input?
	public void testEval1 () {
		int value = 8;
		int[] result = Primes.eval(value);
		int[] correct = new int[] {2, 2, 2, 2};

		Assert.assertTrue(Arrays.equals(result, correct));
	}
	
	// Can it deal with huge input?
	public void testEval2 () {
		int value = 10000000;
		int[] result = Primes.eval(value);
		int[] correct = new int[] {2, 2, 5, 9999991};

		Assert.assertTrue(Arrays.equals(result, correct));
	}
	
	// Can it deal with input that fails?
	public void testEval3 () {
		int value = 7;
		int[] result = Primes.eval(value);
		int[] correct = new int[] {};

		Assert.assertTrue(Arrays.equals(result, correct));
	}
	
	// Can it deal with negative input that fails?
	public void testEval4 () {
		int value = -1;
		int[] result = Primes.eval(value);
		int[] correct = new int[] {};

		Assert.assertTrue(Arrays.equals(result, correct));
	}

	// testIsPrime ---------------------------------------------------------
	
	// Can it deal with typical prime input?
	public void testIsPrime1 () {
		int value = 2;
		boolean result = Primes.isPrime(value);

		Assert.assertTrue(result);
	}
	
	// Can it deal with typical composite input?
	public void testIsPrime2 () {
		int value = 4;
		boolean result = Primes.isPrime(value);

		Assert.assertFalse(result);
	}
}
