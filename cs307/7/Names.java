// Imports
import java.util.ArrayList;
import java.util.Scanner;

public class Names {
	private ArrayList<NameRecord> records = new ArrayList<NameRecord>();
	
	public Names (Scanner s) {
		while (s.hasNextLine()) {
			records.add(new NameRecord(s.nextLine()));
		}
	}
	
	public ArrayList<NameRecord> search (String token) {
		ArrayList<NameRecord> result = new ArrayList<NameRecord>();
		String name;
		
		String needle = ".*";
		for (int i = 0; i < token.length(); i++) {
			needle += "[" + Character.toUpperCase(token.charAt(i)) + Character.toLowerCase(token.charAt(i)) + "]";
		}
		needle += ".*";
		
		for (NameRecord record : records) {
			name = record.getName();
			
			if (name.matches(needle)) {
				result.add(record);
			}
		}
		
		return result;
	}
	
	public NameRecord oneName (String name) {
		NameRecord result = null;
		
		for (NameRecord record : records) {
			if (name.equalsIgnoreCase(record.getName())) {
				result = record;
			}
		}
		
		return result;
	}
	
	public ArrayList<String> appearAlways () {
		ArrayList<String> result = new ArrayList<String>();
		
		for (NameRecord record : records) {
			if (record.everyDecade()) {
				result.add(record.getName());
			}
		}
		
		return result;
	}
	
	public ArrayList<String> appearOnce () {
		ArrayList<String> result = new ArrayList<String>();
		
		for (NameRecord record : records) {
			if (record.oneDecade()) {
				result.add(record.getName());
			}
		}
		
		return result;
	}
	
	public ArrayList<String> increasingRank () {
		ArrayList<String> result = new ArrayList<String>();
		
		for (NameRecord record : records) {
			if (record.increasingRank()) {
				result.add(record.getName());
			}
		}
		
		return result;
	}
	
	public ArrayList<String> decreasingRank () {
		ArrayList<String> result = new ArrayList<String>();
		
		for (NameRecord record : records) {
			if (record.decreasingRank()) {
				result.add(record.getName());
			}
		}
		
		return result;
	}
}