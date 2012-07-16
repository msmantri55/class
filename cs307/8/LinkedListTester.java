/*
	Student information for assignment 9
	
	Name: Taylor Fausak
	EID: tdf268
	E-mail address: tfausak@gmail.com
	TA name: Vishvas
	
	Slip days information
	
	Slip days used on this assignment: 0
	Slip days I think I have used for the term thus far: 
*/

// Imports
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

/*
	Notes on performance:
	
	Add to end: about the same
	Add to front: linked about 300x faster
	Remove from front: linked about 5300x faster
	Get random: array about 2900x faster
	
	Adding elements to the end of array and linked lists are approximately the same.
	Adding elements to the front of a linked list takes significantly longer than an array list (163 vs 22 seconds for 160000 elements)
	Removing elements from the from of a linked list is significantly faster than an array list (~.003 vs 21 seconds for 160000 elements)
	Getting random elements from a linked list is significantly slower than an array list (537 vs ~.02 seconds form 480000 elements)
	Using the addFirst method, adding elements to the front of a linked list is faster than an array list (~.07 vs 22 seconds for 160000 elements)
*/

public class LinkedListTester
{
	public static void main(String[] args)
	{
		//comparison();
		
		LinkedList l = new LinkedList();
		Object[] actual;
		Object[] expected;
		
		// test 1
		System.out.println("\nTest 1: Adding at end");
		l.add("A");
		actual = toArray(l);
		expected = new Object[] {"A"};
        System.out.println("Expected result: " + Arrays.toString(expected));
        System.out.println("Actual result: " + Arrays.toString(actual));
		if (arraysSame(actual, expected)) {
			System.out.println("Passed test 1");
		} else {
			System.out.println("Failed test 1");
		}
		
		// test 2
		System.out.println("\nTest 2: making empty");
		l.makeEmpty();
		actual = toArray(l);
		expected = new Object[] {};
        System.out.println("Expected result: " + Arrays.toString(expected));
        System.out.println("Actual result: " + Arrays.toString(actual));
		if (arraysSame(actual, expected)) {
			System.out.println("Passed test 2");
		} else {
			System.out.println("Failed test 2");
		}
		
		// test 3
		System.out.println("\nTest 3: Adding at pos 0 in empty list");
		l.add(0, "A");
		actual = toArray(l);
		expected = new Object[] {"A"};
        System.out.println("Expected result: " + Arrays.toString(expected));
        System.out.println("Actual result: " + Arrays.toString(actual));
		if (arraysSame(actual, expected)) {
			System.out.println("Passed test 3");
		} else {
			System.out.println("Failed test 3");
		}
		
		
		// test 4
		System.out.println("\nTest 4: Adding at front");
		l = new LinkedList();
		l.addFirst("A");
		actual = toArray(l);
		expected = new Object[] {"A"};
        System.out.println("Expected result: " + Arrays.toString(expected));
        System.out.println("Actual result: " + Arrays.toString(actual));
		if (arraysSame(actual, expected)) {
			System.out.println("Passed test 4");
		} else {
			System.out.println("Failed test 4");
		}
		
		
		// test 5
		System.out.println("\nTest 5: Removing from front");
		l.removeFirst();
		actual = toArray(l);
		expected = new Object[] {};
        System.out.println("Expected result: " + Arrays.toString(expected));
        System.out.println("Actual result: " + Arrays.toString(actual));
		if (arraysSame(actual, expected)) {
			System.out.println("Passed test 5");
		} else {
			System.out.println("Failed test 5");
		}
		
		// test 6
		l = new LinkedList();
		System.out.println("\nTest 6: Adding at end");
		l.addLast("A");
		actual = toArray(l);
		expected = new Object[] {"A"};
        System.out.println("Expected result: " + Arrays.toString(expected));
        System.out.println("Actual result: " + Arrays.toString(actual));
		if (arraysSame(actual, expected)) {
			System.out.println("Passed test 6");
		} else {
			System.out.println("Failed test 6");
		}
		
		
		// test 7
		System.out.println("\nTest 7: Removing from back");
		l.removeLast();
		actual = toArray(l);
		expected = new Object[] {};
        System.out.println("Expected result: " + Arrays.toString(expected));
        System.out.println("Actual result: " + Arrays.toString(actual));
		if (arraysSame(actual, expected)) {
			System.out.println("Passed test 7");
		} else {
			System.out.println("Failed test 7");
		}
		
		// test 8
		System.out.println("\nTest 8: Adding at middle");
		l = new LinkedList();
		l.add("A");
		l.add("C");
		l.add(1, "B");
		actual = toArray(l);
		expected = new Object[] {"A", "B", "C"};
        System.out.println("Expected result: " + Arrays.toString(expected));
        System.out.println("Actual result: " + Arrays.toString(actual));
		if (arraysSame(actual, expected)) {
			System.out.println("Passed test 8");
		} else {
			System.out.println("Failed test 8");
		}
		
		// test 9
		System.out.println("\nTest 9: Setting");
		l = new LinkedList();
		l.add("A");
		l.add("D");
		l.add("C");
		l.set(1, "B");
		actual = toArray(l);
		expected = new Object[] {"A", "B", "C"};
        System.out.println("Expected result: " + Arrays.toString(expected));
        System.out.println("Actual result: " + Arrays.toString(actual));
		if (arraysSame(actual, expected)) {
			System.out.println("Passed test 9");
		} else {
			System.out.println("Failed test 9");
		}
		
		// test 10
        System.out.println("\nTest 10: Removing");
		l = new LinkedList();
		l.add("A");
		l.add("B");
		l.add("C");
		l.add("D");
		l.remove(0);
		l.remove( l.size() - 1 );
		actual = toArray(l);
		expected = new Object[] {"B", "C"};
        System.out.println("Expected result: " + Arrays.toString(expected));
        System.out.println("Actual result: " + Arrays.toString(actual));
		if (arraysSame(actual, expected)) {
			System.out.println("Passed test 10");
		} else {
			System.out.println("Failed test 10");
		}
		
		// test 11
		System.out.println("\nTest 11: get");
		l = new LinkedList();
		l.add("A");
		l.add("B");
		l.add("C");
		l.add("D");
		Object actual11 = l.get(2);
		String expected11 = "C";
		if (expected11.equals(actual11)) {
			System.out.println("Passed test 11");
		} else {
			System.out.println("Failed test 11");
		}
		
		// test 12
		System.out.println("\nTest 12: getSubList");
		l = new LinkedList();
		l.add("A");
		l.add("B");
		l.add("C");
		l.add("D");
		actual = toArray((LinkedList) l.getSubList(1, 3));
		expected = new Object[] {"B", "C"};
		if (arraysSame(actual, expected)) {
			System.out.println("Passed test 12");
		} else {
			System.out.println("Failed test 12");
		}
		
		// test 13
		System.out.println("\nTest 14: indexOf");
		l = new LinkedList();
		l.add("A");
		l.add("B");
		l.add("B");
		l.add("D");
		int actual13 = l.indexOf("B");
		int expected13 = 1;
		if (actual13 == expected13) {
			System.out.println("Passed test 13");
		} else {
			System.out.println("Failed test 13");
		}
		
		// test 14
		System.out.println("\nTest 14: indexOf");
		l = new LinkedList();
		l.add("A");
		l.add("B");
		l.add("B");
		l.add("D");
		int actual14 = l.indexOf("B", 2);
		int expected14 = 2;
		if (actual14 == expected14) {
			System.out.println("Passed test 14");
		} else {
			System.out.println("Failed test 14");
		}
		
		// test 15
		System.out.println("\nTest 15: removeRange");
		l = new LinkedList();
		l.add("A");
		l.add("B");
		l.add("C");
		l.add("D");
		l.removeRange(0, 3);
		actual = toArray(l);
		expected = new Object[] {"D"};
		if (arraysSame(actual, expected)) {
			System.out.println("Passed test 15");
		} else {
			System.out.println("Failed test 15");
		}
		
		// test 16
		System.out.println("\nTest 16: removeRange");
		l = new LinkedList();
		l.add("A");
		l.add("B");
		l.add("C");
		l.add("D");
		l.removeRange(1, 3);
		actual = toArray(l);
		expected = new Object[] {"A", "D"};
		if (arraysSame(actual, expected)) {
			System.out.println("Passed test 16");
		} else {
			System.out.println("Failed test 16");
		}
		
		// test 17
		System.out.println("\nTest 17: toString");
		l = new LinkedList();
		l.add("A");
		l.add("B");
		l.add("B");
		l.add("D");
		String expected17 = "[A, B, B, D]";
		String actual17 = l.toString();
		if (expected17.equals(actual17)) {
			System.out.println("Passed test 17");
		} else {
			System.out.println("Failed test 17");
		}
		
		// test 18
		System.out.println("\nTest 18: toString");
		l = new LinkedList();
		String expected18 = "[]";
		String actual18 = l.toString();
		if (expected18.equals(actual18)) {
			System.out.println("Passed test 18");
		} else {
			System.out.println("Failed test 18");
		}
		
		// test 19
		System.out.println("\nTest 19: equals");
		LinkedList l1 = new LinkedList();
		LinkedList l2 = new LinkedList();
		l1.add("A");
		l2.add("A");
		l1.add("B");
		l2.add("B");
		l1.add("C");
		l2.add("C");
		if (l1.equals(l2)) {
			System.out.println("Passed test 19");
		} else {
			System.out.println("Failed test 19");
		}
		
		// test 20
		System.out.println("\nTest 20: equals");
		l1 = new LinkedList();
		l2 = new LinkedList();
		l1.add("A");
		l2.add("A");
		l1.add("B");
		l2.add("B");
		l1.add("C");
		if (!l1.equals(l2)) {
			System.out.println("Passed test 20");
		} else {
			System.out.println("Failed test 20");
		}
		
		// test 21
		System.out.println("\nTest 21: equals");
		l1 = new LinkedList();
		l2 = new LinkedList();
		l1.add("A");
		l2.add("A");
		l2.add("B");
		l1.add("C");
		l2.add("C");
		if (!l1.equals(l2)) {
			System.out.println("Passed test 21");
		} else {
			System.out.println("Failed test 21");
		}
		
		// test 22
		System.out.println("\nTest 22: equals");
		l1 = new LinkedList();
		l2 = new LinkedList();
		l1.add("A");
		l2.add("A");
		l1.add("B");
		l2.add("E");
		l1.add("C");
		l2.add("C");
		if (!l1.equals(l2)) {
			System.out.println("Passed test 22");
		} else {
			System.out.println("Failed test 22");
		}
		
		// comparison to ArrayList
		Stopwatch s = new Stopwatch();
		final int NUM_TESTS = 5;
		int numInts = 20000;
		l = new LinkedList();
		for (int i = 1; i < NUM_TESTS; i++) {
			s.start();
			for (int j = 0; j < numInts; j++) {
				l.addFirst(j);
			}
			s.stop();
			System.out.println("Time to add " + numInts + " values to front of list: " + s);
			numInts *= 2;
			l.makeEmpty();
		}
		
		numInts = 20000;
		ArrayList<Integer> al = new ArrayList<Integer>();
		for (int i = 1; i < NUM_TESTS; i++) {
			s.start();
			for (int j = 0; j < numInts; j++) {
				al.add(0, j);
			}
			s.stop();
			System.out.println("Time to add " + numInts + " values to front of list: " + s);
			numInts *= 2;
			al.clear();
		}
		
		// My "tests" (they don't report if they succeed or not - just check visually)
		LinkedList list = new LinkedList();
		System.out.println("Empty list: " + list);
		list.add("A");
			System.out.println("Adding 'A' to the end: " + list);
		list.add(null);
			System.out.println("Adding null to the end: " + list);
		list.add("B");
			System.out.println("Adding 'B' to the end: " + list);
		list.add(0, "Z");
			System.out.println("Adding 'Z' to position 0: " + list);
		list.add(list.size(), "Y");
			System.out.println("Adding 'Y' to position " + list.size() + ": " + list);
		list.add(list.size() / 2, "X");
			System.out.println("Adding 'X' to position " + (list.size() / 2) + ": " + list);
		System.out.println("Changing " + list.set(0, "1") + " to '1': " + list);
		System.out.println("Changing " + list.set(list.size() - 1, "2") + " to '2': " + list);
		System.out.println("Changing " + list.set(list.size() / 2, "3") + " to '3': " + list);
		System.out.println("Getting object at position 0: " + list.get(0));
		System.out.println("Getting object at position " + (list.size() - 1) + ": " + list.get(list.size() - 1));
		System.out.println("Getting object at position " + (list.size() / 2) + ": " + list.get(list.size() / 2));
		System.out.println("Removing object at position 0 (" + list.remove(0) + "): " + list);
		System.out.println("Removing object at position " + (list.size() - 1) + " (" + list.remove(list.size() - 1) + "): " + list);
		System.out.println("Removing object at position " + (list.size() / 2) + " (" + list.remove(list.size() / 2) + "): " + list);
		System.out.println("Removing 'A' (" + list.remove("A") + "): " + list);
		System.out.println("Removing 'B' (" + list.remove("B") + "): " + list);
		System.out.println("Removing 'C' (" + list.remove("C") + "): " + list);
		list.add("Y");
			System.out.println("Adding 'Y' to the end: " + list);
		list.add("Z");
			System.out.println("Adding 'Z' to the end: " + list);
		System.out.println("Sub-list of " + list + " from 0-1: " + list.getSubList(0, 1));
		System.out.println("Sub-list of " + list + " from 2-2: " + list.getSubList(2, 2));
		System.out.println("Sub-list of " + list + " from 0-2: " + list.getSubList(0, 2));
		System.out.println("Index of 'X' in " + list + ": " + list.indexOf("X"));
		System.out.println("Index of 'Y' in " + list + ": " + list.indexOf("Y"));
		System.out.println("Index of 'Z' in " + list + ": " + list.indexOf("Z"));
		System.out.println("Index of 'X' in " + list + ", starting at 1: " + list.indexOf("X", 1));
		System.out.println("Index of 'Y' in " + list + ", starting at 1: " + list.indexOf("Y", 1));
		System.out.println("Index of 'Z' in " + list + ", starting at 1: " + list.indexOf("Z", 1));
		list.makeEmpty();
			System.out.println("Making the list empty: " + list);
		list.add("A");
			System.out.println("Adding 'A' to the end: " + list);
		list.add("B");
			System.out.println("Adding 'B' to the end: " + list);
		list.add("C");
			System.out.println("Adding 'C' to the end: " + list);
		list.add("D");
			System.out.println("Adding 'D' to the end: " + list);
		list.add("E");
			System.out.println("Adding 'E' to the end: " + list);
		list.removeRange(0, 1);
			System.out.println("Removing objects from 0-1: " + list);
		list.removeRange(1, 3);
			System.out.println("Removing objects from 1-3: " + list);
		int size = list.size();
		list.removeRange(0, size);
			System.out.println("Removing objects from 0-" + size + ": " + list);
		list.addFirst("A");
			System.out.println("Adding 'A' to the front: " + list);
		list.addFirst("B");
			System.out.println("Adding 'B' to the front: " + list);
		list.addLast("C");
			System.out.println("Adding 'C' to the end: " + list);
		list.addLast("D");
			System.out.println("Adding 'D' to the end: " + list);
		System.out.println("Removing the first object (" + list.removeFirst() + "): " + list);
		System.out.println("Removing the last object (" + list.removeLast() + "): " + list);
		list = new LinkedList();
		list.add("A");
		list.add("B");
		list.add("C");
		LinkedList list2 = new LinkedList();
		list2.add("A");
		list2.add("B");
		list2.add("C");
		System.out.println("Comparing " + list + " and " + list2 + ": " + list.equals(list2));
		list2.set(0, "Z");
		System.out.println("Comparing " + list + " and " + list2 + ": " + list.equals(list2));
		list2.remove(0);
		System.out.println("Comparing " + list + " and " + list2 + ": " + list.equals(list2));
    }

