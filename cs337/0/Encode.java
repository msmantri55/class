import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encode {
	public static void main (String[] args) throws IOException {
		final String INPUT = "./project0.txt";
		final String OUTPUT = "./project0.enc";

		int c;
		FileInputStream in;
		FileOutputStream out;

		in = new FileInputStream(INPUT);
		out = new FileOutputStream(OUTPUT);

		while ((c = in.read()) != -1) {
			boolean parity = true;

			// >>>= instead of >>= to fill with 0s
			for (int n = c; n != 0; n >>>= 1) {
				if ((n & 1) == 1) {
					parity = !parity;
				}
			}

			if (!parity) {
				// |= instead of += to avoid potential overflow
				c |= 0x80;
			}

			out.write(c);
		}

		in.close();
		out.close();
	}
}
