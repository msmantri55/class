import java.io.*;
import java.util.*;

public class Biba {
	private Vector<String> levels;
	private HashSet<String> categories;
	private HashMap<String, String> objectLevels;
	private HashMap<String, HashSet<String>> objectCategories;
	private HashMap<String, String> subjectLevels;
	private HashMap<String, HashSet<String>> subjectCategories;
	private HashMap<String, HashSet<String>> subjectObjects;

	public Biba () {
		levels = new Vector<String>();
		categories = new HashSet<String>();
		objectLevels = new HashMap<String, String>();
		objectCategories = new HashMap<String, HashSet<String>>();
		subjectLevels = new HashMap<String, String>();
		subjectCategories = new HashMap<String, HashSet<String>>();
	}

	public static void main (String[] args) throws IOException {
		BufferedReader input;
		String mode;
		Biba b;

		b = new Biba();

		if (args.length != 2) {
			System.err.println("Usage: java Biba <mode> <input>");
			System.exit(1);
		}

		input = new BufferedReader(new FileReader(args[1]));

		mode = args[0].toLowerCase();
		if (mode.equals("biba")) {
			b.biba(input);
		}
		else if (mode.equals("library")) {
			b.library(input);
		}
		else {
			System.err.println("Unknown mode");
			System.exit(1);
		}
	}

	public void biba (BufferedReader input) throws IOException {
		String line, command;
		String[] matches, arguments;

		while ((line = input.readLine()) != null) {
			if (line.trim().equals("")) {
				continue;
			}

			matches = line.toLowerCase().trim().split("\\s+");

			command = matches[0];
			arguments = new String[matches.length - 1];
			System.arraycopy(matches, 1, arguments, 0, arguments.length);

			if (command.equals("levels")) {
				this.bibaLevels(arguments);
			}
			else if (command.equals("categories")) {
				this.bibaCategories(arguments);
			}
			else if (command.equals("object")) {
				this.bibaObject(arguments);
			}
			else if (command.equals("subject")) {
				this.bibaSubject(arguments);
			}
			else if (command.equals("read")) {
				this.bibaRead(arguments);
			}
			else if (command.equals("write")) {
				this.bibaWrite(arguments);
			}
			else if (command.equals("add-cat")) {
				this.bibaAddCat(arguments);
			}
			else if (command.equals("remove-cat")) {
				this.bibaRemoveCat(arguments);
			}
			else {
				System.err.println("Unknown command: " + line.trim());
			}
		}
	}

	public void bibaLevels (String[] levels) {
		if (this.levels.size() != 0) {
			return;
		}

		for (String level : levels) {
			if (this.levels.contains(level)) {
				continue;
			}

			this.levels.add(level);
			System.out.println("Added level '" + level + "'.");
		}
	}

	public void bibaCategories (String[] categories) {
		for (String category : categories) {
			if (this.categories.contains(category)) {
				continue;
			}

			this.categories.add(category);
			System.out.println("Added category '" + category + "'.");
		}
	}

	public void bibaObject (String[] arguments) {
		String name, level, category;
		HashSet<String> categories;

		if (arguments.length < 2) {
			return;
		}

		name = arguments[0];
		level = arguments[1];
		categories = new HashSet<String>();

		if (!this.levels.contains(level)) {
			return;
		}

		for (int i = 2; i < arguments.length; i++) {
			category = arguments[i];

			if (!this.categories.contains(category)) {
				return;
			}

			categories.add(category);
		}

		this.objectLevels.put(name, level);
		this.objectCategories.put(name, categories);
		System.out.println("Created object '" + name + "' at level '" + level + "' with categories '" + categories + "'.");
	}

	public void bibaSubject (String[] arguments) {
		String name, level, category;
		HashSet<String> categories;

		if (arguments.length < 2) {
			return;
		}

		name = arguments[0];
		level = arguments[1];
		categories = new HashSet<String>();

		if (!this.levels.contains(level)) {
			return;
		}

		for (int i = 2; i < arguments.length; i++) {
			category = arguments[i];

			if (!this.categories.contains(category)) {
				return;
			}

			categories.add(category);
		}

		if (this.subjectLevels.containsKey(name)) {
			return;
		}

		this.subjectLevels.put(name, level);
		this.subjectCategories.put(name, categories);
		System.out.println("Created subject '" + name + "' at level '" + level + "' with categories '" + categories + "'.");
	}

	public boolean bibaRead (String[] arguments) {
		String subject, object, subjectLevel, objectLevel;
		HashSet<String> subjectCategories, objectCategories;

		if (arguments.length != 2) {
			return false;
		}

		subject = arguments[0];
		object = arguments[1];

		if (
			!this.subjectLevels.containsKey(subject) ||
			!this.objectLevels.containsKey(object) ||
			!this.subjectCategories.containsKey(subject) ||
			!this.objectCategories.containsKey(object)
		) {
			return false;
		}

		subjectLevel = this.subjectLevels.get(subject);
		objectLevel = this.objectLevels.get(object);
		subjectCategories = this.subjectCategories.get(subject);
		objectCategories = this.objectCategories.get(object);

		if (this.levels.indexOf(subjectLevel) > this.levels.indexOf(objectLevel)) {
			return false;
		}

		if (!subjectCategories.containsAll(objectCategories)) {
			return false;
		}

		System.out.println("Subject '" + subject + "' reads object '" + object + "'.");
		return true;
	}

