public class Board {
	private static final int GUESSES = 12;

	private int numGuesses;
	private Code secretCode;
	private String[] _board;

	// Default number of guesses
	public Board (Code code) {
		this(code, GUESSES);
	}

	// User-defined number of guesses
	public Board (Code code, int guesses) {
		numGuesses = guesses;
		secretCode = code;

		_board = new String[numGuesses + 1];
		String blank = "";

		for (int i = 0; i < secretCode.length(); i++) {
			blank += "-";
		}

		for (int i = 0; i < _board.length; i++) {
			_board[i] = blank;
		}

		_board[0] += " Secret Code";
	}

	// Changes the specified row to have the given code and result
	public void changeRow (int row, Code code, String result) {
		_board[row] = code + " " + result;
	}

	// Returns the given row
	public String getRow (int row) {
		return _board[row];
	}

	// Returns the number of guesses
	public int getGuesses () {
		return numGuesses;
	}

	// Returns each row of _board on its own line
	public String toString () {
		String result = "";

		for (int i = 0; i < _board.length; i++) {
			result += _board[i];

			if (i < _board.length - 1) {
				result += "\n";
			}
		}

		return result;
	}
}
