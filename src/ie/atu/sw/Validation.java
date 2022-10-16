package ie.atu.sw;

/*
 * I put the validation methods into their own class to declutter the other classes.
 * To my knowledge, the methods are state-less, so are declared static.
 */

public class Validation {

	//Logic based on the 51305 week 12 workshops 
	public static void validateNGramSize(int choice) throws Exception {
		// Checks if user input is between range defined in NGramGenerator 
		if (choice == 0 || choice < NGramGenerator.MIN_N_GRAM_SIZE || choice > NGramGenerator.MAX_N_GRAM_SIZE) {
			// Throws exception to getNGramSize() 
			throw new Exception(ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] The n-Gram size must be between "
					+ NGramGenerator.MIN_N_GRAM_SIZE + " and " + NGramGenerator.MAX_N_GRAM_SIZE + "...");
		}
	}
	
	/*
	 * I wanted to tell the user what field they did not complete, so the first three
	 * 'if statements' print feedback before the final 'if statement' throws the
	 * exception. It was the best I could come up with...
	 */
	public static void checkInputFeilds(String inputDir, int nGramSize, String outputDir) throws Exception {
		if (inputDir == null) {
			System.out.println(ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] The text file directory for option 1 is missing.");
		}
		if (nGramSize == 0) {
			System.out.println(ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] The n-Gram size for option 2 is missing.");
		}
		if (outputDir == null) {
			System.out.println(ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] The output file for option 3 is missing.");
		}
		if (inputDir == null || nGramSize == 0 || outputDir == null) {
			throw new Exception("[ERROR] You must enter data for all feilds before you select \"Build n-Grams\"...");
		}
	}

	// Logic based on VonC (2008) 
	public static boolean isWindows() {
		// Assign name of OS to a String 
		String os = System.getProperty("os.name");
		//Returns Boolean value based on name
		if (os.startsWith("Windows")) {
			return true;
		
		} else {
			return false;
		}
	}
}
