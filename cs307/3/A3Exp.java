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

	private static void printTest (int n, boolean r) {
		System.out.print("Test #" + n + ": ");
		if (r) {
			System.out.println("passed");
		} else {
			System.out.println("failed");
		}
	}
}
