/*
	Student information for assignment

	On my honor, Taylor Fausak, this programming assignment is my own work.
	EID: tdf268
	E-mail address: tfausak@gmail.com
	TA name: Vishvas Vasuki
	Programming hours: 
	Lines of code: 

	Slip days info

	Slip days used on this assignment: 0
	Slip days I think I have used for the term thus far: 0
*/

// Imports
import java.util.Random;

public class ArrayProblems
{
	/**
	 * Determine the Hamming distance between two arrays of ints.
	 * @param aList != null, aList.length == bList.length
	 * @param bList != null, bList.length == aList.length
	 * @return the Hamming Distance between the two arrays of ints.
	 */    

	public static int hammingDistance (int[] aList, int[] bList)
	{
		assert (aList != null && bList != null && aList.length == bList.length) : "Violation of precondition: hammingDistance";

		int arrayLength = aList.length;
		int hammingDistance = 0;

		for (int i = 0; i < arrayLength; i++)
		{
			if (aList[i] != bList[i])
			{
				hammingDistance++;
			}
		}

		return hammingDistance;
	}

	/**
	 * determine if one array of ints is a permutation of another.
	 * neither the parameter <tt>listA</tt> or 
	 * the parameter <tt>listB</tt> are altered as a result of this method.
	 * @param listA != null
	 * @param listB != null
	 * @return <tt>true</tt> if listB is a permutation of listA, <tt>false</tt> otherwise
	 * 
	 */

	public static boolean isPermutation (int[] listA, int[] listB)
	{
		assert (listA != null && listB != null) : "Violation of precondition: isPermutation";

		boolean result = false;
		int aSum = 0;
		int bSum = 0;

		// If they aren't the same length, they aren't permutations
		if (listA.length == listB.length)
		{
			// Checks every element in a for a matching one in b
			for (int a : listA)
			{
				// Assume there is not a match at first
				result = false;
				for (int b : listB)
				{
					// Exit the loop if a match is found
					if (a == b)
					{
						result = true;
						break;
					}
				}

				// Exit the loop if a mach isn't found
				if (!result)
				{
					break;
				}
			}
		}

		for (int a : listA)
		{
			aSum += a;
		}
		for (int b : listB)
		{
			bSum += b;
		}

		// Their sums must be the same to be permutations. This
		// prevents the algorithm from being fooled by {1, 1, 2}
		// compared to {1, 2, 2}
		if (aSum != bSum)
		{
			result = false;
		}

		return result;
	}

	/**
	 * determine if there is a majority element in an array of ints.
	 * The parameter <tt>list</tt> is not altered as a result of this 
	 * method.
	 * @param list != null
	 * @return  an index of the value of the majority element if it exists.
	 * If a majority element does not exist return -1.
	 */

	public static int findMajority (int[] list)
	{
		assert (list != null) : "Violation of precondition: findMajority";

		int result;
		int length = list.length;
		int count = 0;
		int index = 0;
		int element = findElement(list);

		// Counts the number of occurances of element
		for (int i = 0; i < length; i++)
		{
			if (list[i] == element)
			{
				count += 1;
				index = i;
			}
		}

		// Returns element's index if there are enough occurances
		if (count > list.length / 2)
		{
			result = index;
		}
		else
		{
			result = -1;
		}

		return result;
	}

	/*
		Helper method for findMajority. This determines if there is
		an element in array that might be the majority. This takes
		the first element and marks it, then traverses the array. If
		the next element is the same as the first, mark is
		incremented. If it isn't, mark is decremented. If mark is
		zero, mark is set to 1 and that element is put up for
		consideration.
	*/

	private static int findElement (int[] array)
	{
		assert (array != null) : "Violation of precondition: findElement";

		int element = array[0];
		int length = array.length;
		int mark = 0;

		for (int i = 0; i < length; i++)
		{
			if (mark == 0)
			{
				mark = 1;
				element = array[i];
			}
			else if (array[i] == element)
			{
				mark += 1;
			}
			else
			{
				mark -= 1;
			}
		}

		return element;
	}

	/**
	 * return a shuffled version of an array of ints.
	 * @param original != null
	 * @return a shuffled version of original. Original is not altered by this
	 * method. Each possible permutation of original has a roughly uniform chance
	 * of being returned.
	 */

	public static int[] shuffle (int[] original)
	{
		assert original != null : "Violation of precondition: shuffle";

		// This is an implementation of the Fisher-Yates shuffle
		Random rng = new Random();
		int length = original.length;
		int[] shuffled = original;

		for (int i = length - 1; i > 0; i--)
		{
			int k = rng.nextInt(i + 1);
			shuffled[i] = original[k];
			shuffled[k] = original[i];
		}

		return shuffled;
	}

