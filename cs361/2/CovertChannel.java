/*
 Taylor Fausak
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class CovertChannel {
	// Instance variables
	
	private HashMap<String, Integer> subjects;
	private HashMap<String, Integer> objects;
	private HashMap<String, SecurityLevel> subjectLevels;
	private HashMap<String, SecurityLevel> objectLevels;
	private String lyleBuffer;
	
	// Constructors
	
	public CovertChannel () {
		this.subjects = new HashMap<String, Integer>();
		this.objects = new HashMap<String, Integer>();
		this.subjectLevels = new HashMap<String, SecurityLevel>();
		this.objectLevels = new HashMap<String, SecurityLevel>();
		this.lyleBuffer = "";
	}
	
	// Main
	
	public static void main (String args[]) throws IOException {
		boolean verbose;
		String inputFileName, line, bits;
		File input, output, log;
		FileReader ifr;
		BufferedReader ibr;
		FileWriter ofw, lfw;
		BufferedWriter obw, lbw;
		CovertChannel cc;
		byte[] bytes;
		
		switch (args.length) {
			case 1:
				verbose = false;
				inputFileName = args[0];
				break;
			case 2:
				verbose = args[0].toLowerCase().equals("v");
				inputFileName = args[1];
				break;
			default:
				verbose = false;
				inputFileName = null;
				System.err.println("Usage: java CovertChannel [v] input");
				System.exit(1);
		}
		
		input = new File(inputFileName);
		ifr = new FileReader(input);
		ibr = new BufferedReader(ifr);
		output = new File(inputFileName + ".out");
		ofw = new FileWriter(output);
		obw = new BufferedWriter(ofw);
		
		if (verbose) {
			log = new File("log");
			lfw = new FileWriter(log);
			lbw = new BufferedWriter(lfw);
		}
		else {
			log = null;
			lfw = null;
			lbw = null;
		}
		
		cc = new CovertChannel();
		cc.createSubject("lyle", SecurityLevel.low);
		cc.createSubject("hal", SecurityLevel.high);

		while ((line = ibr.readLine()) != null) {
			bytes = line.getBytes();
			
			for (byte b : bytes) {
				bits = Integer.toBinaryString(b);
				
				while (bits.length() < 8) {
					bits = "0" + bits;
				}
				
				for (int i = 0; i < bits.length(); i++) {
					if (bits.charAt(i) == '0') {
						if (verbose) {
							lbw.write("run hal");
							lbw.newLine();
							lbw.write("create hal obj");
							lbw.newLine();
						}
						
						cc.run("hal", obw);
						cc.create("hal", "obj");
					}
					else {
						if (verbose) {
							lbw.write("run hal");
							lbw.newLine();
						}
						
						cc.run("hal", obw);
					}
					
					if (verbose) {
						lbw.write("create lyle obj");
						lbw.newLine();
						lbw.write("write lyle obj 1");
						lbw.newLine();
						lbw.write("read lyle obj");
						lbw.newLine();
						lbw.write("destroy lyle obj");
						lbw.newLine();
						lbw.write("run lyle");
						lbw.newLine();
					}
					
					cc.create("lyle", "obj");
					cc.write("lyle", "obj", 1);
					cc.read("lyle", "obj");
					cc.destroy("lyle", "obj");
					cc.run("lyle", obw);
				}
			}
		}
		
		obw.newLine();
		obw.flush();
		if (verbose) {
			lbw.flush();
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

	public void removeSubject (String name) {
		if (this.subjects.containsKey(name)) {
			this.subjects.remove(name);
		}

		if (this.subjectLevels.containsKey(name)) {
			this.subjectLevels.remove(name);
		}
	}

	public void removeObject (String name) {
		if (this.objects.containsKey(name)) {
			this.objects.remove(name);
		}

		if (this.objectLevels.containsKey(name)) {
			this.objectLevels.remove(name);
		}
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
			this.subjects.put(subject, 0);
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

	public void create (String subject, String object) {
		SecurityLevel subjectLevel;

		if (!this.subjectLevels.containsKey(subject) || this.objects.containsKey(object)) {
			return;
		}

		subjectLevel = this.subjectLevels.get(subject);

		this.createObject(object, subjectLevel);
	}

	public void destroy (String subject, String object) {
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

		this.removeObject(object);
	}

	public void run  (String subject, BufferedWriter bw) throws IOException {
		if (!subject.equals("lyle") || !this.subjects.containsKey(subject)) {
			return;
		}
		
		this.lyleBuffer += this.subjects.get(subject);
		
		if (this.lyleBuffer.length() != 8) {
			return;
		}
		
		Integer result = 0;
		
		for (int i = 0; i < this.lyleBuffer.length(); i++) {
			if (this.lyleBuffer.charAt(i) != '1') {
				continue;
			}
			
			result += (int) Math.pow(2, 7 - i);
		}
		
		bw.write((char) result.intValue());
		
		this.lyleBuffer = "";
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
				case create:
					break;
				case destroy:
					break;
				case run:
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
			case create:
				return this.subject + " creates " + this.object;
			case destroy:
				return this.subject + " destroys " + this.object;
			case run:
				return this.subject + " runs";
			default:
				return "Bad instruction (" + this.line + ")";
		}
	}
}

enum Operation {
	read (2),
	write (3),
	create (2),
	destroy (2),
	run (1);

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
