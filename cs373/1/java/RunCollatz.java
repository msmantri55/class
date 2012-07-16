// --------------------------------
// projects/collatz/RunCollatz.java
// Copyright (C) 2010
// Glenn P. Downing
// --------------------------------

/*
To run the program:
    % javac -Xlint RunCollatz.java
    % java  -ea    RunCollatz < RunCollatz.in > RunCollatz.out

To document the program:
    % javadoc -d html -private *.java
*/

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;

final class RunCollatz
{
	public static void main (String[] args) throws IOException
	{
		final Scanner s;
		final Writer w;
		final int[] a;

		s = new Scanner(System.in);
		w = new PrintWriter(System.out);
		a = new int[2];

		while (Collatz.read(s, a))
		{
			final int v = Collatz.eval(a);
			Collatz.print(w, a, v);
		}
	}
}
