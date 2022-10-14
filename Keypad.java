// Keypad.java
// Represents the keypad of the ATM
import java.util.Scanner; // program uses Scanner to obtain user input

public class Keypad
{
   private Scanner input; // reads data from the command line
   private Screen screen;
                         
   // no-argument constructor initializes the Scanner
   public Keypad()
   {
	   input = new Scanner( System.in );    
	   screen = new Screen();
   } // end no-argument Keypad constructor

  
   // return an integer value entered by user 
   public int getInput()
   {
	   try {
		   return input.nextInt(); // we assume that user enters an integer  
	   }
      catch (Exception e) {
		   screen.displayMessageLine( "\n[Warning] Transaction cancelled due to abnormal behaviours.\n"
				   + "Please do no enter dots or numbers that are too large.");
		   System.exit(0);
		 }
	   
	   return input.nextInt();
   } // end method getInput
   
// return an double value entered by user 
   public double getInputDouble()
   {
	   try {
		   return input.nextDouble();
	   }
      catch (Exception e) {
		   screen.displayMessageLine( "\n[Warning] Transaction cancelled due to abnormal behaviours.\n"
				   + "Please do no enter numbers that are too large, started with dots, or with unnessary dots.");
		   System.exit(0);
		 }
	   
	   return input.nextDouble();
   } // end method getInput
   
} // end class Keypad  



/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/