/*
	Student information for assignment 4

	On our honor, Taylor Fausak and Jacob Streckfus, this programming
	assignment is our own work.

	Student #1
	Name: Taylor Fausak
	EID: tdf268
	Email: tfausak@gmail.com
	TA: Vishvas
	Unique: 55435

	Student #2
	Name: Jacbok Streckfus
	EID: jws2564
	Email: jacobstreckfus@mail.utexas.edu
	TA: Ruchica
	Unique: 

	NOTE: I (Taylor Fausak) spent some time after Jacob and I completed this
	assignment to re-work sections that I thought needed a little help. In
	general, I made it much easier to change the length of the code, legal
	colors, and the number of guesses.

	Also, I do not use assertions in most of my methods. According to the
	Java documentation, "Do NOT use assertions for argument checking in
	public methods." I do error check user input, but if something is set
	up wrong in the code, an IdexOutOfBoundsException or similar will
	happen.
*/

public class MastermindDriver {
	public static void main (String[] args) {
		Game g = new Game(true);
		g.runGames();
		g = new Game(false);
		g.runGames();
	}
}