	private static Object[] toArray (LinkedList list) {
		Object[] result = new Object[list.size()];
		Iterator it = list.iterator();
		int index = 0;
		while (it.hasNext()) {
			result[index] = it.next();
			index++;
		}
		return result;
	}

	//pre: none
	private static boolean arraysSame (Object[] one, Object[] two) {
		boolean same;
		if (one == null || two == null) {
			same = (one == two);
		} else {
			//neither one or two are null
			assert one != null && two != null;
			same = one.length == two.length;
			if (same) {
				int index = 0;
				while (index < one.length && same) {
					same = (one[index] == two[index]);
					index++;
				}
			}
		}
		return same;
	}

	// A method to be run to compare the LinkedList you are completing and the Java ArrayList class
	public static void comparison () {
		Stopwatch s = new Stopwatch();
		int initialN = 50000;
		int numTests = 5;
		
		addEndArrayList(s, initialN, numTests);
		addEndLinkedList(s, initialN, numTests);
		
		initialN = 10000;
		addFrontArrayList(s, initialN, numTests);
		addFrontLinkedList(s, initialN, numTests);
		
		removeFrontArrayList(s, initialN, numTests);
		removeFrontLinkedList(s, initialN, numTests);
		
		getRandomArrayList(s, initialN, numTests);
		getRandomLinkedList(s, initialN, numTests);
	}
    
