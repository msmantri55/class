import java.awt.Rectangle;

public class ShortCodeExamples
{
	 public static void main(String[] args)
	 {
		// Number 1
		int x1 = 17 / 5 + 2;
		System.out.println(x1);
		// Expected: 5
		// Actual: 2

		// Number 2
		int x2 = 31 % 5 + 31 % 6;
		System.out.println(x2);
		// Expected: 2
		// Actual: 2

		// Number 3
		double a3 = 17.0 / 5.0 + 2.0;
		System.out.println(a3);
		// Expected: 5.4
		// Actual: 5.4

		// Number 4
		double a4 = 17 / 5.0 + 2.0;
		System.out.println(a4);
		// Expected: 5.4
		// Actual: 5.4

		// Number 5
		double a5 = 17 / 5 + 2.0;
		System.out.println(a5);
		// Expected: 5.0
		// Actual: 5.0

		// Number 6
		double a6 = 17 / 5.0 + 2;
		System.out.println(a6);
		// Expected: 5.4
		// Actual: 5.4

		// Number 7
		double a7 = 3 / 2 + 6 / 5;
		System.out.println(a7);
		// Expected: 2.7
		// Actual: 2.0
		// The typecasting from int to double happens after division

		// Number 8
		double a8 = 5 / 3 * 2.0 / 3;
		System.out.println(a8);
		// Expected: 0.666 (repeating)
		// Actual: 0.666 (repeating)

		// Number 9
		double a9 = 5.0 % 3.0;
		System.out.println(a9);
		// Expected: 2.0
		// Actual: 2.0

		// Number 10
		int x10 = 2147483647;
		int y10 = 1;
		int z10 = x10 + y10;
		System.out.println(z10);
		// Expected: 0
		// Actual: -2147483648
		// I misread this one

		// Number 11
		// What is the reason this does not give the specific
		// answer? You do not need to get into the specifics of this
		// particular case.
		double a11 = 11.00;
		double b11 = 10.09;
		double c11 = a11 - b11;
		System.out.println(c11);
		// Expected: NOT 0.91, but I'm not sure what it really is.
		// It doesn't give the specific answer because floating-
		// point arithmetic is not precise. Java converts base-10
		// numbers to binary, which doesn't always work.
		// Actual: 0.9100000000000001

		// Number 12
		// What is the reason this does not give the specific
		// answer? You do not need to get into the specifics of this
		// particular case.
		double a12 = 1000000000;
		double b12 = 0.00000001;
		double c12 = a12 - b12;
		System.out.println(c12);
		c12 = 0;
		for(int i = 0; i < 1000000000; i++)
			c12 += b12;
		System.out.println(c12);
		c12 = a12;
		for(int i = 0; i < 1000000000; i++)
			c12 += b12;
		System.out.println(c12);
		// Expected: NOT 999999999.99999999 
		//           10
		//	     1000000010
		// Actual: 1.0E9
		//	   10.00000012522831
		//	   1.0E9
		// I didn't predict how off the math would be with doubles

		// Number 13
		int x13 = 3;
		int y13 = 4;
		y13 += x13 * y13;
		System.out.println(y13);
		// Expected: 16
		// Actual: 16

		// Number 14
		int x14 = 5;
		int y14 = 3;
		y14 *= y14 + x14;
		System.out.println(y14);
		// Expected: 24
		// Actual: 24

		// Number 15
		int x15 = 3;
		x15++;
		++x15;
		x15 = x15 + 1;
		x15 += 1;
		System.out.println(x15);
		// Expected: 7
		// Actual: 7

		// Number 16
		int x16 = 4;
		x16++;	// Okay
		System.out.println(x16);
		++x16;	// Okay
		System.out.println(x16);
		int x16_1 = 3;
		int x16_2 = 3;
		x16 = x16_1+++x16_2;	//Absolutely horrible style.
		System.out.println( x16 + " " + x16_1 + " " + x16_2 );
		// Expected: 5
		//           6
		//	     13 3 4
		// ++ has preference with associativity to the right
		// Actual: 5
		//	   6
		//	   6 4 3
		// I thought it was 'x16 = x16_1 + ++x16_2'

		// Number 17
		int x17 = 5;
		System.out.println(++x17);
		System.out.println(x17++);
		System.out.println(x17);
		// Expected: 6
		//	     6
		//	     7
		// Actual: 6
		//	   6
		//	   7

		// Number 18
		int x18 = 5;
		// NOTE: Removed to compile
		//if( x18 = 5)
		if (x18 == 5)
			System.out.println("variable x18 equals 5");
		// Expected: variable x18 equals 5
		// The statement 'if(x18=5)' throws an 'incompatible
		// types' error when compiling.
		// Actual: variable x18 equals 5

		// Number 19
		int x19 = 12;
		int y19 = 6 * 2;
		Rectangle box191 = new Rectangle(200, 100, 5, 10);
		Rectangle box192 = new Rectangle(200, 100, 5, 10);
		boolean same191 = ( box191 == box192);
		boolean same192 = ( box191.equals(box192) );
		boolean same193 = (x19 == y19);
		System.out.println(same191);
		System.out.println(same192);
		System.out.println(same193);
		// Expected: false
		//           true
		//	     true
		// box191 and box 192 are pointers.
		// Actual: false
		//	   true
		//	   true

		// Number 20
		int x20 = 0;
		double a20 = 1.5;
		// NOTE: Removed to compile
		//x20 = a20;
		x20 = (int) a20;
		System.out.println(x20);
		// Expected: 1
		// When casting a double to an int, it is floored.
		// Actual: 1

		// Number 21
		int x21 = 12;
		double a21 = x21;
		System.out.println(a21);
		// Expected: 12.0
		// Actual: 12.0

		// Number 22
		int x22 = 0;
		double a22 = -1.9;
		x22 = (int)a22;
		System.out.println(x22);
		// Expected: -2
		// flooring rounds down, even for negative values.
		// Actual: -1
		// Different floor behavior than expected

		// Number 23
		double a23 = 1.5;
		double b23 = Math.rint(a23);
		System.out.println(b23);
		double c23 = 2.5;
		double d23 = Math.rint(c23);
		System.out.println(d23);
		// Expected: 2.0
		//           3.0
		// You don't have to import java.lang.Math because the
		// java.lang package is imported by default.
		// Actual: 2.0
		//	   2.0
		// Misunderstood Math.rint()

		// Number 24
		int x24 = 10;
		int[] list24 = new int[10];
		for(int i = 0; i < list24.length; i++)
			list24[i] = i * i * (int)Math.pow(-1, i);
		System.out.println( list24[7] );
		// NOTE: Removed to run
		//System.out.println( list24[10] );
		// Expected: -49
		//	     runtime error
		// Actual: -49
		//	   ArrayIndexOutOfBoundsException

		// Number 25
		int[] list25 = {4, 4, 11, 0, 3};
		int result = list25[ list25[ list25[2] % list25[1] ] ];
		System.out.println( result );
		// Expected: 4

		// Number 26
		Rectangle box26;
		// NOTE: Added to compile
		box26 = new Rectangle();
		box26.setSize(25, 75);
		System.out.println( box26.toString() );
		System.out.println( box26 );
		// Expected: Whatever toString() returns
		//	     box26's pointer
		// Actual: java.awt.Rectangle[x=0,y=0,width=25,height=75]
		//	   java.awt.Rectangle[x=0,y=0,width=25,height=75]
		// Doesn't print box26's pointer, but the toString()

		// Number 27
		String s26 = "We are a \"work for the night is coming.\" culture.";
		String s261 = s26.substring(25);
		String s262 = s26.substring(10, 15);
		System.out.println( "s261: " + s261);
		System.out.println( "s262: " + s262);
		// Expected: s261: We are a "work for the ni
		//	     s262: "work 
		// Actual: s261: ght is coming." culture.
		//	   s262: work
		// Misunderstood the substring method and miscounted

		// Number 28
		System.out.println("Number 28");
		mustang(2, 3);
		// Expected: 5 -2

		//Number 29
		System.out.println("Number 29");
		mustang(13);
		// Expected: 1 39
		// Actual: 6 39
		// Miscalculated modulus

		//Number 30
		System.out.println("Number 30");
		bobcats(5, 2);
		// Expected: 5 2
		//	     5 15
		//	     2 6
		//	     2 2
		//	     5 -3
		//	     2 2
		//	     2 4 5
		// Actual: 5 2
		//	   5 15
		//	   2 6
		//	   2 2
		//	   5 -3
		//	   2 2
		//	   2 4 5
	}

	public static void mustang(int x, int y)
	{
		x = x + 3;
		y = y - x;
		System.out.println(x + " " + y);
	}

	public static int mustang(int a)
	{
		int x = 3 * a;
		a = a % 7;
		System.out.println(a + " " + x);
		return a;
	}

	public static int hornedFrogs(int x)
	{
		int y = mustang(x);
		System.out.println(x + " " + y);
		mustang(x, y);
		System.out.println(x + " " + y);
		return y + x;
	}

	public static void bobcats(int b, int c)
	{
		System.out.println(b + " " + c);
		int d = mustang(b);
		b = b - 3;
		c = hornedFrogs(c);
		System.out.println(b + " " + c + " " + d);
	}
}
