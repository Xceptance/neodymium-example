/**
 * 
 */
package posters.dataobjects;

/**
 * @author pfotenhauer
 */
public class CreditCard
{
    private String fullName;

    private String cardNumber;

    private String expDateMonth;

    private String expDateYear;

    private boolean savedInAccount;

    public CreditCard(String fullName, String cardNumber, String expDateMonth, String expDateYear)
    {
        this.fullName = fullName;
        this.cardNumber = cardNumber;
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
        return "xxxx xxxx xxxx " + cardNumber.substring(12);
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

    public boolean isSavedInAccount()
    {
        return savedInAccount;
    }

    @Override
    public String toString()
    {
        return "CreditCard [fullName=" + fullName + ", cardNumber=" + cardNumber + ", expDateMonth=" + expDateMonth + ", expDateYear=" + expDateYear
               + ", savedInAccount=" + savedInAccount + "]";
    }

    public void setSavedInAccount(boolean savedInAcount)
    {
        this.savedInAccount = savedInAcount;
    }

}
