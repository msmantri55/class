import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.IllegalArgumentException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class LZcoding {
	static final int BLOCK_SIZE = 128;
	static final String C_EXT = ".cpz";
	static final String D_EXT = ".dpz";

	public static void main (String[] args) throws FileNotFoundException, IOException {
		try {
			if (args.length != 2) {
				throw new IllegalArgumentException();
			}

			Reader in = new FileReader(args[1]);
			Writer out;

			if (args[0].equals("c")) {
				out = new FileWriter(args[1] + C_EXT);
				compress(in, out);
			}
			else if (args[0].equals("d")) {
				out = new FileWriter(args[1] + D_EXT);
				decompress(in, out);
			}
			else {
				throw new IllegalArgumentException();
			}

			in.close();
			out.close();
		}
		catch (IllegalArgumentException e) {
			System.out.println("Compress: java LZcoding c input");
			System.out.println("Decompress: java LZcoding d input");
			System.exit(1);
		}
	}

	public static void compress (Reader in, Writer out) throws IOException {
		Map<Integer, String> d;
		Map<String, Integer> y;
		String buffer, word;
		int character, index, i;
		byte[] bytes;

		i = 0;
		buffer = "";
		d = new HashMap<Integer, String>();
		d.put(i, buffer);
		y = new HashMap<String, Integer>();
		y.put(buffer, i);

		while ((character = in.read()) != -1) {
			buffer += (char) character;

			if (y.containsKey(buffer)) {
				continue;
			}

			word = buffer.substring(0, buffer.length() - 1);
			assert y.containsKey(word);

			index = y.get(word);
			assert index >= 0;

			bytes = encode(new Pair(index, (char) character));
			assert bytes.length == 3;

			out.write(bytes[0]);
			out.write(bytes[1]);
			out.write(bytes[2]);
			out.write((char) character);

			i++;
			d.put(i, buffer);
			y.put(buffer, i);
			buffer = "";
		}

		out.flush();
	}

	public static void decompress (Reader in, Writer out) throws IOException {
		int high, mid, low, character, index;
		Pair pair;
		Vector<String> dictionary;
		String word;
		byte[] bytes;

		dictionary = new Vector<String>();
		dictionary.add("");

		while ((high = (byte) in.read()) != -1) {
			mid = (byte) in.read();
			low = (byte) in.read();
			character = (char) in.read();

			bytes = new byte[] {(byte) high, (byte) mid, (byte) low};
			pair = decode(bytes, (char) character);

			word = dictionary.get(pair.index) + (char) character;
			dictionary.add(word);
			out.write(word);
		}

		out.flush();
	}

	public static byte[] encode (Pair pair) {
		int tmp, low, mid, high;

		tmp = pair.index / BLOCK_SIZE;
		low = pair.index % BLOCK_SIZE;
		high = tmp / BLOCK_SIZE;
		mid = tmp % BLOCK_SIZE;

		return new byte[] {(byte) high, (byte) mid, (byte) low};
	}

	public static Pair decode (byte[] bytes, char character) {
		int high, mid, low, index;

		assert bytes.length == 3;
		high = bytes[0] * BLOCK_SIZE * BLOCK_SIZE;
		mid = bytes[1] * BLOCK_SIZE;
		low = bytes[2];
		index = high + mid + low;

		return new Pair(index, character);
	}

	static class Pair {
		final int index;
		final char character;

		Pair (int index, char character) {
			this.index = index;
			this.character = character;
		}
	}

	static class Trie {
	}
}
