package posters.tests.testdata.dataobjects;
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
    
    public String getFullName()
    {
        return fullName;
    }

    public String getCardNumber()
    {
        return cardNumber;
    }

    public String getCrypticCardNumber()
    {
        return crypticCardNumber;
    }

    public String getExpDateMonth()
    {
        return expDateMonth;
    }

    public String getExpDateYear()
    {
        return expDateYear;
    }
    
    public String getExpDate() 
    {
        return expDateMonth + "/" + expDateYear;
    }

    @Override
    public String toString()
    {
        return "CreditCard [fullName=" + fullName + ", cardNumber=" + cardNumber + ", crypticCardNumber=" + crypticCardNumber + ", expDateMonth=" + expDateMonth
               + ", expDateYear=" + expDateYear + "]";
    }
}
