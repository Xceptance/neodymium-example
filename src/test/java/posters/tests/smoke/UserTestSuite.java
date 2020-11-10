package posters.tests.smoke;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import posters.tests.unit.LoginTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(
    {
      RegisterFromUserMenuTest.class, RegisterTest.class, LoginTest.class,
    })
public class UserTestSuite
{

}
