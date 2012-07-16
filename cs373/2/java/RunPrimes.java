// javac -Xlint RunPrimes.java
// java -ea RunPrimes < RunPrimes.in > RunPrimes.out

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;

public class RunPrimes {
	public static void main (String[] args) throws IOException {
		Scanner scanner;
		Writer writer;
		int[] value;

		scanner = new Scanner(System.in);
		writer = new PrintWriter(System.out);
		value = new int[1];

		while (Primes.read(scanner, value)) {
			Primes.print(writer, Primes.eval(value[0]));
		}
	}
}
