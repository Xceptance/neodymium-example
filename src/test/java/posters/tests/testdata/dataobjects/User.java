package posters.tests.testdata.dataobjects;

public class User
{
    String firstName;

    String lastName;

    String email;

    String password;

    public User(String firstName, String lastName, String email, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    
    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    @Override
    public String toString()
    {
        return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password + "]";
    }
}
