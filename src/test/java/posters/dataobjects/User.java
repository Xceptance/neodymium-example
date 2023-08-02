package posters.dataobjects;

public class User
{
    String firstName;

    String lastName;

    String email;

    String password;

    /**
     * 
     */
    public User()
    {
    }

    /**
     * @param firstName
     *            the firstName to set
     * @param lastName
     *            the lastName to set
     * @param email
     *            the email to set
     * @param password
     *            the password to set
     */
    public User(String firstName, String lastName, String email, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    @Override
    public String toString()
    {
        return String.format("User [firstName()=%s, lastName()=%s, email()=%s, password()=%s]", getFirstName(), getLastName(), getEmail(), getPassword());
    }
}
