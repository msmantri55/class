/*
	Student information for assignment 3

	On my honor, Taylor Fausak, this assignment is my own work.
	EID: tdf268
	E-mail address: tfausak@gmail.com
	TA name: Vishvas Vasuki
	Unique course ID: 55435

	Slip days info

	Slip days used on this assignment: 1
	Slip days I think I have used for the term thus far: 0
*/

// Imports

/*
	A class that models mathematical matrices for linear algebra.
*/
public class Matrix {
	// Instance variables
	// _cells stores the Matrix
	private int[][] _cells;

	/*
		Default constructor.
		Pre: None.
		Post: Create a 1x1 matrix that contains 0
	*/
	public Matrix () {
		// Make a 1x1 Matrix with 0 as the initial value
		this(1, 1, 0);
	}

	/*
		Create a Matrix with cells equal to the values in mat. A
		deep copy of mat is made. Changes to mat after this
		constructor do not affect this Matrix and changes to this
		Matrix do not affect mat.
		Pre: mat != null, mat.length > 0, mat[0].length > 0, mat is
		rectangular
	*/
	public Matrix (int[][] mat) {
		assert mat != null && mat.length > 0 && mat[0].length > 0 &&
		       rectangularMatrix(mat) : "Violation of precondition:"
		       + " int[][] Matrix constructor";

		// Initialize _cells to the correct size
		_cells = new int[mat.length][mat[0].length];

		// Fill each cell with the correct value
		for (int r = 0; r < mat.length; r++) {
			for (int c = 0; c < mat[0].length; c++) {
				_cells[r][c] = mat[r][c];
			}
		}
	}

	/*
		Create a Matrix of the specified size with all the cells set
		to the initial value.
		Pre: numRows > 0, numCols > 0
		Post: Create a matrix with numRows rows and numCols columns.
		All the elements of this Matrix equal initialVal.
	*/
	public Matrix (int numRows, int numCols, int initialVal) {
		assert numRows > 0 && numCols > 0 : "Violation of "
		       + "precondition: int, int, int Matrix contructor";

		// Initialize _cells to the correct size
		_cells = new int[numRows][numCols];

		// Set each cell as the initial value
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				_cells[r][c] = initialVal;
			}
		}
	}

	/*
		Change the value of one of the cells in this Matrix.
		Pre: 0 <= row < numRows(), 0 <= col < numCols()
		Post: getVal(row, col) = newValue
	*/
	public void changeElement (int row, int col, int newValue) {
		assert row >= 0 && row < numRows() && col >= 0 &&
		       col < numCols() : "Violation of precondition: "
		       + "changeElement";

		_cells[row][col] = newValue;
	}

	/*
		Get the number of rows.
	*/
	public int numRows() {
		return _cells.length;
	}

	/*
		Get the number of columns.
	*/
	public int numCols() {
		return _cells[0].length;
	}

	/*
		Get the value of a cell in this Matrix.
		Pre: 0 <= row < numRows(), 0 <= col < numCols()
	*/
	public int getVal (int row, int col) {
		assert row >= 0 && row < numRows() && col >= 0 &&
		       col < numCols() : "Violation of precondition: "
		       + "getVal";

		return _cells[row][col];
	}

	/*
		Implements Matrix addition, this Matrix + rightHandSide.
		Pre: rightHandSide.numRows() = numRows(),
		rightHandSide.numCols() = numCols()
		Post: Does not alter the calling object or rightHandSide.
	*/
	public Matrix add (Matrix rightHandSide) {
		assert rightHandSide.numRows() == numRows() &&
		       rightHandSide.numCols() == numCols() : "Violation of"
		       + " precondition: add";

		// Create an array to hold the result
		int[][] result = new int[numRows()][numCols()];

		// Add every value in RHS to the current Matrix
		for (int r = 0; r < numRows(); r++) {
			for (int c = 0; c < numCols(); c++) {
				result[r][c] = getVal(r, c)
					     + rightHandSide.getVal(r, c);
			}
		}

		return new Matrix(result);
	}

	/*
		Implements Matrix subtraction, this Matrix - rightHandSide.
		Pre: rightHandSide.numRows() = numRows(),
		rightHandSide.numCols() = numCols()
		Post: Does not alter the calling object or rightHandSide.
	*/
	public Matrix subtract (Matrix rightHandSide) {
		assert rightHandSide.numRows() == numRows() &&
		       rightHandSide.numCols() == numCols() : "Violation of"
		       + " precondition: add";

		// Create an array to hold the result
		int[][] result = new int[numRows()][numCols()];

		// Subtract every value in RHS from the current Matrix
		for (int r = 0; r < numRows(); r++) {
			for (int c = 0; c < numCols(); c++) {
				result[r][c] = getVal(r, c)
					     - rightHandSide.getVal(r, c);
			}
		}

		return new Matrix(result);
	}

	/*
		Implements Matrix multiplication, this Matrix *
		rightHandSide.
		Pre: rightHandSide.numRows() = numCols()
		Post: Does not alter the calling object or rightHandSide.
	*/
	public Matrix multiply (Matrix rightHandSide) {
		assert rightHandSide.numRows() == numCols() : "Violation "
		       + "of precondition: multiply";

		// Create an array to hold the result
		int rows = numRows();
		int cols = rightHandSide.numCols();
		int[][] result = new int[rows][cols];

		// Loop through every element in the result array
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				// Explanation of matrix multiplication: http://en.wikipedia.org/wiki/Matrix_multiplication
				for (int i = 0; i < numCols(); i++) {
					result[r][c] += getVal(r, i)
						      * rightHandSide.getVal(i, c);
				}
			}
		}

		return new Matrix(result);
	}

	/*
		Multiply all the elements of this Matrix by a scalar.
		Pre: None.
		Post: All elements in this Matrix have been multiplied by
		factor.
	*/
	public void scale (int factor) {
		for (int r = 0; r < numRows(); r++) {
			for (int c = 0; c < numCols(); c++) {
				changeElement(r, c, factor * getVal(r, c));
			}
		}
	}

	/*
		Accessor: get a transpose of this Matrix. This Matrix is not
		changed.
		Pre: None.
	*/
	public Matrix getTranspose () {
		// Store the result in an array
		int rows = numCols();
		int cols = numRows();
		int[][] result = new int[rows][cols];

		// Flip the rows and columns
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				result[r][c] = getVal(c, r);
			}
		}

		return new Matrix(result);
	}

	/*
		Mutator: Transpose this Matrix.
		Pre: None.
		Post: numRows() = old numCols(), numCols() = old numRows()
	*/
	public void transpose () {
		// Store the result in an array
		int rows = numCols();
		int cols = numRows();
		int[][] result = new int[rows][cols];

		// Flip the rows and columns
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				result[r][c] = getVal(c, r);
			}
		}

		_cells = result;
	}

	/*
		Override quals. Return true if rightHandSide is the same
		size as this Matrix and all values in the two Matrix objects
		are the same, false otherwise.
	*/
	public boolean equals (Object rightHandSide) {
		// Assume they are equal at first
		boolean equal = true;

		// Exit the loop immediately if there is a mismatch
		for (int r = 0; r < numRows() && equal; r++) {
			for (int c = 0; c < numCols() && equal; c++) {
				if (getVal(r, c) !=
				    ((Matrix) rightHandSide).getVal(r, c)) {
					equal = false;
				}
			}
		}

		return equal;
	}

	/*
		Override toString. Return a String with all the elements of
		this Matrix. Each row is on a seperate line. Spacing is
		based on the longest element in this Matrix.
	*/
	public String toString () {
		String result = "";
		int max = 0;

		// One pass to find the longest integer
		for (int r = 0; r < numRows(); r++) {
			for (int c = 0; c < numCols(); c++) {
				if (("" + getVal(r, c)).length() > max) {
					max = ("" + getVal(r, c)).length();
				}
			}
		}

		// Another pass to append the values with padding to the result
		for (int r = 0; r < numRows(); r++) {
			// This prevents adding a new line at the end
			if (r != 0) {
				result += "\n";
			}

			for (int c = 0; c < numCols(); c++) {
				// Add spacing
				for (int i = ("" + getVal(r, c)).length(); i <= max; i++) {
					result += " ";
				}

				// Add the value
				result += getVal(r, c);
			}
		}

		return result;
	}

	/*
		Return true if this Matrix is in upper triangular form. To
		be upper triangular, all the elements below the main
		diagonal must be 0.
		Pre: numRows() == numCols()
	*/
	public boolean isUpperTriangular () {
		assert numRows() == numCols() : "Violation of precondition"
		      + ": isUpperTriangular";

		// Assume it is in upper triangular form at first
		boolean result = true;

		// Loop will not execute for 1x1 matrices (which are always in
		// upper triangular form)
		for (int r = 1; r < numRows() && result; r++) {
			// Only check below the diagonal (column < row)
			for (int c = 0; c < r && result; c++) {
				// Result is false if the value isn't zero
				if (getVal(r, c) != 0) {
					result = false;
				}
			}
		}

		return result;
	}

	/*
		Private method to ensure mat is rectangular.
	*/
	private boolean rectangularMatrix (int[][] mat) {
		boolean isRectangular = true;
		int row = 1;
		final int COLUMNS = mat[0].length;

		while (isRectangular && row < mat.length) {
			isRectangular = mat[row].length == COLUMNS;
			row++;
		}

		return isRectangular;
	}
}

