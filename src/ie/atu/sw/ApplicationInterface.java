package ie.atu.sw;

import java.io.IOException;
import java.util.Scanner;

/*
 * This class performs the following functions:
 * 		* Displays the menu interface
 * 		* Collects data from the user and stores it as instance variables
 * 		* Communicates with all classes in the application 
 * 		* Catches exceptions from all classes
 * 		* Runs and closes the application 
 * 
 * The design of this class is based on the 51305 week 9 and week 13 workshops. 
 */
public class ApplicationInterface {
	// keepMenuRunning is set by the class's constructor; fileTypeSwitch is set by getTextFileDir()
	private boolean keepMenuRunning, fileTypeSwitch;
	private int nGramSize; // is set by getNGramSize()
	private MenuOptions mo = new MenuOptions(); // Lets ApplicationInterface see the MenuOptions class
	private Scanner userInput; // is set by the class's constructor
	private String inputDir, outputDir; // inputDir is set by getTextFileDir(); outputDir is set by getOutputFile()

	// The constructor initializes the Scanner input and sets keepMenuRunning to
	// true.
	public ApplicationInterface() {
		this.userInput = new Scanner(System.in);
		this.keepMenuRunning = true;
	}

	/*
	 * start() uses a switch statement to call the other methods in this class. The
	 * try/catch block in the loop prevents the app from dying if the user inputs
	 * text to nextInt() or numbers to .next().
	 */
	public void start() {
		do { 
			try {
				mo.showMainMenuOptions(); // Calls the menu options 
				int choice = userInput.nextInt(); // Blocks the control flow and waits for user input

				switch (choice) {
					case 1 -> getTextFileDir(); // Calls method to set the input directory 
					case 2 -> getNGramSize(); // Calls methods to set the n-gram size 
					case 3 -> getOutputFile(); // Calls method to set the output directory 
					case 4 -> buildNGram(); // Calls method that runs the main actions of the app
					case 5 -> {
						System.out.println("[INFO] The application is shutting down. Please wait...");
						resetApp(); // Resets the class's instance variable before shutting down 
						keepMenuRunning = false; // Sets the instance variable that closes the do loop 
					}
					default -> { // Displays user feedback. 
					System.out.println(ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] Invalid input...");
					System.out.println(ConsoleColour.CYAN
							+ "[INFO] Please select one of the options listed 1 to 5 in the Main Menu.");
					}
				}
			} catch (Exception e) { // Catches invalid input to the Scanner class
				//The catch block also prevents an endless loop (Yo and zx485 2016).
				System.out.println(ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] Invalid input...");
				e.printStackTrace();
				userInput.reset(); // Resets the Scanner variable 
				userInput.next(); // Clears the Scanner buffer 
			}
		} while (keepMenuRunning); // keeps the do loop running while Boolean value is true 
		System.out.println("[INFO] The application has shutdown."); // Displays after the do loop is closed 
	}

	/*
	 * getTextFileDir() allows the user to specify either a folder of files or an
	 * individual file path. This is achieved by using a Boolean switch that is passed
	 * to the parseDir() method in the Parser class. True means folder and false means
	 * single file.
	 * 
	 * I use an if/else block because I found the break keyword a good way to return
	 * to the outer main menu do loop. It was difficult to use the break keyword in the
	 * switch statement.
	 */
	private void getTextFileDir() {
		do {
			mo.showSubMenuOneOptions(); // Calls the menu options 
			int choice = userInput.nextInt(); // Blocks the control flow and waits for user input

			if (choice == 1) {
				System.out.println(ConsoleColour.CYAN + "[INFO] Specify the location of the files on your local drive>");
				this.inputDir = userInput.next(); // Sets the instance variable inputDir to the user file path
				this.fileTypeSwitch = true; // In this case true means a file path to a folder of files 
				break; // returns to outer main menu do loop

			} else if (choice == 2) {
				System.out.println(
						ConsoleColour.CYAN + "[INFO] Specify the file path, file name and file type extension>");
				this.inputDir = userInput.next(); // Sets the instance variable inputDir to the user file path
				this.fileTypeSwitch = false; // In this case false means a file path to an individual text file 
				break; // returns to outer main menu do loop

			} else {
				System.out.println(ConsoleColour.RED_BOLD_BRIGHT + "[ERROR] Please select either option 1 or 2...");
			}
		} while (keepMenuRunning); // The do loop keeps running until the user sets a file path  
		System.out.println("[INFO] Data will be read from: " + this.inputDir
				+ "\n[INFO] Select the next option from the main menu to continue.");
	}

