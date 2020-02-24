package posters.dataobjects;

public class ShippingAddress extends Address
{
    private boolean useForBilling;

    public ShippingAddress(String fullName, String company, String addressLine, String city, String state, String zip, String country)
    {
        super(fullName, company, addressLine, city, state, zip, country);
    }

    public boolean isUseForBilling()
    {
        return useForBilling;
    }

    public void setUseForBilling(boolean useForBilling)
    {
        this.useForBilling = useForBilling;
    }

    public void setSavedInAccount(boolean savedInAccount)
    {
        super.setSavedInAccount(savedInAccount);
    }

    @Override
    public String toString()
    {
        return "ShippingAddress [useForBilling=" + useForBilling + ", getFullName()=" + getFullName() + ", getCompany()=" + getCompany() + ", getAddressLine()="
               + getAddressLine() + ", getCity()=" + getCity() + ", getState()=" + getState() + ", getZip()=" + getZip() + ", getCountry()=" + getCountry()
               + ", isSavedInAccount()=" + isSavedInAccount() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
               + "]";
    }

}
