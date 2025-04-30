package posters.tests.testdata.dataobjects;

public class User
{
    String firstName;

    String lastName;

    String email;

    String emailAdmin;
    String passwordAdmin;


    String password;

    String matriculationNumber;

    public User(String firstName, String lastName, String email, String password, String emailAdmin, String passwordAdmin, String matriculationNumber)
    {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.emailAdmin = emailAdmin;
        this.passwordAdmin = passwordAdmin;
        this.matriculationNumber = matriculationNumber;
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
    
    public String getEmailAdmin()
    {
        return emailAdmin;
    }

    public String getPasswordAdmin()
    {
        return passwordAdmin;
    }
    
    public String getMatriculationNumber()
    {
        return matriculationNumber;
    }

    @Override
    public String toString()
    {
        return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password + ", passwordAdmin=" + passwordAdmin + ", emailAdmin=" + emailAdmin + ", matriculationNumber=" + matriculationNumber + "]";
    }
}