	public static void addEndArrayList (Stopwatch s, int initialN, int numTests) {
		ArrayList<Integer> javaList = new ArrayList<Integer>();
		System.out.println("Adding at end: ArrayList");
		int n = initialN;
		for (int i = 0; i < numTests; i++) {
			System.out.print("Adding " + n + " elements to end of an ArrayList. ");
			s.start();
			for (int j = 0; j < n; j++) {
				javaList.add(j);
			}
			s.stop();
			System.out.println("Time to add: " + s);
			n *= 2;
			
			//empty out the list and run garbage collector
			javaList.clear();
			javaList.trimToSize();
			System.gc();
		}
	}
    
	public static void addEndLinkedList (Stopwatch s, int initialN, int numTests) {
		System.out.println("Adding at end: LinkedList");
		int n = initialN;
		LinkedList studentList = new LinkedList();
		for (int i = 0; i < numTests; i++) {
			System.out.print("Adding " + n + " elements to end of a LinkedList. ");
			s.start();
			for (int j = 0; j < n; j++) {
				studentList.add(j);
			}
			s.stop();
			System.out.println("Time to add: " + s);
			n *= 2;
			
			// empty out the list and run garbage collector
			studentList.makeEmpty();
			System.gc();
		}
	}
    
	public static void addFrontArrayList (Stopwatch s, int initialN, int numTests) {
		ArrayList<Integer> javaList = new ArrayList<Integer>();
		System.out.println("Adding at front: ArrayList");
		int n = initialN;
		for (int i = 0; i < numTests; i++) {
			System.out.print("Adding " + n + " elements to front of an ArrayList. ");
			s.start();
			for (int j = 0; j < n; j++) {
				javaList.add(0, j);
			}
			s.stop();
			System.out.println("Time to add: " + s);
			n *= 2;
			
			//empty out the list and run garbage collector
			javaList.clear();
			javaList.trimToSize();
			System.gc();
		}
	}
    
