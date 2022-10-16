package ie.atu.sw;

import java.awt.Desktop;
import java.io.*;

/*
 * This class contains methods that relate to the utilities of the class. 
 * To my knowledge, the methods are state-less, so are declared static. 
 */

public class Utils {
	//Header for the table used by save() 
	private static final String[][] TABLE_HEADER = { { "N-GRAMS", "COUNT", "PROBABILITY" } };

	// Assist stripped from Healy (2022) 
	// I have left the original comments
	public static void printProgress(int index, int total) {
		if (index > total)
			return; // Out of range

		int size = 50; // Must be less than console width
		char done = '█'; // Change to whatever you like.
		char todo = '░'; // Change to whatever you like.

		// Compute basic metrics for the meter
		int complete = (100 * index) / total;
		int completeLen = size * complete / 100;

		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < size; i++) {
			sb.append((i < completeLen) ? done : todo);
		}

		System.out.print("\r" + sb + "] " + complete + "%");

		// Once the meter reaches its max, move to a new line.
		if (done == total)
			System.out.println("\n");
	}
	
	// Assist stripped from Healy (2022)  
	public static void save(Object[][] table, String file) throws Exception {
		System.out.println("[INFO] Saving file...");

		// Creates a new file and new instance of PrintWriter
		try (PrintWriter pw = new PrintWriter(new File(file))) {
			
			//Outer loop prints the header 
			for (int header = 0; header < TABLE_HEADER.length; header++) {
				pw.write(TABLE_HEADER[header][0] + ", " + TABLE_HEADER[header][1] + ", " + TABLE_HEADER[header][2]
						+ "\n");
				//Nested loop prints the content of the Object[][] from NGramGenerator 
				for (int row = 0; row < table.length; row++) {
					// Strips out null entries in the Object[][]
					if (table[row][0] != null) { 
						pw.write(table[row][0] + ", " + table[row][1] + ", " + table[row][2] + "\n");
					}
				}
			}
			// Flush and Closes to prevent loss of data
			pw.flush(); 
			pw.close(); 

			System.out.println("[INFO] The n-Gram frequency chart is saved to: " + file.toString());

		} catch (FileNotFoundException fnfe) {
			// throws new exception to stop app running at buildNGram() and give custom
			// feedback to the user
			throw new FileNotFoundException(ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] Invalid file path or file name: "
					+ file.toString() + "Please revise option 3...");
		}
	}
	
	// Assist stripped from How to Open a File in Java (n.d) 
	public static void openFile(String f) throws IOException {
		System.out.println("[INFO] Opening file...");
		// Constructor of file class having file as argument
		File file = new File(f); 

		// Checks if Desktop is supported by OS 
		if (!Desktop.isDesktopSupported()) {
			System.out.println(ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] File opening is not supported by your system...");
			return;
		}

		Desktop desktop = Desktop.getDesktop();
		if (file.exists()) // checks file exists or not
			desktop.open(file); // opens the specified file
	}
}
