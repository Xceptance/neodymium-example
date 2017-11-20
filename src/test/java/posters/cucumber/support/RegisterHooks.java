/**
 * 
 */
package posters.cucumber.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import posters.flows.DeleteUserFlow;
import posters.neodymium.dataObjects.User;

/**
 * @author pfotenhauer
 */
public class RegisterHooks
{

    @After("@Register")
    public void afterRegistration(Scenario scenario)
    {
        String name = scenario.getName();
        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(name);
        matcher.find();
        String email = matcher.group(1);
        matcher.find();
        String password = matcher.group(1);
        User user = new User("", "", email, password);
        new DeleteUserFlow(user).flow();
    }
}