	public static void addFrontLinkedList (Stopwatch s, int initialN, int numTests) {
		System.out.println("Adding at front: LinkedList");
		int n = initialN;
		LinkedList studentList = new LinkedList();
		for (int i = 0; i < numTests; i++) {
			System.out.print("Adding " + n + " elements to front of a LinkedList. ");
			s.start();
			for (int j = 0; j < n; j++) {
				studentList.add(j, 0);
			}
			s.stop();
			System.out.println("Time to add: " + s);
			n *= 2;
			
			// empty out the list and run garbage collector
			studentList.makeEmpty();
			System.gc();
		}
	}
    
	public static void removeFrontArrayList (Stopwatch s, int initialN, int numTests) {
		ArrayList<Integer> javaList = new ArrayList<Integer>();
		System.out.println("Removing from front: ArrayList");
		int n = initialN;
		for (int i = 0; i < numTests; i++) {
			System.out.print("Removing " + n + " elements from front of an ArrayList. ");
			
			for (int j = 0; j < n; j++) {
				javaList.add(j);
			}
			s.start();
			while (!javaList.isEmpty()) {
				javaList.remove(0);
			}
			s.stop();
			System.out.println("Time to add: " + s);
			n *= 2;
			
			//empty out the list and run garbage collector
			javaList.clear();
			javaList.trimToSize();
			System.gc();
		}
	}
    
