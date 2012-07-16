/**
 * Taylor Fausak and Evan Volpe
 * CS337 Project 2
 * March 5, 2010
 *
 * Tests encryption and decryption defined in RSA.java.
 */

// setenv CLASSPATH .:/lusr/share/lib/java/junit/junit-4.5.jar

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringBufferInputStream;
import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class RSATest extends TestCase  {
	public static void main (String... args) {
		TestRunner.run(new TestSuite(RSATest.class));
	}

	public static void test_decrypt1 () throws IOException {
		InputStream input = new ByteArrayInputStream(new byte[] {2, 51, -59, -102});
		OutputStream output = new ByteArrayOutputStream();
		RSA.decrypt(124815919L, 99834797L, input, output);
		byte[] bytes = ((ByteArrayOutputStream) output).toByteArray();
		Assert.assertTrue(bytes.length == 1);
		Assert.assertTrue(bytes[0] == 65);
	}

	public static void test_encrypt1 () throws IOException {
		InputStream input = new StringBufferInputStream("A");
		OutputStream output = new ByteArrayOutputStream();
		RSA.encrypt(124815919L, 5L, input, output);
		byte[] bytes = ((ByteArrayOutputStream) output).toByteArray();
		Assert.assertTrue(bytes.length == 4);
		Assert.assertTrue(bytes[0] == 2);
		Assert.assertTrue(bytes[1] == 51);
		Assert.assertTrue(bytes[2] == -59);
		Assert.assertTrue(bytes[3] == -102);
	}
	
	public static void test_pow_mod1 () {
		Assert.assertTrue(RSA.pow_mod(3, 8, 5) == 1);
	}
}