	/*
	 * getNGramSize() uses exception handling to ensure that the user enters a value
	 * between 1 and 5. getNGramSize() communicates with the Validation class that
	 * contains the logic that checks the input and throws an exception.
	 */
	private void getNGramSize() {
		do {
			try {
				System.out.println(ConsoleColour.CYAN + "[INFO] Enter an n-Gram size between 1 and 5>");
				int choice = userInput.nextInt(); // Blocks the control flow and waits for user input
				Validation.validateNGramSize(choice); // Passes user input to the Validation class 
				this.nGramSize = choice; // Sets the instance variable nGramSize if an exception is NOT thrown by validateNGramSize() 
				break; // returns to outer main menu do loop 

			} catch (Exception e) { // Catches the exception thrown by validateNGramSize()
				e.getMessage(); // Prints the custom message in validateNGramSize()
				e.printStackTrace(); // Prints the stack trace of the error 
			}
		} while (keepMenuRunning); // The do loop keeps running until a valid n-gram size is input by the user 
		System.out.println("[INFO] The n-Gram size is set at " + this.nGramSize
				+ ".\n[INFO] Select the next option from the main menu to continue.");
	}

	/*
	 * getOutputFile() prompts the user to enter the file path and a file name for
	 * the output directory. As the assignment brief states that the output should
	 * be a .csv file, the method ensures that .csv is appended to the file name.
	 */
	private void getOutputFile() {
		System.out.println("[INFO] Enter the file path for the output file>");
		String filePath = userInput.next(); // Sets the file path as a local variable

		System.out.println("[INFO] Enter the file name>");
		String fileName = userInput.next(); // Sets the file name as a local variable

		// sets the instance variable out outputDir with the
		// details of the local variable and appends the .csv extension
		this.outputDir = filePath + "/" + fileName + ".csv";
		System.out.println("[INFO] The file will be saved to " + this.outputDir
				+ "\n[INFO] Select the next option from the main menu to continue.");
	}

	/*
	 * buildNGram() performs the mains actions of the app.
	 * 
	 * I choose to create new objects in this way so that the application resets the
	 * objects after each execution in the 'do loop'. The workflow ensures that word
	 * array in the Parser class, and the n-gram table array in the NGramGenerator
	 * class do not store data from previous executions when the user runs the app
	 * more than once.
	 * 
	 * Additionally, the try/catch block aims to catch most of the exceptions so
	 * that the application stops at this method.
	 */
	private void buildNGram() { 
		try {
			// Ensures that the user has completed options 1, 2 and 3 
			Validation.checkInputFeilds(this.inputDir, this.nGramSize, this.outputDir); 
			
			// Creates a new instance of Parser and passes user input 
			Parser p = new Parser(this.inputDir, this.fileTypeSwitch); 
			
			// Calls method that runs the key methods in the Parser class
			p.runParser();   
			
			// Creates a new instance of NGramGenerator and passes user input
			NGramGenerator ng = new NGramGenerator(this.nGramSize); 
			
			// Gets the word array stored in Parser and passes it to the key methods in NGramGenerator
			ng.runNGramGenerator(p.getWordArray()); 

			// Gets the n-gram table array stored in NGramGenerator and passes it with user input
			Utils.save(ng.getNGramTable(), this.outputDir);  

			// Calls method that give option to open the saved .csv file 
			callOpenFileOption(); 

			// Calls method that resets the key instance variables in this class
			resetApp(); 
			
			// Prints user feedback 
			System.out.println("[INFO] Returning to the Main Menu...");

		} catch (Exception e) { // Catches exceptions thrown by methods in Parser, Utils and Validation
			e.getMessage(); // Prints custom user feedback 
			e.printStackTrace();// Prints Stack trace of errors 
		}
	}
	
	// callOpenFileOption() uses a basic Boolean switch that gives the user the option to open the .csv file. 
	private void callOpenFileOption() throws IOException {
		System.out.println("[INFO] Do you want to open the file? \n[INFO] Enter Y for Yes or N for No>");
		String choice = userInput.next();
		
		// Adapted from Deitel and Deitel (2002) 
		if (choice.equalsIgnoreCase("y")) { // Passes back to buildNGram() if user enters any other key 
			Utils.openFile(this.outputDir);; // Calls openFileOption() in Utils if user enters y or Y 
		}
	}
	
	// resetApp() resets key instance variable associated with user input
	private void resetApp() {
		System.out.println("[INFO] Resetting the application...");
		this.inputDir = null;
		this.nGramSize = 0;
		this.outputDir = null;
	}
}
