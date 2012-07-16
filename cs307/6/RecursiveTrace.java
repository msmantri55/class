public class RecursiveTrace
{	/*	Answer the questions below.
		Place your answers in the file RecursiveTester.java.
		Simulate these by hand first!!! Not by running the code!!

	*/

	// What is the output when a(5) called?
	// What is the Big O of this method?
	// What would occur if the method call a(-5) is made?
	public void a(int n)
	{    if( n <= 0 )
	        System.out.println(n + " at base case.");
	     else
	     {  System.out.println( n + " performing recursive step");
	        a(n-1);
	     }
	}

	// What is the output when b(7) called.
	public void b(int n)
	{    if( n <= 0 )
	        System.out.println(n + " at base case.");
	     else
	     {  System.out.println( n + " performing recursive step");
	        b(n-2);
	        System.out.println( n + " done with recursive step");
	     }
	}

	// What is the output when c(7) called.
	public int c(int n)
	{    int result = 0;
	     if( n <= 0 )
	     {  System.out.println(n + " at base case.");
	        result = 2;
	 	 }
	     else
	     {  System.out.println( result );
	        result = 3 + c(n - 1);
	        System.out.println( result );
	     }
	        return result;
	}

	// What does d(16) return?
	// What is the Big O of this method?
	public int d(int n)
	{    if( n <= 0 )
	        return 3;
	     else
	        return 2 + d(n/3);
	}

	// What does e(4) return?
	// What is the Big O of this method?
	// Use the stopwatch class to record the time it takes for
	// this method to complete as n goes from 1 to 30.
	// Based on your answer what is the expected time to complete
	// when n = 40?
	public int e(int n)
	{    if( n <= 0 )
	        return 2;
	     else
	        return 3 + e(n - 1) + e(n - 1);
	}


}
