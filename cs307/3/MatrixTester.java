/*
	A class to run tests on the Matrix class.
*/
public class MatrixTester {
	/*
		Main method that runs simple tests on the Matrix class.
	*/
	public static void main (String[] args) {
		int[][] data1 = {{1, 2, 3}, {2, 3, 4}};
		int[][] data2 = {{2, 1, 1}, {2, 3, 1}};
		int[][] e1;

		// Test 1: Default constructor
		Matrix mat1 = new Matrix();
		e1 = new int[][] {{0}};
		printTestResult(get2DArray(mat1), e1, 1, "default constructor");

		// Test 2-3: int[][] constructor
		mat1 = new Matrix(data1);
		data1[0][0] = 2;
		e1 = new int[][] {{2, 2, 3}, {2, 3, 4}};
		printTestResult(data1, e1, 2, "constructor with one parameter of type int[][]");
		e1 = new int[][] {{1, 2, 3}, {2, 3, 4}};
		printTestResult(get2DArray(mat1), e1, 3, "constructor with one parameter of type int[][]");

		// Tests 4-6: Addition
		data1[0][0] = 1;
		mat1 = new Matrix(data1);
		Matrix mat2 = new Matrix(data2);
		Matrix mat3 = mat1.add(mat2);
		e1 = new int[][] {{1, 2, 3}, {2, 3, 4}};
		printTestResult(get2DArray(mat1), e1, 4, "add method");
		e1 = new int[][] {{2, 1, 1}, {2, 3, 1}};
		printTestResult(get2DArray(mat2), e1, 5, "add method");
		e1 = new int[][] {{3, 3, 4}, {4, 6, 5}};
		printTestResult(get2DArray(mat3), e1, 6, "add method");

		// Test 7: Multiplication
		data2 = new int[][] {{1, 2}, {3, 1}, {2, 1}};
		mat2 = new Matrix(data2);
		mat1 = new Matrix(data1);
		mat3 = mat2.multiply(mat1);
		e1 = new int[][] {{5, 8, 11}, {5, 9, 13}, {4, 7, 10}};
		printTestResult(get2DArray(mat3), e1, 7, "multiply method");

		// Test 8: toString
		data1 = new int[][] {{10, 100, 101, -1000},
				     {1000, 10, 55, 4},
				     {1, -1, 4, 0}};
		mat1 = new Matrix(data1);
		String expected = "    10   100   101 -1000\n  1000    10    55     4\n     1    -1     4     0";
		System.out.print("Test number 8 tests the toString method."
				 + " The test ");
		if (mat1.toString().equals(expected)) {
			System.out.println("passed");
		} else {
			System.out.println("failed");
		}

		// Test 9: isUpperTriangular
		data1 = new int[][] {{1, 2, 3, 0},
				     {0, 3, 2, 3},
				     {0, 0, 4, -1},
				     {0, 0, 0, 12}};
		mat1 = new Matrix(data1);
		System.out.print("Test number 9 tests the "
				 + "isUpperTriangular method. The test ");
		if (mat1.isUpperTriangular()) {
			System.out.println("passed");
		} else {
			System.out.println("failed");
		}
		// Test 10: isUpperTriangular
		data1 = new int[][] {{1, 2, 3, 0},
				     {0, 3, 2, 3},
				     {0, 0, 4, -1},
				     {1, 2, 3, 4}};
		mat1 = new Matrix(data1);
		System.out.print("Test number 10 tests the "
				 + "isUpperTriangular method. The test ");
		if (!mat1.isUpperTriangular()) {
			System.out.println("passed");
		} else {
			System.out.println("failed");
		}

		// Begin my tests
		System.out.println("\nStudent tests begin");

		// Test 11: add
		System.out.print("Test 11 ");
		int[][] tmp = new int[][] {{0, 1, 2, 3, 4},
					   {-4, -3, -2, -1, 0},
					   {-2, -1, 0, 1, 2}};
		Matrix matrix1 = new Matrix(tmp);
		tmp = new int[][] {{0, -1, -2, -3, -4},
				   {4, 3, 2, 1, 0},
				   {2, 1, 0, -1, -2}};
		Matrix matrix2 = new Matrix (tmp);
		tmp = new int[][] {{0, 0, 0, 0, 0},
				   {0, 0, 0, 0, 0},
				   {0, 0, 0, 0, 0}};
		if (equals(get2DArray(matrix1.add(matrix2)), tmp)) {
			System.out.println("passed");
		} else {
			System.out.println("failed");
		}

		// Test 12: subtract
		System.out.print("Test 12 ");
		tmp = new int[][] {{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13},
				   {13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1}};
		matrix1 = new Matrix(tmp);
		matrix2 = new Matrix(tmp);
		tmp = new int[][] {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				   {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
		if (equals(get2DArray(matrix1.subtract(matrix2)), tmp)) {
			System.out.println("passed");
		} else {
			System.out.println("failed");
		}

		// Test 13: multiply
		System.out.print("Test 13 ");
		tmp = new int[][] {{1, 0, 2},
				   {-1, 3, 1}};
		matrix1 = new Matrix(tmp);
		tmp = new int[][] {{3, 1},
				   {2, 1},
				   {1, 0}};
		matrix2 = new Matrix(tmp);
		tmp = new int[][] {{5, 1},
				   {4, 2}};
		if (equals(get2DArray(matrix1.multiply(matrix2)), tmp)) {
			System.out.println("passed");
		} else {
			System.out.println("failed");
		}

		// Test 14: scale
		System.out.print("Test 14 ");
		tmp = new int[][] {{-10, -5, 0, 5, 10},
				   {-100, -50, 0, 50, 100},
				   {-1000, -500, 0, 500, 1000}};
		matrix1 = new Matrix(tmp);
		tmp = new int[][] {{-40, -20, 0, 20, 40},
				   {-400, -200, 0, 200, 400},
				   {-4000, -2000, 0, 2000, 4000}};
		matrix1.scale(4);
		if (equals(get2DArray(matrix1), tmp)) {
			System.out.println("passed");
		} else {
			System.out.println("failed");
		}

		// Test 15: toString
		System.out.print("Test 15 ");
		String test = "   -40   -20     0    20    40\n  -400  -200     0   200   400\n -4000 -2000     0  2000  4000";
		if (matrix1.toString().equals(test)) {
			System.out.println("passed");
		} else {
			System.out.println("failed");
		}

		// Test 16: transpose
		System.out.print("Test 16 ");
		tmp = new int[][] {{2, 3, 5, 7, 11, 13, 17},
				   {19, 23, 29, 31, 37, 41, 43}};
		matrix1 = new Matrix(tmp);
		tmp = new int[][] {{2, 19},
				   {3, 23},
				   {5, 29},
				   {7, 31},
				   {11, 37},
				   {13, 41},
				   {17, 43}};
		matrix1.transpose();
		if (equals(get2DArray(matrix1), tmp)) {
			System.out.println("passed");
		} else {
			System.out.println("failed");
		}

		// Test 17: equals
		System.out.print("Test 17 ");
		tmp = new int[][] {{1, 2, 3, 4},
				   {0, 1, 2, 3},
				   {0, 0, 1, 2},
				   {0, 0, 0, 1}};
		matrix1 = new Matrix(tmp);
		matrix2 = new Matrix(tmp);
		if (matrix1.equals(matrix2)) {
			System.out.println("passed");
		} else {
			System.out.println("failed");
		}

		// Test 18: isUpperTriangular
		System.out.print("Test 18 ");
		if (matrix1.isUpperTriangular()) {
			System.out.println("passed");
		} else {
			System.out.println("failed");
		}
	}

	private static void printTestResult (int[][] data1, int[][] data2,
					     int testNum,
					     String testingWhat) {
		System.out.print("Test number " + testNum + " tests the "
				 + testingWhat + ". The test ");
		if (equals(data1, data2)) {
			System.out.println("passed");
		} else {
			System.out.println("failed");
		}
	}

	private static int[][] get2DArray (Matrix m) {
		assert m != null && m.numRows() > 0 && m.numCols() > 0 :
		       "Violation of precondition: get2DArray";
		
		int[][] result = new int[m.numRows()][m.numCols()];
		for (int r = 0; r < result.length; r++) {
			for (int c = 0; c < result[0].length; c++) {
				result[r][c] = m.getVal(r, c);
			}
		}

		return result;
	}

	private static boolean equals (int[][] data1, int[][] data2) {
		assert data1 != null && data1.length > 0
		       && data1[0].length > 0 && rectangularMatrix(data1)
		       && data2 != null && data2.length > 0
		       && data2[0].length > 0 && rectangularMatrix(data2)
		       : "Violation of precondition: get2DArray";
		
		boolean result = data1.length == data2.length
				 && data1[0].length == data2[0].length;
		for (int r = 0; r < data1.length && result; r++) {
			for (int c = 0; c < data1[0].length && result; c++){
				result = data1[r][c] == data2[r][c];
			}
		}

		return result;
	}

	private static boolean rectangularMatrix (int[][] mat) {
		assert mat != null && mat.length > 0 && mat[0].length > 0
		       : "Violation of precondition: rectangularMatrix";
		
		boolean isRectangular = true;
		final int COLUMNS = mat[0].length;
		for (int r = 0; r < mat.length && isRectangular; r++) {
			isRectangular = mat[r].length == COLUMNS;
		}

		return isRectangular;
	}
}