/*
	Code for experiments involving adding and multiplying matrices.

import java.util.Random;

public class A3Exp {
	public static void main (String[] args) {
		Stopwatch s = new Stopwatch();
		Random g = new Random();

		experiment1(550, s, g);
		experiment1(1100, s, g);

		experiment2(75, s, g);
		experiment2(150, s, g);
	}

	private static void experiment1 (int size, Stopwatch s, Random g) {
		Matrix matrix1 = new Matrix(size, size, 0);
		Matrix matrix2 = new Matrix(size, size, 0);

		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				matrix1.changeElement(r, c, g.nextInt());
				matrix2.changeElement(r, c, g.nextInt());
			}
		}

		double total = 0.0;

		for (int i = 0; i < 100; i++) {
			s.start();
			matrix1.add(matrix2);
			s.stop();
			total += s.time();
		}

		System.out.println(size + "x" + size + " addition done 100 times in " + round(total, 5) + " s (" + round(total / 100, 5) + " s per)");
	}

	private static void experiment2 (int size, Stopwatch s, Random g) {
		Matrix matrix1 = new Matrix(size, size, 0);
		Matrix matrix2 = new Matrix(size, size, 0);

		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				matrix1.changeElement(r, c, g.nextInt());
				matrix2.changeElement(r, c, g.nextInt());
			}
		}

		double total = 0.0;

		for (int i = 0; i < 100; i++) {
			s.start();
			matrix1.multiply(matrix2);
			s.stop();
			total += s.time();
		}

		System.out.println(size + "x" + size + " multiplication done 100 times in " + round(total, 5) + " s (" + round(total / 100, 5) + " s per)");
	}

	private static double round (double n, int digits) {
		double power = 1.0;
		for (int i = 1; i <= digits; i++) {
			power *= 10;
		}
		return ((int) (n * power)) / power;
	}
}
*/
