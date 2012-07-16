import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.Math;
import java.util.HashMap;
import java.util.Map;

public class LowerBound {
	public static void main (String[] args) throws IOException {
		final int PRECISION = 3;

		Reader in;
		Map<Character, Integer> frequency;
		double entropy;
		double lowerBound;

		in = new FileReader(args[0]);
		frequency = analyzeFrequency(in);
		entropy = calculateEntropy(frequency);
		lowerBound = calculateLowerBound(frequency.size(), entropy);

		System.out.print(String.format("The lower bound is %." + PRECISION + "f; ", lowerBound));
		System.out.print(String.format("the entropy is %." + PRECISION + "f", entropy));
		System.out.print("\n");
	}

	public static Map<Character, Integer> analyzeFrequency (Reader in) throws IOException {
		int c;
		Map<Character, Integer> frequency;

		frequency = new HashMap<Character, Integer>();

		while ((c = in.read()) != -1) {
			Character key;
			Integer value;

			key = new Character((char) c);
			value = 1;
			
			if (frequency.containsKey(key)) {
				value += frequency.get(key);
			}

			frequency.put(key, value);
		}

		in.close();

		return frequency;
	}

	public static double calculateEntropy (Map<Character, Integer> frequency) {
		long sum;
		int mapSize;
		double probability;
		double entropy;

		sum = 0;
		mapSize = frequency.size();
		entropy = 0.0;

		for (Integer value : frequency.values()) {
			sum += value;
		}

		for (Integer value : frequency.values()) {
			probability = (double) value / sum;
			entropy += probability * Math.log(probability) / Math.log(2.0);
		}

		return -1 * entropy;
	}

	public static double calculateLowerBound (int numKeys, double entropy) {
		double lowerBound;

		lowerBound = numKeys * entropy;

		return lowerBound;
	}
}