	public boolean bibaWrite (String[] arguments) {
		String subject, object, subjectLevel, objectLevel;
		HashSet<String> subjectCategories, objectCategories;

		if (!(arguments.length == 2 || arguments.length == 3)) {
			return false;
		}

		subject = arguments[0];
		object = arguments[1];
		// arguments[2] can be the value (ignored)

		if (
			!this.subjectLevels.containsKey(subject) ||
			!this.objectLevels.containsKey(object) ||
			!this.subjectCategories.containsKey(subject) ||
			!this.objectCategories.containsKey(object)
		) {
			return false;
		}

		subjectLevel = this.subjectLevels.get(subject);
		objectLevel = this.objectLevels.get(object);
		subjectCategories = this.subjectCategories.get(subject);
		objectCategories = this.objectCategories.get(object);

		if (this.levels.indexOf(subjectLevel) < this.levels.indexOf(objectLevel)) {
			return false;
		}

		if (!subjectCategories.containsAll(objectCategories)) {
			return false;
		}

		System.out.println("Subject '" + subject + "' writes object '" + object + "'.");
		return true;
	}

	public void bibaAddCat (String[] arguments) {
		String object, category;
		HashSet<String> objectCategories;

		if (arguments.length != 2) {
			return;
		}

		object = arguments[0];
		category = arguments[1];

		if (!this.objectCategories.containsKey(object)) {
			return;
		}

		objectCategories = this.objectCategories.get(object);
		objectCategories.add(category);
		this.objectCategories.put(object, objectCategories);
		System.out.println("Added category '" + category + "' to object '" + object + "'.");
	}

	public void bibaRemoveCat (String[] arguments) {
		String object, category;
		HashSet<String> objectCategories;

		if (arguments.length != 2) {
			return;
		}

		object = arguments[0];
		category = arguments[1];

		if (!this.objectCategories.containsKey(object)) {
			return;
		}

		objectCategories = this.objectCategories.get(object);
		objectCategories.remove(category);
		this.objectCategories.put(object, objectCategories);
		System.out.println("Removed category '" + category + "' from object '" + object + "'.");
	}

	public void library (BufferedReader input) throws IOException {
		String line, command;
		String[] matches, arguments;

		this.bibaLevels(new String[] {"low"});

		while ((line = input.readLine()) != null) {
			if (line.trim().equals("")) {
				continue;
			}

			matches = line.toLowerCase().trim().split("\\s+");

			command = matches[0];
			arguments = new String[matches.length - 1];
			System.arraycopy(matches, 1, arguments, 0, arguments.length);

			if (command.equals("reader")) {
				this.libraryReader(arguments);
			}
			else if (command.equals("document")) {
				this.libraryDocument(arguments);
			}
			else if (command.equals("checkout")) {
				this.libraryCheckout(arguments);
			}
			else if (command.equals("return")) {
				this.libraryReturn(arguments);
			}
			else if (command.equals("revoke")) {
				this.libraryRevoke(arguments);
			}
			else {
				System.err.println("Unknown command: " + line.trim());
			}
		}
	}

	public void libraryReader (String[] arguments) {
		String subject;

		if (arguments.length != 1) {
			return;
		}

		subject = arguments[0];
		this.bibaCategories(new String[] {subject});
		this.bibaSubject(new String[] {subject, "low", subject});
	}

	public void libraryDocument (String[] arguments) {
		String object;

		if (arguments.length < 2) {
			return;
		}

		object = arguments[0];

		if (this.objectLevels.containsKey(object)) {
			for (int i = 1; i < arguments.length; i++) {
				this.bibaAddCat(new String[] {object, arguments[i]});
			}
		}
		else {
			this.bibaObject(arguments);
		}
	}

	public void libraryCheckout (String[] arguments) {
		String subject, object;
		HashSet<String> objects;

		if (arguments.length != 2) {
			return;
		}

		subject = arguments[1];
		object = arguments[0];
		objects = this.subjectObjects.get(subject);

		if (!bibaRead(new String[] {subject, object})) {
			return;
		}

		if (objects.contains(object)) {
			return;
		}

		this.subjectObjects.put(subject, objects);
	}

	public void libraryReturn (String[] arguments) {
		String subject, object;
		HashSet<String> objects;

		if (arguments.length != 2) {
			return;
		}

		subject = arguments[1];
		object = arguments[0];
		objects = this.subjectObjects.get(subject);

		objects.remove(object);

		this.subjectObjects.put(subject, objects);
	}

	public void libraryRevoke (String[] arguments) {
		if (arguments.length != 2) {
			return;
		}

		this.bibaRemoveCat(arguments);
	}
}