	public static void removeFrontLinkedList (Stopwatch s, int initialN, int numTests) {
		System.out.println("removing from front: LinkedList");
		int n = initialN;
		LinkedList studentList = new LinkedList();
		for (int i = 0; i < numTests; i++) {
			System.out.print("Removing " + n + " elements from front of a LinkedList. ");
			for (int j = 0; j < n; j++) {
				studentList.add(j, 0);
			}
			s.start();
			while (studentList.size() != 0) {
				studentList.removeFirst();
			}
			s.stop();
			System.out.println("Time to remove: " + s);
			n *= 2;
			
			// empty out the list and run garbage collector
			studentList.makeEmpty();
			System.gc();
		}
	}
    
	public static void getRandomArrayList (Stopwatch s, int initialN, int numTests) {
		ArrayList<Integer> javaList = new ArrayList<Integer>();
		System.out.println("Getting random: ArrayList");
		int n = initialN;
		int limit;
		int total = 0;
		Random r = new Random();
		for (int i = 0; i < numTests; i++) {
			System.out.print("Getting " + (n * 3) + " elements from random locations in an ArrayList. ");
			
			for (int j = 0; j < n; j++) {
				javaList.add(j);
			}
			s.start();
			limit = n * 3;
			for (int j = 0; j < limit; j++) {
				total++;
				javaList.get( r.nextInt(n) );
			}
			s.stop();
			System.out.println("Time to get: " + s);
			n *= 2;
			
			//empty out the list and run garbage collector
			javaList.clear();
			javaList.trimToSize();
			System.gc();
		}
	}
    
