// SavingAccount.java
// Represents a bank saving account

public class SavingAccount extends Account
{
    private double interestRateAnnum = 0.001;
    
    // Saving Account constructor initializes attributes
    public SavingAccount(int theAccountNumber, int thePIN, 
    		double theAvaliableBalance, double theTotalBalance)
    {
        super(theAccountNumber, thePIN, theAvaliableBalance, theTotalBalance);
    }
    
    // Saving Account constructor initializes attributes with interest rate annum
    public SavingAccount(int theAccountNumber, int thePIN, 
    		double theAvaliableBalance, double theTotalBalance, double interestRateAnnum)
    {
        super(theAccountNumber, thePIN, theAvaliableBalance, theTotalBalance);
        this.interestRateAnnum = interestRateAnnum;
    }
    
 	// returns interest rate for other bank (back office) systems
    public double getInterestRateAnnum()
    {
        return interestRateAnnum;
    }

    // set interest rate for other bank (back office) systems
    public void setInterestRateAnnum(double interestRateAnnum)
    {
        this.interestRateAnnum = interestRateAnnum;
    }
}

