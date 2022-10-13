public class Chequeaccount extends Account
{
    private int limitpercheque = 10000;
    public Chequeaccount(int theAccountNumber, int thePIN, double theAvaliableBalance, double theTotalBalance)
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
