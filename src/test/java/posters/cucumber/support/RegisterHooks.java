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
        Matcher matcher = Pattern.compile("\"([^\"]*)\" and \"([^\"]*)\"").matcher(scenario.getName());
        matcher.find();
        User user = new User("", "", matcher.group(1), matcher.group(2));
        new DeleteUserFlow(user).flow();
    }
}
