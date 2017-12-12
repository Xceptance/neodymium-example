/**
 * 
 */
package posters.dataObjects;

import java.util.Map;

/**
 * @author pfotenhauer
 */
public class User
{
    String firstName;

    String lastName;

    String eMail;

    String password;

    /**
     * 
     */
    public User(String firstName, String lastName, String eMail, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.password = password;
    }

    public User(Map<String, String> data)
    {
        this.firstName = data.get("firstName");
        this.lastName = data.get("lastName");
        this.eMail = data.get("eMail");
        this.password = data.get("password");
    }

    /**
     * @return the firstName
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * @return the eMail
     */
    public String getEMail()
    {
        return eMail;
    }

    /**
     * @param eMail
     *            the eMail to set
     */
    public void seteMail(String eMail)
    {
        this.eMail = eMail;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
}
