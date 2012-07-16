// Taylor Fausak (tdf268)
// CS 361, Fall 2009, Assignment 6

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class PasswordGenerator {
	private static final String dictionary = "/usr/share/dict/words";
	private HashMap<Integer, HashMap<String, Integer>> ngrams;
	private HashSet<String> words;

	public PasswordGenerator () {
		ngrams = new HashMap<Integer, HashMap<String, Integer>>();
		ngrams.put(0, new HashMap<String, Integer>());
		words = new HashSet<String>();
	}

	public void parse (File input) throws IOException {
		FileReader fr;
		BufferedReader br;
		String line;

		fr = new FileReader(input);
		br = new BufferedReader(fr);

		while ((line = br.readLine()) != null) {
			parseLine(line);
		}

		fr = new FileReader(dictionary);
		br = new BufferedReader(fr);

		while ((line = br.readLine()) != null) {
			line = line.toLowerCase().trim();

			if (line.length() < 4) {
				continue;
			}

			words.add(line);
		}
	}

	private void parseLine (String line) {
		line = line.toLowerCase();

		for (String word : line.split("[^a-z]")) {
			parseWord(word);
		}
	}

	private void parseWord (String word) {
		Object key, value;
		HashMap<String, Integer> frequency;
		char[] letters;

		if (word.length() < 2) {
			return;
		}

		key = word.charAt(0) + "";
		value = 1;
		frequency = ngrams.get(0);

		if (frequency.containsKey(key)) {
			value = frequency.get(key) + 1;
		}

		frequency.put((String) key, (Integer) value);
		ngrams.put(0, frequency);

		letters = word.toCharArray();

		for (int n = 1; n <= letters.length; n++) {
			frequency = new HashMap<String, Integer>();

			if (ngrams.containsKey(n)) {
				frequency = ngrams.get(n);
			}

			for (int i = 0; i <= letters.length - n; i++) {
				key = word.substring(i, i + n);
				value = 1;

				if (frequency.containsKey(key)) {
					value = frequency.get(key) + 1;
				}

				if (n == 1 && i == letters.length - n) {
					continue;
				}

				frequency.put((String) key, (Integer) value);
			}

			ngrams.put(n, frequency);
		}
	}

	public String generate (int order, int length, Random r) {
		String output, prefix;
		HashMap<String, Integer> next;
		Integer value;

		if (length < 1 || order < 0) {
			return "";
		}

		output = select(ngrams.get(0), r);

		if (length < 2) {
			return output;
		}

		for (int i = 1; i < length; i++) {
			next = new HashMap<String, Integer>();

			for (int o = order; o > 0; o--) {
				if (i - o + 1 < 0 || !ngrams.containsKey(o)) {
					continue;
				}

				for (String key : ngrams.get(o).keySet()) {
					prefix = output.substring(i - o + 1, i);
					
					if (key.startsWith(prefix)) {
						value = ngrams.get(o).get(key);
						key = key.charAt(o - 1) + "";
						
						next.put(key, value);
					}
				}

				if (!next.isEmpty()) {
					break;
				}
			}
			
			if (next.isEmpty()) {
				next.put((char) (r.nextInt(26) + 97) + "", 1);
			}

			output += select(next, r);
		}

		for (String word : words) {
			if (output.indexOf(word) != -1) {
				output += " * " + word;
				break;
			}
		}

		return output;
	}

	private String select (HashMap<String, Integer> map, Random r) {
		int sum, limit;
		String[] keys;

		sum = 0;

		for (Integer value : map.values()) {
			sum += value;
		}

		limit = r.nextInt(sum);
		sum = 0;

		keys = map.keySet().toArray(new String[0]);
		Arrays.sort(keys);

		for (String key : keys) {
			sum += map.get(key);

			if (sum > limit) {
				return key;
			}
		}

		return null;
	}

	// java PasswordGenerator order input characters passwords [seed]
	public static void main (String[] args) throws IOException {
		PasswordGenerator pg;
		File f;
		int order, length, limit;
		Random r;

		pg = new PasswordGenerator();
		f = new File(args[1]);
		order = Integer.parseInt(args[0]);
		length = Integer.parseInt(args[2]);
		limit = Integer.parseInt(args[3]);
		r = new Random();

		if (args.length > 4 && !args[4].equals("")) {
			r = new Random(Integer.parseInt(args[4]));
		}

		pg.parse(f);

		for (int i = 0; i < limit; i++) {
			System.out.println(pg.generate(order, length, r));
		}
	}
}
