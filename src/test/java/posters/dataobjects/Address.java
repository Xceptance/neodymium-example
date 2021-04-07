/**
 * 
 */
package posters.dataobjects;

/**
 * @author pfotenhauer
 */
public class Address
{
    String name;

    String company;

    String street;

    String city;

    String state;

    String zip;

    String country;

    /**
     * @param name
     *            the name to set
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
    public Address(String name, String company, String street, String city, String state, String zip, String country)
    {
        this.name = name;
        this.company = company;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the company
     */
    public String getCompany()
    {
        return company;
    }

    /**
     * @param company
     *            the company to set
     */
    public void setCompany(String company)
    {
        this.company = company;
    }

    /**
     * @return the street
     */
    public String getStreet()
    {
        return street;
    }

    /**
     * @param street
     *            the street to set
     */
    public void setStreet(String street)
    {
        this.street = street;
    }

    /**
     * @return the city
     */
    public String getCity()
    {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState()
    {
        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * @return the zip
     */
    public String getZip()
    {
        return zip;
    }

    /**
     * @param zip
     *            the zip to set
     */
    public void setZip(String zip)
    {
        this.zip = zip;
    }

    /**
     * @return the country
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(String country)
    {
        this.country = country;
    }

    @Override
    public String toString()
    {
        return String.format("Address [name()=%s, company()=%s, street()=%s, zip()=%s, city()=%s, state()=%s, country()=%s]",
                             getName(), getCompany(), getStreet(), getZip(), getCity(), getState(), getCountry());
    }
}
