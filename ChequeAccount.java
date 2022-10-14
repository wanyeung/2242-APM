// Chequeaccount.java
// Represents a bank cheque account

public class ChequeAccount extends Account
{
    private int limitpercheque = 10000;
    
    public ChequeAccount(int theAccountNumber, int thePIN, 
    		double theAvaliableBalance, double theTotalBalance)
    {
        super(theAccountNumber, thePIN, theAvaliableBalance, theTotalBalance);
    }
    
    public void setChequeaccount(int Limitpercheque)
    {
        this.limitpercheque = Limitpercheque;
    }
    
    public int getChequeaccount()
    {
        return limitpercheque;
    }
}
