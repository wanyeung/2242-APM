// Transfer.java
// Represents a transfer ATM transaction

public class Transfer extends Transaction{
   private double amount; // amount to deposit
   private Keypad keypad; // reference to keypad
   private Screen screen; // reference to screen
   private int transfereeAccountNumber; // reference to deposit slot
   private final static int CANCELED = 0; // constant for cancel option
   
   // Transfer constructor
   public Transfer( int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad )
   {
      // initialize superclass variables
      super( userAccountNumber, atmScreen, atmBankDatabase );

      keypad = atmKeypad; // initialize references to keypad 
      transfereeAccountNumber = 0; // no current account number to start
      
   } // end Transfer constructor
   
   @Override
   public void execute(){
       screen.displayMessageLine( "\nPlease enter transferee account number: " + 
               "(or 0 to cancel):");
       int accountNumber = keypad.getInput();

       while(!isValidAccount(accountNumber)){
           
       }
       
       transfereeAccountNumber = accountNumber;
       screen.displayMessageLine( "\nTransferee account number "
               + "(1 to Confirm, 0 to cancel): " + transfereeAccountNumber );
       
       
       screen.displayMessageLine( "\nPlease enter a transfer amount in " +
               "HKD (or 0 to cancel): ");
       
       try(){
          amount = keypad.getInput(); 
       }
       catch() {
           
       };
       
        
      
       screen.displayMessageLine( "\nTransderor account number: " );
       screen.displayMessageLine( "\nTransferee account number: " );
       screen.displayMessageLine( "\nTransfere amount: " );
       screen.displayDollarAmount( amount );
       screen.displayMessageLine( "\n1 to Confirm, 0 to cancel transfer: " );
   }
   
   public boolean isValidAccount(int AccountNumber){
       return true;
   }
}
