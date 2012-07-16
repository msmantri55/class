// Imports
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import javax.swing.JFileChooser;

public class FileToStringConverter {
	private String fileAsString;
	
	/*
	 Pick a new file and convert it to a String
	 */
	public void chooseNewFile () {
		// Create a GUI window to pick the file to evaluate
		JFileChooser chooser = new JFileChooser(".");
		StringBuffer text = new StringBuffer();
		int retval = chooser.showOpenDialog(null);
		chooser.grabFocus();
		
		// Read in the file
		if (retval == JFileChooser.APPROVE_OPTION) {
			File source = chooser.getSelectedFile();
			
			try {
				Scanner s = new Scanner(new FileReader(source));
				
				while (s.hasNextLine()) {
					text.append(s.nextLine());
					text.append(" ");
				}
				
				s.close();
			} catch (IOException e) {
				System.out.println("An error occurred while trying to read from the file: " + e);
			}
		}
		
		fileAsString = text.toString();
	}
	
	/*
	 Get the selected file as a string
	 */
	public String getFileAsString () {
		return fileAsString;
	}
	
	/*
	 Return the "words" from the file. Words are any collection of characters
	 separated by whitespace. This leads to some non-standard "words." For
	 example: "No, could be a word with the quote and comma included.
	 */
	public String[] getWords () {
		return fileAsString.split("\\s+");
	}
}