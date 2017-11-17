/**
 * 
 */
package posters.cucumber.support;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import posters.flows.DeleteUserFlow;
import posters.neodymium.dataObjects.User;

/**
 * @author pfotenhauer
 */
public class Hooks
{

    @After("@Register")
    public void afterRegistration(Scenario scenario)
    {
        User user = new User("Jane", "Doe", "jane@doe.com", "topsecret");
        new DeleteUserFlow(user).flow();
    }
}
