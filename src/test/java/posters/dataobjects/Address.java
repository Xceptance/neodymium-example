package posters.dataobjects;

/**
 * @author pfotenhauer
 */
public class Address
{
    String firstName;

    String lastName;

    String company;

    String street;

    String city;

    String state;

    String zip;

    String country;

    /**
     * @param firstName
     *            the firstName to set
     * @param lastName
     *            the lastName to set
     * @param company
     *            the company to set
     * @param street
     *            the street to set
     * @param city
     *            the city to set
     * @param state
     *            the state to set
     * @param zip
     *            the zip to set
     * @param country
     *            the country to set
     */
    public Address(String firstName, String lastName, String company, String street, String city, String state, String zip, String country)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    /**
     * @return the firstName
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * @return the company
     */
    public String getCompany()
    {
        return company;
    }

    /**
     * @return the street
     */
    public String getStreet()
    {
        return street;
    }

    /**
     * @return the city
     */
    public String getCity()
    {
        return city;
    }

    /**
     * @return the state
     */
    public String getState()
    {
        return state;
    }

    /**
     * @return the zip
     */
    public String getZip()
    {
        return zip;
    }

    /**
     * @return the country
     */
    public String getCountry()
    {
        return country;
    }

    @Override
    public String toString()
    {
        return String.format("Address [firtName()=%s, lastName()=%s, company()=%s, street()=%s, zip()=%s, city()=%s, state()=%s, country()=%s]",
                             getFirstName(), getLastName(), getCompany(), getStreet(), getZip(), getCity(), getState(), getCountry());
    }
}
