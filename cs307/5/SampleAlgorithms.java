// Imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.math.BigInteger;

public class SampleAlgorithms {
	public static void main (String[] args) {
		Stopwatch s = new Stopwatch();

		int c = 1000;
		for (int i = 1; i <= 8; i *= 2) {
			int n = c * i;
			s.start();
			method11(n, 100);
			s.stop();
			System.out.println("When N was " + n + ", it took " + s.time() + " seconds");
		}
		
		/*
		// Example of Stopwatch
		s.start();
		int total = 1;
		for (int i = 0; i < 4000000; i++) {
			total *= 2;
		}
		s.stop();
		System.out.println(s.time());
		System.out.println(s);
		System.out.println(s.timeInNanoseconds() + " nanoseconds");
		
		processFiles();
		
		System.out.println();
		Random r = new Random();
		int[] list = new int[1000];
		while (list.length < 4000000) {
			for (int i = 0; i < list.length; i++) {
				list[i] = r.nextInt();
			}
			System.out.println("List length: " + list.length + ". Number of minimums found: " + numMins(list));
			list = new int[list.length * 2];
		}
		*/
	}
	
	public static void processFiles () {
		Scanner input = new Scanner(System.in);
		FileToStringConverter f = new FileToStringConverter();
		
		do {
			f.chooseNewFile();
			Stopwatch s = new Stopwatch();
			
			s.start();
			String[] words = f.getWords();
			s.stop();
			System.out.println("\nTime to convert file to words: " + s);
			System.out.println("Number of words in file: " + words.length);
			
            ArrayList<String> wordList = new ArrayList<String>();
            s.start();
            for (int i = 0; i < words.length; i++) {
				wordList.add(words[i]);
			}
            s.stop();
            System.out.println("\nTime to add words to end of ArrayList: " + s);
            wordList.clear();
			
            s.start();
            for (int i = 0; i < words.length; i++) {
                wordList.add(0, words[i]);
			}
            s.stop();
            System.out.println( "\nTime to add words to beginning of ArrayList: " + s );
			
            System.out.println("\nTreeSet example:");
            handleSet(new TreeSet<String>(), words);
			
            System.out.println("\nHashSet example:");
            handleSet(new HashSet<String>(), words);
			
            System.out.print("Another? (type to do another file): ");
        } while (input.nextLine().toUpperCase().charAt(0) == 'Y');
	}
	
	public static void handleSet (Set<String> wordSet, String[] words) {
        Stopwatch s = new Stopwatch();
		
        s.start();
        for (int i = 0; i < words.length; i++) {
            wordSet.add(words[i]);
		}
        s.stop();
		
        System.out.println( "Time to add words to set: " + s);
        System.out.println("Number of words in set: " + wordSet.size());
	}
	
	/*
	 Count the number of times a new minimum is found in a list
	 Pre: list != null, list.length >0
	 */
	public static int numMins (int[] list) {
		int numMins = 1;
		int min = list[0];
		
		for (int i = 1; i < list.length; i++) {
			if (list[i] < min) {
				numMins++;
				min = list[i];
			}
		}
		
		return numMins;
	}
	
    public static void method1 (int n) {
        int sum = 0;
		
        for (int i = 0; i < n; i++) {
            sum++;
		}
    }
	
    public static void method2 (int n) {
        int sum = 0;
		
        for (int i = 0; i < n; i += 2)
            sum++;
    }
	
    public static void method3 (int n) {
        int sum = 0;
		
        for (int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                sum++;
			}
		}
    }
	
    public static void method4 (int n) {
        int sum = 0;
		
        for (int i = 0; i < n; i++) {
            sum++;
		}
        for (int j = 0; j < n; j++) {
            sum++;
		}
    }
	
    public static void method5 (int n) {
        int sum = 0;
		
        for (int i = 0; i < n; i++) {
            for(int j = 0; j < n*n; j++) {
                sum++;
			}
		}
    }
	
    public static void method6 (int n) {
        int sum = 0;
		
        for (int i = 0; i < n; i++) {
            for(int j = 0; j < i; j++) {
                sum++;
			}
		}
    }
	
    public static void method7 (int n) {
        double sum = 0;
		
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                // Assume Math.random() is O(1)
                sum += Math.random();
            }
        }
    }
	
    public static void method8 (int n) {
        int sum = 0;
		
        for (int i = 0; i < n; i ++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
					sum++;
				}
			}
		}
    }
	
	/*
	 Pre: n contains all digits
	 
	 Note: to create a string with enough digits use a for loop back in main
	 */
    public static void method9 (String n) {
        int sum = 0;
        BigInteger limit = new BigInteger(n);
        BigInteger two = new BigInteger("2");
		
		// Assume compareTo and multiply are O(log(N))
        for (BigInteger i = new BigInteger("1"); i.compareTo(limit) <= 0; i = i.multiply(two)) {
            sum++;
		}
    }
	
    public static void method10 (int n) {
        int sum = 0;
		
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j *= 2) {
                sum++;
			}
		}
    }
	
    public static void method11 (int n, int m) {
        Random r = new Random();
        int[] list;
		
        for (int i = 0; i < m; i++) {
            list = new int[n];
			
            for (int j = 0; j < n; j++) {
				list[j] = r.nextInt();
            }
			
            // Assume Arrays.sort is O(N*log(N))
            Arrays.sort(list);
        }
    }
}
