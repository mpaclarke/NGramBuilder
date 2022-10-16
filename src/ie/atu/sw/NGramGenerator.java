package ie.atu.sw;

/*
 * This class loops through the word array generated in the Parser class,
 * generates n-grams and adds them to a 2D object array. 
 */
public class NGramGenerator {
	public static final int MIN_N_GRAM_SIZE = 1; //Set to public so it is visible to the Validation class
	public static final int MAX_N_GRAM_SIZE = 5;
	private int nGramSize; // Sets the n-gram size for the class 
	private Object[][] nGramTable; // Collects the n-grams, their frequency and their probability 
	// Boolean variables control the state of the progress bar 
	private boolean isWindows; // This variable is checked before running the progress bar

	/*
	 * The constructor takes in the n-gram size from ApplicationInterface and
	 * set the n-gram table to have 26 ^ n-gram size of rows, and three columns. 
	 *
	 * As collectWords() implements a progress bar that does not run on Windows,
	 * I used the constructor to check the OS type.   
	 */
	public NGramGenerator(int nGramSize) throws InterruptedException {
		this.nGramSize = nGramSize; //Sets the n-gram size 
		this.nGramTable = new Object[(int) Math.pow(26, nGramSize)][3]; // Sets the size of the n-gram object array 
		this.isWindows = Validation.isWindows(); // Validation.isWindows() returns true if the OS is windows 
	}

	// Returns n-gram table to buildNGram() in Application Interface
	public Object[][] getNGramTable() { 
		return this.nGramTable;
	}

	// Publicly visible method to run the main function of the class
	// Takes in the word array from ApplicationInterface and passes it to collectWords()
	public void runNGramGenerator(String[] arr) throws InterruptedException {
		System.out.println("[INFO] Building " + this.nGramSize + "-Grams...");
		collectWords(arr);
	}

	// Loops through the word array and passes each element to getNGrams()
	// Implements the progress bar in the Utils class
	private void collectWords(String[] array) throws InterruptedException {
		int size = array.length; // Sets the size of the progress bar
		for (int i = 0; i < array.length; i++) { // loops through array
			getNGrams(array[i]); // Passes array element to getNGrams()

			if (isWindows == false) // checks that the OS is NOT Windows
				callProgressBar(size, i); // passes values to control progress bar
		}
		// Prints feedback after the collection and progress bar is complete.
		System.out.println("\n[INFO] The application has processed the " + this.nGramSize + "-Grams.");
	}
	
	private void callProgressBar(int size, int i) throws InterruptedException {
		Utils.printProgress(i + 1, size);
		// Thread.sleep slowed down the app when the word is over 100,000 elements
		if (size <= 100_000)
		// Interrupted Exception is thrown to buildNGram() in Application Interface
		// Not likely to cause an issue as value is hard-coded 
			Thread.sleep(10);
	}
	
	//Asset stripped from 51305 week 10 workshop 
	private void getNGrams(String word) {
		// Excludes (- n + 1) after word.length as the method does not process sentences
		String[] ngrams = new String[word.length()];  

		for (int i = 0; i <= word.length() - nGramSize; i++) { // Loops over each word
			// Extracts n-grams based on size specified by the user 
			ngrams[i] = word.substring(i, i + nGramSize); 
			addNGrams(ngrams[i]); // passes n-grams to addNGrams() 
		}
	}

	/*
	 * addNGrams() is assist stripped from Healy (2022).
	 * 
	 * I recognize that as there is no loop, elements are not overwritten as they
	 * are assigned to the Object[][]. However, I don't have 100% clarity on how the
	 * modulus operator, and hashCode() method, assign elements to int index.
	 * Additionally I was not able to address the hash code collisions.
	 * 
	 * I added a probability measure for each n-gram.
	 */
	private void addNGrams(String ngram) { // n-gram counter
		int index = ngram.hashCode() % nGramTable.length; // no looping so really fast (REVISE THIS)

		long counter = 1l; // Initializes the n-gram counter 
		double probability = 0.0d; // Initializes the probability measure 
		if (nGramTable[index][0] != null) {
			counter += (Long) nGramTable[index][1];
		}
		//Calculates the probability of the n-gram
		probability = counter / Math.pow(26, nGramSize);

		// adds n-grams to first column in the object array
		nGramTable[index][0] = ngram; 
		// adds n-grams frequency to second column in the object array
		nGramTable[index][1] = counter;
		// adds probability of n-grams to third column; token use of Math.round! 
		nGramTable[index][2] = counter + " / (26 ^ " + nGramSize + ") = "
				+ (Math.round(probability * 1_000_000_000d) / 1_000_000_000d); 
	}
}
