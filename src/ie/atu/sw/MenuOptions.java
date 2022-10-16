package ie.atu.sw;

/*
 * This class contains the menu options. 
 * I put these methods in their own class to declutter ApplicationInterface. 
 * I made the methods protected because they are only useful within this package. 
 */

public class MenuOptions {

	// called by start() in the ApplicationInterface Class 
	protected void showMainMenuOptions() { 
		System.out.println(ConsoleColour.CYAN);
		System.out.println("************************************************************");
		System.out.println("*      ATU - Dept. Computer Science & Applied Physics      *");
		System.out.println("*                                                          *");
		System.out.println("*                  N-Gram Frequency Builder                *");
		System.out.println("*                                                          *");
		System.out.println("*                        Main Menu                         *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify Text File Directory");
		System.out.println("(2) Specify n-Gram Size");
		System.out.println("(3) Specify Output File");
		System.out.println("(4) Build n-Grams ");
		System.out.println("(5) Quit");

		// Prompts user to enter an option 
		System.out.print(ConsoleColour.CYAN_BOLD);
		System.out.print("Select Option [1-4]>");
		System.out.println();

		// Console.Colour sets the custom colour of the console text
		System.out.print(ConsoleColour.CYAN); 
	}
	
	// called by getTextFileDir() in the ApplicationInterface Class 
	protected void showSubMenuOneOptions() {
		System.out.print(ConsoleColour.CYAN);
		System.out.println("[INFO] Do you want to read n-Grams from multiple files in a folder or from one file only?");
		System.out.println("(1) Read n-Grams from multiple files in a folder");
		System.out.println("(2) Read n-Grams from one file");
		System.out.print(ConsoleColour.CYAN_BOLD);
		System.out.print("Select Option [1 or 2]>");
		System.out.println();
	}
}
