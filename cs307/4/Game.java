// Imports
import java.util.Scanner;

public class Game {
	private boolean developer;
	private Code playerCode, secretCode;
	private Board gameBoard;

	// By default, developer mode is turned off
	public Game () {
		this(false);
	}

	public Game (boolean dev) {
		developer = dev;
	}

	public void runGames() {
		boolean play = true;
		boolean firstTime = true;
		int[] result;

		// While the user wants to play...
		while (play) {
			// To change the length of the code or allowable colors, only this
			// line needs to be changed
			secretCode = new Code();
			// playerCode is instantiated to have the same length and colors
			playerCode = new Code(secretCode);
			secretCode.randomize();
			// To change the number of guesses, change only this line
			gameBoard = new Board(secretCode);
			
			// Print out the introduction if it's the player's first time
			if (firstTime) {
				printIntro();
				firstTime = false;
			}

			// Loop through all the player's guesses
			for (int guess = 1; guess <= gameBoard.getGuesses() && play; guess++) {
				// Avoids "You have 1 guesses left"
				if (guess < gameBoard.getGuesses()) {
					centerString("You have " + (gameBoard.getGuesses() - guess + 1) + " guesses left");
				} else {
					centerString("You have 1 guess left");
				}

				// Show the secret code if developer mode is on
				if (developer) {
					centerString("The secret code is " + secretCode);
				}

				// Ask the player for a code. askForGuess uses secretCode to
				// ensure playerCode is legal (i.e., correct length, valid
				// colors).
				playerCode.replace(askForGuess(secretCode));

				// Find out how many pegs the player got
				result = playerCode.compare(secretCode);
				gameBoard.changeRow(guess, playerCode, "Result: " + resultToString(result));

				// If they guessed the secret code...
				if (result[0] == secretCode.length()) {
					System.out.println();
					
					// Show the secret code on the board
					gameBoard.changeRow(0, secretCode, "");

					// Print all the guesses they made
					for (int i = 0; i <= guess; i++) {
						System.out.println(gameBoard.getRow(i));
					}

					System.out.println();
					centerString("Congratulations! You solved the puzzle!\n");
					
					// Ask if the player wants to play another game. If they do,
					// break to reset the counter and generate a new code.
					play = playAgain();
					if (play) {
						System.out.println();
						break;
					}
				} else if (guess == gameBoard.getGuesses()) {
					System.out.println(gameBoard);
					System.out.println();
					centerString("Sorry, you did not solve the puzzle.\n");
					
					// Ask if the player wants to play another game. If they do,
					// break to reset the counter and generate a new code.
					play = playAgain();
					if (play) {
						System.out.println();
						break;
					}
				} else {
					// If they didn't win or run out of guesses, print the board
					System.out.println(gameBoard);
				}
			}
		}
	}

	// Find out if the player wants to play again
	private boolean playAgain () {
		Scanner s = new Scanner(System.in);
		System.out.print("Enter Y if you want to play again: ");
		String again = s.nextLine();

		// I'm not picky about case
		if (again.equals("Y") || again.equals("y")) {
			return true;
		} else {
			return false;
		}
	}

	// Converts the int[] result to a string result
	private String resultToString (int[] array) {
		String result = "";

		// The first row is the number of black pegs
		for (int i = 0; i < array[0]; i++) {
			result += "Black ";
		}

		// The second row is the number of white pegs
		for (int i = 0; i < array[1]; i++) {
			result += "White ";
		}

		return result;
	}

	// Prompts the user for their guess
	private String askForGuess (Code code) {
		Scanner s = new Scanner(System.in);
		String guess = "";

		// Compare against the code given to make sure it is legal
		while (!code.legalCode(guess)) {
			System.out.print("\nPlease enter your next guess: ");

			guess = s.nextLine();
		}

		return guess;
	}

	// Centers a string on an 80-character wide terminal
	public void centerString (String string) {
		String result = "";

		for (int i = 0; i < 40 - (string.length() / 2); i++) {
			result += " ";
		}

		System.out.println(result + string);
	}

	// Prints the introductory text and instructions
	public void printIntro () {
		System.out.println();
		centerString("Welcome to Mastermind!\n");
		System.out.println("This is a text-based version of the classic board game Mastermind.");
		System.out.println("The computer will think of a secret code that you have to guess in " + gameBoard.getGuesses() + " guesses.");
		System.out.println("It will have " + secretCode.length() + " colored pegs that will be one of " + (secretCode.legalChars()).length + " colors:");
		System.out.println(" " + secretCode.legalColorsList() + "\n");
		System.out.println("Try to guess what colored pegs are in the code (the same color may appear more");
		System.out.println("than once!) and what order they are in. After making a guess, the result will");
		System.out.println("be displayed. The result consists of a black peg for each peg that you have");
		System.out.println("exactly correct (color and position). For each peg that is the correct color");
		System.out.println("but out of position, you get a white peg.\n");
	}
}