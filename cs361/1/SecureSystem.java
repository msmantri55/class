/*
 Taylor Fausak
 12 September 2009
 http://www.cs.utexas.edu/users/byoung/cs361/assignment1-nonthreaded.html

 Originally, I wrote this as described in the assignment - with classes for
 everything. My classes ended up being wrappers around Integers (SecureSubject
 and SecureObject) or HashMaps (ObjectManager and ReferenceMonitor).

 So I made SecureSystem handle everything. I'm counting on the classes not being
 necessary for the next assignment (or a good grade, hopefully).

 Also, I used enums instead of final ints throughout, as recommended by Sun.

 Finally, I overloaded toString instead of writing printInstruction or
 printState because there wasn't a reason not to do this.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class SecureSystem {
	// Instance variables
	
	private HashMap<String, Integer> subjects;
	private HashMap<String, Integer> objects;
	private HashMap<String, SecurityLevel> subjectLevels;
	private HashMap<String, SecurityLevel> objectLevels;
	
	// Constructors
	
	public SecureSystem () {
		subjects = new HashMap<String, Integer>();
		objects = new HashMap<String, Integer>();
		subjectLevels = new HashMap<String, SecurityLevel>();
		objectLevels = new HashMap<String, SecurityLevel>();
	}
	
	// Main
	
	public static void main (String args[]) throws IOException {
		File f;
		FileReader fr;
		BufferedReader br;
		String line;
		SecureSystem ss;
		Instruction i;

		if (args.length != 1) {
			System.err.println("Usage: java SecureSystem <instruction_list>");
			System.exit(1);
		}
		
		ss = new SecureSystem();
		
		ss.createSubject("lyle", SecurityLevel.low);
		ss.createSubject("hal", SecurityLevel.high);
		
		ss.createObject("lobj", SecurityLevel.low);
		ss.createObject("hobj", SecurityLevel.high);

		f = new File(args[0]);
		fr = new FileReader(f);
		br = new BufferedReader(fr);

		while ((line = br.readLine()) != null) {
			i = new Instruction(line);
			
			if (i.valid()) {
				switch (i.getOperation()) {
					case read:
						ss.read(i.getSubject(), i.getObject());
						break;
					case write:
						ss.write(i.getSubject(), i.getObject(), i.getValue());
						break;
					default:
						break;
				}
			}
			
			System.out.println(i);
			System.out.println(ss);
		}
	}
	
	// Methods
	
	public void createSubject (String name, SecurityLevel level) {
		this.subjects.put(name, 0);
		this.subjectLevels.put(name, level);
	}
	
	public void createObject (String name, SecurityLevel level) {
		this.createObject(name, level, 0);
	}
	
	public void createObject (String name, SecurityLevel level, Integer value) {
		this.objects.put(name, value);
		this.objectLevels.put(name, level);
	}
	
	public Integer read (String subject, String object) {
		Integer subjectLevel;
		Integer objectLevel;
		Integer objectValue;
		
		if (!this.subjectLevels.containsKey(subject) || !this.objectLevels.containsKey(object)) {
			return 0;
		}
		
		subjectLevel = this.subjectLevels.get(subject).level();
		objectLevel = this.objectLevels.get(object).level();
		
		if (subjectLevel < objectLevel) {
			return 0;
		}
		
		objectValue = this.objects.get(object);
		
		this.subjects.put(subject, objectValue);
		
		return objectValue;
	}
	
	public void write (String subject, String object, Integer value) {
		Integer subjectLevel;
		Integer objectLevel;
		
		if (!this.subjectLevels.containsKey(subject) || !this.objectLevels.containsKey(object)) {
			return;
		}

		subjectLevel = this.subjectLevels.get(subject).level();
		objectLevel = this.objectLevels.get(object).level();
		
		if (subjectLevel > objectLevel) {
			return;
		}
		
		this.objects.put(object, value);
	}
	
	public String toString () {
		String result;
		Set keys;
		Iterator i;
		
		result = "The current state is:\n";
		
		keys = this.objects.keySet();
		i = keys.iterator();
		while (i.hasNext()) {
			Object next = i.next();
			result += "\t(object) " + next + ": " + this.objects.get(next) + "\n";
		}
		
		keys = this.subjects.keySet();
		i = keys.iterator();
		while (i.hasNext()) {
			Object next = i.next();
			result += "\t(subject) " + next + ": " + this.subjects.get(next) + "\n";
		}
		
		return result;
	}
}

class Instruction {
	// Instance variables
	
	private String line;
	private Operation operation;
	private String subject;
	private String object;
	private Integer value;
	private boolean invalid;
	
	// Constructors
	
	public Instruction (String line) {
		String[] matches;
		
		this.line = line;
		matches = this.line.toLowerCase().trim().split("\\s+");
		
		try {
			this.operation = Operation.valueOf(matches[0]);
			
			if (matches.length - 1 != operation.args()) {
				this.invalid = true;
			}
			
			switch (this.operation) {
				case read:
					this.subject = matches[1];
					this.object = matches[2];
					break;
				case write:
					this.subject = matches[1];
					this.object = matches[2];
					this.value = value.parseInt(matches[3]);
					break;
				default:
					break;
			}
		}
		catch (IllegalArgumentException e) {
			this.invalid = true;
		}
		catch (ArrayIndexOutOfBoundsException e) {
			this.invalid = true;
		}
	}
	
	// Methods
	
	public boolean valid () { return !this.invalid; }
	
	public Operation getOperation () { return this.operation; }
	public String getSubject () { return this.subject; }
	public String getObject () { return this.object; }
	public Integer getValue () { return this.value; }
	
	public void setOperation (Operation operation) { this.operation = operation; }
	public void setSubject (String subject) { this.subject = subject; }
	public void setObject (String object) { this.object = object; }
	public void setValue (Integer value) { this.value = value; }
	
	public String toString () {
		if (this.invalid) {
			return "Bad instruction (" + this.line + ")";
		}
		
		switch (this.operation) {
			case read:
				return this.subject + " reads " + this.object;
			case write:
				return this.subject + " writes " + this.value + " to " + this.object;
			default:
				return "Bad instruction (" + this.line + ")";
		}
	}
}

enum Operation {
	read (2),
	write (3);

	// Instance variables

	private final Integer args;

	// Constructors

	Operation (Integer args) { this.args = args; }

	// Methods

	Integer args () { return this.args; }
}

enum SecurityLevel {
	low (1),
	high (2);

	// Instance variables

	private final Integer level;

	// Constructors

	SecurityLevel (Integer level) { this.level = level; }

	// Methods

	Integer level () { return this.level; }
}
