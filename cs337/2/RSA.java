/**
 * Taylor Fausak and Evan Volpe
 * CS337 Project 2
 * March 5, 2010
 *
 * Encrypts or decrypts a file using RSA.
 */

// javac -Xlint:all *.java ; java -ea KeyGen ; java -ea RSA encrypt input.txt key.txt ; java -ea RSA decrypt encrypted key.txt ; diff input.txt decrypted

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RSA {
	static final String ENCRYPTED_OUTPUT_FILE_NAME = "encrypted";
	static final String DECRYPTED_OUTPUT_FILE_NAME = "decrypted";
	
	public static void main (String... args) {
		BufferedReader reader;
		InputStream input;
		long n, e, d;
		OutputStream output;
		
		try {
			if (args.length != 3) {
				throw new IllegalArgumentException("Wrong number of arguments");
			}
			
			input = new FileInputStream(args[1]);
			reader = new BufferedReader(new FileReader(args[2]));
			n = Long.parseLong(reader.readLine());
			e = Long.parseLong(reader.readLine());
			d = Long.parseLong(reader.readLine());
			reader.close();
			
			if ("encrypt".equals(args[0])) {
				output = new FileOutputStream(ENCRYPTED_OUTPUT_FILE_NAME);
				encrypt(n, e, input, output);
			}
			else if ("decrypt".equals(args[0])) {
				output = new FileOutputStream(DECRYPTED_OUTPUT_FILE_NAME);
				decrypt(n, d, input, output);
			}
			else {
				throw new IllegalArgumentException("Unknown option");
			}
		
			input.close();
			output.flush();
			output.close();
		} catch (IllegalArgumentException exception) {
			System.out.println(exception);
			System.out.println("Probable cause: wrong or unknown arguments");
			System.out.println("Usage: java RSA encrypt input key_file");
			System.out.println("       java RSA decrypt input key_file");
			System.exit(1);
		} catch (IOException exception) {
			System.out.println(exception);
			System.out.println("Probable cause: file system trouble");
			System.exit(1);
		}
	}
	
	/**
	 * Decrypts a message encrypted using RSA.
	 * @param n modulus component of the key
	 * @param d decryption (private) component of the key
	 * @param input input stream to read encrypted data from
	 * @param output output stream to write decrypted data to
	 * @return 
	 */
	static void decrypt (long n, long d, InputStream input, OutputStream output) throws IOException {
		byte[] bytes;
		long cipher, message;
		int read;
		
		// Read the input 4 bytes at a time
		while ((read = input.read(bytes = new byte[4])) != -1) {
			assert(read == 4);
			
			// Build the cipher from the bytes
			// It's necessary to mask with 0xff in order to avoid sign extension
			cipher = (0xff & (short) bytes[0]) << 8;
			cipher = (cipher | (0xff & (short) bytes[1])) << 8;
			cipher = (cipher | (0xff & (short) bytes[2])) << 8;
			cipher = cipher | (0xff & (short) bytes[3]);
			
			message = pow_mod(cipher, d, n);
			
			// Split the message into bytes for writing
			bytes = new byte[] {
				(byte) ((message & 0xff0000) >> 16),
				(byte) ((message & 0xff00) >> 8),
				(byte) (message & 0xff)
			};
			
			// Check for padding bytes at the end of the file
			if (input.available() == 0) {
				// Write to output, stopping if a zero byte occurs
				if (bytes[0] != 0) {
					output.write(bytes[0]);
				}
				if (bytes[1] != 0) {
					output.write(bytes[1]);
				}
				output.write(bytes[2]);
			}
			else {
				output.write(bytes);
			}
		}
	}
	
	/**
	 * Encrypts a message using RSA.
	 * @param n modulus component of the key
	 * @param e encryption (public) component of the key
	 * @param input input stream to read decrypted data from
	 * @param output output stream to write encrypted data to
	 * @return 
	 */
	static void encrypt (long n, long e, InputStream input, OutputStream output) throws IOException {
		byte[] bytes;
		long message, cipher;
		int read;
		
		// Read the input 3 bytes at a time
		while ((read = input.read(bytes = new byte[3])) != -1) {
			assert(read > 0 && read < 4);
			
			// Pad the input with zeros if not enough bytes were read
			if (read == 1) {
				bytes = new byte[] {0, 0, bytes[0]};
			}
			else if (read == 2) {
				bytes = new byte[] {0, bytes[0], bytes[1]};
			}
			
			// Build the message from the bytes
			// It's necessary to mask with 0xff in order to avoid sign extension
			message = (0xff & (short) bytes[0]) << 8;
			message = (message | (0xff & (short) bytes[1])) << 8;
			message = message | (0xff & (short) bytes[2]);
			cipher = pow_mod(message, e, n);
			
			// Split the cipher into bytes for writing
			bytes = new byte[] {
				(byte) ((cipher & 0xff000000) >> 24),
				(byte) ((cipher & 0xff0000) >> 16),
				(byte) ((cipher & 0xff00) >> 8),
				(byte) (cipher & 0xff)
			};
			
			output.write(bytes);
		}
	}
	
	/**
	 * Efficiently computes large powers modulo some number using squaring.
	 * @param base
	 * @param power
	 * @param modulus
	 * @return base to the power mod modulus
	 */
	static long pow_mod (long base, long power, long modulus) {
		String bitString;
		char[] bits;
		long result;
		
		// Ironically, String.toCharArray is the fastest way we found
		bitString = Long.toBinaryString(power);
		bits = bitString.toCharArray();
		result = 1;
		
		for (char bit : bits) {
			result = (result * result) % modulus;
			
			if (bit == '1') {
				result = (result * base) % modulus;
			}
		}
		
		return result;
	}
}
