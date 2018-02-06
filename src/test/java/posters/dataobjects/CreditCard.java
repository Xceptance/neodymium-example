/**
 * 
 */
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
     * @param fullName
     *            the fullName to set
     */
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    /**
     * @return the cardNumber
     */
    public String getCardNumber()
    {
        return cardNumber;
    }

    /**
     * @param cardNumber
     *            the cardNumber to set
     */
    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }

    /**
     * @return the crypticCardNumber
     */
    public String getCrypticCardNumber()
    {
        return crypticCardNumber;
    }

    /**
     * @param crypticCardNumber
     *            the crypticCardNumber to set
     */
    public void setCrypticCardNumber(String crypticCardNumber)
    {
        this.crypticCardNumber = crypticCardNumber;
    }

    /**
     * @return the expDateMonth
     */
    public String getExpDateMonth()
    {
        return expDateMonth;
    }

    /**
     * @param expDateMonth
     *            the expDateMonth to set
     */
    public void setExpDateMonth(String expDateMonth)
    {
        this.expDateMonth = expDateMonth;
    }

    /**
     * @return the expDateYear
     */
    public String getExpDateYear()
    {
        return expDateYear;
    }

    /**
     * @param expDateYear
     *            the expDateYear to set
     */
    public void setExpDateYear(String expDateYear)
    {
        this.expDateYear = expDateYear;
    }

    @Override
    public String toString()
    {
        return String.format("CreditCard [fullName()=%s, cardNumber()=%s, expMonth()=%s, expYear()=%s]",
                             getFullName(), getCardNumber(), getExpDateMonth(), getExpDateYear());
    }
}
