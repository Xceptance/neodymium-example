package posters.dataobjects;

public class Payments
{
    private CreditCard creditCard;

    private CreditCard newCreditCard;

    public CreditCard getCreditCard()
    {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard)
    {
        this.creditCard = creditCard;
    }

    public CreditCard getNewCreditCard()
    {
        return newCreditCard;
    }

    public void setNewCreditCard(CreditCard newCreditCard)
    {
        this.newCreditCard = newCreditCard;
    }

    @Override
    public String toString()
    {
        return "Payments [creditCard=" + creditCard + ", newCreditCard=" + newCreditCard + "]";
    }

}