	public static void getRandomLinkedList (Stopwatch s, int initialN, int numTests) {
		System.out.println("Getting random: LinkedList");
		int n = initialN;
		int limit;
		int total = 0;
		Random r = new Random();
		LinkedList studentList = new LinkedList();
		for (int i = 0; i < numTests; i++) {
			System.out.print("Getting " + (n * 3) + " elements from random locations in a LinkedList. ");
			for (int j = 0; j < n; j++) {
				studentList.add(j, 0);
			}
			s.start();
			limit = n * 3;
			for (int j = 0; j < limit; j++) {
				total++;
				studentList.get( r.nextInt(n) );
			}
			s.stop();
			System.out.println("Time to get: " + s);
			n *= 2;
			
			// empty out the list and run garbage collector
			studentList.makeEmpty();
			System.gc();
		}
	}
    
	public static void getAllArrayList (Stopwatch s, int initialN, int numTests) {
		ArrayList<Integer> javaList = new ArrayList<Integer>();
		System.out.println("Getting all: ArrayList");
		int n = initialN;
		int total = 0;
		for (int i = 0; i < numTests; i++) {
			System.out.print("Getting all" + n + " elements from an ArrayList. ");
			
			for (int j = 0; j < n; j++) {
				javaList.add(j);
			}
			
			Iterator<Integer> it = javaList.iterator();
			s.start();
			while( it.hasNext() ){
				total++;
				it.next();
			}
			s.stop();
			System.out.println("Time to get: " + s);
			n *= 2;
			
			//empty out the list and run garbage collector
			javaList.clear();
			javaList.trimToSize();
			System.gc();
		}
	}
    
	public static void getAllLinkedList (Stopwatch s, int initialN, int numTests) {
		System.out.println("Getting all: LinkedList");
		int n = initialN;
		int total = 0;
		LinkedList studentList = new LinkedList();
		for (int i = 0; i < numTests; i++) {
			System.out.print("Getting all" + n + " elements from front of an LinkedList. ");
			for (int j = 0; j < n; j++) {
				studentList.add(j, 0);
			}
			Iterator it = studentList.iterator();
			
			s.start();
			while (it.hasNext()) {
				total++;
				it.next();
			}
			s.stop();
			System.out.println("Time to get: " + s);
			n *= 2;
			
			// empty out the list and run garbage collector
			studentList.makeEmpty();
			System.gc();
		}
	}
}