/*
 Student information for assignment 6
 
 On our honor, Taylor Fausak and Ashley McKemie, this programming assignment is
 our own work.
 
 Student #1
 Name: Taylor Fausak
 EID: tdf268
 Email: tfausak@gmail.com
 TA: Vishvas
 Unique: 55435
 
 Student #2
 Name: Ashley McKemie
 EID: lam996
 Email: a.mckemie@mail.utexas.edu
 TA: Vishvas
 Unique: 55435
 */

// Imports
import java.util.ArrayList;
import java.lang.Math;
import java.awt.Graphics;
import java.awt.Color;

public class Recursive {
	/*
	 McCarthy's 91 function
	 */
	public int mcCarthy91 (int n) {
		assert n > 0 : "Failed Precondition: mc91";
		
		if (n <= 100) {
			return mcCarthy91(mcCarthy91(n + 11));
		} else {
			assert n > 100;
			
			return n - 10;
		}
	}

	/*
	 Returns the characters associated with this digit on a phone keypad
	 */
	private String digitLetters (char ch) {
		assert (ch >= '0') && (ch <= '9') : "Failed precondition: digitLetters";

		String[] letters = {"0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PRS", "TUV", "WXY"};
		int index = (int) (ch - '0');
		
		return letters[index];
	}

	public ArrayList<String> listMnemonics (String number) {
		assert number != null : "Failed precondition: listMnemonics";

		// TODO
		ArrayList<String> result = new ArrayList<String>();
		recursiveMnemonics(result, "", number);
		
		return result;
	}

	private void recursiveMnemonics (ArrayList<String> mnemonics, String mnemonicSoFar, String digitsLeft) {
		// TODO
	}

	/*
	 Returns a String that represents N in binary. All chars in the returned
	 String are either '1's or '0's. Most significant digit is at position 0.
	 */
	public String getBinary (int n) {
		assert n >= 0 : "Failed precondition: getBinary";
		
		if (n == 1) {
			return "1";
		} else {
			return getBinary(n / 2) + (n % 2);
		}
	}

	/*
	 Returns a String that is the reverse of target.
	 */
	public String revString (String target) {
		assert target != null : "Failed precondition: revString";
		
		if (target.length() == 1) {
			return target;
		} else {
			return target.charAt(target.length() - 1) + revString(target.substring(0, target.length() - 1));
		}
	}

	/*
	 Determine the sum of digits in n.
	 */
	public int sumOfDigits (int n) {
		assert n >= 0 : "Failed precondition: sumOfDigits";
		
		if (n == 0) {
			return 0;
		} else {
			return n % 10 + sumOfDigits (n / 10);
		}
	}

	/*
	 Determine if water at a given point on a map can flow off the map.
	 */
	public boolean canFlowOffMap (int[][] map, int row, int col) {
		assert map != null && map.length > 0 && isRectangular(map) && inbounds(row, col, map) : "Failed precondition: canFlowOffMap";

		boolean result = true;
		
		// Ask TAs about boolean map ('marked' values)
		for (int i = 0; i < 4; i++) {
			int[] tmp = canFlow(map, row, col, i);
			if (tmp != null) {
				if (canFlowOffMap(map, tmp[0], tmp[1])) {
					result = true;
				} else {
					result = false;
				}
			}
		}
		
		return result;
	}
	
	/*
	 Helper method for canFlowOffMap. Determines if water can flow in the
	 direction given.
	 */
	private int[] canFlow (int[][] map, int row, int col, int dir) {
		int[] result = null;
		
		// 0 is north, 1 is east, 2 is south, 3 is west
		if (dir == 0) {
			if (map[row][col] < map[row + 1][col]) {
				result = new int[] {row + 1, col};
			}
		} else if (dir == 1) {
			if (map[row][col] < map[row][col + 1]) {
				result = new int[] {row, col + 1};
			}
		} else if (dir == 2) {
			if (map[row][col] < map[row - 1][col]) {
				result = new int[] {row - 1, col};
			}
		} else {
			assert dir == 3;
			
			if (map[row][col] < map[row][col - 1]) {
				result = new int[] {row, col - 1};
			}
		}
		
		return result;
	}
	
	/*
	 Create a DrawingPanel and place Sierpinski triangles in it. The lower left
	 corner should be 20 pixels from the left and bottom edges of the window.
	 */
	public void drawTriangles (int windowSize, int minSideLength, int startingSideLength){ 
		DrawingPanel p = new DrawingPanel(windowSize, windowSize);
		Graphics g = p.getGraphics();
		g.setColor(Color.BLUE);
		drawTriangles(minSideLength, startingSideLength, 20, windowSize - 20, g);
		System.out.println();
	}

	public void drawTriangles (int minSideLength, double currentSideLength, double x1, double y1, Graphics g) {
		System.out.print(".");
		if (currentSideLength <= minSideLength) {
			int[] xPoints = {(int) x1, (int) (x1 + currentSideLength / 2), (int) (x1 + currentSideLength), (int) x1};
			int[] yPoints = {(int) y1, (int) (y1 - Math.sqrt(3) * currentSideLength / 2), (int) y1, (int) y1};
			int nPoints = 4;
			g.drawPolygon(xPoints, yPoints, nPoints);
		} else {
			drawTriangles(minSideLength, currentSideLength / 2, x1, y1, g);
			drawTriangles(minSideLength, currentSideLength / 2, x1 + currentSideLength / 2, y1, g);
			drawTriangles(minSideLength, currentSideLength / 2, x1 + currentSideLength / 4, y1 - Math.sqrt(3) * currentSideLength / 4, g);
		}
	}

	/*
	 Determines if the given point (row and column) is contained in mat
	 */
	private boolean inbounds (int r, int c, int[][] mat) {
		assert mat != null : "Failed precondition: inbounds";

		return r >= 0 && r < mat.length && mat[r] != null && c >= 0 && c < mat[r].length;
	}

	/*
	 Returns true if mat is rectangular.
	 */
	private static boolean isRectangular (int[][] mat) {
		assert (mat != null) && (mat.length > 0) : "Violation of precondition: isRectangular";

		boolean correct = true;
		final int numCols = mat[0].length;
		for (int r = 0; r < mat.length && correct; r++) {
			correct = (mat[r] != null) && (mat[r].length == numCols);
		}

		return correct;
	}

	/*
	 Draws a Sierpinski carpet.
	 */
	public void drawCarpet (int size, int limit) {
		DrawingPanel p = new DrawingPanel(size, size);
		Graphics g = p.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0,0,size,size);
		g.setColor(Color.WHITE);
		drawSquares(g, size, limit, 0, 0);
	}

	/*
	 Draw the individual squares of the carpet.
	 */
	private static void drawSquares(Graphics g, int size, int limit, double x, double y) {
		// TODO
		if (size >= limit) {
			g.fillRect((int) (x + size / 3), (int) (y + size / 3), size / 3, size / 3);
			
			for (int k = 0; k < 9; k++) {
				if (k != 4) {
					int i = k / 3;
					int j = k % 3;
					
					drawSquares(g, size / 3, limit, x + i * size / 3, y + j * size / 3);
				}
			}
		}
	}
}