/*
 Student information for assignment 7
 
 On my honor, Taylor Fausak, this programming assignment is my own work.
 
 Student information
 Name: Taylor Fausak
 EID: tdf268
 E-mail address: tfausak@gmail.com
 TA name: Vishvas Vasuki
 Unique course ID: 55435
 
 Slip days information
 Slip days used on this assignment: 1
 Slip days I think I have used for the term thus far: 3
 */

// Imports
import java.io.File;
import javax.swing.JFileChooser;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class NameSurfer {
	// Constants for menu choices
	public static final int SEARCH = 1;
	public static final int ONE_NAME = 2;
	public static final int APPEAR_ONCE = 3;
	public static final int APPEAR_ALWAYS = 4;
	public static final int INCREASING_RANK = 5;
	public static final int DECREASING_RANK = 6;
	public static final int QUIT = 7;
	
	private static final int EPOCH = 1900;
	private static final int DEFAULT_SIZE = 11;

	// Main method (drives the whole program)
	public static void main(String[] args) {
		try {
			Scanner s = new Scanner(getFile());
			Names n = new Names(s);
			int choice;
			Scanner k = new Scanner(System.in);
			s.close();
			
			do {
				showMenu();
				choice = getChoice(k);
				
				switch (choice) {
					case SEARCH: search(n, k); break;
					case ONE_NAME: oneName(n, k); break;
					case APPEAR_ONCE: appearOnce(n); break;
					case APPEAR_ALWAYS: appearAlways(n); break;
					case INCREASING_RANK: increasingRank(n); break;
					case DECREASING_RANK: decreasingRank(n); break;
					default: System.out.println("Goodbye"); break;
				}
			} while (choice != QUIT);
		}
		catch (FileNotFoundException e) {
			System.out.println("Problem reading the data file. Exiting the program.");
		}
	}
	
	/*
	 Method that shows names that have always been decreasing.
	 pre: n != null
	 post: prints names that have always been decreasing
	 */
	private static void decreasingRank (Names n) {
		System.out.println("The following people consistently decrease rank: ");
		
		for (String name : n.decreasingRank()) {
			System.out.println(name);
		}
	}
	
	/*
	 Method that shows names that have always been increasing.
	 pre: n != null
	 post: prints names that have always been increasing
	 */
	private static void increasingRank (Names n) {
		System.out.println("The following people consistently increase rank: ");
		
		for (String name : n.increasingRank()) {
			System.out.println(name);
		}
	}
	
	/*
	 Method that shows names that have appeared in only one decade.
	 pre: n != null
	 post: prints names that have appeared in only one decade
	 */
	private static void appearOnce (Names n) {
		System.out.println("The following people have only been ranked once: ");
		
		for (String name : n.appearOnce()) {
			System.out.println(name);
		}
	}
	
	/*
	 Method that shows names that have appeared in every decade.
	 pre: n != null
	 post: prints names that have appeared in every decade
	 */
	private static void appearAlways (Names n) {
		System.out.println("The following people have always been ranked: ");
		
		for (String name : n.appearAlways()) {
			System.out.println(name);
		}
	}
	
	/*
	 Method that shows data for one name, or states that the name has never been ranked.
	 pre: n != null, k != null and is connected to System.in
	 post: prints data for one name, or prints a message indicating that the name has never been ranked
	 */
	private static void oneName (Names n, Scanner k) {
		System.out.print("Please enter a name to search for: ");
		String token = k.nextLine();
		NameRecord result = n.oneName(token);
		if (result != null) {
			System.out.println(result.getName() + "'s best decade was " + result.bestDecade() + ".");
			System.out.println("These are all of " + result.getName() + " rankings.");
			for (int i = 0; i < DEFAULT_SIZE; i++) {
				System.out.println("\t" + (EPOCH + (i * 10)) + ": " + result.getRank(i));
			}
		} else {
			System.out.println("Sorry, no name matches that.");
		}
	}
	
	/*
	 Method that shows all names that contain the given substring.
	 pre: n != null, k != null and is connected to System.in
	 post: prints all names that contain the given substring
	 */
	private static void search (Names n, Scanner k) {
		System.out.print("Please enter a string to search for: ");
		String token = k.nextLine();
		ArrayList<NameRecord> results = n.search(token);
		if (results.size() != 0) {
			System.out.println(results.size() + " matches found:");
			
			for (NameRecord record : results) {
				System.out.println(record.getName() + " " + record.bestDecade());
			}
		} else {
			System.out.println("Sorry, there were no results.");
		}
	}
	
	/*
	 Gets the user's choice
	 pre: keyboard != null and is connected to System.in
	 post: returns an in that is >= SEARCH and <= QUIT
	 */
	private static int getChoice (Scanner k) {
		int choice = getInt(k, "Please enter an option: ");
		k.nextLine();
		
		while (choice < SEARCH || choice > QUIT) {
			System.out.println(choice + " is not a valid option.");
			choice = getInt(k, "Please enter a valid option: ");
			k.nextLine();
		}
		
		return choice;
	}
	
	/*
	 Ensures than an int is entered from the keyboard
	 pre: k != null and is connected to System.in
	 */
	private static int getInt (Scanner k, String prompt) {
		System.out.print(prompt);
		
		while (!k.hasNextInt()) {
			k.next();
			System.out.println("That is not an integer.");
			System.out.print(prompt);
		}
		
		return k.nextInt();
	}

    /*
	 Shows the user menu.
	 */
	private static void showMenu() {
		System.out.println("Options:");
		System.out.println("Enter 1 to search for names.");
		System.out.println("Enter 2 to display data for one name.");
		System.out.println("Enter 3 to display all names that appear in only one decade.");
		System.out.println("Enter 4 to display all names that appear in all decades.");
		System.out.println("Enter 5 to display all names that consistently increase rank.");
		System.out.println("Enter 6 to display all names that consistently decrease rank.");
		System.out.println("Enter 7 to quit.");
	}
	
	/*
	 Method that chooses a file using a traditional (GUI) window.
	 post: returns the file chosen by the use or null if no file is picked
	 */
	public static File getFile() {
        JFileChooser chooser = new JFileChooser(".");
        int retval = chooser.showOpenDialog(null);
        File f = null;
		// NO NO NO! Programs shouldn't grab focus!
        //chooser.grabFocus();
		
        if (retval == JFileChooser.APPROVE_OPTION) {
			f = chooser.getSelectedFile();
		}
		
        return f;
    }
}
