/**
 * Taylor Fausak and Evan Volpe
 * CS337 Project 2
 * March 5, 2010
 *
 * Creates RSA public and private key pairs.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.util.Random;

public class KeyGen {
	// Number of bits in each prime number generated
	static final int BIT_LENGTH = 14;
	
	// File name to write keys to
	static final String OUTPUT_FILE_NAME = "key.txt";
	
	public static void main (String... args) {
		long n, e, d;
		long[] key;
		Writer writer;
		
		try {
			key = generate_key(BIT_LENGTH, new Random());
			n = key[0]; // modulus
			e = key[1]; // public (encrypt) key
			d = key[2]; // private (decrypt) key
			
			writer = new FileWriter(OUTPUT_FILE_NAME);
			writer.write(n + "\n" + e + "\n" + d + "\n");
			writer.flush();
			writer.close();
		} catch (IOException exception) {
			System.out.println(exception);
			System.out.println("Probable cause: file system trouble.");
			System.exit(1);
		}
	}

	/**
	 * Solves Bezout's identity using the extended Euclidean algorithm.
	 * @param a the first value
	 * @param b the second value
	 * @return two longs, x and y, such that ax + by = gcd(a,b)
	 */
	static long[] extended_gcd (long a, long b) {
		long[] bezout;
		long x, y;
		
		if (a == 0 && b == 0) {
			return new long[] {0, 0};
		}
		
		if (b == 0) {
			return new long[] {1, 0};
		}
		
		if (a == 0 || a % b == 0) {
			return new long[] {0, 1};
		}

		bezout = extended_gcd(b, a % b);
		x = bezout[0];
		y = bezout[1];

		return new long[] {y, x - y * (a / b)};
	}

	/**
	 * Calculates the greatest common divisor of two numbers.
	 * @param a the first value
	 * @param b the second value
	 * @return the greatest common divisor of a and b
	 */
	static long gcd (long a, long b) {
		if (b == 0) {
			return a;
		}

		return gcd(b, a - b * (a / b));
	}
	
	/**
	 * Generates an RSA key pair.
	 * @param length the bit length of primes used to create the modulus
	 * @param random the random number generator to use
	 * @return the modulus, the public key, and the private key
	 */
	static long[] generate_key (int length, Random random) {
		long p, q, n, phi, e, d;
		long[] bezout;
		
		assert(length > 2 && length < 64);
		
		p = generate_prime(length, random);
		q = generate_prime(length, random);
		n = p * q;
		phi = (p - 1) * (q - 1);
		
		assert(n > 16777216 && n < 1073741824); // n > 2^24 && n < 2^30

		// Generate an e such that 1 < e < phi and gcd(e, phi) = 1
		for (e = 2; e < phi; e++) {
			if (gcd(e, phi) == 1) {
				break;
			}
		}
		assert(e != phi);

		// Finds a d such that de = 1 mod phi
		bezout = extended_gcd(phi, e);
		d = bezout[1] % phi;
		if (d < 0) {
			d += phi; // d must be positive (% can return a negative number)
		}

		return new long[] {n, e, d};
	}

	/**
	 * Generates a prime number with the specified bit length.
	 * @param length the bit length of the generated prime
	 * @param random the random number generator to use
	 * @return a prime such that 2^length < prime < 2^(length + 1)
	 */
	static long generate_prime (int length, Random random) {
		BigInteger prime;
		long result;
		
		assert(length > 2 && length < 64);

		prime = BigInteger.probablePrime(length, random);
		result = prime.longValue();

		return result;
	}
}
