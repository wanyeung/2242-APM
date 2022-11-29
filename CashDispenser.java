// CashDispenser.java
// Represents the cash dispenser of the ATM

public class CashDispenser 
{
   // the default initial number of banknotes in the cash dispensers
   private final static int INITIAL_COUNT_100 = 100; // by default, there are 100 HKD100 notes
   private final static int INITIAL_COUNT_500 = 20; // by default, there are 20 HKD500 notes
   private final static int INITIAL_COUNT_1000 = 10; // by default, there are 10 HKD1000 notes
   private int count_100,count_500,count_1000; // number of notes remaining
   private int note100Required,note500Required,note1000Required;
   
   // no-argument CashDispenser constructor initializes count to default
   public CashDispenser()
   {
      count_100 = INITIAL_COUNT_100; // set count HKD100 notes attribute to default
      count_500 = INITIAL_COUNT_500;  // set count HKD500 notes attribute to default
      count_1000 = INITIAL_COUNT_1000; // set count HKD1000 notes attribute to default
   } // end CashDispenser constructor

   // simulates dispensing of specified amount of cash
   public void dispenseCash( int amount )
   {
	   count_1000 -= note1000Required; // dispensing HKD 1000 banknotes, update the amount left in bank
	   count_500 -= note500Required; // dispensing HKD 500 banknotes, update the amount left in bank
	   count_100 -= note100Required; // dispensing HKD 100 banknotes, update the amount left in bank
	 
   } // end method dispenseCash

   // indicates whether cash dispenser can dispense desired amount
   public boolean isSufficientCashAvailable( int amount )
   {
	   // the required banknotes needed to dispense the amount
	      int note100Required = 0,note500Required = 0,note1000Required = 0, remain = amount;
	      
	      note1000Required = remain / 1000; // calculate the required HKD 1000 banknotes needed
	      if (note1000Required > count_1000) // if there are not enough notes in ATM
	    	  note1000Required = count_1000; // take the remaining banknotes in the ATM 
	      
	      remain -= note1000Required * 1000; // calculate the amount left after 1000
	      	
	      note500Required = remain / 500; // calcluate the required HKD 500 banknotes needed
	      if (note500Required > count_500) // if there are not enough notes in ATM
	    	  note500Required = count_500;// take the remaining banknotes in the ATM 
	      
	      remain -= note500Required * 500; // calculate the amount left after 500
 
	      note100Required = remain / 100; // calculate the required HKD 100 banknotes needed
	      if (note100Required > count_100) // if there are not enough notes in ATM
	    	  return false; // then there are not enough notes in ATM
	      
	      remain -= note100Required * 100; // calculate the amount left
	      
	      if(remain == 0) // updated the Notes required if there is no remaining amount left for calcuation
	    	  setNotesRequired(note100Required,note500Required,note1000Required);
	      
	      return true;
   }
   
   public int getNote100Required() {
	   return note100Required;
   }
   
   public int getNote500Required() {
	   return note500Required;
   }
   
   public int getNote1000Required() {
	   return note1000Required;
   }
   
   public void setNotesRequired(int note100Required,int note500Required,int note1000Required) {
	   this.note100Required = note100Required;
	   this.note500Required = note500Required;
	   this.note1000Required = note1000Required;
   }

} // end class CashDispenser



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