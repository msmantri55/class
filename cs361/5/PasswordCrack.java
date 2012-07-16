import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public abstract class PasswordCrack {
	public static void main (String[] args) throws IOException {
		HashSet<String> words;
		HashSet<User> users;
		BufferedReader br;
		String line;

		words = new HashSet<String>();
		users = new HashSet<User>();

		// Add words to the set from the dictionary
		br = new BufferedReader(new FileReader(args[0]));
		while ((line = br.readLine()) != null) {
			words.add(line.toLowerCase().trim());
		}

		// Add users to the set from the passwd file
		br = new BufferedReader(new FileReader(args[1]));
		while ((line = br.readLine()) != null) {
			users.add(new User(line));
		}

		// Try progressively more obscure passwords until none are left
		for (int level = 0; !users.isEmpty(); level++) {
			HashSet<User> cracked = new HashSet<User>();

			// Show the current round number and users left to crack
			System.out.print("Round #" + (level + 1) + "\n\t");
			for (User user : users) {
				System.out.print(user.userName + " ");
			}
			System.out.print("\n");

			// Loop through uncracked users
			for (User user : users) {
				// Seed the word list with their name
				words.add(user.userName.toLowerCase());
				words.add(user.firstName.toLowerCase());
				words.add(user.lastName.toLowerCase());

				// Attempt every word in the list
				for (String word : words) {
					// If it worked...
					if (crack(user, word, level)) {
						// Flag the user as cracked
						cracked.add(user);

						// Print user name and password
						System.out.print(user.userName);
						System.out.print(": ");
						System.out.print(user.password);
						System.out.print("\n");

						// Stop checking words
						break;
					}
				}

				// Remove their name from the word list
				words.remove(user.userName.toLowerCase());
				words.remove(user.firstName.toLowerCase());
				words.remove(user.lastName.toLowerCase());
			}

			// Remove users whose password is now known
			for (User user : cracked) {
				users.remove(user);
			}
		}
	}

	public static boolean crack (User user, String word, int level) {
		HashSet<String> words = new HashSet<String>();
		
		// Seed the word list with the initial word
		words.add(word);

		// Add more manglings depending on the level
		// This gets big fast (200x increase per iteration)
		while (level-- > 0) {
			words = Mangle.applyAll(words);
			words = Mangle.truncate(words, 8);
		}

		// Attempt every word in the list
		for (String password : words) {
			if (user.attempt(password)) {
				return true;
			}
		}

		return false;
	}
}
