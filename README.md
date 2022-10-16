# NGramBuilder
This project was for a college assignment for a module that introduced the student to Object-Oriented Programming. The module taught Java as the primary language. 

Notes about the assignment. 
  - The student was required to program an n-gram frequency builder.
  - The program should read a group of text files and output a .csv file that lists the n-gram and its frequency.  
  - The program should allow the user to:
      1. Enter a file path to a folder of text files on the user’s local drive. 
      2. Enter an n-gram size from 1 to 5.
      3. Enter the file path and name of the .csv file.
  - The student was not allowed to use Array lists. 
  - The overall goal of the assignment encouraged students to demonstrate the topics covered in the introductory module. 


Workflow
  - Options 1, 2 and 3 collect input data from the user.
  - Option 4 executes the main parts of the application and runs in the following order:
    1. Exception handling validates that the user has input data for the previous options.
    2. The application creates a new Parser object and passes the user’s directory and a Boolean switch for the file type to the Parser constructor.
    3. The Parser class extracts words from the files and puts them in a one-dimensional array using the StringBuilder class and the split() and toString() methods (Allner and Özdemir 2013; Healy 2022).
    5. The application creates a new NGramGenerator object and passes the word array and the user’s n-gram size to the NGramGenerator constructor.
    6. The NGramGenerator class collects each word from the word array, processes the n-grams based on the words, and saves the n-grams to a Object[][]. The Object[][] stores the n-gram, the frequency (count) and the probability of each n-gram.
    7. The Object[][] is passed to the Utils class with the user’s output directory and the document is saved as a .csv file.
  - Option 5 quits the application by resetting the instance variable and closing the do loop that keeps the application running.


Features
  - The user can input a directory of files or a path to a single file.
  - Validation restricts the use of the app, and exception handling provides the user with detailed feedback (Axel 2017; Yo and zx485 2016).
  - The parseDir() method states the names of the files being read, and states the number of words processed (Sharma 2021).
  - parseDir() also excludes the .DS_Store file hidden in Mac OS directories (Mad 2015).
  - The parser() method collects Latin alphabet characters only and excludes non-alphabetic characters, numbers and whitespace (Healy 2022).
  - The NGramGenerator class implements a progress bar and validation prevents the progress bar from running on Windows OS (Healy 2022; VonC 2008).
  - The addGrams() method calculates the probability of the n-grams. This information is saved to the table Object[][] and prints to the .csv file.
  - The save() method prints headings to the .csv file and also excludes null entries from the Object[][] (I did not solve the Hash Code collisions).
  - The user can open the .csv file from the application (How to Open a File in Java n.d).
  - The ConsoleColour class enhances the user feedback.

References 
Allner, U and Özdemir, U. (2013) Java read file and store text in an array, Stack Overflow, 04 Mar, available https://stackoverflow.com/questions/19844649/java-read-file-and-store-text-in-an-array [accessed 20 July 2022].

Axel, H. (2017) Throw java exception and stops, Stack Overflow, 28 Aug, available: https://stackoverflow.com/questions/45916132/throw-java-exception-and-stops [accessed 08 August 2022].

Deitel, H.M. and Deitel P.J. (2002) Java: How to Program, 4th ed, USA: Prentice Hall.

Healy, J. (2022) Assignment Workshop, 51305, Object Orientated Software Development, available: https://galwaymayoinstitute.sharepoint.com/:f:/r/sites/21-2251305-- OBJECTORIENTEDSOFTWAREDEVELOPMENT/Shared%20Documents/General/Recordings/View%20O nly?csf=1&web=1&e=3bIe8d [accessed 04 May 2022].

How to Open a File in Java (n.d) Java T Point, available: https://www.javatpoint.com/how-to-open-a-file-in-java [accessed 29 July 2022].

Mad, C. (2015) Java list files in a folder avoiding .DS_Store, Stack Overflow, 27 May, available: https://stackoverflow.com/questions/30486404/java-list-files-in-a-folder-avoiding-ds-store [accessed 20 July 2022].

Sharma, S. (2021) Java program to merge contents of all the files in a directory, 25 Aug, Geeks for Geeks, available: https://www.geeksforgeeks.org/java-program-to-merge-contents-of-all-the-files- in-a-directory/ [accessed 20 July 2022].

VonC (2008) How do I programmatically determine operating system in Java?, Stack Overflow, 23 Oct, available: https://stackoverflow.com/questions/228477/how-do-i-programmatically-determine- operating-system-in-java [accessed 19 August 2022].

Yo, M and zx485 (2016) try/catch with InputMismatchException creates infinite loop Stack Overflow, 19 Nov, available: https://stackoverflow.com/questions/12702076/try-catch-with- inputmismatchexception-creates-infinite-loop [accessed 08 August 2022].
