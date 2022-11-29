// ChequeAccount.java
// Represents a bank cheque account

public class ChequeAccount extends Account
{
    private int limitPerCheque = 10000;
    
    // Cheque Account constructor initializes attributes
    public ChequeAccount(int theAccountNumber, int thePIN, 
            double theAvaliableBalance, double theTotalBalance)
    {
        super(theAccountNumber, thePIN, theAvaliableBalance, theTotalBalance);
    }
 
    // Cheque Account constructor initializes attributes with limitPerCheque
    public ChequeAccount(int theAccountNumber, int thePIN, 
            double theAvaliableBalance, double theTotalBalance, int limitPerCheque)
    {
        super(theAccountNumber, thePIN, theAvaliableBalance, theTotalBalance);
        this.limitPerCheque = limitPerCheque;
    }
 
    
    // return limit per cheque  for other bank (back office) systems
    public int getlimitPerCheque()
    {
        return limitPerCheque;
    }
    
    // set limit per cheque for other bank (back office) systems
    public void setlimitPerCheque(int limitPerCheque)
    {
        this.limitPerCheque = limitPerCheque;
    }
    
}
