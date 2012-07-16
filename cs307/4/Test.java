/*
 Many tests which look like they should fail, either because they give bogus
 codes or codes of the wrong length, do not. This is expected behavior. All user
 input is checked in Game.java, making it unnecessary to check in the methods.
 The constructors are not checked because it is assumed the programmer will know
 what she is doing when using them.
 
 A majority of my testing consisted of user input. Attempting to enter codes
 that were too long or had bogus characters made the game simply ask for another
 code. Testing for correct black/white pegs was easy with user input because
 the history of guesses was shown on the board.
 */

public class Test extends Game {
	public static void main (String[] args) {
		Code code = new Code();

		// Make sure the default constructors work
		System.out.println(code + "\n" + new Board(code) + "\n");
		
		// Try randomizing the code
		code.randomize();
		System.out.println(code + "\n" + new Board(code) + "\n");
		
		// Try replacing the code
		code.replace("BGOP");
		System.out.println(code + "\n" + new Board(code) + "\n");
		code.replace("Y");
		System.out.println(code + "\n" + new Board(code) + "\n");
		//code.replace("BGOPY");
		//System.out.println(code + "\n" + new Board(code) + "\n");
		code.replace("QQQQ");
		System.out.println(code + "\n" + new Board(code) + "\n");
		
		// Try all the constructors
		code = new Code("BGOP");
		System.out.println(code + "\n" + new Board(code) + "\n");
		code = new Code("B");
		System.out.println(code + "\n" + new Board(code) + "\n");
		//code = new Code("BGOPY");
		//System.out.println(code + "\n" + new Board(code) + "\n");
		code = new Code("QQQQ");
		System.out.println(code + "\n" + new Board(code) + "\n");
		code = new Code("ASDFq1", 6, new char[] {'A', 'S', 'D', 'F', 'q', '1'}, new String[6]);
		System.out.println(code + "\n" + new Board(code) + "\n");
		
		code = new Code();
		System.out.println("length: " + code.length() + "\n");
		System.out.println("colorAt(2): " + code.colorAt(2) + "\n");
		System.out.println("legalCode(\"QQQQ\"): " + code.legalCode("QQQQ") + "\n");
		System.out.println("legalCode(\"BGOPRY\"): " + code.legalCode("BGOPRY") + "\n");
		System.out.println("legalColorsList: " + code.legalColorsList() + "\n");
		code.replace("GOPR");
		int[] result = code.compare(new Code("GOPR"));
		System.out.println("compare: " + result[0] + " " + result[1] + "\n");
		result = code.compare(new Code("OPRG"));
		System.out.println("compare: " + result[0] + " " + result[1] + "\n");
		
		Board board = new Board(code);
		board.changeRow(1, code, "Changed!");
		System.out.println(board.getRow(1) + "\n");
		board.changeRow(12, code, "Also changed!");
		System.out.println(board + "\n");
		board = new Board(code, 5);
		System.out.println(board + "\n");
	}
}