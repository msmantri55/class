/**
 * Taylor Fausak and Evan Volpe
 * CS337 Project 2
 * March 5, 2010
 *
 * Tests key generation defined in KeyGen.java.
 */

// setenv CLASSPATH .:/lusr/share/lib/java/junit/junit-4.5.jar

import java.util.Random;
import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class KeyGenTest extends TestCase {
	public static void main (String... args) {
		TestRunner.run(new TestSuite(KeyGenTest.class));
	}
	
	public void test_extended_gcd1 () {
		long[] result = KeyGen.extended_gcd(0L, 0L);
		Assert.assertTrue(result.length == 2);
		Assert.assertTrue(result[0] == 0L);
		Assert.assertTrue(result[1] == 0L);
	}
	
	public void test_extended_gcd2 () {
		long[] result = KeyGen.extended_gcd(3L, 0L);
		Assert.assertTrue(result.length == 2);
		Assert.assertTrue(result[0] == 1L);
		Assert.assertTrue(result[1] == 0L);
	}
	
	public void test_extended_gcd3 () {
		long[] result = KeyGen.extended_gcd(0L, 3L);
		Assert.assertTrue(result.length == 2);
		Assert.assertTrue(result[0] == 0L);
		Assert.assertTrue(result[1] == 1L);
	}
	
	public void test_extended_gcd4 () {
		long[] result = KeyGen.extended_gcd(6L, 3L);
		Assert.assertTrue(result.length == 2);
		Assert.assertTrue(result[0] == 0L);
		Assert.assertTrue(result[1] == 1L);
	}
	
	public void test_extended_gcd5 () {
		long[] result = KeyGen.extended_gcd(7L, 3L);
		Assert.assertTrue(result.length == 2);
		Assert.assertTrue(result[0] == 1L);
		Assert.assertTrue(result[1] == -2L);
	}
	
	public void test_gcd1 () {
		long result = KeyGen.gcd(0L, 0L);
		Assert.assertTrue(result == 0L);
	}
	
	public void test_gcd2 () {
		long result = KeyGen.gcd(Long.MAX_VALUE, 0L);
		Assert.assertTrue(result == Long.MAX_VALUE);
	}
	
	public void test_gcd3 () {
		long result = KeyGen.gcd(0L, Long.MAX_VALUE);
		Assert.assertTrue(result == Long.MAX_VALUE);
	}
	
	public void test_gcd4 () {
		long result = KeyGen.gcd(Long.MIN_VALUE, 0L);
		Assert.assertTrue(result == 0 - Long.MIN_VALUE);
	}
	
	public void test_gcd5 () {
		long result = KeyGen.gcd(0L, Long.MIN_VALUE);
		Assert.assertTrue(result == 0 - Long.MIN_VALUE);
	}
	
	public void test_gcd6 () {
		long result = KeyGen.gcd(12345L, 54321L);
		Assert.assertTrue(result == 3L);
	}
	
	public void test_gcd7 () {
		long result = KeyGen.gcd(48611L, 104729L);
		Assert.assertTrue(result == 1L);
	}
	
	public void test_generate_key1 () {
		long[] result = KeyGen.generate_key(3, new Random(0));
		Assert.assertTrue(result.length == 3);
		Assert.assertTrue(result[0] == 25L);
		Assert.assertTrue(result[1] == 3L);
		Assert.assertTrue(result[2] == 11L);
	}
	
	public void test_generate_key2 () {
		long[] result = KeyGen.generate_key(14, new Random(0));
		Assert.assertTrue(result.length == 3);
		Assert.assertTrue(result[0] == 127181893L);
		Assert.assertTrue(result[1] == 11L);
		Assert.assertTrue(result[2] == 80919491L);
	}
	
	public void test_generate_prime1 () {
		long result = KeyGen.generate_prime(3, new Random(0));
		Assert.assertTrue(result == 5);
	}
	
	public void test_generate_prime2 () {
		long result = KeyGen.generate_prime(14, new Random(0));
		Assert.assertTrue(result == 12601);
	}
}