	/**
	 * determine which String has the largest number of vowels. 
	 * Vowels are defined to 'A', 'a', 'E', 'e', 'I', 'i', 'O', 'o', 'U', and 'u'. 
	 * <p>pre: list != null, list.length > 0, there is an least 1 non null element in list
	 * <p>post: return the String in list that has the largest number of characters that are vowels.
	 * If there is a tie return any of the Strings that has the greatest number of vowels.
	 * @param list the array to check
	 * @return the String in list that has the greatest number of vowels.
	 */

	public static String mostVowels (String[] list)
	{
		assert list != null && list.length > 0 && atLeastOneNonNull(list) : "Violation of precondition: mostVowels"; 

		char[] vowels = {'A', 'E', 'I', 'O', 'U',
		                 'a', 'e', 'i', 'o', 'u'};
		int length = list.length;
		int vLength = vowels.length;
		int count = 0;
		int max = 0;
		int index = 0;

		for (int i = 0; i < length; i++)
		{
			// Avoids a NullPointerException
			if (list[i] == null)
			{
				list[i] = "";
			}

			char[] test = list[i].toCharArray();
			int tLength = test.length;
			count = 0;

			for (int a = 0; a < tLength; a++)
			{
				for (int b = 0; b < vLength; b++)
				{
					if (test[a] == vowels[b])
					{
						count++;
					}
				}
			}

			if (count > max)
			{
				max = count;
				index = i;
			}
		}

		return list[index];
	}

	private static boolean atLeastOneNonNull (String[] list)
	{
		assert list != null : "Violation of precondition: atLeastOneNonNull"; 
		boolean foundNonNull = false;
		int i = 0;
		while (!foundNonNull && i < list.length)
		{
			foundNonNull = list[i] != null;
			i++;
		}

		return foundNonNull;
	}

	/**
	 * determine if the chess board represented by board is a safe set up.
	 * <p>pre: board != null, board.length > 0, board is a square matrix.
	 * (In other words all rows in board have board.length columns.),
	 * all elements of board == 'q' or '.'. 'q's represent queens, '.'s
	 * represent open spaces.<br>
	 * <p>post: return true if the configuration of board is safe,
	 * that is no queen can attack any other queen on the board.
	 * false otherwise.
	 * the parameter <tt>board</tt> is not altered as a result of 
	 * this method.
	 * @param board the chessboard
	 * @return true if the configuration of board is safe,
	 * that is no queen can attack any other queen on the board.
	 * false otherwise.
	 */

	public static boolean queensAreSafe (char[][] board)
	{
		char[] validChars = {'q', '.'};
		assert (board != null) && (board.length > 0) && isSquare(board) && onlyContains(board, validChars) : "Violation of precondition: queensAreSafe";

		int length = board.length;
		// There cannot be more than length queens on the board
		int[][] queens = new int[length][2];
		int count = 0;
		boolean safe = true;

		// Scan for queens and store their position
		for (int r = 0; r < length && safe; r++)
		{
			for (int c = 0; c < length && safe; c++)
			{
				if (board[r][c] == 'q')
				{
					if (count < length)
					{
						queens[count][0] = r;
						queens[count][1] = c;
						count++;
					}
					// Can't have more queens than rows
					else
					{
						safe = false;
					}
				}
			}
		}

		// Only analyze valid queens (not whole queens array)
		for (int i = 0; i < count && safe; i++)
		{
			// Compate against every other queen
			for (int j = i + 1; j < count && safe; j++)
			{
				// Can't occupy same column or row
				if (queens[i][0] == queens[j][0] ||
				    queens[i][1] == queens[j][1])
				{
					safe = false;
				}
				else
				{
					double slope = (double) (queens[i][1] - queens[j][1]) / (double) (queens[i][0] - queens[j][0]);

					if (slope == 1.0 || slope == -1.0)
					{
						safe = false;
					}
				}
			}
		}

		return safe;
	}

	/**
	 * determine the status of a sudoku puzzle board. 0s represent open spaces.
	 * <p> pre: board != null, board.length = 9, board is a square matrix.
	 * <p> post: return 0 if the board is solved,<br> 
	 * return 1 if it is incorrent due to an invalid digit,<br>
	 * return 2 if it is incorrect due to a duplicate digit in a row, column, or sub region<br>
	 * return 3 if it is a valid in progress board
	 * @param board the sudoku board
	 * @return an int based on the status of the board
	 */

