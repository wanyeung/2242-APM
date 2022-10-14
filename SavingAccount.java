// Chequeaccount.java
// Represents a bank saving account

public class SavingAccount extends Account
{
    private double interestRate = 0.001;
    
    public SavingAccount(int theAccountNumber, int thePIN, 
    		double theAvaliableBalance, double theTotalBalance)
    {
        super(theAccountNumber, thePIN, theAvaliableBalance, theTotalBalance);
    }
    
    public double getInterestRate()
    {
        return interestRate;
    }
    
    public void setInterestRate(double interestRate)
    {
        this.interestRate = interestRate;
    }
}

