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

    @Override
    public String toString()
    {
        return "ShippingAddress [useForBilling=" + useForBilling + ", fullName=" + fullName + ", company=" + company + ", addressLine=" + addressLine
               + ", city=" + city + ", state=" + state + ", zip=" + zip + ", country=" + country + "]";
    }

}