	public static int sudokuStatus (int[][] board)
	{
		assert board != null && board.length == 9 && isSquare(board)
			: "Violation of precondition: sudokuStatus";

		final int length = board.length;
		int result = 0;

		for (int r = 0; r < length && result != 1 && result != 2; r++)
		{
			for (int c = 0; c < length && result != 1 && result != 2; c++)
			{
				// Checks for invalid numbers
				if (board[r][c] < 0 || board[r][c] > 9)
				{
					result = 1;
				}
				// An empty space means an unsolved board
				else if (board[r][c] == 0)
				{
					result = 3;
				}
				else
				{
					// Checks the sub region for repeats
					if (r % 3 == 0 && c % 3 == 0)
					{
						int[] s = {board[r][c],
							   board[r][c+1],
							   board[r][c+2],
							   board[r+1][c],
							   board[r+1][c+1],
							   board[r+1][c+2],
							   board[r+2][c],
							   board[r+2][c+1],
							   board[r+2][c+2]};

						if (hasRepeat(s))
						{
							result = 2;
						}
					}

					// Checks the column for repeats
					int[] col = {board[0][c],
						     board[1][c],
						     board[2][c],
						     board[3][c],
						     board[4][c],
						     board[5][c],
						     board[6][c],
						     board[7][c],
						     board[8][c]};

					if (hasRepeat(col))
					{
						result = 2;
					}
				}
			}

			// Check the row for repeats
			if (hasRepeat(board[r]))
			{
				result = 2;
			}
		}

		return result;
	}

	/*
		Helper method for sudokuStatus. Determines if an array has
		repeated values in the range 1-9. 0 is ignored. As soon as a
		repeated value is found, it exits the loop.
	*/

	private static boolean hasRepeat (int[] array)
	{   
		assert array != null
			: "Violation of precondition: hasRepeat";

		final int length = array.length;
		int[] values = new int[10];
		boolean repeat = false;

		for (int i = 0; i < length && !repeat; i++)
		{
			if (array[i] >= 0 && array[i] <= 9)
			{
				values[array[i]]++;

				if (array[i] != 0 && values[array[i]] > 1)
				{
					repeat = true;
				}
			}
		}

		return repeat;
	}   

	/**
	 * determine which row or column in a matrix has the largest sum.
	 * <p>pre: mat != null, mat.length > 0,
	 * mat is a rectangular matrix, mat[0].length > 0
	 * <p>post: determine which row or column of ints has the maximum sum in max. 
	 * If a row contains the maximum sum, return a string starting with "R" and 
	 * then the number of the row with no spaces. For example "R0" or "R12". If a 
	 * column contains the maximum sum, return a string starting with "C" and then 
	 * the number of the column with no spaces. For example "C0" or "C12".  
	 * If there is more than one row or column with the maximum sum 
	 * return a String for any one of them. 
	 */

	public static String maxSum (int[][] mat)
	{
		assert (mat != null) && (mat.length > 0) && (mat[0].length > 0) && isRectangular(mat) : "Violation of precondition: maxSum";

		int rows = mat.length;
		int cols = mat[0].length;
		// Allow for negative sums and sums larger than ints range
		long max = -9223372036854775808L;
		boolean row = false;
		int index = 0;
		String result;

		for (int r = 0; r < rows; r++)
		{
			for (int c = 0; c < cols; c++)
			{
				long colSum = 0;

				for (int i = 0; i < rows; i++)
				{
					colSum += mat[i][c];
				}

				if (colSum > max)
				{
					max = colSum;
					row = false;
					index = c;
				}
			}

			long rowSum = 0;

			for (int i = 0; i < cols; i++)
			{
				rowSum += mat[r][i];
			}

			if (rowSum > max)
			{
				max = rowSum;
				row = true;
				index = r;
			}
		}

		if (row)
		{
			result = "R";
		}
		else
		{
			result = "C";
		}
		result += index;

		return result;
	}

