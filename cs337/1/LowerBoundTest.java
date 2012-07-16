// setenv CLASSPATH .:/lusr/share/lib/java/junit/junit-4.5.jar

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class LowerBoundTest extends TestCase {
	public static void main (String[] args) {
		TestRunner.run(new TestSuite(LowerBoundTest.class));
	}

	// analyzeFrequency ----------------------------------------------------

	// Dealing with empty input
	public void testAnalyzeFrequency1 () throws IOException {
		Reader in = new StringReader("");
		Map<Character, Integer> frequency = LowerBound.analyzeFrequency(in);

		Assert.assertTrue(frequency.equals(new HashMap<Character, Integer>()));
	}

	// Simple input
	public void testAnalyzeFrequency2 () throws IOException {
		Reader in = new StringReader("a");
		Map<Character, Integer> frequency = LowerBound.analyzeFrequency(in);
		Map<Character, Integer> result = new HashMap<Character, Integer>();
		result.put('a', 1);

		Assert.assertTrue(frequency.equals(result));
	}

	// calculateEntropy ----------------------------------------------------

	// Dealing with empty input
	public void testCalculateEntropy1 () {
		Map<Character, Integer> frequency = new HashMap<Character, Integer>();
		double entropy = LowerBound.calculateEntropy(frequency);

		Assert.assertTrue(entropy == 0.0);
	}

	// Simple alphabet and frequencies
	public void testCalculateEntropy2 () {
		Map<Character, Integer> frequency = new HashMap<Character, Integer>();
		frequency.put('a', 1);
		frequency.put('b', 1);
		double entropy = LowerBound.calculateEntropy(frequency);

		Assert.assertTrue(entropy == 1.0);
	}

	// calculateLowerBound -------------------------------------------------

	// Not much to be tested here
	public void testCalculateLowerBound () {
		int numKeys = 2;
		double entropy = 1.0;
		double lowerBound = LowerBound.calculateLowerBound(numKeys, entropy);

		Assert.assertTrue(lowerBound == 2.0);
	}
}
