// Imports
import java.util.Random;

public class Code {
	private static final int LENGTH = 4;
	private static final char[] CHARS = {'B', 'G', 'O', 'P', 'R', 'Y'};
	private static final String[] STRINGS = {"blue", "green", "orange", "purple", "red", "yellow"};

	private int codeLength;
	private char[] codeChars;
	private String[] codeStrings;
	private char[] _code;

	// By default, use default values and randomize the code
	public Code () {
		this(LENGTH, CHARS, STRINGS);

		randomize();
	}

	// Use default values and set the code to the given string
	public Code (String code) {
		this(LENGTH, CHARS, STRINGS);

		replace(code);
	}
	
	// Uses values from the given Code
	public Code (Code code) {
		this(code.length(), code.legalChars(), code.legalStrings());
	}
	
	// Use values from the given Code and string
	public Code (String string, Code code) {
		this(string, code.length(), code.legalChars(), code.legalStrings());
	}

	// User-defined everything
	public Code (String code, int length, char[] chars, String[] strings) {
		this(length, chars, strings);

		replace(code);
	}

	// Used by every other constructor to set instance variables
	public Code (int length, char[] chars, String[] strings) {
		codeLength = length;
		codeChars = chars;
		codeStrings = strings;
		_code = new char[codeLength];
	}

	// Return the chars of the code as a string
	public String toString () {
		String result = "";

		for (char c : _code) {
			result += c;
		}

		return result;
	}

	// Change the code to a random one
	public void randomize () {
		Random r = new Random();

		for (int i = 0; i < codeLength; i++) {
			_code[i] = codeChars[r.nextInt(codeChars.length)];
		}
	}

	// Replace the code with the given String
	public void replace (String code) {
		for (int i = 0; i < code.length(); i++) {
			_code[i] = code.charAt(i);
		}
	}

	// Make sure the given string is the right length with the right colors
	public boolean legalCode (String code) {
		boolean result = true;
		boolean match;

		if (code.length() != codeLength) {
			result = false;
		}

		for (int i = 0; i < codeLength && result; i++) {
			match = false;

			for (int j = 0; j < codeChars.length && !match; j++) {
				if (code.charAt(i) == codeChars[j]) {
					match = true;
					result = true;
				} else {
					result = false;
				}
			}
		}

		return result;
	}

	// Compares the given code against _code and returns the number of black
	// and white pegs
	public int[] compare (Code code) {
		int blackPegs = 0;
		int whitePegs = 0;

		// Remembers which spots need to be searched
		boolean[][] inActive = new boolean[2][codeLength];

		// Looks for black pegs and deactive the cells they are in
		for (int i = 0; i < codeLength; i++) {
			if (code.colorAt(i) == _code[i]) {
				blackPegs++;
				inActive[0][i] = true;
				inActive[1][i] = true;
			}
		}

		// Looks for white pegs, ignoring deactivated cells
		for (int i = 0; i < codeLength; i++) {
		if (!inActive[0][i]) {
			for (int j = 0; j < codeLength; j++) {
			if (!inActive[1][j]) {
				if (code.colorAt(i) == _code[j]) {
					whitePegs++;
					inActive[0][i] = true;
					inActive[1][j] = true;
					break;
				}
			}
			}
		}
		}

		return new int[] {blackPegs, whitePegs};
	}

	// Returns the length of the code
	public int length () {
		return codeLength;
	}

	// Returns a character array of legal colors
	public char[] legalChars () {
		return codeChars;
	}
			 
	// Returns a string array of legal colors
	public String[] legalStrings () {
		return codeStrings;
	}

	// Returns the legal colors as a nicely formatted string (eg "blue (B)")
	public String legalColorsList () {
		String result = "";

		for (int i = 0; i < codeChars.length; i++) {
			result += codeStrings[i] + " (" + codeChars[i] + ")";

			if (i != codeChars.length - 1) {
				result += ", ";
			}

			if (i == codeChars.length - 2) {
				result += "or ";
			}
		}

		return result;
	}

	// Returns the color at the given index
	public char colorAt (int index) {
		return _code[index];
	}
}