	public static void main (String[] args)
	{
		// Test 1
		int[] h1 = {1, 2, 3, 4, 5, 4, 3, 2, 1};
		int[] h2 = {1, 2, 2, 4, 5, 4, 3, 1, 1};
		if (hammingDistance(h1, h2) == 2)
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 1\t(hammingDistance)");

		// Test 2
		int[] a = {1, 2, 3};
		int[] b = {2, 1, 3};
		if (isPermutation(a, b))
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 2\t(isPermutation)");

		// Test 3
		b = new int[] {2, 1, 3, 3};
		if (!isPermutation(a,b))
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 3\t(isPermutation)");

		// Test 4
		if (findMajority(b) == -1)
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 4\t(findMajority)");

		// Test 5
		b[0] = 3;
		int result = findMajority(b);
		if (result == 0 || result == 2 || result == 3)
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 5\t(findMajority)");

		// Test 6
		b = new int[] {-1, -1, -1, 0, 0};
		result = findMajority(b);
		if (result == 0 || result == 1 || result == 2)
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 6\t(findMajority)");


		// Test 7
		int[][] sBoard = { {7, 0, 1, 8, 0, 0, 9, 0, 0},
				   {0, 3, 0, 4, 0, 0, 6, 5, 0},
				   {9, 0, 0, 0, 5, 0, 3, 0, 0},
				   {0, 2, 0, 1, 0, 0, 4, 0, 0},
				   {3, 0, 0, 0, 8, 0, 0, 0, 5},
				   {0, 0, 4, 0, 0, 5, 0, 7, 0},
				   {0, 0, 5, 0, 2, 0, 0, 0, 3},
				   {0, 1, 3, 0, 0, 6, 0, 4, 0},
				   {0, 0, 9, 0, 0, 8, 5, 0, 2} };
		result = sudokuStatus(sBoard);
		if (result == 3)
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 7\t(sudokuStatus)");


		// Test 8
		b = new int[] {2, 5, 5, 5, 3, 6, 7};
		a = shuffle(b);
		if (isPermutation(a,b))
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 8\t(shuffle)");

		// Test 9
		String[] sList = {"aaaaaaa", "aieou"};
		if (mostVowels(sList).equals("aaaaaaa"))
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 9\t(mostVowels)");

		// Test 10
		sList = new String[] {"Staying", null, "", "moo", "SEqUOIA NAtIOnAl FOrEst", "!!!!>>+_)(*&^%$#@!>)))???\\///\n\n/n"};
		if (mostVowels(sList).equals("SEqUOIA NAtIOnAl FOrEst"))
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 10\t(mostVowels)");

		// Test 11
		char[][] safe = { {'.', '.', '.'},
				  {'q', '.', '.'},
				  {'.', '.', 'q'} };
		if (queensAreSafe(safe))
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 11\t(queensAreSafe)");

		// Test 12
		char[][] unsafe = { {'.', '.', '.', 'q'},
				    {'.', '.', '.', '.'},
				    {'.', '.', '.', '.'},
				    {'q', '.', '.', '.'} };
		if (!queensAreSafe(unsafe))
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 12\t(queensAreSafe)");

		// Test 13
		sBoard[8][7] = 4;
		result = sudokuStatus(sBoard);
		if (result == 2)
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 13\t(sudokuStatus)");

		// Test 14
		sBoard[8][7] = 1;
		result = sudokuStatus(sBoard);
		if (result == 3)
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 14\t(sudokuStatus)");

		// Test 15
		sBoard = new int[][] {{7, 5, 1, 8, 6, 3, 9, 2, 4},
				      {8, 3, 2, 4, 1, 9, 6, 5, 7},
				      {9, 4, 6, 7, 5, 2, 3, 8, 1},
				      {5, 2, 8, 1, 9, 7, 4, 3, 6},
				      {3, 6, 7, 2, 8, 4, 1, 9, 5},
				      {1, 9, 4, 6, 3, 5, 2, 7, 8},
				      {4, 8, 5, 9, 2, 1, 7, 6, 3},
				      {2, 1, 3, 5, 7, 6, 8, 4, 9},
				      {6, 7, 9, 3, 4, 8, 5, 1, 2}};
		result = sudokuStatus(sBoard);
		if (result == 0)
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 15\t(sudokuStatus)");

		// Test 16
		int[][] mat = {{1, 2, 3, 4},
			       {0, 0, 0, 0},
			       {1, 2, 3, 10},
			       {4, 3, 2, 1}};
		if (maxSum(mat).equals("R2"))
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 16\t(maxSum)");

		// TODO: Add tests
		// My test cases check to make sure the methods can handle
		// the limits of the data they are dealing with
		final int max = 2147483647;
		final int min = -2147483648;

		// Test 17
		int[] t17_1 = {max, min, 0};
		int[] t17_2 = {0, max, min};
		if (hammingDistance(t17_1, t17_2) == 3)
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 17\t(hammingDistance)");

		// Test 18
		int[] t18_1 = {max, min, 0, -1, max};
		int[] t18_2 = {min, -1, 0, max, max};
		if (isPermutation(t18_1, t18_2))
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 18\t(isPermutation)");

		// Test 19
		int[] t19 = {max, max, max, min, min};
		result = findMajority(t19);
		if (result == 0 || result == 1 || result == 2)
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 19\t(findMajority)");

		// Test 20
		int[][] t20 = {{5, 3, 4, 6, 7, 8, 9, 1, 2},
			       {6, 7, 2, 1, 9, 5, 3, 4, 8},
			       {1, 9, 8, 3, 4, 2, 5, 6, 7},
			       {8, 5, 9, 7, 6, 1, 4, 2, 3},
			       {4, 2, 6, 8, 5, 3, 7, 9, 1},
			       {7, 1, 3, 9, 2, 4, 8, 5, 6},
			       {9, 6, 1, 5, 3, 7, 2, 8, 4},
			       {2, 8, 7, 4, 1, 9, 6, 3, 5},
			       {3, 4, 5, 2, 8, 6, 1, 7, 9}};
		if (sudokuStatus(t20) == 0)
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 20\t(sudokuStatus)");

		// Test 21
		int[] t21_1 = {min, min, -1, 0, 1, max, max};
		int[] t21_2 = shuffle(t21_1);
		if (isPermutation(t21_1, t21_2))
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 21\t(shuffle)");

		// Test 22
		String[] t22 = {null, "/\n/\t~!@#$%^&*()_+", "computer science", "CUTE SCIENCE ROMP", "eight incredible vowels"};
		if (mostVowels(t22).equals("eight incredible vowels"))
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 22\t(mostVowels)");

		// Test 23
		char[][] t23 = {{'.', '.', '.', 'q', '.', '.', '.', '.'}, 
				{'.', '.', '.', '.', '.', '.', 'q', '.'},
				{'.', '.', 'q', '.', '.', '.', '.', '.'},
				{'.', '.', '.', '.', '.', '.', '.', 'q'},
				{'.', 'q', '.', '.', '.', '.', '.', '.'},
				{'.', '.', '.', '.', 'q', '.', '.', '.'},
				{'q', '.', '.', '.', '.', '.', '.', '.'},
				{'.', '.', '.', '.', '.', 'q', '.', '.'}};
		if (queensAreSafe(t23))
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 23\t(queensAreSafe)");

		// Test 24
		int[][] t24 = {{min, min + 1, min + 2, min + 3},
			       {-100, -99, -98, -97, -96},
			       {-10, -9, -8, -7, -6},
			       {0, 0, 0, 0}};
		if (maxSum(t24).equals("R3"))
			System.out.print("Passed");
		else
			System.out.print("Failed");
		System.out.println(" Test 24\t(maxSum)");
	}

