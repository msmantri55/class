// ---------------------------------
// projects/collatz/TestCollatz.java
// Copyright (C) 2010
// Glenn P. Downing
// ---------------------------------

/*
To test the program:
    % setenv CLASSPATH .:/lusr/share/lib/java/junit/junit-4.5.jar
    % javac -Xlint TestCollatz.java
    % java  -ea    TestCollatz
*/

import java.io.IOException;
import java.io.StringWriter;
import java.util.Scanner;
import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public final class TestCollatz extends TestCase
{
	public void testRead ()
	{
		final Scanner s;
		final int[] a;
		final boolean b;

		s = new Scanner("1 10\n");
		a = new int[2];
		b = Collatz.read(s, a);

		Assert.assertTrue(b == true);
		Assert.assertTrue(a[0] == 1);
		Assert.assertTrue(a[1] == 10);
	}

	public void testEval1 ()
	{
		Assert.assertTrue(Collatz.eval(new int[] {1, 10}) == 20);
	}

	public void testEval2 ()
	{
		Assert.assertTrue(Collatz.eval(new int[] {100, 200}) == 125);
	}

	public void testEval3 ()
	{
		Assert.assertTrue(Collatz.eval(new int[] {201, 210}) == 89);
	}

	public void testEval4 ()
	{
		Assert.assertTrue(Collatz.eval(new int[] {900, 1000}) == 174);
	}

	public void testEval5 ()
	{
		Assert.assertTrue(Collatz.eval(new int[] {10, 1}) == 20);
	}

	public void testEval6 ()
	{
		Assert.assertTrue(Collatz.eval(new int[] {200, 100}) == 125);
	}

	public void testEval7 ()
	{
		Assert.assertTrue(Collatz.eval(new int[] {210, 201}) == 89);
	}

	public void testEval8 ()
	{
		Assert.assertTrue(Collatz.eval(new int[] {1, 1000000}) == 525);
	}

	public void testEval9 ()
	{
		Assert.assertTrue(Collatz.eval(new int[] {-1, 1}) == 0);
	}

	public void testEval10 ()
	{
		Assert.assertTrue(Collatz.eval(new int[] {419839, 419839}) == 162);
	}

	public void testPrinter () throws IOException
	{
		final StringWriter sw;

		sw = new StringWriter();
		Collatz.print(sw, new int[] {1, 10}, 20);

		Assert.assertTrue(sw.toString().equals("1 10 20\n"));
	}

	public static void main (String[] args)
	{
		System.out.println("TestCollatz.java");
		TestRunner.run(new TestSuite(TestCollatz.class));
		System.out.println("Done.");
	}
}
