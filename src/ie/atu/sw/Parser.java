package ie.atu.sw;

import java.io.*;

/*
 * This class take in the user file path and Boolean switch that chooses either logic that 
 * reads a folder of files or reads a single file. 
 * This class loops through the text file(s) and adds the words from the documents to a 1D array. 
 * I used StringBulider and the split() method to create the array dynamically. 
 * The design of this class is based on  Allner and Özdemir (2013); Healy (2022); Mad 2015; Sharma (2021).  
 */
public class Parser {
	// Stores the number of times parser() collects words from the BufferedReader
	private int wordCount;
	private boolean dirOrFile; // Set by the constructor
	private String inputDir; // Set by the constructor
	private String[] wordArray; // Stores words collected by parser()
	// Declared as class level so it can be seen by parseDir() and parser()
	private StringBuilder sb = new StringBuilder();

	public Parser(String inputDir, boolean fileTypeSwitch) throws Exception {
		this.wordCount = 0; // Initializing the word count to zero.
		this.inputDir = inputDir; // Path set by user in AppicationInterface 
		this.dirOrFile = fileTypeSwitch; // Boolean set by AppicationInterface 
	}
	
	// Returns the array of words to ApplicationInterface 
	public String[] getWordArray() {
		return this.wordArray;
	}
	
	// Publicly visible method to run the main function of the class
	public void runParser() throws Exception {
		parseDir(this.inputDir, this.dirOrFile);
	}
	
	// parseDir handles directories or single text files  
	private void parseDir(String inputDir, boolean dirOrFile) throws Exception {
		System.out.println("[INFO] Accessing data from " + this.inputDir);
		File fileDir = new File(inputDir); // Create new instance of a file

		try {
			// Checks what the user wants; true -> directory; false -> single file
			if (dirOrFile == true) {
				// Creates an array of files and excludes hidden file on Mac OS (Mad 2015)
				String[] fileNames = fileDir.list((dir, name) -> !name.equals(".DS_Store"));

				// Loops through files in directory
				for (String fileName : fileNames) {
					System.out.println("Reading from " + fileName + "...");
					// Gets the individual file name and exact file path
					File f = new File(fileDir.getAbsolutePath(), fileName);
					// Passes details to Buffered Reader in parser()
					parser(f);
				}

			} else {
				// Passes file path with single file to Buffered Reader
				System.out.println("Reading from " + fileDir + "...");
				File f = new File(fileDir.getAbsolutePath());
				parser(f);
			}
			/*
			 * Takes words appended in the parser() 'for loop' and adds them dynamically to
			 * a 1D array (Allner and Özdemir 2013). The for loop in parser appends the word
			 * and whitespace to sb. I added the whitespace to ensure that split() added
			 * each whole word as an individual element in the array
			 */
			wordArray = sb.toString().split("\\s+");
			System.out.println("[INFO] All data read from " + fileDir.getName() + ".");
			System.out.println("[INFO] The application has collected " + this.wordCount + " words from the file(s).");

			// Catches null pointer exception if the folder is empty
		} catch (NullPointerException npe) {
			// throws new exception to stop app running at buildNGram() and gives custom
			// feedback to the user
			throw new NullPointerException(ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] There are no files at: "
					+ this.inputDir + "Please revise option 1...");
		}
	}

	private void parser(File file) throws Exception {
		// The nested methods read data from the text file
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
			// Initializes String that store data from br  
			String line = null; 
			
			// Loops through each line of text in the file 
			while ((line = br.readLine()) != null) {
				// Creates an array that stores words from the line
				String[] words = line.split("\\s+");
				
				// Loops through each element of words[]
				for (String word : words) {
					// Collects Latin alphabet characters only and excludes non-alphabetic
					// characters, numbers and whitespace
					word = word.trim().replaceAll("[^a-zA-Z]", "").toLowerCase();
					//Adds processed word to class variable sb
					sb.append(word).append(" ");
					
					// Counts the number of loops 
					wordCount++;
				}
			}
			br.close(); // Closes to prevent memory leak 

		} catch (FileNotFoundException fnfe) {
			// throws new exception to stop app running at buildNGram() and give custom
			// feedback to the user
			throw new FileNotFoundException(ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] File not found at: "
					+ file.getName() + "Please revise option 1...");

		} catch (IOException ioe) {
			//Same as above... 
			throw new IOException(ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] Cannot read file: " + file.getName());
		}
	}
}