	/*
		Preconditions:
		mat != null
		Postconditions:
		return true if mat is a square matrix, false otherwise
	*/

	private static boolean isSquare (char[][] mat)
	{
		assert mat != null : "Violation of precondition: isSquare";

		final int numRows = mat.length;
		int row = 0;
		boolean square = true;
		while (square && row < numRows)
		{
			square = (mat[row] != null) && (mat[row].length == numRows);
			row++;
		}

		return square;
	}

	/*
		Preconditions:
		mat != null
		Postconditions:
		return true if mat is a square matrix, false otherwise
	*/

	private static boolean isSquare (int[][] mat)
	{
		assert mat != null : "Violation of precondition: isSquare";

		final int numRows = mat.length;
		int row = 0;
		boolean square = true;
		while (square && row < numRows)
		{
			square = (mat[row] != null) && (mat[row].length == numRows);
			row++;
		}

		return square;
	}

	/*
		Preconditions:
		mat != null
		valud != null
		Postconditions:
		return true if all elements in mat are one of the characters
		in valid
	*/
	private static boolean onlyContains (char[][] mat, char[] valid)
	{
		assert mat != null && valid != null : "Violation of precondition: onlyContains";

		int row = 0;
		int col;
		boolean correct = true;
		while (correct && row < mat.length)
		{
			col = 0;
			while (correct && col < mat[row].length)
			{
				correct = contains(valid, mat[row][col]);
				col++;
			}
			row++;
		}

		return correct;
	}

	/*
		Preconditions:
		list != null
		Postconditions:
		return true if c is in list
	*/

	private static boolean contains (char[] list, char c)
	{
		assert (list != null) : "Violation of precondition: contains";

		boolean found = false;
		int index = 0;
		while (!found && index < list.length)
		{
			found = list[index] == c;
			index++;
		}

		return found;
	}

	/*
		Preconditions:
		mat != null
		mat.length > 0
		Postconditions:
		return true if mat is rectangular
	*/

	private static boolean isRectangular (int[][] mat)
	{
		assert (mat != null) && (mat.length > 0) : "Violation of precondition: isRectangular";

		boolean correct = true;
		final int numCols = mat[0].length;
		int row = 0;
		while (correct && row < mat.length)
		{
			correct = (mat[row] != null) && (mat[row].length == numCols);
			row++;
		}

		return correct;
	}
}
