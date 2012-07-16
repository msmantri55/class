// Imports
import java.util.ArrayList;
import java.util.Collections;

public class RecursiveTester {	
	public static void main (String[] args) {	
		Recursive r = new Recursive();

		boolean passedAll = true;
		for (int i = 1; i <= 101 && passedAll; i++)
			passedAll = r.mcCarthy91(i) == 91;
	
		System.out.print("Test 1 (mcCarthy91): ");
		if (passedAll)
			System.out.println("Passed");
		else
			System.out.println("Failed");
		
		System.out.print("Test 2 (mcCarthy91): ");
		if (r.mcCarthy91(102) == 92)
			System.out.println("Passed");
		else
			System.out.println("Failed");
		
		System.out.print("Test 3 (mcCarthy91): ");
		if (r.mcCarthy91(10000) == 9990)
			System.out.println("Passed");
		else
			System.out.println("Failed");
/*
		ArrayList<String> mnemonics = r.listMnemonics("1");
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("1");
		if( mnemonics.equals(expected))
			System.out.println( "Test 4 passed");
		else
			System.out.println( "Test 4 failed");

		mnemonics = r.listMnemonics("22");
		Collections.sort(mnemonics);
		expected.clear();
		expected.add("AA");
		expected.add("AB");
		expected.add("AC");
		expected.add("BA");
		expected.add("BB");
		expected.add("BC");
		expected.add("CA");
		expected.add("CB");
		expected.add("CC");
		Collections.sort(expected);
		if( mnemonics.equals(expected))
			System.out.println( "Test 5 passed");
		else
			System.out.println( "Test 5 failed");
*/
		String binary = r.getBinary(13);
		String expectedBinary = "1101";
		System.out.print("Test 6 (getBinary): ");
		if(binary.equals(expectedBinary))
			System.out.println("Passed");
		else
			System.out.println("Failed");
		
		binary = r.getBinary(64);
		expectedBinary = "1000000";
		System.out.print("Test 7 (getBinary): ");
		if(binary.equals(expectedBinary))
			System.out.println("Passed");
		else
			System.out.println("Failed");
		
		String rev = r.revString("target");
		System.out.print("Test 8 (revString): ");
		if(rev.equals("tegrat"))
			System.out.println("Passed");
		else
			System.out.println("Failed");

		rev = r.revString("Calvin and Hobbes");
			System.out.print("Test 9 (revString): ");
		if(rev.equals("sebboH dna nivlaC"))
			System.out.println("Passed");
		else
			System.out.println("Failed");
/*
		int[][] world = {{5,5,5,5,5,5},
				         {5,5,5,5,5,5},
				         {5,5,5,5,5,5},
				         {5,5,4,4,5,5},
				         {5,5,3,3,5,5},
				         {5,5,2,2,5,5},
				         {5,5,5,1,5,5},
				         {5,5,5,-2,5,5}};
		
		if( r.canFlowOffMap(world,0,0))
			System.out.println( "Test 10 passed");
		else
			System.out.println( "Test 10 failed");
		
		if( r.canFlowOffMap(world,1,1))
			System.out.println( "Test 11 passed");
		else
			System.out.println( "Test 11 failed");	
		
		if( r.canFlowOffMap(world,3,3))
			System.out.println( "Test 12 passed");
		else
			System.out.println( "Test 12 failed");
		
		if( !r.canFlowOffMap(world,1,5))
			System.out.println( "Test 13 passed");
		else
			System.out.println( "Test 13 failed");
		
		world = new int[][]
		                  {{10, 10, 10, 10, 10, 10, 10},
				           {10, 10, 10,  5, 10, 10, 10},
				           {10, 10, 10,  6, 10, 10, 10},
				           {10, 10, 10,  7, 10, 10, 10},
				           {5,   6,  7,  8,  7,  6, 10},
				           {10, 10, 10,  7, 10, 10, 10},
				           {10, 10, 10,  6, 10, 10, 10},
				           {10, 10, 10,  5, 10, 10, 10},
				           {10, 10, 10, 10, 10, 10, 10},
		                  };
		
		if( !r.canFlowOffMap(world,3,3))
			System.out.println( "Test 14 passed");
		else
			System.out.println( "Test 14 failed");	
		
		if( r.canFlowOffMap(world,4,3))
			System.out.println( "Test 15 passed");
		else
			System.out.println( "Test 15 failed");
        */
		
        // try the Sierpinski triangle
        r.drawTriangles(400, 100, 360);
		r.drawTriangles(296, 128, 256);
		r.drawTriangles(296, 64, 256);
		r.drawTriangles(296, 32, 256);
		r.drawTriangles(296, 16, 256);
		r.drawTriangles(296, 8, 256);
		r.drawTriangles(296, 4, 256);
		r.drawTriangles(296, 2, 256);
		
        //try the Sierpinski Carpet
        r.drawCarpet(600, 1);
		
		// Our tests
		System.out.print("Test # (sumOfDigits): ");
		if (r.sumOfDigits(123456) == 21)
			System.out.println("Passed");
		else
			System.out.println("Failed");
		
		System.out.print("Test # (sumOfDigits): ");
		if (r.sumOfDigits(97531) == 25)
			System.out.println("Passed");
		else
			System.out.println("Failed");
	}
}

/*
CS 307 Students. Put your answers to the questions from the
RecursiveTrace class here.

*/