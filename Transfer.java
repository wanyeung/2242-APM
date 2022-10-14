// Transfer.java
// Represents a transfer ATM transaction

public class Transfer extends Transaction{
	
   private Keypad keypad; // reference to keypad
   private Screen screen; // reference to screen
   private int accountNumber; // indicates Transferor account involved
   private int transfereeAccountNumber; // indicates transferee account involved
   private BankDatabase bankDatabase; // account info database
   private double amount; // amount to deposit
   private final static int CANCELED = 0; // constant for cancel option
   
   // Transfer constructor
   public Transfer( int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad )
   {
      // initialize superclass variables
      super( userAccountNumber, atmScreen, atmBankDatabase );

      accountNumber = userAccountNumber;
      screen = atmScreen;
      bankDatabase = atmBankDatabase;
      keypad = atmKeypad; // initialize references to keypad 
      transfereeAccountNumber = 0; // no current account number to start
      
   } // end Transfer constructor
   
   @Override
   public void execute(){
	
	 //Start inputing transferee account number
       while(bankDatabase.isValidAccount(transfereeAccountNumber) == false || transfereeAccountNumber == 0){
    	   
    	 //Exclude the base case
    	   if(transfereeAccountNumber != 0)
    		   screen.displayMessageLine( "\nThe transferee account you entered does not exist. Please Enter again. \n");
    	   
    	 //Input transferee account number
    	   screen.displayMessage( "\nPlease enter transferee account number" + 
                   "(or 0 to cancel): ");
           transfereeAccountNumber = keypad.getInput();  
           
         //Cancel transfer
           if(transfereeAccountNumber == CANCELED) 
        	   return;
           
         //Invalid Transfer account - transfering to Transferor's account
           if(transfereeAccountNumber == accountNumber) {
        	   screen.displayMessageLine( "\nYou cannot transfer to yourself. Please Enter again. \n");
        	   transfereeAccountNumber = 0;
           }
        	   
       }
       
       //Confirmation
       screen.displayMessage( "\nTransferee account number :" + transfereeAccountNumber);
       if(!isConfirmed()) 
    	   return;
       
           //Input amount to transfer
           while(bankDatabase.getAvailableBalance(transfereeAccountNumber) <= amount || amount <= 0.00) {
        	   screen.displayMessage( "\nPlease enter a transfer amount in " +
                       "HKD (or 0 to cancel): ");
               amount = keypad.getInputDouble();  
               
               //Cancel transfer
               if(amount == CANCELED) 
            	   return;
               
               if(bankDatabase.getAvailableBalance(accountNumber) <= amount && amount > 0.00)
            	   screen.displayMessageLine( "\nYou do not have enough money in your account. Please Enter again. \n");
               
               if(amount <= 0.00)
            	   screen.displayMessageLine( "\nYou should enter positive amount number. Please Enter again. \n");
           }
       

      
       screen.displayMessageLine( "\nTransferor account number: " + accountNumber );
       screen.displayMessageLine( "Transferee account number: " + transfereeAccountNumber );
       screen.displayMessage( "Transfere amount: ");
       screen.displayDollarAmount( amount );
       
       if(!isConfirmed()) 
    	   return;
       
       startTransfer();
   }
   
   public void startTransfer() {
	   bankDatabase.debit(accountNumber, amount);
	   bankDatabase.credit(transfereeAccountNumber, amount);
	   
	   screen.displayMessage("\nYou successfully transfered ");
	   screen.displayDollarAmount(amount);
	   screen.displayMessageLine(" to Account " + transfereeAccountNumber + ".");
	   screen.displayMessage("Your remaining Available balance:");
	   screen.displayDollarAmount( bankDatabase.getAvailableBalance(accountNumber) );
	   screen.displayMessageLine( "" );
   }
   
   public boolean isConfirmed() {
	   int confirmation = 0;
	   screen.displayMessage( "\nPress anything to confirm (or 0 to cancel): " );
	   
	   confirmation = keypad.getInput();
       
       if(confirmation == CANCELED)
    	   return false;
       
       return true;
   }
}
