public class SavingAccount extends Account
{
    private double Interestrate = 0.001;
    public SavingAccount(int theAccountNumber, int thePIN, double theAvaliableBalance, double theTotalBalance)
    {
        super(theAccountNumber, thePIN, theAvaliableBalance, theTotalBalance);
    }
    public double getInterestrate()
    {
        return Interestrate;
    }
    public void setInterestrate(double interestrate)
    {
        this.Interestrate = interestrate;
    }
}

