package posters.dataobjects;

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

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getCompany()
    {
        return company;
    }

    public String getStreet()
    {
        return street;
    }

    public String getCity()
    {
        return city;
    }

    public String getState()
    {
        return state;
    }

    public String getZip()
    {
        return zip;
    }

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
