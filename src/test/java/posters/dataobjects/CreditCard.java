package posters.dataobjects;

/**
 * @author pfotenhauer
 */
public class CreditCard
{
    String fullName;

    String cardNumber;

    String crypticCardNumber;

    String expDateMonth;

    String expDateYear;

    /**
     * @param fullName
     *            the fullName to set
     * @param cardNumber
     *            the cardNumber to set
     * @param crypticCardNumber
     *            the crypticCardNumber to set
     * @param expDateMonth
     *            the expDateMonth to set
     * @param expDateYear
     *            the expDateYear to set
     */
    public CreditCard(String fullName, String cardNumber, String crypticCardNumber, String expDateMonth, String expDateYear)
    {
        this.fullName = fullName;
        this.cardNumber = cardNumber;
        this.crypticCardNumber = crypticCardNumber;
        this.expDateMonth = expDateMonth;
        this.expDateYear = expDateYear;
    }

    /**
     * @return the fullName
     */
    public String getFullName()
    {
        return fullName;
    }

    /**
     * @return the cardNumber
     */
    public String getCardNumber()
    {
        return cardNumber;
    }

    /**
     * @return the crypticCardNumber
     */
    public String getCrypticCardNumber()
    {
        return crypticCardNumber;
    }

    /**
     * @return the expDateMonth
     */
    public String getExpDateMonth()
    {
        return expDateMonth;
    }

    /**
     * @return the expDateYear
     */
    public String getExpDateYear()
    {
        return expDateYear;
    }

    @Override
    public String toString()
    {
        return String.format("CreditCard [fullName()=%s, cardNumber()=%s, crypticCardNumber()=%s, expMonth()=%s, expYear()=%s]",
                             getFullName(), getCardNumber(), getCrypticCardNumber(), getExpDateMonth(), getExpDateYear());
    }
}
